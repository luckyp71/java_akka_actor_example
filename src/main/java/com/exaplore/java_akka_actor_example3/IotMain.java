package com.exaplore.java_akka_actor_example3;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import java.io.IOException;

public class IotMain {

	public static void main (String[] args) {
		
		//Create actor system called iot-system
		ActorSystem system = ActorSystem.create("iot-system");
		
		try {
			ActorRef supervisor = system.actorOf(IotSupervisor.props(), "iot-supervisor");
			
			Thread.sleep(50);
			
			System.out.println("Please hit enter to terminate the system");
			System.in.read();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch(InterruptedException ie) {
			ie.printStackTrace();
		} finally {
			System.out.println("The system will be terminated immediately");
			system.terminate();
		}
	}
	
}
