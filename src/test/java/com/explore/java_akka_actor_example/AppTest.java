package com.explore.java_akka_actor_example;

import org.junit.Test;

import com.exaplore.java_akka_actor_example4.Device;

//import akka.testkit.TestKit;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import akka.actor.ActorRef;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

public class AppTest {
	
	@Test
	public void testReplyWithEmptyReadingIfNoTemperatureIsKnown() {
		ActorSystem system = ActorSystem.create("device-actor");
		TestKit probe = new TestKit(system);
		ActorRef deviceActor = system.actorOf(Device.props("group", "device"));
		deviceActor.tell(new Device.ReadTemperature(42L), probe.getRef());
		Device.RespondTemperature response = probe.expectMsgClass(Device.RespondTemperature.class);
		
		long expectedValue = 42L;
		
		assertEquals(expectedValue, response.getRequestId());
		assertEquals(Optional.empty(), response.getValue());
	}


	@Test
	public void testReplyWithLatestTemperatureReading() {
		ActorSystem system = ActorSystem.create("device-group");
		TestKit probe = new TestKit(system);
		ActorRef deviceActor = system.actorOf(Device.props("group", "device"));
		
		deviceActor.tell(new Device.RecordTemperature(1L, 24.0), probe.getRef());
		assertEquals(1L, probe.expectMsgClass(Device.TemperatureRecorded.class).getRequestId());
		
		deviceActor.tell(new Device.ReadTemperature(2L), probe.getRef());
		Device.RespondTemperature response1 = probe.expectMsgClass(Device.RespondTemperature.class);
		assertEquals(2L, response1.getRequestId());
		assertEquals(Optional.of(24.0), response1.getValue());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
