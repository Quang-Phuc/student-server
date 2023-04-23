package com.phuclq.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phuclq.student.kafka.MessageInfoEvent;
import com.phuclq.student.kafka.MessageInfoEventListener;
import com.phuclq.student.kafka.MessageInfoEventProcessor;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class MessageInfoController {
	@Autowired
	private MessageInfoEventProcessor processor;

	private Flux<MessageInfoEvent> bridge;

	public MessageInfoController() {
		// (3) Broadcast to several subscribers
		this.bridge = createBridge().publish().autoConnect().cache(10).log();
	}

	// (1) Spring MVC annotation
	@GetMapping(value = "/notify", produces = "text/event-stream;charset=UTF-8")
	public Flux<MessageInfoEvent> getWeatherInfo() {
		return bridge;
	}

	private Flux<MessageInfoEvent> createBridge() {
		Flux<MessageInfoEvent> bridge = Flux.create(sink -> { // (2)
			processor.register(new MessageInfoEventListener() {

				@Override
				public void processComplete() {
					sink.complete();
				}

				@Override
				public void onData(MessageInfoEvent data) {
					sink.next(data);
				}
			});
		});
		return bridge;
	}
}
