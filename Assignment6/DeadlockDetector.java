package Assignment6;
import java.util.ArrayList;
import java.util.List;

public class DeadlockDetector {
    private final List<Node> nodes;
    private final Message messageSystem;

    public DeadlockDetector(int nodeCount) {
        this.nodes = new ArrayList<>(nodeCount);
        this.messageSystem = new Message();
        String[] nodeIds = new String[nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            nodeIds[i] = String.valueOf(i);
        }

        for (int i = 0; i < nodeCount; i++) {
            nodes.add(new Node(nodeIds[i], i + 1, messageSystem, nodeIds));
        }
    }

    public void start() {
        System.out.println("Starting deadlock detection simulation");
        for (Node node : nodes) {
            node.start();
        }

        int maxIterations = 100; // Adjust as needed
        int iterations = 0;

        while (!Thread.interrupted() && iterations < maxIterations) {
            System.out.println("\nChecking for deadlock...");
            if (detectDeadlock()) {
                System.out.println("Deadlock detected!");
                break;
            } else {
                System.out.println("No deadlock detected.");
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            iterations++;
        }

        // Rest of the method remains the same
    }

    private boolean detectDeadlock() {
        for (Node node : nodes) {
            if (node.getPublicKey() == node.getPrivateKey()) {
                System.out.println("Potential deadlock initiator: Node " + node.getId());
                for (Node otherNode : nodes) {
                    if (otherNode.getBlocker() != null && 
                        otherNode.getBlocker().equals(node.getId()) && 
                        otherNode.getPublicKey() == node.getPublicKey()) {
                        System.out.println("Deadlock confirmed: Node " + otherNode.getId() + 
                                           " is blocked by Node " + node.getId() + 
                                           " with matching public key " + node.getPublicKey());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void printNodeStatus() {
        System.out.println("\nCurrent Node Status:");
        System.out.println("Node\tPublic Key\tPrivate Key\tBlocker");
        System.out.println("----\t----------\t-----------\t-------");
        for (Node node : nodes) {
            System.out.printf("%s\t%d\t\t%d\t\t%s%n", 
                node.getId(), node.getPublicKey(), node.getPrivateKey(), 
                node.getBlocker() != null ? node.getBlocker() : "None");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        DeadlockDetector detector = new DeadlockDetector(5);
        System.out.println("Initial node status:");
        detector.printNodeStatus();
        detector.start();
        System.out.println("Final node status:");
        detector.printNodeStatus();
    }
}