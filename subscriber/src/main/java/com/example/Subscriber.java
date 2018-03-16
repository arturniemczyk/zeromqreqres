package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zeromq.ZMQ;
import java.time.LocalDateTime;

@SpringBootApplication
public class Subscriber implements CommandLineRunner
{

    public static void main(String[] args) {
        SpringApplication.run(Subscriber.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);

        subscriber.connect("tcp://sender:5555");
        subscriber.subscribe("".getBytes());

        while (!Thread.currentThread ().isInterrupted ()) {
            String res = subscriber.recvStr ();
            System.out.println( "Sending at " + res + " received at " + LocalDateTime.now().toString());
        }

        subscriber.close ();
        context.term ();
    }
}
