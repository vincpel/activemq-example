package com.vincpel.activemq_example_p2p;




import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.ActiveMQSession;

public class Consumer{
	public static void main (String[] args)throws Exception
	{
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		ActiveMQConnection connection = 
				(ActiveMQConnection) factory.createConnection();
		
		connection.start();

		ActiveMQSession session = 
				(ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Queue queue = session.createQueue("test_queue");

		ActiveMQMessageConsumer consumer = 
				(ActiveMQMessageConsumer) session.createConsumer(queue);
		
		int messages = 0;
		final int MESSAGES_TO_CONSUME=10;
		do
		{
			TextMessage message = (TextMessage) consumer.receive();
			consumer.receive();
			
			messages++;
			System.out.println ("Message #" + messages + ": " + message.getText());
			consumer.acknowledge();
		} while (messages < MESSAGES_TO_CONSUME);

		connection.stop();

		System.exit(0);
	}
}
