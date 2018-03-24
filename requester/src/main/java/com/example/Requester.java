package com.example;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zeromq.ZMQ;

import java.time.LocalDateTime;

@SpringBootApplication
public class Requester implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(Requester.class, args);

    }

    @Override
    public void run(String... strings) throws Exception {

        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket requester = context.socket(ZMQ.REQ);
        requester.connect("tcp://responder:5555");

        int requestNr = 1;

        while (!Thread.currentThread ().isInterrupted ()) {

            System.out.println("----------------");
            String request = "Echo" + requestNr;

            System.out.println( request + " sent at " + LocalDateTime.now());
            requester.send(request.getBytes(), 0);

            byte[] reply = requester.recv(0);
            System.out.println("Response " + new String(reply) + " received at " + LocalDateTime.now());
            System.out.println("----------------");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requestNr++;
        }

        requester.close();
        context.term();

    }
}