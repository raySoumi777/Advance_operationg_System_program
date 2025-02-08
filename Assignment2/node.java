package Assignment2;

import java.time.LocalTime;
import java.util.ArrayList;

public class node  {
	int nodeid;
	String nodeName;
	LocalTime time;
	ArrayList<node> waiting=new ArrayList<node>();
	ArrayList<node> req_hold=new ArrayList<node>();
	boolean inCs;
	Thread obj=new Thread();
	public int getnodeId() {
		return nodeid;
	}
	public void setId(int id) {
		this.nodeid = id;
	}
	public String getnodeName() {
		return nodeName;
	}
	public void setnodeName(String name) {
		nodeName = name;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public ArrayList<node> getWaiting() {
		return waiting;
	}
	public void setWaiting(node nd) {
		this.waiting.add(nd);
	}
	public boolean isInCs() {
		return inCs;
	}
	public void setInCs(boolean inCs) {
		this.inCs = inCs;
	}
	public ArrayList<node> getReq_hold() {
		return req_hold;
	}
	public void setReq_hold(node req_hold) {
		this.req_hold.add(req_hold);
	}
	public void run() {
		this.setInCs(true);
		int total = 45;
		synchronized (this)
        {
 
            // iterating over using the for loo
            for (int i = 0; i < 10; i++) {
            	if(total==i) {
            		break;
            }
 
            // Waking up the thread in waiting state
            // using notify() method
            
        }
            notify();
            System.out.println("done");
            this.setInCs(false);
    }
		
	}
	
	

}
