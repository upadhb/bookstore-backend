package com.pluralsight.bookstore.util;

import java.util.Random;

public class IsbnGenerator implements NumberGenerator {
    @Override
    public String generateNumber() {
        System.out.println("IsbnGenerator");
        return "13-" + Math.abs(new Random().nextInt());

    }
}
