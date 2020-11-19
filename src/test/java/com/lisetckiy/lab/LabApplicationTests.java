package com.lisetckiy.lab;

import com.lisetckiy.lab.model.ChordModel;
import com.lisetckiy.lab.model.LightweightChordNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Map;

import static com.lisetckiy.lab.Utils.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@Slf4j
class LabApplicationTests {

    @Autowired
    private LWChordNodeBuilder lwChordNodeBuilder;

    private static int m_3 = 3;
    private static int m_4 = 4;
    @Test
    void test_pow2() {
        assertEquals("Error", Utils.pow2(2, 0), 1);
        assertEquals("Error", Utils.pow2(2, 1), 2);
        assertEquals("Error", Utils.pow2(2, 2), 4);
        assertEquals("Error", Utils.pow2(2, 3), 8);
    }

    @Test
    void test_in() {
        assertTrue("error: 2 not in (3;0]", number(2).notIn(roundBracket(3), squareBracket(0)));
        assertTrue("error: 3 not in (3;0]", number(3).notIn(roundBracket(3), squareBracket(0)));
        assertTrue("error: 1 not in (3;0]", number(1).notIn(roundBracket(3), squareBracket(0)));

        assertTrue("error: 4 in (3;0]", number(4).in(roundBracket(3), squareBracket(0)));
        assertTrue("error: 5 in (3;0]", number(5).in(roundBracket(3), squareBracket(0)));
        assertTrue("error: 0 in (3;0]", number(0).in(roundBracket(3), squareBracket(0)));
        assertTrue("error: 6 in [6;1)", number(6).in(squareBracket(6), roundBracket(1)));
    }

    @Test
    void testLWBuilder() {
        log.info(lwChordNodeBuilder.test2ValidationData().toString());
    }

    @Test
    void test1() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(0);
        log.info("test1 result=> " + chordModel.getNodes().toString());
    }

    @Test
    void test2_1() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(0, 1, 3, 6);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test2ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }

    @Test
    void test2_1_tigran() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(0, 1, 3, 6);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test2ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }

    @Test
    void test2_2() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(1, 0, 3, 6);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test2ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));

    }

    @Test
    void test2_3() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(6, 1, 3, 0);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test2ValidationData_tigran();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }


    @Test
    void test2_4() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(6, 3, 1, 0);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test2ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }


    @Test
    void test2_5() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(3, 0, 6, 1);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test2ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }


    @Test
    void test_6joins_and_2removes() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init( 3, 0, 6, 1, 2, 7);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test3ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
        chordModel.remove(2);
        chordModel.remove(7);
        chordModel.init(3, 0, 6, 1);
        Map<Integer, LightweightChordNode> validationMap1 = lwChordNodeBuilder.test2ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap1.get(key), value.getLightweightNode()));
    }

    @Test
    void test_6joins_and_5removes_m_3() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(3, 0, 6, 1, 2, 7);
        log.info("init: {}", chordModel);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test3ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
        chordModel.remove(2);
        log.info("remove 2: {}", chordModel);
        chordModel.remove(7);
        log.info("remove 7: {}", chordModel);

        Map<Integer, LightweightChordNode> validationMap1 = lwChordNodeBuilder.test2ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap1.get(key), value.getLightweightNode()));
        chordModel.remove(6);
        log.info("remove 6: {}", chordModel);
        chordModel.remove(3);
        log.info("remove 3: {}", chordModel);
        Map<Integer, LightweightChordNode> validationMap2 = lwChordNodeBuilder.test2_1ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap2.get(key), value.getLightweightNode()));
        chordModel.remove(1);
        log.info("remove 1: {}", chordModel);
        Map<Integer, LightweightChordNode> validationMap3 = lwChordNodeBuilder.test2_2ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap3.get(key), value.getLightweightNode()));
    }

    @Test
    void test_6joins_and_5removes_m_4() {
        ChordModel chordModel = new ChordModel(m_4);
        chordModel.init(14, 8, 5, 1);
        log.info("init: {}", chordModel);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test4ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
        chordModel.remove(8);
        log.info("remove 8: {}", chordModel);
        chordModel.remove(5);
        log.info("remove 5: {}", chordModel);
        Map<Integer, LightweightChordNode> validationMap3 = lwChordNodeBuilder.test4_1ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap3.get(key), value.getLightweightNode()));
    }

    @Test
    void test3_1() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(3, 0, 6, 1, 2, 7);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test3ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }

    @Test
    void test3_2() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(3, 0, 6, 1, 7, 2);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test3ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }

    @Test
    void test3_3() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(2, 3, 0, 7, 6, 1);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test3ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }

    @Test
    void test3_4() {
        ChordModel chordModel = new ChordModel(m_3);
        chordModel.init(3, 7, 0, 6, 2, 1);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test3ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }


    @Test
    void test4_4() {
        ChordModel chordModel = new ChordModel(m_4);
        chordModel.init(1, 5, 8, 14);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test4ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }

    @Test
    void test4_5() {
        ChordModel chordModel = new ChordModel(m_4);
        chordModel.init(14, 8, 5, 1);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test4ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key), value.getLightweightNode()));
    }
}
