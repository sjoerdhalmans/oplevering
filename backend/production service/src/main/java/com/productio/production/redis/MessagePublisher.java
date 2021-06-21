package com.productio.production.redis;

public interface MessagePublisher {
    void publish(String message);
}