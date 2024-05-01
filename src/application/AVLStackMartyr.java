package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import javafx.scene.control.TextArea;

public class AVLStackMartyr {

	NodeAVLStack root;

	
	
	public void insert(String date , Martyr martyr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        Date parsedDate = dateFormat.parse(date);
        root = insert(parsedDate, root);
        find(date).getMartyrStack().push(martyr);
        //root.getMartyrStack().push(martyr);
    }

    private NodeAVLStack insert(Date date, NodeAVLStack node) {
        if (node == null) {
            return new NodeAVLStack(date);
        }

        int comparison = date.compareTo(node.date);
        if (comparison < 0) {
            node.left = insert(date, node.left);
        } else if (comparison > 0) {
            node.right = insert(date, node.right);
        } else {
            return node;
        }

        node.height = 1 + max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && date.compareTo(node.date) < 0) {
            return rotateRight(node);
        }
        if (balance < -1 && date.compareTo(node.date) > 0) {
            return rotateLeft(node);
        }
        if (balance > 1 && date.compareTo(node.date) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && date.compareTo(node.date) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

	private NodeAVLStack deleteNode(NodeAVLStack root, Date date) {
		if (root == null)
			return root;

		// element.getName().compareTo(root.martyr.getName()) <0
		if (date.compareTo(root.date) < 0)
			root.left = deleteNode(root.left, date);

		// element.getName().compareTo(root.martyr.getName()) > 0
		else if (date.compareTo(root.date) > 0)
			root.right = deleteNode(root.right, date);

		else {
			if ((root.left == null) || (root.right == null)) {
				NodeAVLStack temp = null;
				if (temp == root.left)
					temp = root.right;
				else
					temp = root.left;

				if (temp == null) {
					temp = root;
					root = null;
				} else
					root = temp;
			} else {
				NodeAVLStack temp = minValueNode(root.right);

				root.date = temp.date;

				root.right = deleteNode(root.right, temp.date);
			}
		}

		if (root == null)
			return root;

		root.height = max(height(root.left), height(root.right)) + 1;

		int balance = getBalance(root);

		if (balance > 1 && getBalance(root.left) >= 0)
			return rotateRight(root);

		if (balance > 1 && getBalance(root.left) < 0) {
			root.left = rotateLeft(root.left);
			return rotateRight(root);
		}

		if (balance < -1 && getBalance(root.right) <= 0)
			return rotateLeft(root);

		if (balance < -1 && getBalance(root.right) > 0) {
			root.right = rotateRight(root.right);
			return rotateLeft(root);
		}

		return root;
	}

	public void delete(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		Date date1 = new Date();
		date1 = dateFormat.parse(date);
		root = deleteNode(root, date1);
	}

	private NodeAVLStack minValueNode(NodeAVLStack right) {
		NodeAVLStack current = right;

		while (current.left != null)
			current = current.left;

		return current;
	}

	private boolean contains(Date date, NodeAVLStack node) {
		if (node == null)
			return false;

		// element.getName().compareTo(node.martyr.getName()) < 0
		else if (date.compareTo(node.date) < 0)
			return contains(date, node.left);
		// element.getName().compareTo(node.martyr.getName()) > 0
		else if (date.compareTo(node.date) > 0)
			return contains(date, node.right);
		return true;
	}

	public boolean contains(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		Date date1 = new Date();
		date1 = dateFormat.parse(date);
		return contains(date1, root);
	}

	private NodeAVLStack find(Date date, NodeAVLStack node) {
		if (node == null)
			return null;
		// element.getName().compareTo(node.martyr.getName()) < 0
		else if (date.compareTo(node.date) < 0)
			return find(date, node.left);
		// element.getName().compareTo(node.martyr.getName()) > 0
		else if (date.compareTo(node.date) > 0)
			return find(date, node.right);
		else
			return node;
	}

	public NodeAVLStack find(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		Date date1 = new Date();
		date1 = dateFormat.parse(date);
		return find(date1, root);
	}

	public int height(NodeAVLStack node) {
		if (node == null)
			return 0;
		return node.height;
	}

	private int max(int left, int right) {
		return (left > right) ? left : right;
	}

	private int getBalance(NodeAVLStack node) {
		if (node == null)
			return 0;
		return height(node.left) - height(node.right);
	}

	private NodeAVLStack rotateLeft(NodeAVLStack node) {
		NodeAVLStack b = node.right;
		if (b == null) {
			return node;
		}

		NodeAVLStack c = b.left;
		b.left = node;
		node.right = c;
		node.height = max(height(node.left), height(node.right)) + 1;
		b.height = max(height(b.left), height(b.right)) + 1;
		return b;
	}

	private NodeAVLStack rotateRight(NodeAVLStack node) {
		NodeAVLStack a = node.left;
		if (a == null) {
			return node;
		}

		NodeAVLStack c = a.right;
		a.right = node;
		node.left = c;
		node.height = max(height(node.left), height(node.right)) + 1;
		a.height = max(height(a.left), height(a.right)) + 1;
		return a;
	}

	public void printByPreOrder() {
		preOrderDiplay(root);
		System.out.println();
	}

	private void preOrderDiplay(NodeAVLStack root) {
		if (root != null) {
			System.out.printf("%s ", new SimpleDateFormat("mm/dd/yyyy").format(root.date));
			preOrderDiplay(root.left);
			preOrderDiplay(root.right);
		}
	}

	public void printByINOrder() {
		inOrderDiplay(root);
		System.out.println();
	}

	private void inOrderDiplay(NodeAVLStack root) {
		if (root != null) {
			inOrderDiplay(root.left);
			System.out.printf("%s ", new SimpleDateFormat("mm/dd/yyyy").format(root.date));
			inOrderDiplay(root.right);
		}
	}

	public void printByPostOrder() {
		postOrderDiplay(root );
		System.out.println();
	}

	private void postOrderDiplay(NodeAVLStack root ) {
		if (root != null) {
			postOrderDiplay(root.left );
			postOrderDiplay(root.right );
			System.out.printf("%s ", new SimpleDateFormat("mm/dd/yyyy").format(root.date));
			PrintStack(root);
		}
	}
	public void PrintStack(NodeAVLStack root) {
		root.getMartyrStack().printStack();
	}
	
	public  int countElements() {
	      return  countElements(root);
	    }

		
		private  int countElements(NodeAVLStack node) {
		        if (node == null)
		            return 0;
		        else
		            return countElements(node.left) + countElements(node.right) + 1;
		    }
		
		public TextArea printLevelByLevelReverse(TextArea area) {
		    if (root == null) {
		        area.setText("The AVL tree is empty.");
		        return area;
		    }

		    Stack<NodeAVLStack> stack = new Stack<>();
		    QueueOfList<NodeAVLStack> queue = new QueueOfList<>();
		    queue.enQueue(root);

		    while (!queue.isEmpty()) {
		        NodeQueue<NodeAVLStack> node = queue.deQueue();
		        stack.push(node.getNode()); // Store nodes in a stack instead of printing immediately

		        if (node.getNode().left != null)
		            queue.enQueue(node.getNode().left);

		        if (node.getNode().right != null)
		            queue.enQueue(node.getNode().right);
		    }
		    
		    // Retrieve all martyrs from the stack
		    while (!stack.isEmpty()) {
		        NodeAVLStack node = stack.pop();
		        NodeStack checkNode = node.getMartyrStack().front ;
		        Martyr martyr = checkNode.getElement(); // Assuming a getter method for accessing the martyr object
		        area.setText(area.getText() + martyr.toString() + "\n");
		        while(checkNode.next != null) {
		        	checkNode = checkNode.next;
		        	martyr = checkNode.getElement();
		        	area.setText(area.getText() + martyr.toString() + "\n");
		        }
		        
		        // Alternatively, you can append the martyr's information to a StringBuilder
		        // and set the final result to the TextArea at the end for better performance.
		    }

		    return area;
		}

		
//		public TextArea printLevelByLevel(TextArea area) {
////		    if (root == null) {
////		        System.out.println("The AVL tree is empty.");
////		        return;
////		    }
//
//		    QueueOfList<NodeAVLStack> queue = new QueueOfList<>();
//		    queue.enQueue(root);
//
//		    while (!queue.isEmpty()) {
//		        NodeQueue<NodeAVLStack> node = queue.deQueue();
//		        area.setText(area.getText()+ node.toString()+"\n");
////		        System.out.println("Martyr Info: " + node.toString());
//
//		        if (node.getNode().left != null)
//		            queue.enQueue(node.getNode().left);
//
//		        if (node.getNode().right != null)
//		            queue.enQueue(node.getNode().right);
//		    }
//		    return area;
//		}


}

//public void insert(String date) throws ParseException {
//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
//Date date1 = new Date();
//date1 = dateFormat.parse(date);
//root = insert(date1, root);
//}
//private NodeAVLStack insert(Date date, NodeAVLStack node) {
//if (node == null) {
//    return new NodeAVLStack(date);
//}
//
//int comparison = date.compareTo(node.date);
//if (comparison < 0) {
//    node.left = insert(date, node.left);
//} else if (comparison > 0) {
//    node.right = insert(date, node.right);
//} else {
//    return node;
//}
//
//node.height = 1 + max(height(node.left), height(node.right));
//
//int balance = getBalance(node);
//
//if (balance > 1 && date.compareTo(node.date) < 0) {
//    return rotateRight(node);
//}
//if (balance < -1 && date.compareTo(node.date) > 0) {
//    return rotateLeft(node);
//}
//if (balance > 1 && date.compareTo(node.date) > 0) {
//    node.left = rotateLeft(node.left);
//    return rotateRight(node);
//}
//if (balance < -1 && date.compareTo(node.date) < 0) {
//    node.right = rotateRight(node.right);
//    return rotateLeft(node);
//}
//
//return node;
//}


//private NodeAVLStack insert(Date date, NodeAVLStack node) {
//if (node == null)
//	return (new NodeAVLStack(date));
//// element.getName().compareTo(node.martyr.getName()) <0
//if (date.compareTo(node.date) < 0)
//	node.left = insert(date, node.left);
//
//// element.getName().compareTo(node.martyr.getName()) >0
//else if (date.compareTo(node.date) > 0)
//	node.right = insert(date, node.right);
//
//else
//	return node;
//
//node.height = 1 + max(height(node.left), height(node.right));
//
//int balance = getBalance(node);
////element.getName().compareTo(node.left.martyr.getName()) <0 
//if (balance > 1 && date.compareTo(node.date) < 0)
//	return rotateRight(node);
////element.getName().compareTo(node.right.martyr.getName()) > 0 
//if (balance < -1 && date.compareTo(node.date) > 0)
//	return rotateLeft(node);
//
//// element.getName().compareTo(node.left.martyr.getName()) > 0
//if (balance > 1 && date.compareTo(node.date) > 0) {
//	node.left = rotateLeft(node);
//	return rotateRight(node);
//}
//
//// element.getName().compareTo(node.right.martyr.getName()) < 0
//if (balance < -1 && date.compareTo(node.date) < 0) {
//	node.right = rotateRight(node.right);
//	return rotateLeft(node);
//}
//
//return node;
//
//}