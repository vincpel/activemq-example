
package com.vincpel.activemq_example_p2p;

import javax.jms.*;
import org.apache.activemq.*;


public class Producer
{
	public static void main (String[] args) throws Exception
	{
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory
				("tcp://localhost:61616");

		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Queue queue = session.createQueue("test_queue");

		MessageProducer producer = session.createProducer(queue);

		TextMessage message = session.createTextMessage ("Hello, activeMQ!");

		producer.send(message);

		connection.stop();

		System.exit(0);
	}
}

