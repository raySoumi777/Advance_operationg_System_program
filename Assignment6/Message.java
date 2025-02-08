package Assignment6;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Message {
	 private final ConcurrentHashMap<String, ConcurrentLinkedQueue<MessageContent>> messageQueues = new ConcurrentHashMap<>();

	    public void send(String recipient, MessageContent message) {
	        messageQueues.computeIfAbsent(recipient, k -> new ConcurrentLinkedQueue<>()).offer(message);
	        System.out.println("Message sent: " + message.type + " from " + message.sender + " to " + recipient);
	    }

	    public MessageContent receive(String recipient) {
	        ConcurrentLinkedQueue<MessageContent> queue = messageQueues.get(recipient);
	        MessageContent message = (queue != null) ? queue.poll() : null;
	        if (message != null) {
	            System.out.println("Message received by " + recipient + ": " + message.type + " from " + message.sender);
	        }
	        return message;
	    }

    public static class MessageContent {
        public final MessageType type;
        public final String sender;
        public final int key;

        public MessageContent(MessageType type, String sender, int key) {
            this.type = type;
            this.sender = sender;
            this.key = key;
        }
    }

    public enum MessageType {
        REQUEST, WAIT, TRANSMIT
    }
}