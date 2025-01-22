package com.example.trinhnghenhac.utils;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();
    /**
     * Merge the list so that it creates a new list with random order while keeping the correct order of each list.
     */
    @SafeVarargs
    public static <T> List<T> mergeRandom(@NonNull List<T>... lists) {
        List<T> merged = new ArrayList<>();

        // Pointer to each list
        List<Iterator<T>> iters = new ArrayList<>();
        for (List<T> list : lists) {
            if (!list.isEmpty())
                iters.add(list.iterator());
        }
        while (!iters.isEmpty()) {
            // Pick random list
            Iterator<T> iter = getRandomValue(iters);
            if (iter.hasNext()) {
                merged.add(iter.next());
                if (!iter.hasNext()) iters.remove(iter);
            }
        }
        return merged;
    }

    public static int getRandom(int end) {
        return random.nextInt(end);
    }

    public static int getRandom(int start, int end) {
        return start + random.nextInt(end - start);
    }

    public static <T> int getRandomIndex(List<T> list) {
        return random.nextInt(list.size());
    }

    public static <T> T getRandomValue(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
