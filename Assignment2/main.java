package Assignment2;
import java.time.LocalTime;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int no_of_nodes=5,incs=0;;
		boolean x=true;
		ArrayList<node> nodeList = new ArrayList<node>();
		node next = null;
		//read input fromfile
		for(int i=0;i<no_of_nodes;i++) {
			node temp=new node();
			temp.setnodeName(Integer.toString(i));
			temp.setId(i);
			nodeList.add(temp);
		}
		ArrayList<node> order=new ArrayList<node>();
		order.add(nodeList.get(2));
		order.add(nodeList.get(3));
		order.add(nodeList.get(0));
		order.add(nodeList.get(4));
		order.add(nodeList.get(1));
		for(int p=0;p<order.size();p++) {
			node nd=order.get(p);
			if(nd.getTime()==null) {
				nd.setTime(LocalTime.now());
			}
			if(sendrequest(nd,no_of_nodes,nodeList)) {
					
				if(check_availibility(nd)) {
					next=enterInCs(nd);
					incs=nd.getnodeId();
				}
				
			}
			if(next!=null) {
				nd = next;
				p=next.nodeid;
			}
			
		}
	}

	private static node enterInCs(node nd) {
		// TODO Auto-generated method stu
		System.out.println(nd.getnodeId()+" is in CS");
		nd.run();
		node next=null;
		if(nd.isInCs()) {
			next=nd.waiting.get(0);
			for(node t : nd.waiting) {
				t.req_hold.remove(nd);
			}
		}
		return next;

	}

	private static boolean check_availibility(node nd) {
		// TODO Auto-generated method stub
		for (node tmp :nd.req_hold) {
			if(!tmp.isInCs()) {
				nd.req_hold.remove(tmp);
			}
		}
		return nd.req_hold.isEmpty();
	}

	private static boolean sendrequest(node nd,int no,ArrayList<node> list) {
		// TODO Auto-generated method stub
		boolean x=true;
		for(int i=0;i<no;i++) {
			if(!list.get(i).getnodeName().equals(nd.getnodeName())) {
				if(list.get(i).isInCs()) {
					if(list.get(i).getTime().compareTo(nd.getTime())==1) {
						list.get(i).setWaiting(nd);
						nd.setReq_hold(list.get(i));
						System.out.println(nd.getnodeName()+" can't go to cs");
						x=false;
					}
				}
			}
		}
		return x;
	}

}

