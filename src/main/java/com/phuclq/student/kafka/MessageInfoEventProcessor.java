package com.phuclq.student.kafka;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageInfoEventProcessor {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private MessageInfoEventListener listener;

	public void register(MessageInfoEventListener listener) {
		this.listener = listener;
	}

	public void onEvent(MessageInfoEvent event) {
		if (listener != null) {
			listener.onData(event);
		}
	}

	public void onComplete() {
		if (listener != null) {
			listener.processComplete();
		}
	}

	@KafkaListener(topics = "test", groupId = "group_id")
	public void consume(MessageInfoEvent message) throws IOException {
		logger.info(String.format("#### -> Consumed message -> %s", message));
		onEvent(message);
	}
}
