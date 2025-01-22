package com.example.trinhnghenhac.utils;

import java.util.ListIterator;

import javax.annotation.Nullable;

public class ListIteratorUtils {
    public static int FIRST = 0;
    public static int LAST = Integer.MAX_VALUE;
    @Nullable
    public static <T> T getCurrent(ListIterator<T> listIterator) {
        if (listIterator.hasPrevious()) {
            T value = listIterator.previous();
            listIterator.next();
            return value;
        }
        return null;
    }

    public static <T> void goTo(ListIterator<T> listIterator, int index) {
        while (listIterator.nextIndex() < index && listIterator.hasNext()) listIterator.next();
        while (listIterator.previousIndex() >= index && listIterator.hasPrevious()) listIterator.previous();
    }

    @Nullable
    public static <T> T peekNext(ListIterator<T> listIterator) {
        if (listIterator.hasNext()) {
            T value = listIterator.next();
            listIterator.previous();
            return value;
        }
        return null;
    }
}
