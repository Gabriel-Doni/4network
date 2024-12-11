package com.teste._network.utils;

import java.util.List;

public class Return {
    public record MessageWithArray<E>(String message, List<E> list) {
    }

    public record MessageWithToken(String message, String token) {
    }

    public record Message(String message) {
    }
}