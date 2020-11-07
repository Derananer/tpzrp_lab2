package com.lisetckiy.lab.model;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.lisetckiy.lab.Utils.*;

@Slf4j
public class ChordNode {
    private final int m;

    @Getter
    private final int id;

    private final FingerTable fingers;

    private ChordNode predecessor;

    @ToString.Exclude
    private ChordNode newPredecessor;

    public void applyChanges() {
        this.predecessor = this.newPredecessor;
        fingers.applyChanges();
    }

    public void setPredecessor(ChordNode predecessor) {
        this.newPredecessor = predecessor;
    }

    public ChordNode(int id, int m) {
        this.m = m;
        this.fingers = new FingerTable(id, m);
        this.id = id;
    }


    public ChordNode getSuccessor() {
        return this.fingers.get(1).getNode();
    }

    public ChordNode getNewSuccessor() {
        return this.fingers.get(1).getNewNode();
    }

    public ChordNode findSuccessor(int id) {
        return findPredecessor(id).getSuccessor();
    }

    public ChordNode findPredecessor(int id_1) {
        ChordNode node = this;
        if (this.id == id_1) return this.predecessor;
        while (number(id_1).notIn(roundBracket(node.id), squareBracket(node.getSuccessor().id))) {
            node = node.closestPrecedingFinger(id_1);
        }
        return node;
    }

    public void remove() {
        predecessor.fingers.get(1).setNode(getSuccessor());
        getSuccessor().setPredecessor(predecessor);
        ChordNode p = predecessor;
//        predecessor.updateAfterRemove(this.id, getSuccessor());
        while(p.id != this.id){
            p.updateAfterRemove(this.id, getSuccessor());
            p = p.predecessor;
        }
    }

    private void updateAfterRemove(int removingId, ChordNode successor) {
        for (int i = 1; i <= m; i++) {
            if (fingers.get(i).getNode().id == removingId) {
                fingers.get(i).setNode(successor);
            }
        }
    }

    public ChordNode findPredecessorInternal(int id_1) {
        ChordNode node = this;
//        log.info("node: " + node.getId() +  " id: " + id_1);
        if (node.id == id_1) return this;
        if (node.getSuccessor().id == id_1) return node.getSuccessor();
//        log.info("search predecessor 1: true: {} not in ({};{}])", id_1, node.id, node.getSuccessor().id);
        while (number(id_1).notIn(roundBracket(node.id), squareBracket(node.getSuccessor().id))) {
//            log.info("search predecessor 1: true: {} not in ({};{}])", id_1, node.id, node.getSuccessor().id);
            node = node.closestPrecedingFingerInternal(id_1);
            if (node.id == id_1) return node;
            if (node.getSuccessor().id == id_1) return node.getSuccessor();
        }
        return node;
    }

    public ChordNode closestPrecedingFingerInternal(int id) {
        for (int i = m; i >= 1; i--) {
//            log.info("true: {} in ({};{})", fingers.get(i).getNode().id, this.id, id);
            if (number(fingers.get(i).getNode().id).in(roundBracket(this.id), roundBracket(id))) {
                return fingers.get(i).getNode();
            }
        }
        return this;
    }

    public ChordNode closestPrecedingFinger(int id) {
        for (int i = m; i >= 1; i--) {
//            log.info("true: {} in ({};{})", fingers.get(i).getNode().id, this.id, id);
            if (number(fingers.get(i).getNode().id).in(roundBracket(this.id), roundBracket(id))) {
                return fingers.get(i).getNode();
            }
        }
        return this;
    }


    public void join(ChordNode existingNode) {
        if (existingNode != null) {
            initFingerTable(existingNode);
            updateOthers(existingNode);
        } else {
            for (int i = 1; i <= m; i++) {
//                log.info("call 1 set node for node[{}] item[{}]", this.getId(), i);
                fingers.get(i).setNode(this);
                setPredecessor(this);
            }
        }
    }

    private void initFingerTable(ChordNode node) {
        fingers.get(1).setNode(node.findSuccessor(fingers.get(1).getStart()));
        setPredecessor(getNewSuccessor().predecessor);
        getNewSuccessor().setPredecessor(this);
        for (int i = 1; i <= m - 1; i++) {
            if (number(fingers.get(i + 1).getStart()).in(squareBracket(this.id), roundBracket(fingers.get(i).getNewNode().id))) {
                fingers.get(i + 1).setNode(fingers.get(i).getNewNode());
            } else {
                fingers.get(i + 1).setNode(node.findSuccessor(fingers.get(i + 1).getStart()));
            }
        }
    }


    private void updateOthers(ChordNode node) {
        ChordNode p;
        for (int i = 1; i <= m; i++) {
            p = node.findPredecessorInternal(number(id - pow2(2, i - 1)).mod(pow2(2, m)));
//            log.info("UpdateOthers predecessor p[{}] found for request {}findPredecessor({})", p.getId(),node.id,  number(id - pow2(2, i - 1)).mod(pow2(2, m)));
            p.updateFingerTables(this, i, p);
        }
    }

    private void updateFingerTables(ChordNode s, int i, ChordNode p) {
//        log.info("UFT: if [{}] in [{};{})", s.id, fingers.get(i).getStart(), fingers.get(i).getNewNode().id);
        if (number(s.id).in(squareBracket(fingers.get(i).getStart()), roundBracket(fingers.get(i).getNewNode().id)) && fingers.get(i).getStart() != fingers.get(i).getNewNode().id) {
            fingers.get(i).setNode(s);
            if (!checkThatOnlyOneNodeInNet(p))
                predecessor.updateFingerTables(s, i, p);
        }
    }


    private boolean checkThatOnlyOneNodeInNet(ChordNode node) {
        return node.getSuccessor().getId() == node.id && node.predecessor.id == node.id;
    }

    public LightweightChordNode getLightweightNode() {
        List<LightweightFinger> lightweightFingers = new ArrayList<>();
        this.fingers.stream()
                .map(item -> new LightweightFinger(item.getStart(), item.getNode().getId()))
                .forEach(lightweightFingers::add);
        return new LightweightChordNode(getId(), getSuccessor().id, predecessor.id, lightweightFingers);
    }


    @Override
    public String toString() {
        return "{id=" + id + ", successor=" + getSuccessor().getId() + ", predecessor=" + predecessor.getId() + ", " + fingers.toString() + "}";

    }
}