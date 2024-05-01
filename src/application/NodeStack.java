package application;
public class NodeStack {

	private Martyr martyr;
	public NodeStack next;

	public NodeStack(Martyr element) {
		this(element, null);
	}

	public NodeStack(Martyr element, NodeStack next) {
		this.martyr = element;
		this.next = next;
	}

	public Martyr getElement() {
		return martyr;
	}

	public void setElement(Martyr element) {
		this.martyr = element;
	}

}