package roops.core.objects;

//Authors: Marcelo Frias
import roops.util.RoopsArray; @roops.util.BenchmarkClass
/**
 * 
 * @Invariant all n : BinTreeNode | n in this.root.*(left @+ right ) => ( 
 *            ( n !in n.^(left @+ right) ) && 
 *            ( all m: BinTreeNode | m in n.left.*(left @+right) => m.key < n.key ) && 
 *            ( all m: BinTreeNode | m in n.right.*(left @+right) => n.key < m.key ) && 
 *            ( n.left!=null => n.left.parent=n ) &&
 *            ( n.right!=null=> n.right.parent=n ) && 
 *            ( n=this.root => n.parent=null ) ) ;
 * 
 */
public class BinTree {

	@roops.util.NrOfGoals(9)
	@roops.util.BenchmarkMethod static
	public void addTest(BinTree tree, int x) {
		if (tree!=null) {
		  tree.add(x);
		}
	}

	@roops.util.NrOfGoals(5)
	@roops.util.BenchmarkMethod static
	public void findTest(BinTree tree, int x) {
		boolean ret_val;
		if (tree!=null) {
		  ret_val = tree.find(x);
		}
	}

	@roops.util.NrOfGoals(17)
	@roops.util.BenchmarkMethod static
	public void removeTest(BinTree tree, BinTreeNode z) {
		BinTreeNode ret_val;
		if (tree!=null && z!=null) {
		  ret_val = tree.remove(z);
		}
	}	

	
	public /*@ nullable @*/ BinTreeNode root;


	public void add(int x) {
		BinTreeNode current = root;

		if (root == null) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			root = new BinTreeNode();
			initNode(root,x);
			return;
		}

		while (current.key != x) {
			
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
			
			if (x < current.key) {
				
				{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
				
				if (current.left == null) {
					{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}
					current.left = new BinTreeNode();
					initNode(current.left,x);
				} else {
					{roops.util.Goals.reached(4, roops.util.Verdict.REACHABLE);}
					current = current.left;
				}
			} else {
				{roops.util.Goals.reached(5, roops.util.Verdict.REACHABLE);}
				
				if (current.right == null) {
					{roops.util.Goals.reached(6, roops.util.Verdict.REACHABLE);}
					current.right = new BinTreeNode();
					initNode(current.right,x);
				} else {
					{roops.util.Goals.reached(7, roops.util.Verdict.REACHABLE);}
					current = current.right;
				}
			}
		}
		
		{roops.util.Goals.reached(8, roops.util.Verdict.REACHABLE);}
	}


	private void initNode(BinTreeNode node, int x) {
		node.key = x;
		node.left = null;
		node.right = null;
	}

	public boolean find(int x) {
		BinTreeNode current = root;

		while (current != null) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			
			if (current.key == x) {
				{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
				return true;
			}

			if (x < current.key) {
				{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
				current = current.left;
			} else {
				{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}
				current = current.right;
			}
		}

		{roops.util.Goals.reached(4, roops.util.Verdict.REACHABLE);}
		return false;
	}

	private BinTreeNode treeMinimum(final BinTreeNode x_param) {
		BinTreeNode x = x_param;
		while (x.left != null) {
			{roops.util.Goals.reached(15, roops.util.Verdict.REACHABLE);}
			x = x.left;
		}
		{roops.util.Goals.reached(16, roops.util.Verdict.REACHABLE);}
		return x;
	}

	private BinTreeNode treeSuccessor(final BinTreeNode x_param) {
		BinTreeNode x = x_param;
		BinTreeNode result;
		if (x.right != null) {
			{roops.util.Goals.reached(11, roops.util.Verdict.REACHABLE);}
			result = treeMinimum(x.right);
		} else {
			{roops.util.Goals.reached(12, roops.util.Verdict.UNREACHABLE);}
			BinTreeNode y = x.parent;
			while (y != null && x == y.right) {
				{roops.util.Goals.reached(13, roops.util.Verdict.UNREACHABLE);}
				x = y;
				y = y.parent;
			}

			result = y;
		}
		{roops.util.Goals.reached(14, roops.util.Verdict.REACHABLE);}
		return result;
	}

	
	public BinTreeNode remove(final BinTreeNode z) {
		BinTreeNode y = null;
		if (z.left == null || z.right == null) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			y = z;
		} else {
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
			y = treeSuccessor(z);
		}

		BinTreeNode x = null;
		if (y.left != null) {
			{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
			x = y.left;
		} else {
			{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}
			x = y.right;
		}

		if (x != null) {
			{roops.util.Goals.reached(4, roops.util.Verdict.REACHABLE);}
			x.parent = y.parent;
		}

		if (y.parent == null) {
			{roops.util.Goals.reached(5, roops.util.Verdict.REACHABLE);}
			this.root = x;
		} else {
			{roops.util.Goals.reached(6, roops.util.Verdict.REACHABLE);}
			if (y == y.parent.left){
				{roops.util.Goals.reached(7, roops.util.Verdict.REACHABLE);}
				y.parent.left = x;
			}else{
				{roops.util.Goals.reached(8, roops.util.Verdict.REACHABLE);}
				y.parent.right = x;
			}
		}

		if (y != z) {
			{roops.util.Goals.reached(9, roops.util.Verdict.REACHABLE);}
			z.key = y.key;
		}

		{roops.util.Goals.reached(10, roops.util.Verdict.REACHABLE);}
		return y;
	}


	public BinTree() {}

}
/* end roops.core.objects */
