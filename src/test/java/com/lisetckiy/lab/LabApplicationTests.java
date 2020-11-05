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
    private ChordModel chordModel;

    @Autowired
    private LWChordNodeBuilder lwChordNodeBuilder;

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
        chordModel.test(3, 0);
        log.info("test1 result=> " + chordModel.getNodes().toString());
    }

    @Test
    void test2_1() {
        chordModel.test(3, 0, 1, 3, 6);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test2ValidationData();
        chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key),  value.getLightweightNode()));
    }

    @Test
    void test2_2() {
        chordModel.test(3, 1, 0, 3, 6);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test2ValidationData();
		chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key),  value.getLightweightNode()));

	}

    @Test
    void test2_3() {
        chordModel.test(3, 6, 1, 3, 0);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test2ValidationData();
		chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key),  value.getLightweightNode()));
    }


    @Test
    void test2_4() {
        chordModel.test(3, 6, 3, 1, 0);
        Map<Integer, LightweightChordNode> validationMap = lwChordNodeBuilder.test2ValidationData();
		chordModel.getNodes().forEach((key, value) -> assertEquals("node with id[" + key + "]", validationMap.get(key),  value.getLightweightNode()));
    }

}
