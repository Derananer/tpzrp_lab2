package com.lisetckiy.lab.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static com.lisetckiy.lab.Utils.pow2;

@Slf4j
public class FingerTableItem {

    @Getter
    private int start;

    @Getter
    private ChordNode node;

    @ToString.Exclude
    @Getter
    private ChordNode newNode;

    public void setNode(ChordNode node) {
//        log.info("set node[{}]", node.getId());
        this.newNode = node;
    }

    public void applyChanges(){
        this.node = this.newNode;
    }

    public FingerTableItem(FingerTableItem fingerTableItem)
    {
        this.start = fingerTableItem.getStart();
        this.node = fingerTableItem.getNode();
    }

    public FingerTableItem(int n, int i, int m) {
        this.start = initStart(n, i , m);
    }

    private int initStart(int n, int i, int m) {
        return (n + pow2(2, (i - 1))) % pow2(2, m);
    }

//    private int[] interval(int n, int i, int m) {
//        return new int[]{start(n, i, m), start(n, i + 1, m)};
//    }

    @Override
    public String toString() {
        return "{start=" + start + ", nodeId=" + node.getId() + "}";
    }
}
