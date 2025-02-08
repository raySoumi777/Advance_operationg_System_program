package Assignment4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
	static int flag=0;
	//recieve confirmation of request by Token holder
	public static void receiveTokenRequest(Node child) {
		if (child.getParent().isHasToken()) {
			child.getParent().setRequestQueue(child);
			System.out.println("Token request by "+child.getNode_name()+" is accepted by "+child.getParent().getNode_name());
		} else {
			forwardTokenRequest(child);
		}
	}
	//forwarding request to the token holder
	private static void forwardTokenRequest(Node child) {
		if ((child.getParent() != null) && (!child.getParent().hasRequest(child))) {
			child.getParent().setRequestQueue(child);
			System.out.println("Token request by "+child.getNode_name()+" is forwarded to "+child.getParent().getNode_name());
			receiveTokenRequest(child.getParent());
		}
	}
	//upon completetion of critical section access release of token
	public static void releaseToken(Node holder,char[] arr,ArrayList<Node> node_list, String token_holder,Node prev) {
		if (!holder.isRequestForCs()) {//if the node is just forwarded request
			while (!holder.requestQueue.isEmpty() ) {//will continue until all request of it's queue is not completed 
				if(prev!=null && !holder.hasRequest(prev) && !prev.requestQueue.isEmpty())
					holder.setRequestQueue(prev);//setting current node as request to next node queue
				Node nextHolder = holder.requestQueue.remove(0);//removal of first element to whom token will be sent
				holder.setHasToken(false);
				nextHolder.setHasToken(true);
				System.out.println("Token passed to " + nextHolder.getNode_name());
				nextHolder.setParent(nextHolder); // Next holder should not have a parent in this context
				holder.setParent(nextHolder);
				if(!holder.requestQueue.isEmpty()) {//after passing token status of current holder
					System.out.print("Current nodes in queue of " + holder.getNode_name() + " is: ");
					for (Node i : holder.requestQueue) {
						if(i!=null)
							System.out.print(i.getNode_name() + " , ");
					}
					System.out.println();
				}
				//again recursive call for next node
				releaseToken(nextHolder,arr,node_list,token_holder,holder);
				if(!holder.requestQueue.isEmpty()) {
					System.out.println("Token passed to " + holder.getNode_name());
					holder.getRequestQueue().toString();
				}
			}
		} else {//if node is requested for access
			holder.setRequestForCs(false);
			aquireToken(holder);//aquire method called
			holder.setParent(holder);
			if(Character.toString(arr[arr.length-1]).equalsIgnoreCase(holder.getNode_name())) {
				flag=0;
			}else {
				flag=1;
			}
			//status of structure after access of critical section
			tree_structure(node_list,token_holder);
			holder.setHasToken(false);
			System.out.println(holder.getNode_name() + " is out of CS");
			if(!holder.requestQueue.isEmpty()) {
				//recall for the request of current node
				releaseToken(holder,arr,node_list,token_holder,prev);
			}else {
				
			}
		}

	}

	//confirmation for Access in critical section
	public static void aquireToken(Node holder) {
		holder.setHasToken(true);
		System.out.println("Token Aquired by "+holder.getNode_name());
		System.out.println("working in CS");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//variable declaration
		int no_of_node=0,ini = 0,end = 0,o=0;
		ArrayList<Node> node_list=new ArrayList<Node>();
		int[][] connectivity = null;
		String cnnt;
		String token_holder = null;
		ArrayList<Node> request_order=new ArrayList<Node>();
		char[] temp = null;
		try {
			// Create a FileReader instance
			FileReader fileReader = new FileReader("test42.txt");

			// Create a BufferedReader instance
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Read lines from the file
			//read no of nodes
			no_of_node= Integer.parseInt(bufferedReader.readLine());
			char[] node_values= bufferedReader.readLine().toCharArray();

			//first token holder
			token_holder=bufferedReader.readLine();
			//request order read	            
			temp = bufferedReader.readLine().toCharArray();
			connectivity = new int[no_of_node][no_of_node];//adjacency matrix definition for node-parent relation
			for (int[] row : connectivity) Arrays.fill(row, 0);//array initialization
			cnnt = bufferedReader.readLine();
			while (cnnt != null) {
				String[] parts = cnnt.split(",");

				if(parts!=null) {
					for(int i=0;i<no_of_node;i++) {
						if(node_values[i]==parts[0].charAt(0)) {
							ini=i;
							break;
						}
					}
					for(int i=0;i<no_of_node;i++) {
						if(node_values[i]==parts[1].charAt(0)) {
							end=i;
							break;
						}
					}
				}
				connectivity[ini][end]=1;
				cnnt = bufferedReader.readLine();
			}


			//node creation
			for(int i=0;i<node_values.length;i++) {
				Node n=new Node();//new node creation
				n.setNode_name(Character.toString(node_values[i]));//set node name
				if(n.getNode_name().equalsIgnoreCase(token_holder)) {
					n.setHasToken(true);//initialise token holder
				}
				else {
					n.setHasToken(false);
				}
				for(int j=0;j<node_values.length;j++) {
					if(connectivity[i][j]==1) {
						for(Node nd : node_list) {
							if(nd.getNode_name().equalsIgnoreCase(Character.toString(node_values[j]))) {
								n.setParent(nd);//parent to node link establishment
								break;
							}
						}
						break;
					}

				}
				node_list.add(n);
				for(char c: temp) {
					if(Character.toString(c).equalsIgnoreCase(n.getNode_name())) {
						request_order.add(n);
						break;
					}
				}
			}


			// Close the reader
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("current token holder is : "+token_holder);
		for(int x=0;x<temp.length;x++) {
			for(Node n : node_list) {
				if(n.getNode_name().equalsIgnoreCase(Character.toString(temp[x]))) {
					if(!n.isHasToken()) {
						n.setRequestForCs(true);
						//request generate as per given order
						receiveTokenRequest(n);
					}
					else {
						//structure of tree after al l request recieved
						tree_structure(node_list,token_holder);
						char[] arr = new char[temp.length-1];
						for(char i: temp) {
							if(i!=temp[x]) {
								arr[o]=i;
								o++;
							}
						}
						//token relase operation to give access of critical section
						releaseToken(n,arr,node_list,token_holder,null);
					}
					break;
				}

			}
		}



	}
	//tree structure or status of whole structure
	private static void tree_structure(ArrayList<Node> node_list, String token_holder) {
		// TODO Auto-generated method stub
		System.out.println("     The tree Structure      ");
		System.out.println("     ------------------       ");
		for(Node n : node_list) {
			if(n.getNode_name().equals(token_holder)) {
				System.out.print("Node : "+n.getNode_name()+" Is in CS: "+n.isHasToken()+"  Request queue :");
				if(n.isRequestForCs()) System.out.print(" | "+n.getNode_name()+" | ");
				for(Node m :n.requestQueue) {
					if(m!=null) System.out.print(" | "+m.getNode_name()+" | ");
				}
				System.out.println();
			}else {
				System.out.print("Node : "+n.getNode_name()+" Node parent :"+n.getParent().getNode_name()+" Is in CS: "+n.isHasToken()+"  Request queue :");
				if(n.isRequestForCs()) System.out.print(" | "+n.getNode_name()+" | ");
				for(Node m :n.requestQueue) {
					if(m!=null)System.out.print(" | "+m.getNode_name()+" | ");
				}
				System.out.println();
			}
		}
	}
}
