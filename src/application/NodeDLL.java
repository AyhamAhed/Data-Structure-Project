package application;
//this class node of doubly linked list for location

public class NodeDLL {

	//nodes for double linked list have location and double list (has martyr) and next , prev
		private String Location ;
		private AVLMartyr AvlMartyr;
		private AVLStackMartyr AvlMartyrStack;
		public NodeDLL next , previous;
		
//		public NodeDLL(String location) {
//			this(location);
//		}
		
		public NodeDLL(String location) {
			this.Location = location;
			this.AvlMartyr = new AVLMartyr() ;
			this.AvlMartyrStack = new AVLStackMartyr();
			this.next = null;
			this.previous = null;
			
		}

		public String getLocation() {
			return Location;
		}
		
		public void setLocation(String location) {
			Location = location;
		}
		


		public AVLMartyr getAvlMartyr() {
			return AvlMartyr;
		}

		public void setAvlMartyr(AVLMartyr avlMartyr) {
			AvlMartyr = avlMartyr;
		}

		public AVLStackMartyr getAvlMartyrStack() {
			return AvlMartyrStack;
		}

		public void setAvlMartyrStack(AVLStackMartyr avlMartyrStack) {
			AvlMartyrStack = avlMartyrStack;
		}

		@Override
		public String toString() {
				return "[Location = " + Location + "]";	
			
		}


	
}
