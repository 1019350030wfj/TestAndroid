package com.jayden.officialandroiddemo.concurrent;

public interface AbsStorage {
    void produce(int num);
    void consume(int num);
}
