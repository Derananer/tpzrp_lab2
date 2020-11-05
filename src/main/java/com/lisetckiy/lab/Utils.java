package com.lisetckiy.lab;

public class Utils {

    public static class CustomNumber {
        int value;

        public CustomNumber(int value) {
            this.value = value;
        }

        public boolean in(Bracket leftBorder, Bracket rightBorder) {
            if (leftBorder.getValue() > rightBorder.getValue())
                return leftBorder.less(value) || rightBorder.greater(value);
            else if (leftBorder.getValue() == rightBorder.getValue()){
                return leftBorder.isRoundType() && rightBorder.isSquareType() || leftBorder.isSquareType() && rightBorder.isRoundType();
//                return true;
//                return false;
            }
            else return (leftBorder.less(value) && rightBorder.greater(value));
        }

        public boolean notIn(Bracket leftBorder, Bracket rightBorder) {
            return !in(leftBorder, rightBorder);
        }

        public int mod(int module) {
            return (value + module) % module;
        }
    }

    public interface Bracket {
        boolean less(int value);

        boolean greater(int value);

        int getValue();

        boolean isRoundType();

        boolean isSquareType();
    }

    public static class SquareBracket implements Bracket {

        private int number;

        private SquareBracket(int value) {
            this.number = value;
        }

        @Override
        public boolean less(int value) {
            return number <= value;
        }

        @Override
        public boolean greater(int value) {
            return number >= value;
        }

        @Override
        public int getValue() {
            return number;
        }

        @Override
        public boolean isRoundType() {
            return false;
        }

        @Override
        public boolean isSquareType() {
            return true;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Bracket) {
                return obj instanceof RoundBracket && ((RoundBracket) obj).number == this.number ||
                        obj instanceof SquareBracket && ((SquareBracket) obj).number == this.number;
            }
            return false;
        }
    }

    public static class RoundBracket implements Bracket {

        private int number;

        private RoundBracket(int value) {
            this.number = value;
        }

        @Override
        public boolean less(int value) {
            return number < value;
        }

        @Override
        public boolean greater(int value) {
            return number > value;
        }

        @Override
        public int getValue() {
            return number;
        }

        @Override
        public boolean isRoundType() {
            return true;
        }

        @Override
        public boolean isSquareType() {
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Bracket) {
                return obj instanceof RoundBracket && ((RoundBracket) obj).number == this.number ||
                        obj instanceof SquareBracket && ((SquareBracket) obj).number == this.number;
            }
            return false;
        }
    }

    public static SquareBracket squareBracket(int value) {
        return new SquareBracket(value);
    }

    public static RoundBracket roundBracket(int value) {
        return new RoundBracket(value);
    }

    public static CustomNumber number(int value) {
        return new CustomNumber(value);
    }

    public static int pow2(int value, int degree) {
        int tmp = value;
        if (degree == 0) return 1;
        for (int i = 0; i < degree - 1; i++) {
            value *= tmp;
        }
        return value;
    }
}