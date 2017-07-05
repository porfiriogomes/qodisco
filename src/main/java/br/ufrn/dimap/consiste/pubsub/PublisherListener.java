package br.ufrn.dimap.consiste.pubsub;

import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;

public class PublisherListener extends AbstractInterceptHandler{

	@Override
	public void onPublish(InterceptPublishMessage msg) {
		System.out.println("Publishing message on " + msg.getTopicName() + " at: " + System.currentTimeMillis());
	}

}
