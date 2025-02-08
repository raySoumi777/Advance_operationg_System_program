package Assignment2;

import java.time.LocalTime;

public class Request {

	public int id;
	public LocalTime timestamp;
	public Request(int id, LocalTime timestamp) {
		super();
		this.id = id;
		this.timestamp = timestamp;
	}

}
