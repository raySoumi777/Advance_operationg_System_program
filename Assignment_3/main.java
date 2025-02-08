package Assignment_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class main {
	static String current_token_holder;//variable for token holder
	static ArrayList<String> req_queue = new ArrayList<>();//queue data structure for request list
	//Method for processing request through several hop
	public static void sendRequest(String nd,ArrayList<String> rq,CircularQueue nodeList) {
		String temp_node=nd;
		while(true) {
			temp_node=nodeList.check(temp_node);//method to search for neighbouring node
			if(temp_node.equals(rq.get(0))) {
				grantRequest(rq.get(0));// finally granting request for the next requesting node
				break;
			}else {
				System.out.println(temp_node+" got message and passed");//passing request from node to node
			}
		}
	}
	private static void grantRequest(String cs_node) {
		// TODO Auto-generated method stub
		current_token_holder=cs_node;//token delivered to the next node
		System.out.println("Token delivered to "+cs_node);
		 if(req_queue.size()>1) {
			 System.out.println("in queue-->"+req_queue.get(1));//queue description after token delivery
		 }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Necessary variable initialisation
		 int no_of_node=0;
		 CircularQueue nodeList = null;//circular queue DAta structure for token ring
		 char[] cnnt;
		 //reading data from file input
		 try {
			 FileReader fileReader = new FileReader("test22.txt");
			 BufferedReader bufferedReader = new BufferedReader(fileReader);
			 no_of_node= Integer.parseInt(bufferedReader.readLine());//reading total no of nodes
			 System.out.println("no of nodes:"+no_of_node);
			 nodeList=new CircularQueue(no_of_node);
			 cnnt = bufferedReader.readLine().toCharArray();
			 
			 for(int i=0;i<no_of_node;i++) {
				 nodeList.enQueue(Character.toString(cnnt[i]));//reading order of nodes
			 }
			 cnnt=null;
			 cnnt = bufferedReader.readLine().toCharArray();
			 for(int i=0;i<cnnt.length;i++) {
				 req_queue.add(Character.toString(cnnt[i]));//reading order of node request
			 }
			 current_token_holder=bufferedReader.readLine();//reading first token holder
		 }catch (IOException e) {
	            e.printStackTrace();
	        }
		 
		 System.out.println("Currently token holder is : "+current_token_holder);
		 System.out.println(req_queue.get(0)+"&"+req_queue.get(1)+" sending request for token ");
		 System.out.println("in queue-->"+req_queue.get(0)+","+req_queue.get(1));
		 //sending requset for the first time
		 sendRequest(current_token_holder,req_queue,nodeList);
		 if(current_token_holder.equalsIgnoreCase(req_queue.get(0))) {
			 req_queue.remove(0);
		 }
		 //a loop for generating continous request generation for access token
		 while(! req_queue.isEmpty()) {
			 System.out.println("Currently token holder is : "+current_token_holder);//current token holder
			if(req_queue.size()>1) {
				 System.out.println(req_queue.get(1)+" sending request for token ");//generating new request
				 System.out.println("in queue-->"+req_queue.get(0)+","+req_queue.get(1));//current queue description
			}
			 
			 //Accessing token reqest from requesting node to current token holder
			 sendRequest(current_token_holder,req_queue,nodeList);
			 if(current_token_holder.equalsIgnoreCase(req_queue.get(0))) {
				 req_queue.remove(0);//remove current token holder from request queue
			 }

		 }
	}

}