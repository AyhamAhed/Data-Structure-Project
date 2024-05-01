package application;

import java.util.ArrayList;

public class DoubleCircularLinkedList {

	private NodeDLL head; // head of double linked list for location
	private int size; // size

	// constructor no argument
	public DoubleCircularLinkedList() {
		this.head = null;
		this.size = 0;
	}

	public boolean insertCDLL(String location) {
	    NodeDLL newNode = new NodeDLL(location);
	    size++;
	    
	    if(searchCDLL(location) != null) {
	    	return false;
	    }
	    
	    if (head == null) {
	        head = newNode;
	        newNode.next = newNode;
	        newNode.previous = newNode;
	        return true;
	    } else {
	        NodeDLL curr = head;
	        
	        // Traverse the circular list to find the correct position to insert the new node
	        while (curr.next != head && newNode.getLocation().compareTo(curr.getLocation()) >= 0) {
	            curr = curr.next;
	        }
	        
	        // If the new node should be inserted at the end, update the connections
	        if (newNode.getLocation().compareTo(curr.getLocation()) >= 0) {
	            newNode.next = head;
	            newNode.previous = curr;
	            curr.next = newNode;
	            head.previous = newNode;
	        }
	        // If the new node should be inserted before the current node, update the connections
	        else {
	            newNode.next = curr;
	            newNode.previous = curr.previous;
	            curr.previous.next = newNode;
	            curr.previous = newNode;

	            // If the new node is inserted before the head, update the head reference
	            if (curr == head) {
	                head = newNode;
	            }
	        }
	        
	        return true;
	    }
	}

	public boolean deleteCDLL(String location) {
		if (head == null) {
			return false;
		}

		// Check if the head node contains the same location
		if (head.getLocation().equals(location)) {
			if (head.next == head) { // Only one node in the list
				head = null;
			} else {
				NodeDLL lastNode = head.previous;
				head = head.next;
				lastNode.next = head;
				head.previous = lastNode;
			}
			size--;
			return true;
		}

		NodeDLL curr = head.next;
		while (curr != head && !curr.getLocation().equals(location)) {
			curr = curr.next;
		}

		// If curr is not null, it means the location was found
		if (curr != null && curr != head) {
			curr.previous.next = curr.next;
			curr.next.previous = curr.previous;
			size--;
			return true;
		}

		return false;
	}

	public boolean updateCDLL(String location, String key) {
		NodeDLL curr = head;

		// Check if the list is empty
		if (curr == null) {
			return false;
		}

		do {
			// If the current node has the same key, update its location and return true
			if (curr.getLocation().equals(key)) {
				if(searchCDLL(location) == null) {
					deleteCDLL(key);
					insertCDLL(location);
				}
				return true;
			}

			curr = curr.next;
		} while (curr != head);

		// The key was not found in any node
		return false;
	}

	public NodeDLL searchCDLL(String location) {
		NodeDLL curr = head;

		// Check if the list is empty
		if (curr == null) {
			return null;
		}

		// Check if the head node has the desired location
		if (curr.getLocation().equals(location)) {
			return curr;
		}

		curr = curr.next; // Move to the next node

		// Traverse the circular doubly linked list until we reach the head node again
		while (curr != head) {
			// If the current node has the desired location, return it
			if (curr.getLocation().equals(location)) {
				return curr;
			}

			curr = curr.next;
		}

		// The location was not found in any node
		return null;
	}

	public void printCDLL() {
	    if (head == null) {
	        System.out.println("Empty list.");
	        return;
	    }
	    NodeDLL curr = head;
	    
//	    AVLMartyr avl = new AVLMartyr();
	    // Print the elements starting from the head node and continue until we reach the head node again
	    while (true) {
	        System.out.println(curr.getLocation());
//	        avl.printByPostOrder(curr.getLocation());
	        curr.getAvlMartyr().printByPostOrder();
	        curr.getAvlMartyrStack().printByPostOrder();
	       // curr.getAvlMartyrStack().PrintStack();
	        curr = curr.next;
	        if (curr == head) {
	            break;
	        }
	    }
	}
	public ArrayList<String> getLocations() {
	    ArrayList<String> locations = new ArrayList<>();
	    NodeDLL curr = head;
	    locations.add(curr.getLocation());
	    while (curr.next != head) {
	        locations.add(curr.next.getLocation());
	        curr = curr.next;
	    }
	    return locations;
	}

	public NodeDLL getHead() {
		return head;
	}

	public void setHead(NodeDLL head) {
		this.head = head;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
