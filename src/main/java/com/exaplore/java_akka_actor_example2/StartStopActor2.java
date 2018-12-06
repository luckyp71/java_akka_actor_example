package com.exaplore.java_akka_actor_example2;

import akka.actor.AbstractActor;

public class StartStopActor2 extends AbstractActor {
	
	@Override
	public void preStart() throws Exception {
		System.out.println("second started");
	}
	

	@Override
	public void postStop() throws Exception {
		System.out.println("second stopped");
	}

	
	//Actor empty Behavior is a useful placeholder when we don't want to handle any messages in the actor
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.build();
	}	
}