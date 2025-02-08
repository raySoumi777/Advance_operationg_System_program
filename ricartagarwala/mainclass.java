package ricartagarwala;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class mainclass {
	static ArrayList<node> nodes=new ArrayList<node>();
	node tokenholder;
	static int no_of_nodes=0;
	
	public static void Request(int index,LocalTime tm) {
		// TODO Auto-generated method stub
		System.out.println(nodes.get(index).getName()+" is sending request for token access at time "+tm);
		nodes.get(index).tm=tm;
		for(int i=0;i<no_of_nodes;i++) {
			if(i!=index && nodes.get(i).hastoken==false) {
				nodes.get(index).count++;
				nodes.get(index).setPermission(nodes.get(i));
				System.out.println(nodes.get(i).getName()+" gives permission  to the node "+nodes.get(index).getName());
			}else if(i!=index && nodes.get(i).hastoken==true) {
				
				if(nodes.get(i).tm.compareTo(nodes.get(index).tm)<0) {
					nodes.get(i).setholder(nodes.get(index));
					System.out.println(nodes.get(i).getName()+" holds permission for the node "+nodes.get(index).getName()+" who requests at "+tm);
				}
				else {
					nodes.get(index).count++;
					nodes.get(index).setPermission(nodes.get(i));
					System.out.println(nodes.get(i).getName()+" gives permission  to the node "+nodes.get(index).getName());
				}
				
			}
		}
		System.out.print("node "+nodes.get(index).getName()+" gets permission from : ");
		for(node n: nodes.get(index).permission) {
			System.out.print("   "+n.getName());
		}
		System.out.println();
	}
	public static void access(int index) {
		// TODO Auto-generated method stub
		nodes.get(index).hastoken=true;
		System.out.println(nodes.get(index).getName()+" is currently in critical section");
		
	}
	public static void Realese(int index) {
		// TODO Auto-generated method stub
		nodes.get(index).hastoken=false;
		nodes.get(index).count=0;
		nodes.get(index).permission.clear();
		System.out.println(nodes.get(index).getName()+" is out of critical section ");
		for(node n:nodes.get(index).holder) {
			if(n.count!=no_of_nodes) {
				n.setPermission(nodes.get(index));
				n.count++;
				System.out.println(nodes.get(index).getName()+" gives permission  to the node "+n.getName());
			}
			
		}
	}
	public static void main(String[] args) {
		int msg=0,ch=800;
		char[] nodeList;
		int temp_node = 0,temp = 0;
		try {
			FileReader fileReader = new FileReader("testn2.txt");
			BufferedReader bufferReader = new BufferedReader(fileReader);
			no_of_nodes=Integer.parseInt(bufferReader.readLine());
			nodeList = bufferReader.readLine().toCharArray();
			for(int i=0;i<no_of_nodes;i++) {
				node t=new node();
				t.setName(Character.toString(nodeList[i]));
				nodes.add(t);
			}
			msg=Integer.parseInt(bufferReader.readLine());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(int i=0;i<=msg;i++) {
			if(ch>700) {
				temp_node=new Random().nextInt(no_of_nodes);
				LocalTime ts= LocalTime.now();
				Request(temp_node,ts);
			}
			if(nodes.get(temp_node).getCount()==(no_of_nodes-1)) {
				access(temp_node);
			}
			ch=new Random().nextInt(1000);
			if(ch<700 && i!=msg) {
				 temp=new Random().nextInt(no_of_nodes);
				 LocalTime ts= LocalTime.now();
				if(temp!=temp_node)Request(temp,ts);
			}
			Realese(temp_node);
			if(ch<700 && temp!=temp_node) temp_node=temp;	
		}
		
		
	}
	

	

	
}
