package roops.core.objects;

//Authors: Marcelo Frias
import roops.util.RoopsArray; @roops.util.BenchmarkClass
/**
 * @Invariant all x: AvlNode | x in this.root.*(left @+ right) @- null => 
 * (
 *		(x !in x.^(left @+ right)) && 
 *		(all y: AvlNode | (y in x.left.*(left @+ right) @-null) => y.element < x.element ) && 
 *		(all y: AvlNode | (y in x.right.*(left @+right) @- null) => x.element < y.element ) && 
 *		(x.left=null && x.right=null => x.height=0) && 
 *		(x.left=null && x.right!=null => x.height=1 && x.right.height=0) && 
 *		(x.left!=null && x.right=null => x.height=1 && x.left.height=0) && 
 *		(x.left!=null && x.right!=null => x.height= (x.left.height>x.right.height ? x.left.height : x.right.height )+1 && ( (x.left.height > x.right.height ? x.left.height - x.right.height : x.right.height - x.left.height ))<=1)
 * );
 * 
 */
public class AvlTree {

  @roops.util.NrOfGoals(5)
  @roops.util.BenchmarkMethod static
  public void findNodeTest(AvlTree tree, int x) {
    	
    	if (tree!=null) {
		  AvlNode ret_val = tree.findNode(x);
    	}
  }

   @roops.util.NrOfGoals(3)
   @roops.util.BenchmarkMethod static
   public void fmaxTest(AvlTree tree) {
	   
	   if (tree!=null) {
	     AvlNode ret_val = tree.fmax();
	   }
   }

   @roops.util.NrOfGoals(3)
   @roops.util.BenchmarkMethod static
   public void fminTest(AvlTree tree) {
	   
	   if (tree!=null) {
	     AvlNode ret_val = tree.fmin();
	   }
   }

	public /*@ nullable @*/AvlNode root;

	public/*@ nullable @*/AvlNode findNode(final int x) {
		return find(x, this.root);
	}


	private AvlNode find(final int x, final AvlNode arg) {
		AvlNode t = arg;
		while (t != null) {
			
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			if (x < t.element) {
				
				{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
				t = t.left;
			} else if (x > t.element) {
				
				{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
				t = t.right;
			} else {
				
				{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}
				return t; // Match
			}
		}

		{roops.util.Goals.reached(4, roops.util.Verdict.REACHABLE);}
		return null; // No match
	}

	
	public AvlNode fmax() {
		return findMax(this.root);
	}


	private AvlNode findMax(final AvlNode arg) {
		AvlNode t = arg;
		if (t == null) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			return t;
		}

		while (t.right != null) {
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
			t = t.right;
		}
		
		{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
		return t;
	}



	public AvlNode fmin() {
		return findMin(this.root);
	}

	

	private AvlNode findMin(final AvlNode arg) {
		AvlNode t = arg;
		if (t == null) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			return t;
		}

		while (t.left != null) {
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
			t = t.left;
		}
		
		{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
		return t;
	}

	public AvlTree() {}

}
/* end roops.core.objects */

