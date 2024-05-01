package application;

import java.util.ArrayList;

import javafx.scene.control.TextArea;

public class AVLMartyr {

	NodeAVL root;
	
	public void insert(Martyr element) {
		 root = insert(element, root);
	}
	private NodeAVL insert(Martyr element, NodeAVL node) {
	    if (node == null) {
	        return new NodeAVL(element);
	    }

	    if (element.getName().compareTo(node.martyr.getName()) < 0) {
	        node.left = insert(element, node.left);
	    } else if (element.getName().compareTo(node.martyr.getName()) > 0) {
	        node.right = insert(element, node.right);
	    } else {
	        return node;
	    }

	    node.height = 1 + max(height(node.left), height(node.right));

	    int balance = getBalance(node);

	    if (balance > 1 && element.getName().compareTo(node.left != null ? node.left.martyr.getName() : "") < 0) {
	        return rotateRight(node);
	    }
	    if (balance < -1 && element.getName().compareTo(node.right != null ? node.right.martyr.getName() : "") > 0) {
	        return rotateLeft(node);
	    }
	    if (balance > 1 && element.getName().compareTo(node.left != null ? node.left.martyr.getName() : "") > 0) {
	        node.left = rotateLeft(node.left);
	        return rotateRight(node);
	    }
	    if (balance < -1 && element.getName().compareTo(node.right != null ? node.right.martyr.getName() : "") < 0) {
	        node.right = rotateRight(node.right);
	        return rotateLeft(node);
	    }

	    return node;
	}

	
//	private NodeAVL insert(Martyr element, NodeAVL node) {
//		if (node == null)
//			return (new NodeAVL(element));
//	//	element.getName().compareTo(node.martyr.getName()) <0 
//		if (element.getName().compareTo(node.martyr.getName()) < 0 )
//			node.left = insert(element, node.left);
//
//		//element.getName().compareTo(node.martyr.getName()) >0 
//		else if (element.getName().compareTo(node.martyr.getName()) >0 )
//			node.right = insert(element, node.right);
//
//		else
//			return node;
//
//		node.height = 1 + max(height(node.left), height(node.right));
//
//		int balance = getBalance(node);
////element.getName().compareTo(node.left.martyr.getName()) <0 
//		if (balance > 1 && element.getName().compareTo(node.left.martyr.getName()) <0 )
//			return rotateRight(node);
////element.getName().compareTo(node.right.martyr.getName()) > 0 
//		if (balance < -1 && element.getName().compareTo(node.right.martyr.getName()) > 0 )
//			return rotateLeft(node);
//
//		//element.getName().compareTo(node.left.martyr.getName()) > 0 
//		if (balance > 1 && element.getName().compareTo(node.left.martyr.getName()) > 0 ) {
//			node.left = rotateLeft(node);
//			return rotateRight(node);
//		}
//
//		// element.getName().compareTo(node.right.martyr.getName()) < 0 
//		if (balance < -1 && element.getName().compareTo(node.right.martyr.getName()) < 0 ) {
//			node.right = rotateRight(node.right);
//			return rotateLeft(node);
//		}
//
//		return node;
//
//	}
	
	
	private NodeAVL deleteNode(NodeAVL root, Martyr element) {
		if (root == null)
			return root;

		//	element.getName().compareTo(root.martyr.getName()) <0 
		if (element.getName().compareTo(root.martyr.getName()) < 0 )
			root.left = deleteNode(root.left, element);

		//element.getName().compareTo(root.martyr.getName()) > 0 
		else if (element.getName().compareTo(root.martyr.getName()) > 0 )
			root.right = deleteNode(root.right, element);

		else {
			if ((root.left == null) || (root.right == null)) {
				NodeAVL temp = null;
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
				NodeAVL temp = minValueNode(root.right);

				root.martyr = temp.martyr;

				root.right = deleteNode(root.right, temp.martyr);
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

	public void delete(Martyr element) {
		root = deleteNode(root, element);
	}

	private NodeAVL minValueNode(NodeAVL node) {
		NodeAVL current = node;

		while (current.left != null)
			current = current.left;

		return current;
	}
	
	private boolean contains(Martyr element, NodeAVL node) {
		if (node == null)
			return false;
		
		// element.getName().compareTo(node.martyr.getName()) < 0 
		else if ( element.getName().compareTo(node.martyr.getName()) < 0 )
			return contains(element, node.left);
		// element.getName().compareTo(node.martyr.getName()) > 0 
		else if (element.getName().compareTo(node.martyr.getName()) > 0 )
			return contains(element, node.right);
		return true;
	}
	
	public boolean contains(Martyr element) {
		return contains(element, root);
	}
	
	private NodeAVL find(String element, NodeAVL node) {
		if (node == null)
			return null;
		// element.getName().compareTo(node.martyr.getName()) < 0 
		else if (element.compareTo(node.martyr.getName()) < 0 )
			return find(element, node.left);
		// element.getName().compareTo(node.martyr.getName()) > 0 
		else if (element.compareTo(node.martyr.getName()) > 0)
			return find(element, node.right);
		else
			return node;
	}

	public NodeAVL find(String element) {
		return find(element, root);
	}

	public int height(NodeAVL node) {
		if (node == null)
			return 0;
		return node.height;
	}

	private int max(int left, int right) {
		return (left > right) ? left : right;
	}

	private int getBalance(NodeAVL node) {
		if (node == null)
			return 0;
		return height(node.left) - height(node.right);
	}

	private NodeAVL rotateLeft(NodeAVL node) {
	    NodeAVL b = node.right;
	    if (b == null) {
	        return node;
	    }

	    NodeAVL c = b.left;
	    b.left = node;
	    node.right = c;
	    node.height = max(height(node.left), height(node.right)) + 1;
	    b.height = max(height(b.left), height(b.right)) + 1;
	    return b;
	}

//	private NodeAVL rotateLeft(NodeAVL node) {
//		NodeAVL b = node.right;
//		NodeAVL c = b.left;
//		b.left = node;
//		node.right = c;
//		node.height = max(height(node.left), height(node.right)) + 1;
//		b.height = max(height(b.left), height(b.right)) + 1;
//		return b;
//	}
	
	private NodeAVL rotateRight(NodeAVL node) {
	    NodeAVL a = node.left;
	    if (a == null) {
	        return node;
	    }

	    NodeAVL c = a.right;
	    a.right = node;
	    node.left = c;
	    node.height = max(height(node.left), height(node.right)) + 1;
	    a.height = max(height(a.left), height(a.right)) + 1;
	    return a;
	}

	
//	private NodeAVL rotateRight(NodeAVL node) {
//		NodeAVL a = node.left;
//		NodeAVL c = a.right;
//		a.right = node;
//		node.left = c;
//		node.height = max(height(node.left), height(node.right)) + 1;
//		a.height = max(height(a.left), height(a.right)) + 1;
//		return a;
//	}
	
	public void printByPreOrder() {
		preOrderDiplay(root);
		System.out.println();
	}

	private void preOrderDiplay(NodeAVL root) {
		if (root != null) {
//			if(root.martyr.getLocation().equals(location)) {
				System.out.printf("%s ", root.martyr.getName());				
//			}
			preOrderDiplay(root.left );
			preOrderDiplay(root.right );
		}
	}

	public void printByINOrder() {
		inOrderDiplay(root);
		System.out.println();
	}

	private void inOrderDiplay(NodeAVL root) {
		if (root != null) {
			inOrderDiplay(root.left );
//			if(root.martyr.getLocation().equals(location)) {
				System.out.printf("%s ", root.martyr.getName());				
//			}
			inOrderDiplay(root.right);
		}
	}

	public void printByPostOrder() {
		postOrderDiplay(root) ;
		System.out.println();
	}

	private void postOrderDiplay(NodeAVL root) {
		if (root != null) {
			postOrderDiplay(root.left );
			postOrderDiplay(root.right );
//			if(root.martyr.getLocation().equals(location)) {
				System.out.printf("%s ", root.martyr.getName());				
//			}
		}
	}
	
	public  int countElements() {
      return  countElements(root);
    }
	
	private  int countElements(NodeAVL node) {
	        if (node == null)
	            return 0;
	        else
	            return countElements(node.left) + countElements(node.right) + 1;
	    }

	public ArrayList<String> getMartyr() {
		ArrayList<String> martyrlist = new ArrayList<>();
	 	martyrlist =  getMartyr(root ,martyrlist );
	 	return martyrlist;
		//System.out.println();
	}
	
	private ArrayList<String> getMartyr(NodeAVL root , ArrayList<String> martyrlist){
		// = new ArrayList<>();
		if (root != null) {
			martyrlist.add(root.martyr.getName());		
				getMartyr(root.left ,martyrlist );
				getMartyr(root.right ,martyrlist);
		}
		return martyrlist;
	}
	
	public TextArea printLevelByLevel(TextArea area) {
	   if (root == null) {
        area.setText("The AVL tree is empty.");
        return area;
    }

	    QueueOfList<NodeAVL> queue = new QueueOfList<>();
	    queue.enQueue(root);

	    while (!queue.isEmpty()) {
	        NodeQueue<NodeAVL> node = queue.deQueue();
	        area.setText(area.getText()+node.toString()+"\n");
	      //  System.out.println(node.toString());

	        if (node.getNode().left != null)
	            queue.enQueue(node.getNode().left);

	        if (node.getNode().right != null)
	            queue.enQueue(node.getNode().right);
	    }
	    return area;
	}
}
