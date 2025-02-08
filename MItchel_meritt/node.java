package MItchel_meritt;

public class node {
	private int public_key;
	private int private_key;
	private node blocked_by;
	private node bolcking;
	private String name;
	public int getPublic_key() {
		return public_key;
	}
	public void setPublic_key(int public_key) {
		this.public_key = public_key;
	}
	public int getPrivate_key() {
		return private_key;
	}
	public void setPrivate_key(int private_key) {
		this.private_key = private_key;
	}
	public node getBlocked_by() {
		return blocked_by;
	}
	public void setBlocked_by(node blocked_by) {
		this.blocked_by = blocked_by;
	}
	public node getBolcking() {
		return bolcking;
	}
	public void setBolcking(node bolcking) {
		this.bolcking = bolcking;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
