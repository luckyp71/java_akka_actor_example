package com.exaplore.java_akka_actor_example1;

import java.io.IOException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorHierarchyExperiments {
	
	public static void main(String[] args) {
		
		ActorSystem system = ActorSystem.create("test");
		
		ActorRef firstRef = system.actorOf(Props.create(PrintMyActorRefActor.class), "first-actor");
		
		System.out.println("First: "+firstRef);
		
		firstRef.tell("printit", ActorRef.noSender());
	
		try {
			Thread.sleep(50);
			System.in.read();
			System.out.println("Shuting down the system");
			system.terminate();
			
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("The system will shutdown immediately");
		}
	}
}
