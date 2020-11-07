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


    public Map<Integer,LightweightChordNode> test2_1ValidationData(){
        Map<Integer, LightweightChordNode> map = new HashMap<>();
        map.put(0, buildChordNode(0, 1,1, new int[][]{{1,1},{2,0},{4,0}}));
        map.put(1, buildChordNode(1, 0,0, new int[][]{{2,0},{3,0},{5,0}}));
        return map;
    }

    public Map<Integer,LightweightChordNode> test2_2ValidationData(){
        Map<Integer, LightweightChordNode> map = new HashMap<>();
        map.put(0, buildChordNode(0, 0,0, new int[][]{{1,0},{2,0},{4,0}}));
        return map;
    }

    public Map<Integer,LightweightChordNode> test3ValidationData(){
        Map<Integer, LightweightChordNode> map = new HashMap<>();
        map.put(0, buildChordNode(0, 1,7, new int[][]{{1,1},{2,2},{4,6}}));
        map.put(1, buildChordNode(1, 2,0, new int[][]{{2,2},{3,3},{5,6}}));
        map.put(3, buildChordNode(3, 6,2, new int[][]{{4,6},{5,6},{7,7}}));
        map.put(6, buildChordNode(6, 7,3, new int[][]{{7,7},{0,0},{2,2}}));
        map.put(7, buildChordNode(7, 0,6, new int[][]{{0,0},{1,1},{3,3}}));
        map.put(2, buildChordNode(2, 3,1, new int[][]{{3,3},{4,6},{6,6}}));
        return map;
    }

    public Map<Integer,LightweightChordNode> test4ValidationData(){
        Map<Integer, LightweightChordNode> map = new HashMap<>();
        map.put(1, buildChordNode(1, 5,14, new int[][]{{2,5},{3,5},{5,5},{9,14}}));
        map.put(5, buildChordNode(5, 8,1, new int[][]{{6,8},{7,8},{9,14},{13, 14}}));
        map.put(8, buildChordNode(8, 14,5, new int[][]{{9,14},{10,14},{12,14},{0,1}}));
        map.put(14, buildChordNode(14, 1,8, new int[][]{{15,1},{0,1},{2,5},{6,8}}));
        return map;
    }

    public Map<Integer,LightweightChordNode> test4_1ValidationData(){
        Map<Integer, LightweightChordNode> map = new HashMap<>();
        map.put(1, buildChordNode(1, 14,14, new int[][]{{2,14},{3,14},{5,14},{9,14}}));
        map.put(14, buildChordNode(14, 1,1, new int[][]{{15,1},{0,1},{2,14},{6,14}}));
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
