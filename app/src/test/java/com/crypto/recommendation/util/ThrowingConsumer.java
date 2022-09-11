package com.crypto.recommendation.util;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {
    void doAccept(T t) throws Exception;
    default void accept(T t) {
        try {
            doAccept(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
