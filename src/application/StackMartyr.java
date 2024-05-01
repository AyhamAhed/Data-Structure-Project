package application;
public class StackMartyr {
	
	 NodeStack front;
	private int size;

	public StackMartyr() {
		front = null;
		size = 0;
	}
	public void push(Martyr element) {
		NodeStack newNode = new NodeStack(element);
		newNode.next= front;
		front = newNode;
		size++;
	}
	public Martyr pop() {
		if (!isEmpty()) {
			NodeStack top = front;
			 front=front.next;
			size--;
			return top.getElement();
		} else
			return null;
	}
	public Martyr peek() {
		if (!isEmpty())
			return front.getElement();
		else
			return null;
	}
	
	public int getSize() {
		return size;
	}
	public void printStack() {
		NodeStack curr = front;
		//System.out.println("The stack from top to bottom");
		while(curr != null) {
			System.out.println(curr.getElement());
			curr = curr.next;
		}
	}
	
	public Martyr Search(String martyr) {
		NodeStack curr = front;
		while(curr != null || curr.getElement().getName().equals(martyr)) {
			curr = curr.next;
		}
		return curr.getElement();
	}
	public boolean isEmpty() {
		return (front == null);
	}
}