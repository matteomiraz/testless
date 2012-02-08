package roops.core.objects;

//Authors: Marcelo Frias

public class FibHeapNode {


	public boolean mark = false;
	public int cost = 0;
	public int degree = 0;

	public /*@ nullable @*/ FibHeapNode parent;
	public /*@ nullable @*/ FibHeapNode left;
	public /*@ nullable @*/ FibHeapNode right;
	public /*@ nullable @*/ FibHeapNode child;

	public FibHeapNode() {}

	public boolean isMark() {
		return mark;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public FibHeapNode getParent() {
		return parent;
	}

	public void setParent(FibHeapNode parent) {
		this.parent = parent;
	}

	public FibHeapNode getLeft() {
		return left;
	}

	public void setLeft(FibHeapNode left) {
		this.left = left;
	}

	public FibHeapNode getRight() {
		return right;
	}

	public void setRight(FibHeapNode right) {
		this.right = right;
	}

	public FibHeapNode getChild() {
		return child;
	}

	public void setChild(FibHeapNode child) {
		this.child = child;
	}
	
	
}
/* end roops.core.objects */
