package com.example.trinhnghenhac.functionalinterfaces;

@FunctionalInterface
public interface Action<T> {
    void run(T t);
}
