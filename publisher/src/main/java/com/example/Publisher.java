package com.example;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zeromq.ZMQ;

import java.time.LocalDateTime;

@SpringBootApplication
public class Publisher implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(Publisher.class, args);

    }

    @Override
    public void run(String... strings) throws Exception {

        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);

        publisher.bind("tcp://*:5555");

        while (!Thread.currentThread ().isInterrupted ()) {

            publisher.send (LocalDateTime.now().toString());

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        publisher.close ();
        context.term ();

    }
}