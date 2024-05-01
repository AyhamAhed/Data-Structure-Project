package application;

public class NodeAVL {

	Martyr martyr;
	int height;
	NodeAVL left;
	NodeAVL right;

//	NodeAVL() {
//		
//	}
	
	NodeAVL(Martyr martyr) {
		this(null, null, martyr);
	}

	NodeAVL(NodeAVL left, NodeAVL right, Martyr martyr) {
		this.left = left;
		this.right = right;
		this.martyr = martyr;
		this.height = 0;
	}

	@Override
	public String toString() {
		return  martyr.toString() ;
	}
	
	
}
