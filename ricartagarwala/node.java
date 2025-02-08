package ricartagarwala;

import java.time.LocalTime;
import java.util.ArrayList;

public class node {
	String name;
	int count;
	ArrayList<node> permission = new ArrayList<node>();
	ArrayList<node> holder = new ArrayList<node>();
	boolean hastoken=false;
	LocalTime tm;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ArrayList<node> getPermission() {
		return permission;
	}
	public void setPermission(node p) {
		this.permission.add(p);
	}
	public ArrayList<node> getholder() {
		return permission;
	}
	public void setholder(node p) {
		this.holder.add(p);
	}
	
	
}
