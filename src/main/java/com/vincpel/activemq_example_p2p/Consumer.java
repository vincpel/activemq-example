package com.vincpel.activemq_example_p2p;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer{
	public static void main (String[] args)throws Exception
	{
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		Connection connection = factory.createConnection();
		
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Queue queue = session.createQueue("test_queue");

		MessageConsumer consumer = session.createConsumer(queue);

		int messages = 0;
		final int MESSAGES_TO_CONSUME=10;
		do
		{
			TextMessage message = (TextMessage)consumer.receive();
			messages++;
			System.out.println ("Message #" + messages + ": " + message.getText());
		} while (messages < MESSAGES_TO_CONSUME);

		connection.stop();

		System.exit(0);
	}
}
