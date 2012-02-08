package intkey;

    // BinarySearchTree class
    //
    // CONSTRUCTION: with no initializer
    //
    // ******************PUBLIC OPERATIONS*********************
    // void insert( x )       --> Insert x
    // void remove( x )       --> Remove x
    // Comparable find( x )   --> Return item that matches x
    // Comparable findMin( )  --> Return smallest item
    // Comparable findMax( )  --> Return largest item
    // boolean isEmpty( )     --> Return true if empty; else false
    // void makeEmpty( )      --> Remove all items
    // void printTree( )      --> Print tree in sorted order

    /**
     * Implements an unbalanced binary search tree.
     * Note that all "matching" is based on the compareTo method.
     * @author Mark Allen Weiss
     */
    public class BinarySearchTree
    {

        /**
         * Construct the tree.
         */
        public BinarySearchTree( )
        {
            root = null;
        }

        /**
         * Insert into the tree; duplicates are ignored.
         * @param x the item to insert.
         */
        public void insert( int x )
        {
            root = insert( x, root );
        }

        /**
         * Remove from the tree. Nothing is done if x is not found.
         * @param x the item to remove.
         */
        public void remove( int x )
        {
            root = remove( x, root );
        }

        /**
         * Find the smallest item in the tree.
         * @return smallest item or null if empty.
         */
        public int findMin( )
        {
            return elementAt( findMin( root ) );
        }

        /**
         * Find the largest item in the tree.
         * @return the largest item of null if empty.
         */
        public int findMax( )
        {
            return elementAt( findMax( root ) );
        }

        /**
         * Find an item in the tree.
         * @param x the item to search for.
         * @return the matching item or null if not found.
         */
        public  int find( int x )
        {
            return elementAt( find( x, root ) );
        }

        /**
         * Make the tree logically empty.
         */
        public void makeEmpty( )
        {
            root = null;
        }

        boolean repOK(BinaryNode t) {
        	return repOK(t,new Range());
        }

        boolean repOK(BinaryNode t, Range range) {
            if(t == null) {
                return true;
            }
            if(!range.inRange(t.element)) {
            	return false;
            }
            boolean ret=true;
            ret = ret && repOK(t.left,range.setUpper(t.element));
            ret = ret && repOK(t.right,range.setLower(t.element));
            return ret;
        }
        /**
         * Test if the tree is logically empty.
         * @return true if empty, false otherwise.
         */
        public boolean isEmpty( )
        {
            return root == null;
        }

        /**
         * Print the tree contents in sorted order.
         */
        public void printTree( )
        {
            if( isEmpty( ) )
                System.out.println( "Empty tree" );
            else
                printTree( root );
        }

        /**
         * Internal method to get element field.
         * @param t the node.
         * @return the element field or null if t is null.
         */
        private int elementAt( BinaryNode t )
        {
            if(t==null) {
                return -1;
            }
            return t.element;
        }

        /**
         * Internal method to insert into a subtree.
         * @param x the item to insert.
         * @param t the node that roots the tree.
         * @return the new root.
         */
        ///@Assertion(value = { @Case(pre = "(repOK(t))", post = "repOK($ret)") })
        private BinaryNode insert( int x, BinaryNode t )
        {
/* 1*/      if( t == null )
/* 2*/          t = new BinaryNode( x, null, null );
/* 3*/      else if( x < t.element)
/* 4*/          t.left = insert( x, t.left );
/* 5*/      else if( x> t.element )
/* 6*/          t.right = insert( x, t.right );
/* 7*/      else
/* 8*/          ;  // Duplicate; do nothing
/* 9*/      return t;
        }

        /**
         * Internal method to remove from a subtree.
         * @param x the item to remove.
         * @param t the node that roots the tree.
         * @return the new root.
         */
        private BinaryNode remove( int x, BinaryNode t )
        {
            if( t == null )
                return t;   // Item not found; do nothing
            if( x < t.element )
                t.left = remove( x, t.left );
            else if( x > t.element )
                t.right = remove( x, t.right );
            else if( t.left != null && t.right != null ) // Two children
            {
                t.element = findMin( t.right ).element;
                t.right = remove( t.element, t.right );
            }
            else
                t = ( t.left != null ) ? t.left : t.right;
            return t;
        }

        /**
         * Internal method to find the smallest item in a subtree.
         * @param t the node that roots the tree.
         * @return node containing the smallest item.
         */
        private BinaryNode findMin( BinaryNode t )
        {
            if( t == null )
                return null;
            else if( t.left == null )
                return t;
            return findMin( t.left );
        }

        /**
         * Internal method to find the largest item in a subtree.
         * @param t the node that roots the tree.
         * @return node containing the largest item.
         */
        private BinaryNode findMax( BinaryNode t )
        {
            if( t != null )
                while( t.right != null )
                    t = t.right;

            return t;
        }

        /**
         * Internal method to find an item in a subtree.
         * @param x is item to search for.
         * @param t the node that roots the tree.
         * @return node containing the matched item.
         */
        private BinaryNode find( int x, BinaryNode t )
        {
            if( t == null )
                return null;
            if( x < t.element)
                return find( x, t.left );
            else if( x > t.element )
                return find( x, t.right );
            else
                return t;    // Match
        }

        /**
         * Internal method to print a subtree in sorted order.
         * @param t the node that roots the tree.
         */
        private void printTree( BinaryNode t )
        {
            if( t != null )
            {
                printTree( t.left );
                System.out.println( t.element );
                printTree( t.right );
            }
        }

          /** The tree root. */
        private BinaryNode root;



    }
