package Assignment2;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
class Node2 {
    private Lock lock = new ReentrantLock();

    public void processRequest(String request,BlockingQueue<String> requestQueue) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Processing request: " + request);
            System.out.println(requestQueue);
            Thread.sleep(10000); // Simulate processing for 10 seconds
        } finally {
            lock.unlock();
        }
    }
}