package MItchel_meritt;

import java.util.ArrayList;
import java.util.Random;
public class controller {
	static int no_of_nodes=5;
	static int counter=0;
	static ArrayList<node> nodeList = new ArrayList<node>(no_of_nodes);
	static int flag=0;
	//block rule
	private static void block_rule(node node1, node node2) {
		// TODO Auto-generated method stub
		int x=Math.max(node1.getPublic_key(), node2.getPublic_key());
		node2.setPrivate_key(x+1);
		node2.setPublic_key(x+1);
	}
	
	//transmit rule
	private static void transmit(node nd) {
		// TODO Auto-generated method stub
		if(nd.getPublic_key()!=nd.getBolcking().getPublic_key()) {
			nd.getBolcking().setPublic_key(nd.getPublic_key());
			if(nd.getBolcking().getBolcking()!=null) {
				transmit(nd.getBolcking());
			}
		}
	}
	//printing status
	public static void display() {
		System.out.println("Node name\tPublic Key\tPrivate Key\tBolcked By\tBlocking");
		System.out.println("---------\t----------\t-----------\t----------\t--------");
		for(node nd:nodeList) {
			if(nd.getBlocked_by()==null && nd.getBolcking()==null) {
				System.out.println(nd.getName()+"\t\t    "+nd.getPublic_key()+"\t\t    "+nd.getPrivate_key()+"\t\t null \t\t null");
				
			}else if(nd.getBolcking()==null) {
					System.out.println(nd.getName()+"\t\t    "+nd.getPublic_key()+"\t\t    "+nd.getPrivate_key()+"\t\t    "+nd.getBlocked_by().getName()+"\t\t null");
			}else if(nd.getBlocked_by()==null) {
				System.out.println(nd.getName()+"\t\t    "+nd.getPublic_key()+"\t\t    "+nd.getPrivate_key()+"\t\t null "+"\t\t   "+nd.getBolcking().getName());
			}	
			else {
				System.out.println(nd.getName()+"\t\t    "+nd.getPublic_key()+"\t\t    "+nd.getPrivate_key()+"\t\t    "+nd.getBlocked_by().getName()+"\t\t    "+nd.getBolcking().getName());
			}
		}
	}
	
	
	public void WFG() {
		// TODO Auto-generated method stub
		//node initialisation
		for(int i=0;i<no_of_nodes;i++) {
			node n=new node();
			n.setName(Integer.toString((i+1)));
			n.setPrivate_key(i+1);
			n.setPublic_key(i+1);
			nodeList.add(n);
		}
		System.out.println("After node initialisation");
		display();
		//wait for graph formation
		while(counter<no_of_nodes) {
			int nd1=new Random().nextInt(no_of_nodes);
			int nd2=new Random().nextInt(no_of_nodes);
			if(nd1!=nd2 && nodeList.get(nd1).getBlocked_by()==null && nodeList.get(nd2).getBolcking()==null && nd1<5 && nd2<5 && nodeList.get(nd1).getBlocked_by()!=nodeList.get(nd2)) {
				//set blocker and blocked by
				//nd1---------->nd2
				System.out.println("Node "+nodeList.get(nd1).getName()+" is blocked by node "+nodeList.get(nd2).getName());
				nodeList.get(nd1).setBlocked_by(nodeList.get(nd2));
				nodeList.get(nd2).setBolcking(nodeList.get(nd1));
				counter++;
				//apply block rule
					block_rule(nodeList.get(nd2),nodeList.get(nd1));
					System.out.println("After block rule ");
					display();
					if(nodeList.get(nd1).getBolcking()!=null && nodeList.get(nd1).getPublic_key()>nodeList.get(nd1).getBolcking().getPublic_key()) {
						//transmit rule
						transmit(nodeList.get(nd1));
						System.out.println("After Transmit rule");
						display();
					}
				
			}
			run();
			if(flag==1) break;
		}
	}
	
	public void run() {
		//deadlock check
		for(int i=0;i<no_of_nodes;i++) {
			if(nodeList.get(i).getBlocked_by()!=null &&nodeList.get(i).getBlocked_by().getPublic_key()==nodeList.get(i).getPublic_key() && nodeList.get(i).getPublic_key()==nodeList.get(i).getPrivate_key() ) {
				System.out.println(nodeList.get(i).getName()+" node having public key :  "+nodeList.get(i).getPublic_key()+" and having private key :  "+nodeList.get(i).getPrivate_key());
				System.out.println(nodeList.get(i).getBlocked_by().getName()+" node having public key "+nodeList.get(i).getBlocked_by().getPublic_key());
				System.out.println("Deadlock Situation occured");
				flag=1;
				break;
			
		}
	}

	}
}
