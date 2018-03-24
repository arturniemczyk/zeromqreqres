package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zeromq.ZMQ;
import java.time.LocalDateTime;

@SpringBootApplication
public class Responder implements CommandLineRunner
{

    public static void main(String[] args) {
        SpringApplication.run(Responder.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket responder = context.socket(ZMQ.REP);
        responder.bind("tcp://*:5555");

        while (!Thread.currentThread().isInterrupted()) {

            byte[] request = responder.recv(0);
            System.out.println("Request " + new String (request) + " received at " + LocalDateTime.now());

            String reply = new String (request);
            System.out.println("Reply " + reply + " sent at " + LocalDateTime.now());
            responder.send(reply.getBytes(), 0);
        }
        responder.close();
        context.term();

    }
}
