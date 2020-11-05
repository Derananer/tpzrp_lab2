package com.lisetckiy.lab.model.old;

import com.lisetckiy.lab.model.ChordModel;
import com.lisetckiy.lab.model.ChordNode;
import com.lisetckiy.lab.model.FingerTable;
import lombok.Getter;

public class ChordNode1 {

    private int m;

    @Getter
    private int id;

    private FingerTable fingers;

    private ChordNode1 predecessor;

    public ChordNode1(ChordNode1 node) {
        this.m = node.m;
        this.fingers = new FingerTable(node.fingers);
        this.id = node.id;
    }

    public ChordNode1(int id, int m) {
        this.m = m;
        this.fingers = new FingerTable(id, m);
        this.id = id;
    }


//    public void join(ChordModel existingNode){
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
////                log.info("call 1 set node for node[{}] item[{}]", this.getId(), i);
//                fingers.get(i).setNode(this);
//                predecessor = this;
//            }
//        }
//    }

}
