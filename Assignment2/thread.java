package Assignment2;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class thread implements Runnable {
    private BlockingQueue<String> requestQueue;
    private Node2 sharedResource;

    public thread(BlockingQueue<String> requestQueue, Node2 sharedResource) {
        this.requestQueue = requestQueue;
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        while (true) {
            try {
            	System.out.println("requested by "+this);
                String request = requestQueue.take();
                sharedResource.processRequest(request,requestQueue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


	
		
		

	

