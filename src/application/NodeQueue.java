package application;

public class NodeQueue<T> {

	
	private T node;
	public NodeQueue<T> next;

	public NodeQueue(T node) {
		this(node, null);

	}

	public NodeQueue(T node, NodeQueue next) {
		this.node = node;
		this.next = next;
	}

	
	public T getNode() {
		return node;
	}

	public void setNode(T node) {
		this.node = node;
	}

	@Override
	public String toString() {
		return  node.toString();
	}

	

}
