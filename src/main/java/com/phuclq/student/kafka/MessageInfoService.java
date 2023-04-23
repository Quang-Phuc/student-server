package com.phuclq.student.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class MessageInfoService{
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private KafkaTemplate<String, MessageInfoEvent> kafkaTemplate;

	public ListenableFuture<SendResult<String, MessageInfoEvent>> sendMessage(String topic, MessageInfoEvent message) {
		logger.info(String.format("#### -> Producing message -> %s", message));
		return this.kafkaTemplate.send(topic, message);
	}

//	@Scheduled(fixedDelay = 5000)
//	public void getWeatherInfoJob() throws IOException {
//		logger.info("generate fake weather event");
//		// fake event
//		MessageInfoEvent event = new MessageInfoEvent(1001, "xin chao");
//		sendMessage("test", event);
//	}
}
