package Assignment6;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Node extends Thread {
    private final String nodeid;
    private final AtomicInteger publicKey;
    private final AtomicInteger privateKey;
    private volatile String blocker;
    private final Message messageSystem;
    private final String[] resourceHolders;
    private final Random random = new Random();

    public Node(String nodeid, int initialKey, Message messageSystem, String[] resourceHolders) {
        this.nodeid = nodeid;
        this.publicKey = new AtomicInteger(initialKey);
        this.privateKey = new AtomicInteger(initialKey);
        this.messageSystem = messageSystem;
        this.resourceHolders = resourceHolders;
    }

    private void releaseResource() {
        if (blocker != null) {
            System.out.println("Node " + nodeid + " releasing resource from " + blocker);
            blocker = null;
        }
    }

    @Override
    public void run() {
        System.out.println("Node " + nodeid + " started");
        while (!Thread.interrupted()) {
            processMessages();
            if (random.nextInt(100) > 25) {
                requestResource();
            }
            if (random.nextInt(100) < 10) { // 10% chance to release
                releaseResource();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Node " + nodeid + " stopped");
    }

    private void processMessages() {
        Message.MessageContent message = messageSystem.receive(nodeid);
        if (message != null) {
            System.out.println("Node " + nodeid + " received message: " + message.type + " from " + message.sender);
            switch (message.type) {
                case REQUEST:
                    handleRequest(message);
                    break;
                case WAIT:
                    handleWait(message);
                    break;
                case TRANSMIT:
                    handleTransmit(message);
                    break;
            }
        }
    }

    private void handleRequest(Message.MessageContent message) {
        if (blocker == null) {
            blocker = message.sender;
            System.out.println("Node " + nodeid + " is now blocked by " + blocker);
            messageSystem.send(message.sender, new Message.MessageContent(Message.MessageType.WAIT, nodeid, publicKey.get()));
        } else {
            System.out.println("Node " + nodeid + " forwarding request from " + message.sender + " to " + blocker);
            messageSystem.send(blocker, message);
        }
    }

    private void handleWait(Message.MessageContent message) {
        int newKey = Math.max(publicKey.get(), message.key) + 1;
        publicKey.set(newKey);
        privateKey.set(newKey);
        System.out.println("Node " + nodeid + " updated keys to " + newKey);
        if (blocker != null) {
            System.out.println("Node " + nodeid + " transmitting new key to " + blocker);
            messageSystem.send(blocker, new Message.MessageContent(Message.MessageType.TRANSMIT, nodeid, newKey));
        }
    }

    private void handleTransmit(Message.MessageContent message) {
        publicKey.set(message.key);
        System.out.println("Node " + nodeid + " updated public key to " + message.key);
    }

    private void requestResource() {
        String resource = resourceHolders[random.nextInt(resourceHolders.length)];
        if (!resource.equals(nodeid)) {
            if (blocker != null && random.nextInt(100) < 20) { // 20% chance to release
                System.out.println("Node " + nodeid + " releasing resource from " + blocker);
                blocker = null;
            } else {
                System.out.println("Node " + nodeid + " requesting resource from " + resource);
                messageSystem.send(resource, new Message.MessageContent(Message.MessageType.REQUEST, nodeid, publicKey.get()));
            }
        }
    }

    public String getnodenodeid() {
        return this.nodeid;
    }

    public int getPublicKey() {
        return publicKey.get();
    }

    public int getPrivateKey() {
        return privateKey.get();
    }

    public String getBlocker() {
        return blocker;
    }
}