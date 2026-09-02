package com.plusOne.clicker.messaging;


import com.plusOne.clicker.domain.AdEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class InMemoryPubSub {

    private final BlockingQueue<AdEvent> queue = new LinkedBlockingQueue<>();

    public void publish(AdEvent event) {
        queue.offer(event);
    }

    public AdEvent consume() throws InterruptedException {
        return queue.take();
    }
}
