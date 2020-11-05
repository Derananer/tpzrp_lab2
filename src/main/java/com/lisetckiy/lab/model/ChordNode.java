package com.lisetckiy.lab.model;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lisetckiy.lab.Utils.*;

@Slf4j
public class ChordNode {
    private int m;

    @Getter
    private int id;

    private FingerTable fingers;

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

    public ChordNode(ChordNode node) {
        this.m = node.m;
        this.fingers = new FingerTable(node.fingers);
        this.id = node.id;
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
//        log.info("node: " + node.toString());
//        log.info("search predecessor 0: {} not in ({};{}])", id, node.id, node.getSuccessor().id);
//        if (node.id == node.getSuccessor().id) return this;
        while (number(id_1).notIn(roundBracket(node.id), squareBracket(node.getSuccessor().id))) {
//            log.info("search predecessor 1: true: {} not in ({};{}])", id_1, node.id, node.getSuccessor().id);
            node = node.closestPrecedingFinger(id_1);
//            log.info("node [{}]", node.id);
//            try {
//                new Thread().sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        return node;
    }

    public ChordNode findPredecessorInternal(int id_1) {
        ChordNode node = this;


        log.info("node: " + node.getId() +  " id: " + id_1);
        if (node.id == id_1) return this;
        if(node.getSuccessor().id == id_1) return node.getSuccessor();

//        log.info("search predecessor 0: {} not in ({};{}])", id, node.id, node.getSuccessor().id);
//        if (node.id == node.getSuccessor().id) return this;
        log.info("search predecessor 1: true: {} not in ({};{}])", id_1, node.id, node.getSuccessor().id);
        while (number(id_1).notIn(roundBracket(node.id), squareBracket(node.getSuccessor().id))) {
            log.info("search predecessor 1: true: {} not in ({};{}])", id_1, node.id, node.getSuccessor().id);
            node = node.closestPrecedingFingerInternal(id_1);
            if(node.id == id_1) return node;
            if(node.getSuccessor().id == id_1) return node.getSuccessor();
//            log.info("node [{}]", node.id);
//            try {
//                new Thread().sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        return node;
    }
    public ChordNode closestPrecedingFingerInternal(int id) {
        for (int i = m; i >= 1; i--) {
            log.info("true: {} in ({};{})", fingers.get(i).getNode().id, this.id, id);
            if (number(fingers.get(i).getNode().id).in(roundBracket(this.id), roundBracket(id))) {
                return fingers.get(i).getNode();
            }
        }
        return this;
    }

    public ChordNode closestPrecedingFinger(int id) {
        for (int i = m; i >= 1; i--) {
            log.info("true: {} in ({};{})", fingers.get(i).getNode().id, this.id, id);
            if (number(fingers.get(i).getNode().id).in(roundBracket(this.id), roundBracket(id))) {
                return fingers.get(i).getNode();
            }
        }
        return this;
    }


    public void join(ChordNode existingNode) {
        if (existingNode != null) {
            initFingerTable(existingNode);
//                predecessor.fingers.get(1).setNode(this);
//            if (checkThatOnlyOneNodeInNet(existingNode))
//                newPredecessor.fingers.get(1).setNode(this);
//            else
            updateOthers(existingNode);
        } else {
            for (int i = 1; i <= m; i++) {
                log.info("call 1 set node for node[{}] item[{}]", this.getId(), i);
                fingers.get(i).setNode(this);
                setPredecessor(this);
            }
        }
    }

    private void initFingerTable(ChordNode node) {
//        log.info("call 2 set node for node[{}] item[{}]", this.getId(), 1);
        fingers.get(1).setNode(node.findSuccessor(fingers.get(1).getStart()));
//        log.info("successor[{}] setted for node with id=[{}]", getSuccessor().getId(), getId());
        setPredecessor(getNewSuccessor().predecessor);
//        predecessor.fingers.get(1).setNode(this);
        getNewSuccessor().setPredecessor(this);
        for (int i = 1; i <= m - 1; i++) {
            if (number(fingers.get(i + 1).getStart()).in(squareBracket(this.id), roundBracket(fingers.get(i).getNewNode().id))) {
//                log.info("call 3 set node for node[{}] item[{}]", this.getId(), i + 1);
                fingers.get(i + 1).setNode(fingers.get(i).getNewNode());
            } else {
//                log.info("call 4 set node for node[{}] item[{}]", this.getId(), i + 1);
                fingers.get(i + 1).setNode(node.findSuccessor(fingers.get(i + 1).getStart()));
            }
        }
    }


    private void updateOthers(ChordNode node) {
        ChordNode p;
        for (int i = 1; i <= m; i++) {
            p = node.findPredecessorInternal(number(id - pow2(2, i - 1)).mod(pow2(2, m)));
//            p = findPredecessor(this.id);
            log.info("UpdateOthers predecessor p[{}] found for request {}findPredecessor({})", p.getId(),node.id,  number(id - pow2(2, i - 1)).mod(pow2(2, m)));
            p.updateFingerTables(this, i, p);
        }
    }

    private void updateFingerTables(ChordNode s, int i, ChordNode p) {
        log.info("UFT: if [{}] in [{};{})", s.id, fingers.get(i).getStart(), fingers.get(i).getNewNode().id);
        if (number(s.id).in(squareBracket(fingers.get(i).getStart()), roundBracket(fingers.get(i).getNewNode().id) )&& fingers.get(i).getStart() != fingers.get(i).getNewNode().id ) {
            fingers.get(i).setNode(s);
//            if (this.id != p.id)
//            try {
//                new Thread().sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if (!checkThatOnlyOneNodeInNet(p))
                predecessor.updateFingerTables(s, i, p);
//            log.info("call 5 set node for node[{}] item[{}]", this.getId(), i);
//            if (number(s.id).in(squareBracket(fingers.get(i).getStart()), roundBracket(fingers.get(i).getNode().id))) {
//            }
        }
//        if (this.predecessor.id != p.id)
//            predecessor.updateFingerTables(s, i, p);
    }


    //    public void join(ChordNode existingNode) {
//        if (existingNode != null) {
//            if (!checkThatOnlyOneNodeInNet(existingNode)) {
//                initFingerTable(existingNode);
////                updateOthers();
//            } else {
//                initFingerTable(existingNode);
////                predecessor.fingers.get(1).setNode(this);
////                updateOthers();
//            }
//
//        } else {
//            for (int i = 1; i <= m; i++) {
//                log.info("call 1 set node for node[{}] item[{}]", this.getId(), i);
//                fingers.get(i).setNode(this);
//                predecessor = this;
//            }
//        }
//    }
//
    private boolean checkThatOnlyOneNodeInNet(ChordNode node) {
        return node.getSuccessor().getId() == node.id && node.predecessor.id == node.id;
    }
//
//    private void initFingerTable(ChordNode node) {
//        log.info("call 2 set node for node[{}] item[{}]", this.getId(), 1);
//        fingers.get(1).setNode(node.findSuccessor(fingers.get(1).getStart()));
//        log.info("successor[{}] setted for node with id=[{}]", getSuccessor().getId(), getId());
//        predecessor = getSuccessor().predecessor;
////        predecessor.fingers.get(1).setNode(this);
//        getSuccessor().predecessor = this;
//        for (int i = 1; i <= m - 1; i++) {
//            if (number(fingers.get(i + 1).getStart()).in(squareBracket(this.id), roundBracket(fingers.get(i).getNode().id))) {
//                log.info("call 3 set node for node[{}] item[{}]", this.getId(), i + 1);
//                fingers.get(i + 1).setNode(fingers.get(i).getNode());
//            } else {
//                log.info("call 4 set node for node[{}] item[{}]", this.getId(), i + 1);
//                fingers.get(i + 1).setNode(node.findSuccessor(fingers.get(i + 1).getStart()));
//            }
//        }
//        updateOthers();
//    }
//
//    private void updateOthers() {
//        ChordNode p;
//        for (int i = 1; i <= m; i++) {
//            p = findPredecessor(number(id - pow2(2, i - 1)).mod(pow2(2, m)));
////            p = findPredecessor(this.id);
//            log.info("UpdateOthers predecessor p[{}] found for request findPredecessor({})", p.getId(), number(id - pow2(2, i - 1)).mod(pow2(2, m)));
//            p.updateFingerTables(this, i, p);
//        }
//    }
//
//    private void updateFingerTables(ChordNode s, int i, ChordNode p) {
//        log.info("UFT: if [{}] in [{};{})", s.id, id, fingers.get(i).getNode().id);
//        if (number(s.id).in(squareBracket(id), roundBracket(fingers.get(i).getNode().id))) {
//            fingers.get(i).setNode(s);
//            predecessor.updateFingerTables(s, i, p);
////            log.info("call 5 set node for node[{}] item[{}]", this.getId(), i);
////            if (number(s.id).in(squareBracket(fingers.get(i).getStart()), roundBracket(fingers.get(i).getNode().id))) {
////            }
//        }
////        if (this.predecessor.id != p.id)
////            predecessor.updateFingerTables(s, i, p);
//    }


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
