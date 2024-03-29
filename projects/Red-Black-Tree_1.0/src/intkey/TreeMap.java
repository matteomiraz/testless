//import gnu.trove.THashMap.KeyView.EntryIterator;
package intkey;

import java.util.NoSuchElementException;

public class TreeMap  {
    /**
     * The Comparator used to maintain order in this TreeMap, or null if this
     * TreeMap uses its elements natural ordering.
     *
     * @serial
     */


    private transient Entry root = null;

    /**
     * The number of entries in the tree
     */
    private transient int size = 0;

    /**
     * The number of structural modifications to the tree.
     */
    private transient int modCount = 0;

    private void incrementSize() {
        modCount++;
        size++;
    }

    private void decrementSize() {
        modCount++;
        size--;
    }

    // Query Operations

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map.
     */
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.
     *
     * @param key
     *            key whose presence in this map is to be tested.
     *
     * @return <tt>true</tt> if this map contains a mapping for the specified
     *         key.
     * @throws ClassCastException
     *             if the key cannot be compared with the keys currently in the
     *             map.
     * @throws NullPointerException
     *             key is <tt>null</tt> and this map uses natural ordering, or
     *             its comparator does not tolerate <tt>null</tt> keys.
     */
    public boolean containsKey(int key) {
        return getEntry(key) != null;
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value. More formally, returns <tt>true</tt> if and only if
     * this map contains at least one mapping to a value <tt>value</tt> such that
     * <tt>(value==null ? value==null : value.equals(value))</tt>. This operation
     * will probably require time linear in the Map size for most
     * implementations of Map.
     *
     * @param value
     *            value whose presence in this Map is to be tested.
     * @return <tt>true</tt> if a mapping to <tt>value</tt> exists;
     *         <tt>false</tt> otherwise.
     * @since 1.2
     */
    public boolean containsValue(Object value) {
        return (root == null ? false : (value == null ? valueSearchNull(root)
                : valueSearchNonNull(root, value)));
    }

    private boolean valueSearchNull(Entry n) {
        if (n.value == null)
            return true;

        // Check left and right subtrees for value
        return (n.left != null && valueSearchNull(n.left))
                || (n.right != null && valueSearchNull(n.right));
    }

    private boolean valueSearchNonNull(Entry n, Object value) {
        // Check this node for the value
        if (value.equals(n.value))
            return true;

        // Check left and right subtrees for value
        return (n.left != null && valueSearchNonNull(n.left, value))
                || (n.right != null && valueSearchNonNull(n.right, value));
    }

    /**
     * Returns the value to which this map maps the specified key. Returns
     * <tt>null</tt> if the map contains no mapping for this key. A return
     * value of <tt>null</tt> does not <i>necessarily</i> indicate that the
     * map contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to <tt>null</tt>. The <tt>containsKey</tt>
     * operation may be used to distinguish these two cases.
     *
     * @param key
     *            key whose associated value is to be returned.
     * @return the value to which this map maps the specified key, or
     *         <tt>null</tt> if the map contains no mapping for the key.
     * @throws ClassCastException
     *             key cannot be compared with the keys currently in the map.
     * @throws NullPointerException
     *             key is <tt>null</tt> and this map uses natural ordering, or
     *             its comparator does not tolerate <tt>null</tt> keys.
     *
     * @see #containsKey(Object)
     */
    public Object get(int key) {
        Entry p = getEntry(key);
        return (p == null ? null : p.value);
    }


    /**
     * Returns the first (lowest) key currently in this sorted map.
     *
     * @return the first (lowest) key currently in this sorted map.
     * @throws NoSuchElementException
     *             Map is empty.
     */
    public int firstKey() {
        return key(firstEntry());
    }

    /**
     * Returns the last (highest) key currently in this sorted map.
     *
     * @return the last (highest) key currently in this sorted map.
     * @throws NoSuchElementException
     *             Map is empty.
     */
    public int lastKey() {
        return key(lastEntry());
    }


    /**
     * Returns this map's entry for the given key, or <tt>null</tt> if the map
     * does not contain an entry for the key.
     *
     * @return this map's entry for the given key, or <tt>null</tt> if the map
     *         does not contain an entry for the key.
     * @throws ClassCastException
     *             if the key cannot be compared with the keys currently in the
     *             map.
     * @throws NullPointerException
     *             key is <tt>null</tt> and this map uses natural order, or
     *             its comparator does not tolerate * <tt>null</tt> keys.
     */
    private Entry getEntry(int key) {
        Entry p = root;
        int k =  key;
        while (p != null) {
            //int cmp = compare(k, p.key);
            if (k == p.key)
                return p;
            else if (k < p.key)
                p = p.left;
            else
                p = p.right;
        }
        return null;
    }


    /**
     * Returns the key corresponding to the specified Entry. Throw
     * NoSuchElementException if the Entry is <tt>null</tt>.
     */
    private static int key(Entry e) {
        if (e == null)
            throw new NoSuchElementException();
        return e.key;
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for this key, the old value is
     * replaced.
     *
     * @param key
     *            key with which the specified value is to be associated.
     * @param value
     *            value to be associated with the specified key.
     *
     * @return previous value associated with specified key, or <tt>null</tt>
     *         if there was no mapping for key. A <tt>null</tt> return can
     *         also indicate that the map previously associated <tt>null</tt>
     *         with the specified key.
     * @throws ClassCastException
     *             key cannot be compared with the keys currently in the map.
     * @throws NullPointerException
     *             key is <tt>null</tt> and this map uses natural order, or
     *             its comparator does not tolerate <tt>null</tt> keys.
     */
//    @Assertion(value = { @Case(pre = "(root == null)", post = "(root != null && root.consistency())") })
//    @Assertion(value = { @Case(pre = "(comparator!=null &&(root != null && root.wellConnected(null)))", post = "true") })
    public Object put(int key, Object value) {
        Entry t = root;

        if (t == null) {
            incrementSize();
            root = new Entry(key, value, null);
            return null;
        }

        while (true) {
            int cmp = compare(key, t.key);
            if (cmp == 0) {
                return t.setValue(value);
            } else if (cmp < 0) {
                if (t.left != null) {
                    t = t.left;
                } else {
                    incrementSize();
                    t.left = new Entry(key, value, t);
                    fixAfterInsertion(t.left);
                    return null;
                }
            } else { // cmp > 0
                if (t.right != null) {
                    t = t.right;
                } else {
                    incrementSize();
                    t.right = new Entry(key, value, t);
                    fixAfterInsertion(t.right);
                    return null;
                }
            }
        }
        //return null;
    }

    /**
     * Removes the mapping for this key from this TreeMap if present.
     *
     * @param key
     *            key for which mapping should be removed
     * @return previous value associated with specified key, or <tt>null</tt>
     *         if there was no mapping for key. A <tt>null</tt> return can
     *         also indicate that the map previously associated <tt>null</tt>
     *         with the specified key.
     *
     * @throws ClassCastException
     *             key cannot be compared with the keys currently in the map.
     * @throws NullPointerException
     *             key is <tt>null</tt> and this map uses natural order, or
     *             its comparator does not tolerate <tt>null</tt> keys.
     */
    public Object remove(int key) {
        Entry p = getEntry(key);
        if (p == null)
            return null;

        Object oldValue = p.value;
        deleteEntry(p);
        return oldValue;
    }
    private int realSize() {
        if(root==null) {
            return 0;
        }
        return root.size();
    }

    /**
     * Removes all mappings from this TreeMap.
     */
    public void clear() {
        modCount++;
        size = 0;
        root = null;
    }

    /**
     * Compares two keys using the correct comparison method for this TreeMap.
     */
    private int compare(int k1, int k2) {
        if(k1 < k2) {
            return -1;

        }else if(k1==k2) {
            return 0;
        }else {
            return 1;
        }
    }

    /**
     * Test two values for equality. Differs from o1.equals(o2) only in that it
     * copes with <tt>null</tt> o1 properly.
     */
    private static boolean valEquals(Object o1, Object o2) {
        return (o1 == null ? o2 == null : o1.equals(o2));
    }

    private static final boolean RED = false;

    private static final boolean BLACK = true;

    /**
     * Node in the Tree. Doubles as a means to pass key-value pairs back to user
     * (see Map.Entry).
     */

    static public class Entry {
        int key;

        Object value;

        Entry left = null;

        Entry right = null;

        Entry parent;

        boolean color = BLACK;
        public Entry() {
            parent=null;
            value=null;
            key=-1;
        }

        /**
         * Make a new cell with given key, value, and parent, and with
         * <tt>null</tt> child links, and BLACK color.
         */
        Entry(int key, Object value, Entry parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
        public boolean consistency()
        {
            return wellConnected(null) && redConsistency() && blackConsistency() && ordered() ;
        }
        boolean ordered() {
            return ordered(this,new Range());
        }
        boolean ordered(Entry t, Range range) {
            if(t == null) {
                return true;
            }
            if(!range.inRange(t.key)) {
                return false;
            }
            boolean ret=true;
            ret = ret && ordered(t.left,range.setUpper(t.key));
            ret = ret && ordered(t.right,range.setLower(t.key));
            return ret;
        }
        int size() {
            int ls=0,rs=0;
            if(left!=null) {
                ls=left.size();
            }
            if(right!=null) {
                rs=right.size();
            }
            return 1+ls+rs;
        }
        /**
         * Returns true iff this tree is well-connected.
         */

        public boolean wellConnected(Entry expectedParent) {
            boolean ok = true;
            if (expectedParent != parent) {

                return false;
            }

            if (right != null) {
                //ok && is redundant because ok is assigned true
                ok = ok && right.wellConnected(this);
            }

            if (left != null) {

                ok = ok && left.wellConnected(this);
            }

            if(right==left && right!=null && left!=null) {//left!=null is redundant because left==right && right!=null
                return false;
            }

            return ok;
        }

        /**
         * Returns true if no red node in subtree has red children
         *
         * @post returns true if no red node in subtree has red children
         */
        protected boolean redConsistency() {
            boolean ret = true;
            if (color == RED
                    && ((left != null && left.color == RED) || (right != null && right.color == RED)))
                return false;
            if (left != null) {
                ret = ret && left.redConsistency();
            }
            if (right != null) {
                ret = ret && right.redConsistency();
            }
            return ret;
        }

        /**
         * Returns the black height of this subtree.
         *
         * @pre
         * @post returns the black height of this subtree
         */
        protected int blackHeight() {
            int ret = 0;
            if (color == BLACK) {
                ret = 1;
            }
            if (left != null) {
                ret += left.blackHeight();
            }
            return ret;
        }

        /**
         * Returns true if black properties of tree are correct
         *
         * @post returns true if black properties of tree are correct
         */
        protected boolean blackConsistency() {

            if (color != BLACK) // root must be black
            {
                return false;
            }
            // the number of black nodes on way to any leaf must be same
            if (!consistentlyBlackHeight(blackHeight())) {
                return false;
            }
            return true;
        }

        /**
         * Checks to make sure that the black height of tree is height
         *
         * @post checks to make sure that the black height of tree is height
         */
        protected boolean consistentlyBlackHeight(int height) {
            boolean ret = true;
            if (color == BLACK)
                height--;
            if (left == null) {
                ret = ret && (height == 0);
            } else {
                ret = ret && (left.consistentlyBlackHeight(height));
            }
            if (right == null) {
                ret = ret && (height == 0);
            } else {
                ret = ret && (right.consistentlyBlackHeight(height));
            }

            return ret;
        }

        /**
         * Returns the key.
         *
         * @return the key.
         */
        public int getKey() {
            return key;
        }

        /**
         * Returns the value associated with the key.
         *
         * @return the value associated with the key.
         */
        public Object getValue() {
            return value;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was
         *         called.
         */
        public Object setValue(Object value) {
        	Object oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Entry))
                return false;
            Entry e = (Entry) o;

            return key== e.getKey() && valEquals(value, e.getValue());
        }

        public int hashCode() {
            int keyHash = key;
            int valueHash = (value == null ? 0 : value.hashCode());
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * Returns the first Entry in the TreeMap (according to the TreeMap's
     * key-sort function). Returns null if the TreeMap is empty.
     */
    private Entry firstEntry() {
        Entry p = root;
        if (p != null)
            while (p.left != null)
                p = p.left;
        return p;
    }

    /**
     * Returns the last Entry in the TreeMap (according to the TreeMap's
     * key-sort function). Returns null if the TreeMap is empty.
     */
    private Entry lastEntry() {
        Entry p = root;
        if (p != null)
            while (p.right != null)
                p = p.right;
        return p;
    }

    /**
     * Returns the successor of the specified Entry, or null if no such.
     */
    private Entry successor(Entry t) {
        if (t == null)
            return null;
        else if (t.right != null) {
            Entry p = t.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            Entry p = t.parent;
            Entry ch = t;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    /**
     * Balancing operations.
     *
     * Implementations of rebalancings during insertion and deletion are
     * slightly different than the CLR version. Rather than using dummy
     * nilnodes, we use a set of accessors that deal properly with null. They
     * are used to avoid messiness surrounding nullness checks in the main
     * algorithms.
     */

    private static boolean colorOf(Entry p) {
        return (p == null ? BLACK : p.color);
    }

    private static Entry parentOf(Entry p) {
        return (p == null ? null : p.parent);
    }

    private static void setColor(Entry p, boolean c) {
        if (p != null)
            p.color = c;
    }

    private static Entry leftOf(Entry p) {
        return (p == null) ? null : p.left;
    }

    private static Entry rightOf(Entry p) {
        return (p == null) ? null : p.right;
    }

    /** From CLR * */
    private void rotateLeft(Entry p) {
        Entry r = p.right;
        p.right = r.left;
        if (r.left != null)
            r.left.parent = p;
        r.parent = p.parent;
        if (p.parent == null)
            root = r;
        else if (p.parent.left == p)
            p.parent.left = r;
        else
            p.parent.right = r;
        r.left = p;
        p.parent = r;
    }

    /** From CLR * */
    private void rotateRight(Entry p) {
        Entry l = p.left;
        p.left = l.right;
        if (l.right != null)
            l.right.parent = p;
        l.parent = p.parent;
        if (p.parent == null)
            root = l;
        else if (p.parent.right == p)
            p.parent.right = l;
        else
            p.parent.left = l;
        l.right = p;
        p.parent = l;
    }

    /** From CLR * */
    private void fixAfterInsertion(Entry x) {
        x.color = RED;

        while (x != null && x != root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                Entry y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);//bug seeded
                    setColor(parentOf(parentOf(x)), RED);
                    if (parentOf(parentOf(x)) != null)
                        rotateRight(parentOf(parentOf(x)));
                }
            } else {
                Entry y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    if (parentOf(parentOf(x)) != null)
                        rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }

    /**
     * Delete node p, and then rebalance the tree.
     */
    //@Assertion(value = { @Case(pre = "p.wellConnected(p.parent) && (root == null || root.consistency())", post = "(root == null || root.consistency())") })
    private void deleteEntry(//@NonNull
    Entry p) {
        decrementSize();

        // If strictly internal, copy successor's element to p and then make p
        // point to successor.
        if (p.left != null && p.right != null) {
            Entry s = successor(p);
            p.key = s.key;
            p.value = s.value;
            p = s;
        } // p has 2 children

        // Start fixup at replacement node, if it exists.
        Entry replacement = (p.left != null ? p.left : p.right);

        if (replacement != null) {
            // Link replacement to parent
            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left = replacement;
            else
                p.parent.right = replacement;

            // Null out links so they are OK to use by fixAfterDeletion.
            p.left = p.right = p.parent = null;

            // Fix replacement
            if (p.color == BLACK)
                fixAfterDeletion(replacement);
        } else if (p.parent == null) { // return if we are the only node.
            root = null;
        } else { // No children. Use self as phantom replacement and unlink.
            if (p.color == BLACK)
                fixAfterDeletion(p);

            if (p.parent != null) {
                if (p == p.parent.left)
                    p.parent.left = null;
                else if (p == p.parent.right)
                    p.parent.right = null;
                p.parent = null;
            }
        }
    }

    /** From CLR * */
    private void fixAfterDeletion(Entry x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                Entry sib = rightOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                if (colorOf(leftOf(sib)) == BLACK
                        && colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else { // symmetric
                Entry sib = leftOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(sib)) == BLACK
                        && colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }

        setColor(x, BLACK);
    }
}
