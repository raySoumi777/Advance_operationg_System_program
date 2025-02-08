package Assignment1;

import java.util.ArrayList;

public class node {
	String val;
	ArrayList<String> reachability=new ArrayList<String>();
	int count=0;
	String prev_node="null";
	
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public ArrayList<String> getReachability() {
		return reachability;
	}
	public void setReachability(String x) {
		this.reachability.add(x);
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPrev_node() {
		return prev_node;
	}
	public void setPrev_node(String prev_node) {
		this.prev_node = prev_node;
	}
	
	
}

