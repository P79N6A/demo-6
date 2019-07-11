package com.example.demo.utils;

import java.util.Collection;

public abstract class AbstractSender implements Sender {
    @Override
    public void send(Collection<Measurement> measures) {
        for (Measurement m : measures) {
            send(m);
        }
    }
}