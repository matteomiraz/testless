package roops.core.objects;

//Authors: Marcelo Frias
public class BinTreeNode {

	public int key;

	public /*@ nullable @*/BinTreeNode left;

	public /*@ nullable @*/BinTreeNode right;

	public /*@ nullable @*/BinTreeNode parent;

	public BinTreeNode() {}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public BinTreeNode getLeft() {
		return left;
	}

	public void setLeft(BinTreeNode left) {
		this.left = left;
	}

	public BinTreeNode getRight() {
		return right;
	}

	public void setRight(BinTreeNode right) {
		this.right = right;
	}

	public BinTreeNode getParent() {
		return parent;
	}

	public void setParent(BinTreeNode parent) {
		this.parent = parent;
	}
	
	
}

/* end roops.core.objects */

