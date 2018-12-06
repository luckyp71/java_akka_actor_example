package com.exaplore.java_akka_actor_example2;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.Props;
import java.io.IOException;

public class CoreApplication {

	public static void main (String[] args) {
		
		ActorSystem system = ActorSystem.create("test2");
		
		ActorRef first = system.actorOf(Props.create(StartStopActor1.class), "first");
		first.tell("stop", ActorRef.noSender());
		
		try {
			Thread.sleep(50);
			System.out.println("Please hit enter to terminate");
			System.in.read();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("The system will shutdown immediately");
			system.terminate();
		}

	}
}