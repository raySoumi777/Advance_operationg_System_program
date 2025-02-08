package tokenring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class main {
	static ArrayList<Integer> queu=new ArrayList<Integer>();
	static CircularQueue nodel;
	static char[] cnnt;
	static String current_initiator;
	static int no_of_nodes=0,msg=0,j=0;
	public static void display() {
		System.out.print("Current queue status --> ");
		for(int x : queu) {
			System.out.print(cnnt[x]+ " ,");
		}

		System.out.println();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		nodel=null;
		
		try {
			FileReader fileReader = new FileReader("testn2.txt");
			BufferedReader bufferReader = new BufferedReader(fileReader);
			no_of_nodes=Integer.parseInt(bufferReader.readLine());
			nodel=new CircularQueue(no_of_nodes);
			cnnt=bufferReader.readLine().toCharArray();
			for(int i=0;i<no_of_nodes;i++) {
				nodel.enQueue(Character.toString(cnnt[i]));
			}
			msg=Integer.parseInt(bufferReader.readLine());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		int c=new Random().nextInt(no_of_nodes);
		int totalmsg=msg;
		while(j<totalmsg) {
			current_initiator = nodel.queue.get(c);
			System.out.println(cnnt[c]+" is current holder of token and enters into critical section");
			j++;
			int loop=new Random().nextInt(0,5);
			for(int i=0;i<loop;i++) {
				int temp =new Random().nextInt(no_of_nodes);
				if(temp==c) {
					i--;
				}else if(msg>0 && !queu.contains(temp)){
					msg-=1;
					System.out.println(cnnt[temp]+" requests for token");
					request(temp,temp);
				}
			}
			display();
			System.out.println(current_initiator+" is out critical section");
			if(queu.isEmpty()) {
				c=new Random().nextInt(no_of_nodes);
			}else c=queu.remove(0);
			System.out.println(cnnt[c]+" got the token ");
			display();
		}
		
		
	}
	private static void request(int temp,int req) {
		// TODO Auto-generated method stub
		String nxt=nodel.check(Character.toString(cnnt[temp]));
		if(current_initiator.equals(nxt)) {
			for(int i=0;i<no_of_nodes;i++) {
				if(Character.toString(cnnt[i]).equals(nxt)) {
					queu.add(req);
					System.out.println(cnnt[i]+" got the request and "+cnnt[req]+" is added in queue");
					break;
				}
			}
		}else {
			for(int i=0;i<no_of_nodes;i++) {
				if(Character.toString(cnnt[i]).equals(nxt)) {
					System.out.println("node "+cnnt[i]+" got the request and pass it");
					request(i,req);
					break;
				}
			}
		}
	}

}
