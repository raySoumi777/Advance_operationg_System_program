package Assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class check_initiator {

	//function to check the maximum reachability of a node
	public static void check_reachability(int i,ArrayList<node> nodes,String initiator,int[][] connectivity,int no_of_node) {
		
			for(int j=0;j<no_of_node;j++) {
				if(connectivity[i][j]==1 && i!=j) {
					if(nodes.get(j).getPrev_node().equalsIgnoreCase("null")) {
					nodes.get(i).setReachability(Integer.toString(j));
					nodes.get(i).setCount(nodes.get(i).getCount()+1);
					nodes.get(j).setPrev_node(Integer.toString(i));
					System.out.println(nodes.get(i).getVal()+"-->"+nodes.get(j).getVal());
					}
				}
			}
			
		
	}
	//function to update reachability of initiator
	public static void update_reachability(node node1,node node2) {
		if(node2.reachability.size()!=0) {
			for(int i=0;i<node2.reachability.size();i++) {
				node1.setReachability(node2.reachability.get(i));
				node1.setCount(node1.getCount()+1);
				
			}
		}
		
	}
	
	public static void msg_sending(int[][] connectivity,ArrayList<node> list,node nd,int i) {
		String[] strings = {"Message", "Marker"};
		Random random = new Random();
        // Generate a random index
        int index = random.nextInt(strings.length);
        if(strings[index].equalsIgnoreCase("message")) {
        	
        }
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int no_of_node=0;
		//variable declaration
		ArrayList<node> nodes=new ArrayList<node>();
		int[][] connectivity=null;
		char[] node_values =null;
		String cnnt;
		int ini=0,end=0;
		String initiator=null;
		 try {
	            // Create a FileReader instance
	            FileReader fileReader = new FileReader("TEST1.txt");

	            // Create a BufferedReader instance
	            BufferedReader bufferedReader = new BufferedReader(fileReader);

	            // Read lines from the file
	           //read no of nodes
	            no_of_node= Integer.parseInt(bufferedReader.readLine());
	            System.out.println("No of nodes :"+no_of_node);
	            connectivity = new int[no_of_node][no_of_node];//adjacency matrix definition
	            for (int[] row : connectivity) Arrays.fill(row, 0);//array initialization
	            node_values= bufferedReader.readLine().toCharArray();//read values of node
	            System.out.println("\nValues of nodes ");
	            for(int p=0;p<node_values.length;p++) {
	            	System.out.print(node_values[p]+" ");
	            }
	            //node creation for each value
	            for(int i=0;i<no_of_node;i++) {
	    			nodes.add(new node());
	    			nodes.get(i).setVal(Character.toString(node_values[i]));
	    		}
	            //Take iniator as input
	            initiator = bufferedReader.readLine();
	            System.out.println("\nThe Initiator : "+initiator);
	            //reading edge connectivity and building adjacency matrix
	            while ((cnnt = bufferedReader.readLine()) != null) {
	            	String[] parts = cnnt.split(",");
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
	            	connectivity[ini][end]=1;
	            	
	            }
	            
	            // Close the reader
	            bufferedReader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 //adjacency matrix display
		 System.out.println("\nAdjacency Matrix :\n");
		 for(int i=0;i<no_of_node;i++) {
			 for(int j=0;j<no_of_node;j++) {
				 System.out.print(connectivity[i][j]);
			 }
			 System.out.println();
		 }
		System.out.println("\nPaths are \n-------\n");
		for(int i=0;i<no_of_node;i++) {
			if(nodes.get(i).val.equalsIgnoreCase(initiator)) {
				nodes.get(i).setPrev_node("initial");
				//calculate reachability of initiator
				check_reachability(i,nodes,initiator,connectivity,no_of_node);
				int k=0;
				while(nodes.get(i).getCount()!=(no_of_node-1) && k!=nodes.get(i).reachability.size()){
					//check reachability of each reachable node of initiator
					check_reachability(Integer.parseInt(nodes.get(i).reachability.get(k)),nodes,initiator,connectivity,no_of_node);
					//update reachability of initiator
					update_reachability(nodes.get(i),nodes.get(Integer.parseInt(nodes.get(i).reachability.get(k))));
					k++;
				}
				if(nodes.get(i).getCount()==(no_of_node-1)) {
					System.out.println("\nIt is a good initator");
				}
				else {
					System.out.print("\nIt is not a good initiator");
				}
				
				
				break;
			}
		}
	}

}