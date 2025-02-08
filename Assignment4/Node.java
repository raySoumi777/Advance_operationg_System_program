package Assignment4;

import java.util.ArrayList;

public class Node {

	String node_name;
	ArrayList<Node> requestQueue = new ArrayList<Node>() ;
	Node Parent;
	boolean hasToken=false;
	boolean requestForCs=false;
	public boolean isRequestForCs() {
		return requestForCs;
	}
	public void setRequestForCs(boolean requestForCs) {
		this.requestForCs = requestForCs;
	}
	public String getNode_name() {
		return node_name;
	}
	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}
	public Node getRequestQueue() {
		return requestQueue.get(0);
	}
	public void setRequestQueue(Node rq) {
		this.requestQueue.add(rq);
	}
	public Node getParent() {
		return Parent;
	}
	public boolean isHasToken() {
		return hasToken;
	}
	public void setHasToken(boolean hasToken) {
		this.hasToken = hasToken;
	}
	public void setParent(Node parent) {
		Parent = parent;
	}
	public boolean hasRequest(Node child) {
		boolean flag=false;
		if(this.requestQueue!=null) {
			for(Node i : this.requestQueue) {
				if(i==child) {
					flag=true;
					break;
				}
			}
		}
		return flag;
	}
	
}