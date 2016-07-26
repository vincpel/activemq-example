package com.vincpel.subscriber_model;

import javax.jms.*;

import javax.naming.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.BufferedReader;

import java.io.InputStreamReader;

public class PublisherSubscriberModel implements javax.jms.MessageListener {
	private TopicSession pubSession;
	private TopicPublisher publisher;
	private TopicConnection connection;

	public PublisherSubscriberModel(String topicName, String username,
			String password) throws Exception {
		
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory
				("tcp://localhost:61616");
		
		
		
		
		connection = factory.createTopicConnection(username, password);
		pubSession = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
		TopicSession subSession = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
		Topic chatTopic = subSession.createTopic(topicName);

		publisher = pubSession.createPublisher(chatTopic);

		TopicSubscriber subscriber = subSession.createSubscriber(chatTopic);

		subscriber.setMessageListener(this);

		connection.start();
		TextMessage message = pubSession.createTextMessage();
		message.setText(username + ": Howdy Friends!");

		publisher.publish(message);

	}

	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		String text;
		try {
			text = textMessage.getText();
			System.out.println(text);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block

	}

	public static void main(String[] args) {
		BasicConfigurator.configure();
		try {
			if (args.length != 3)
				System.out.println("Please Provide the topic name,username,password!");
			PublisherSubscriberModel demo = new PublisherSubscriberModel(args[0], args[1], args[2]);
			BufferedReader commandLine = new java.io.BufferedReader(
					new InputStreamReader(System.in));
			while (true) {
				String s = commandLine.readLine();
				if (s.equalsIgnoreCase("exit")) {
					demo.connection.close();
					System.exit(0);
				}else {
					demo.publisher.publish(demo.pubSession.createTextMessage(s));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}