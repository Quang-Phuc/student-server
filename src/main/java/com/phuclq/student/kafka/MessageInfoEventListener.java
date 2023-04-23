package com.phuclq.student.kafka;

public interface MessageInfoEventListener {
	void onData(MessageInfoEvent event);
    void processComplete();
}
