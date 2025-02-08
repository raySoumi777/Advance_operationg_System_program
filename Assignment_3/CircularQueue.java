package Assignment_3;
//Java program for insertion and
//deletion in Circular Queue
import java.util.ArrayList;

class CircularQueue{

//Declaring the class variables.
private int size, front, rear;

//Declaring array list of integer type.
private ArrayList<String> queue = new ArrayList<String>();

//Constructor
CircularQueue(int size)
{
	this.size = size;
	this.front = this.rear = -1;
}

//Method to insert a new element in the queue.
public void enQueue(String data)
{
	
	// Condition if queue is full.
	if((front == 0 && rear == size - 1) ||
	(rear == (front - 1) % (size - 1)))
	{
		System.out.print("Queue is Full");
	}

	// condition for empty queue.
	else if(front == -1)
	{
		front = 0;
		rear = 0;
		queue.add(rear, data);
	}

	else if(rear == size - 1 && front != 0)
	{
		rear = 0;
		queue.set(rear, data);
	}

	else
	{
		rear = (rear + 1);
	
		// Adding a new element if 
		if(front <= rear)
		{
			queue.add(rear, data);
		}
	
		// Else updating old value
		else
		{
			queue.set(rear, data);
		}
	}
}

//Function to dequeue an element
//form th queue.
public String deQueue()
{
	String temp;

	// Condition for empty queue.
	if(front == -1)
	{
		System.out.print("Queue is Empty");
		
		// Return -1 in case of empty queue
		return "-1"; 
	}

	temp = queue.get(front);

	// Condition for only one element
	if(front == rear)
	{
		front = -1;
		rear = -1;
	}

	else if(front == size - 1)
	{
		front = 0;
	}
	else
	{
		front = front + 1;
	}
	
	// Returns the dequeued element
	return temp;
}

//Method to display the elements of queue
public void displayQueue()
{
	
	// Condition for empty queue.
	if(front == -1)
	{
		System.out.print("Queue is Empty");
		return;
	}

	// If rear has not crossed the max size
	// or queue rear is still greater then
	// front.
	System.out.print("Elements in the " +
					"circular queue are: ");

	if(rear >= front)
	{
	
		// Loop to print elements from
		// front to rear.
		for(int i = front; i <= rear; i++)
		{
			System.out.print(queue.get(i));
			System.out.print(" ");
		}
		System.out.println();
	}

	// If rear crossed the max index and
	// indexing has started in loop
	else
	{
		
		// Loop for printing elements from
		// front to max size or last index
		for(int i = front; i < size; i++)
		{
			System.out.print(queue.get(i));
			System.out.print(" ");
		}

		// Loop for printing elements from
		// 0th index till rear position
		for(int i = 0; i <= rear; i++)
		{
			System.out.print(queue.get(i));
			System.out.print(" ");
		}
		System.out.println();
	}
}

public String check(String temp_node) {
	// TODO Auto-generated method stub
	String ret_node=null;
	int i=front;
	while(i<=rear) {
		if(queue.get(i).equalsIgnoreCase(temp_node)) {
			ret_node=queue.get((i+1)%size);
			break;
		}
		i=(i+1)%size;
	}
	return ret_node;
}
}