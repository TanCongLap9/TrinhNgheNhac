package com.example.trinhnghenhac.utils;

import androidx.annotation.IntDef;

public class AssertUtils {
    @IntDef({EQ, NE, LT, GT, LE, GE})
    public @interface Operator {}
    public static final int EQ = 0;
    public static final int NE = 1;
    public static final int LT = 2;
    public static final int GT = 3;
    public static final int LE = 4;
    public static final int GE = 5;

    public static void assertThat(Object object1, @Operator int operator, Object object2) {
        switch (operator) {
            case EQ:
                if (object1 == object2) return;
                throw new AssertionError("object1 != object2");
            case NE:
                if (object1 != object2) return;
                throw new AssertionError("object1 == object2");
            default:
                throw new AssertionError("Invalid operator on objects");
        }
    }

    public static void assertNotNull(Object object) {
        if (object != null) return;
        throw new AssertionError("object is null");
    }

    public static void assertThat(int i1, @Operator int operator, int i2) {
        switch (operator) {
            case EQ:
                if (i1 == i2) return;
                throw new AssertionError(i1 + "!=" + i2);
            case NE:
                if (i1 != i2) return;
                throw new AssertionError(i1 + "==" + i2);
            case LT:
                if (i1 < i2) return;
                throw new AssertionError(i1 + ">=" + i2);
            case LE:
                if (i1 <= i2) return;
                throw new AssertionError(i1 + ">" + i2);
            case GT:
                if (i1 > i2) return;
                throw new AssertionError(i1 + "<=" + i2);
            case GE:
                if (i1 >= i2) return;
                throw new AssertionError(i1 + "<" + i2);
            default:
                throw new AssertionError("Invalid operator on objects");
        }
    }
}
