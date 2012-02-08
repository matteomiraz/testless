package roops.core.objects;

public class AvlNode {

	public int element; 

	public /*@ nullable @*/AvlNode left; 

	public /*@ nullable @*/AvlNode right; 

	public int height; // Height

        public AvlNode() {}

		public int getElement() {
			return element;
		}

		public void setElement(int element) {
			this.element = element;
		}

		public AvlNode getLeft() {
			return left;
		}

		public void setLeft(AvlNode left) {
			this.left = left;
		}

		public AvlNode getRight() {
			return right;
		}

		public void setRight(AvlNode right) {
			this.right = right;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
        
        
}

/* end roops.core.objects */

