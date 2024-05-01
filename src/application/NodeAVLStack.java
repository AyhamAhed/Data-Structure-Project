package application;
import java.util.Date;

public class NodeAVLStack {

	Date date;
	private StackMartyr martyrStack;
	int height;
	NodeAVLStack left;
	NodeAVLStack right;

	NodeAVLStack() {
	}
	
	NodeAVLStack(Date date ) {
		this(null, null , date);
	}

	NodeAVLStack(NodeAVLStack left, NodeAVLStack right,Date date) {
		this.left = left;
		this.right = right;
		this.martyrStack = new StackMartyr();
		this.height = 0;
		this.date = date;
	}

	public StackMartyr getMartyrStack() {
		return martyrStack;
	}

	public void setMartyrStack(StackMartyr martyrStack) {
		this.martyrStack = martyrStack;
	}

	@Override
	public String toString() {
		return  martyrStack.peek().toString();
	}
	
	
	
	
}
