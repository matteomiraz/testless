package intkey;


    // Basic node stored in unbalanced binary search trees
    // Note that this class is not accessible outside
    // of package DataStructures

    public class BinaryNode
    {
            // Constructors
        public BinaryNode( int theElement )
        {
            this( theElement, null, null);
        }

        public BinaryNode( int theElement, BinaryNode lt, BinaryNode rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            //this.parent = parent;
        }
        public BinaryNode(){this(-1);}

            // Friendly data; accessible by other package routines
        int element;      // The data in the node
        BinaryNode left;         // Left child
        BinaryNode right;        // Right child
        //BinaryNode parent;
        
        public int getElement() {
			return element;
		}
        
        public BinaryNode getLeft() {
			return left;
		}
        
        public BinaryNode getRight() {
			return right;
		}
    }
