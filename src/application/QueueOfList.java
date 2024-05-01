package application;

public class QueueOfList<T> {

	private NodeQueue front;
	private int size;
	
	
	public QueueOfList() {
		this.front = null;
		this.size = 0;
	}
	
	public void enQueue(T root) {
		NodeQueue curr = front;
		NodeQueue newNode = new NodeQueue(root);
		if(isEmpty()) {
			front = newNode;
		}
		else {
			while(curr.next != null) {
				curr = curr.next;
			}
			curr.next = newNode;
		}
		size++;
	}
	
	
	public NodeQueue deQueue() {
		NodeQueue newNode = null;
		if(!isEmpty()) {
			newNode = front ;
			front = front.next;
			size--;
			return newNode;
		}
		return null;
	}
	public void printQueue() {
		
		NodeQueue fordequeue = deQueue();
		
		while(fordequeue != null) {
			System.out.println(fordequeue.toString());
			fordequeue = deQueue();
			
		}
	}	
//	public void getThreeElements() {
//		//Stack getStack = new Stack();
//		for(int i = 1 ; i<=3 ; i++) {
//			System.out.println(stack.peek());
//			stack.pop();
//		}
//	}
	
	public boolean isEmpty() {
		return (front == null);
	}
	
	
}
