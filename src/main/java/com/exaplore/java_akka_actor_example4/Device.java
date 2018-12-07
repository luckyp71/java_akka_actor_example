package com.exaplore.java_akka_actor_example4;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;

import java.util.Optional;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Device extends AbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	final String groupId;
	final String deviceId;
	
	//Constructor
	public Device(String groupId, String deviceId) {
		this.groupId = groupId;
		this.deviceId = deviceId;
	}
	
	public static Props props(String groupId, String deviceId) {
		return Props.create(Device.class, groupId, deviceId);
	}
	
	//RecordTemperature Class
	public static final class RecordTemperature {
		final long requestId;
		final double value;
		
		public RecordTemperature(long requestId, double value) {
			this.requestId = requestId;
			this.value = value;
		}

		public long getRequestId() {
			return requestId;
		}

		public double getValue() {
			return value;
		}
	}
	
	//TemperatureRecorded class
	public static final class TemperatureRecorded {
		final long requestId;
		
		public TemperatureRecorded(long requestId) {
			this.requestId = requestId;
		}

		public long getRequestId() {
			return requestId;
		}
	}
	
	//ReadTemperature Class
	public static final class ReadTemperature {
		long requestId;
		
		public ReadTemperature(long requestId) {
			this.requestId = requestId;
		}

		public long getRequestId() {
			return requestId;
		}

		public void setRequestId(long requestId) {
			this.requestId = requestId;
		}
	}
	
	//RespondTemperature Class
	public static final class RespondTemperature {
		long requestId;
		final Optional<Double> value;
		
		public RespondTemperature(long requestId, Optional<Double> value) {
			this.requestId = requestId;
			this.value = value;
		}

		public long getRequestId() {
			return requestId;
		}

		public void setRequestId(long requestId) {
			this.requestId = requestId;
		}

		public Optional<Double> getValue() {
			return value;
		}
	}
	
	Optional<Double> lastTemperatureReading = Optional.empty();
	
	@Override
	public void preStart() throws Exception {
		log.info("Device actor {}-{} started", groupId, deviceId);
	}

	@Override
	public void postStop() throws Exception {
		log.info("Device actor {}-{} stopped", groupId, deviceId);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder() 
				.match(RecordTemperature.class, r -> {
					log.info("Recorded temperature reading {} with {}", r.value,  r.requestId);
					lastTemperatureReading = Optional.of(r.value);
					getSender().tell(new TemperatureRecorded(r.requestId), getSelf());
				})
				.match(ReadTemperature.class, r -> {
					getSender().tell(new RespondTemperature(r.requestId, lastTemperatureReading), getSelf());
				})
				.build();
	}	
}
