package com.lisetckiy.lab.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ChordModel {

    @Getter
    private Map<Integer, ChordNode> nodes;


    void applyChanges(){
        nodes.forEach((key, value) -> value.applyChanges());
    }

    public void test(int m, int... ids) {
        nodes = new HashMap<>();
        ChordNode initialNode = new ChordNode(ids[0], m);
        initialNode.join(null);
        nodes.put(initialNode.getId(), initialNode);
        applyChanges();
        log.info("===== node joined with id[{}] ====", initialNode.getId());
        log.info("all nodes: {}", nodes);
        for (int i = 1; i < ids.length; i++) {
            ChordNode previous = initialNode;
            initialNode = new ChordNode(ids[i], m);
            initialNode.join(previous);
            nodes.put(initialNode.getId(), initialNode);
            applyChanges();
            log.info("===== node joined with id[{}] ====", initialNode.getId());
            log.info("all nodes: {}", nodes);
        }
    }

    public ChordNode getNode(int nodeNumber) {
        return nodes.get(nodeNumber);
    }
}
