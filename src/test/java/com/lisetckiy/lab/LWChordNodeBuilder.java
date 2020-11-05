package com.lisetckiy.lab;

import com.lisetckiy.lab.model.LightweightChordNode;
import com.lisetckiy.lab.model.LightweightFinger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LWChordNodeBuilder {

    public Map<Integer,LightweightChordNode> test2ValidationData(){
        Map<Integer, LightweightChordNode> map = new HashMap<>();
        map.put(0, buildChordNode(0, 1,6, new int[][]{{1,1},{2,3},{4,6}}));
        map.put(1, buildChordNode(1, 3,0, new int[][]{{2,3},{3,3},{5,6}}));
        map.put(3, buildChordNode(3, 6,1, new int[][]{{4,6},{5,6},{7,0}}));
        map.put(6, buildChordNode(6, 0,3, new int[][]{{7,0},{0,0},{2,3}}));
        return map;
    }

    private LightweightChordNode buildChordNode(int id, int suc, int pred, int [][] fingers)
    {
        List<LightweightFinger> lightweightFingers = new ArrayList<>();
        for (int[] startAndNode : fingers) {
            lightweightFingers.add(new LightweightFinger(startAndNode[0],startAndNode[1]));
        }
        return new LightweightChordNode(id, suc, pred, lightweightFingers);
    }
}
