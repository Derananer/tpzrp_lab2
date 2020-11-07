package com.lisetckiy.lab.model;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ToString
public class ChordModel {

    private int m;

    @Getter
    private Map<Integer, ChordNode> nodes;

    public ChordModel(int m) {
        this.m = m;
    }

    void applyChanges() {
        nodes.forEach((key, value) -> value.applyChanges());
    }

    public void init(int... ids) {
        nodes = new HashMap<>();
//        ChordNode initialNode = new ChordNode(ids[0], m);
//        initialNode.join(null);
//        nodes.put(initialNode.getId(), initialNode);
//        applyChanges();
//        log.info("===== node joined with id[{}] ====", initialNode.getId());
        add(ids[0]);
//        log.info("all nodes: {}", nodes);
        for (int i = 1; i < ids.length; i++) {
            add(ids[i]);
//            log.info("===== node joined with id[{}] ====", initialNode.getId());
//            log.info("all nodes: {}", nodes);
        }
    }

    public void add(int id) {
        ChordNode initialNode = new ChordNode(id, m);
        initialNode.join(getArbitraryNode());
        nodes.put(initialNode.getId(), initialNode);
        applyChanges();
    }

    public void remove(int id) {
        ChordNode arbitraryNode = getArbitraryNode();
        if (arbitraryNode != null) {
            ChordNode successor = arbitraryNode.findSuccessor(id);
            if(successor.getId() == id)
            {
                successor.remove();
            }
        }
        nodes.remove(id);
        applyChanges();
    }

    private ChordNode getArbitraryNode() {
        if (!nodes.isEmpty()) {
            return nodes.values().iterator().next();
        }
        return null;
    }

    public ChordNode getNode(int nodeNumber) {
        return nodes.get(nodeNumber);
    }
}
