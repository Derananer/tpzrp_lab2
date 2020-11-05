package com.lisetckiy.lab.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LightweightChordNode {

    private int id;
    private int successor;
    private int predecessor;
    private List<LightweightFinger> fingers;

}
