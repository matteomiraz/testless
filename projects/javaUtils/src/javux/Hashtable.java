/*
 * @(#)Hashtable.java	1.116 06/05/26
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javux;

import java.io.StreamCorruptedException;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import tful.Contracts;

/**
 * This class implements a hashtable, which maps keys to values. Any
 * non-<code>null</code> object can be used as a key or as a value. <p>
 *
 * To successfully store and retrieve objects from a hashtable, the
 * objects used as keys must implement the <code>hashCode</code>
 * method and the <code>equals</code> method. <p>
 *
 * An instance of <code>Hashtable</code> has two parameters that affect its
 * performance: <i>initial capacity</i> and <i>load factor</i>.  The
 * <i>capacity</i> is the number of <i>buckets</i> in the hash table, and the
 * <i>initial capacity</i> is simply the capacity at the time the hash table
 * is created.  Note that the hash table is <i>open</i>: in the case of a "hash
 * collision", a single bucket stores multiple entries, which must be searched
 * sequentially.  The <i>load factor</i> is a measure of how full the hash
 * table is allowed to get before its capacity is automatically increased.
 * The initial capacity and load factor parameters are merely hints to
 * the implementation.  The exact details as to when and whether the rehash
 * method is invoked are implementation-dependent.<p>
 *
 * Generally, the default load factor (.75) offers a good tradeoff between
 * time and space costs.  Higher values decrease the space overhead but
 * increase the time cost to look up an entry (which is reflected in most
 * <tt>Hashtable</tt> operations, including <tt>get</tt> and <tt>put</tt>).<p>
 *
 * The initial capacity controls a tradeoff between wasted space and the
 * need for <code>rehash</code> operations, which are time-consuming.
 * No <code>rehash</code> operations will <i>ever</i> occur if the initial
 * capacity is greater than the maximum number of entries the
 * <tt>Hashtable</tt> will contain divided by its load factor.  However,
 * setting the initial capacity too high can waste space.<p>
 *
 * If many entries are to be made into a <code>Hashtable</code>,
 * creating it with a sufficiently large capacity may allow the
 * entries to be inserted more efficiently than letting it perform
 * automatic rehashing as needed to grow the table. <p>
 *
 * This example creates a hashtable of numbers. It uses the names of
 * the numbers as keys:
 * <pre>   {@code
 *   Hashtable<String, Integer> numbers
 *     = new Hashtable<String, Integer>();
 *   numbers.put("one", 1);
 *   numbers.put("two", 2);
 *   numbers.put("three", 3);}</pre>
 *
 * <p>To retrieve a number, use the following code:
 * <pre>   {@code
 *   Integer n = numbers.get("two");
 *   if (n != null) {
 *     System.out.println("two = " + n);
 *   }}</pre>
 *
 * <p>The iterators returned by the <tt>iterator</tt> method of the collections
 * returned by all of this class's "collection view methods" are
 * <em>fail-fast</em>: if the Hashtable is structurally modified at any time
 * after the iterator is created, in any way except through the iterator's own
 * <tt>remove</tt> method, the iterator will throw a {@link
 * ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the future.
 * The Enumerations returned by Hashtable's keys and elements methods are
 * <em>not</em> fail-fast.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>As of the Java 2 platform v1.2, this class was retrofitted to
 * implement the {@link Map} interface, making it a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html"> Java
 * Collections Framework</a>.  Unlike the new collection
 * implementations, {@code Hashtable} is synchronized.
 *
 * @author  Arthur van Hoff
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @version 1.116, 05/26/06
 * @see     Object#equals(java.lang.Object)
 * @see     Object#hashCode()
 * @see     Hashtable#rehash()
 * @see     Collection
 * @see	    Map
 * @see	    HashMap
 * @see	    TreeMap
 * @since JDK1.0
 */
public class Hashtable<K,V>
    extends Dictionary<K,V>
    implements Map<K,V>, Cloneable, java.io.Serializable {

    /**
     * The hash table data.
     */
    private transient Entry[] table;

    /**
     * The total number of entries in the hash table.
     */
    private transient int count;

    /**
     * The table is rehashed when its size exceeds this threshold.  (The
     * value of this field is (int)(capacity * loadFactor).)
     *
     * @serial
     */
    private int threshold;

    /**
     * The load factor for the hashtable.
     *
     * @serial
     */
    private float loadFactor;

    /**
     * The number of times this Hashtable has been structurally modified
     * Structural modifications are those that change the number of entries in
     * the Hashtable or otherwise modify its internal structure (e.g.,
     * rehash).  This field is used to make iterators on Collection-views of
     * the Hashtable fail-fast.  (See ConcurrentModificationException).
     */
    private transient int modCount = 0;

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = 1421746759512286392L;

    /**
     * Constructs a new, empty hashtable with the specified initial
     * capacity and the specified load factor.
     *
     * @param      initialCapacity   the initial capacity of the hashtable.
     * @param      loadFactor        the load factor of the hashtable.
     * @exceptiona  IllegalArgumentException  if the initial capacity is less
     *             than zero, or if the load factor is nonpositive.
     */
    public Hashtable(int initialCapacity, float loadFactor) throws IllegalArgumentException {
    	tful.Contracts.assertSmall(initialCapacity);

	if (initialCapacity < 0)
	    throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal Load: "+loadFactor);

        if (initialCapacity==0)
            initialCapacity = 1;
	this.loadFactor = loadFactor;
	table = new Entry[initialCapacity];
	threshold = (int)(initialCapacity * loadFactor);
    }

    /**
     * Constructs a new, empty hashtable with the specified initial capacity
     * and default load factor (0.75).
     *
     * @param     initialCapacity   the initial capacity of the hashtable.
     * @exceptiona IllegalArgumentException if the initial capacity is less
     *              than zero.
     */
    public Hashtable(int initialCapacity) throws IllegalArgumentException{
	this(initialCapacity, 0.75f);
    }

    /**
     * Constructs a new, empty hashtable with a default initial capacity (11)
     * and load factor (0.75).
     */
    public Hashtable() {
	this(11, 0.75f);
    }

    /**
     * Constructs a new hashtable with the same mappings as the given
     * Map.  The hashtable is created with an initial capacity sufficient to
     * hold the mappings in the given Map and a default load factor (0.75).
     *
     * @param t the map whose mappings are to be placed in this map.
     * @throwsa NullPointerException if the specified map is null.
     * @since   1.2
     */
    public Hashtable(Map<? extends K, ? extends V> t) {
	this(Math.max(2*t.size(), 11), 0.75f);
	Contracts.ensureHashCodeCollection(t.keySet());
	putAll(t);
    }

    /**
     * Returns the number of keys in this hashtable.
     *
     * @return  the number of keys in this hashtable.
     */
    public synchronized int size() {
	return count;
    }

    /**
     * Tests if this hashtable maps no keys to values.
     *
     * @return  <code>true</code> if this hashtable maps no keys to values;
     *          <code>false</code> otherwise.
     */
    public synchronized boolean isEmpty() {
	return count == 0;
    }

    /**
     * Returns an enumeration of the keys in this hashtable.
     *
     * @return  an enumeration of the keys in this hashtable.
     * @see     Enumeration
     * @see     #elements()
     * @see	#keySet()
     * @see	Map
     */
    public synchronized Enumeration<K> keys() {
	return this.<K>getEnumeration(KEYS);
    }

    /**
     * Returns an enumeration of the values in this hashtable.
     * Use the Enumeration methods on the returned object to fetch the elements
     * sequentially.
     *
     * @return  an enumeration of the values in this hashtable.
     * @see     java.util.Enumeration
     * @see     #keys()
     * @see	#values()
     * @see	Map
     */
    public synchronized Enumeration<V> elements() {
	return this.<V>getEnumeration(VALUES);
    }

    /**
     * Tests if some key maps into the specified value in this hashtable.
     * This operation is more expensive than the {@link #containsKey
     * containsKey} method.
     *
     * <p>Note that this method is identical in functionality to
     * {@link #containsValue containsValue}, (which is part of the
     * {@link Map} interface in the collections framework).
     *
     * @param      value   a value to search for
     * @return     <code>true</code> if and only if some key maps to the
     *             <code>value</code> argument in this hashtable as
     *             determined by the <tt>equals</tt> method;
     *             <code>false</code> otherwise.
     * @exceptiona  NullPointerException  if the value is <code>null</code>
     */
    public synchronized boolean contains(Object value) {
	if (value == null) {
	    throw new NullPointerException();
	}

	Entry tab[] = table;
	for (int i = tab.length ; i-- > 0 ;) {
	    for (Entry<K,V> e = tab[i] ; e != null ; e = e.next) {
		if (e.value.equals(value)) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * Returns true if this hashtable maps one or more keys to this value.
     *
     * <p>Note that this method is identical in functionality to {@link
     * #contains contains} (which predates the {@link Map} interface).
     *
     * @param value value whose presence in this hashtable is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value
     * @throwsa NullPointerException  if the value is <code>null</code>
     * @since 1.2
     */
    public boolean containsValue(Object value) throws java.lang.ClassCastException{
	return contains(value);
    }

    /**
     * Tests if the specified object is a key in this hashtable.
     *
     * @param   key   possible key
     * @return  <code>true</code> if and only if the specified object
     *          is a key in this hashtable, as determined by the
     *          <tt>equals</tt> method; <code>false</code> otherwise.
     * @throwsa NullPointerException NullPointerException  if the key is <code>null</code>
     * @see     #contains(Object)
     */
    public synchronized boolean containsKey(Object key) throws java.lang.ClassCastException{
	Entry tab[] = table;
	int hash = key.hashCode();
	int index = (hash & 0x7FFFFFFF) % tab.length;
	for (Entry<K,V> e = tab[index] ; e != null ; e = e.next) {
	    if ((e.hash == hash) && e.key.equals(key)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key.equals(k))},
     * then this method returns {@code v}; otherwise it returns
     * {@code null}.  (There can be at most one such mapping.)
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     *         {@code null} if this map contains no mapping for the key
     * @throwsa NullPointerException if the specified key is null
     * @see     #put(Object, Object)
     */
    public synchronized V get(Object key) throws java.lang.ClassCastException{
	Entry tab[] = table;
	int hash = key.hashCode();
	int index = (hash & 0x7FFFFFFF) % tab.length;
	for (Entry<K,V> e = tab[index] ; e != null ; e = e.next) {
	    if ((e.hash == hash) && e.key.equals(key)) {
		return e.value;
	    }
	}
	return null;
    }

    /**
     * Increases the capacity of and internally reorganizes this
     * hashtable, in order to accommodate and access its entries more
     * efficiently.  This method is called automatically when the
     * number of keys in the hashtable exceeds this hashtable's capacity
     * and load factor.
     */
    protected void rehash() {
	int oldCapacity = table.length;
	Entry[] oldMap = table;

	int newCapacity = oldCapacity * 2 + 1;
	Entry[] newMap = new Entry[newCapacity];

	modCount++;
	threshold = (int)(newCapacity * loadFactor);
	table = newMap;

	for (int i = oldCapacity ; i-- > 0 ;) {
	    for (Entry<K,V> old = oldMap[i] ; old != null ; ) {
		Entry<K,V> e = old;
		old = old.next;

		int index = (e.hash & 0x7FFFFFFF) % newCapacity;
		e.next = newMap[index];
		newMap[index] = e;
	    }
	}
    }

    /**
     * Maps the specified <code>key</code> to the specified
     * <code>value</code> in this hashtable. Neither the key nor the
     * value can be <code>null</code>. <p>
     *
     * The value can be retrieved by calling the <code>get</code> method
     * with a key that is equal to the original key.
     *
     * @param      key     the hashtable key
     * @param      value   the value
     * @return     the previous value of the specified key in this hashtable,
     *             or <code>null</code> if it did not have one
     * @exceptiona NullPointerException  if the key or value is
     *               <code>null</code>
     * @see     Object#equals(Object)
     * @see     #get(Object)
     */
    public synchronized V put(K key, V value) throws java.lang.ClassCastException, java.lang.IllegalArgumentException{
    	Contracts.ensureHashCode(key);

	// Make sure the value is not null
	if (value == null) {
	    throw new NullPointerException();
	}

	// Makes sure the key is not already in the hashtable.
	Entry tab[] = table;
	int hash = key.hashCode();
	int index = (hash & 0x7FFFFFFF) % tab.length;
	for (Entry<K,V> e = tab[index] ; e != null ; e = e.next) {
	    if ((e.hash == hash) && e.key.equals(key)) {
		V old = e.value;
		e.value = value;
		return old;
	    }
	}

	modCount++;
	if (count >= threshold) {
	    // Rehash the table if the threshold is exceeded
	    rehash();

            tab = table;
            index = (hash & 0x7FFFFFFF) % tab.length;
	}

	// Creates the new entry.
	Entry<K,V> e = tab[index];
	tab[index] = new Entry<K,V>(hash, key, value, e);
	count++;
	return null;
    }

    /**
     * Removes the key (and its corresponding value) from this
     * hashtable. This method does nothing if the key is not in the hashtable.
     *
     * @param   key   the key that needs to be removed
     * @return  the value to which the key had been mapped in this hashtable,
     *          or <code>null</code> if the key did not have a mapping
     * @throwsa NullPointerException NullPointerException  if the key is <code>null</code>
     */
    public synchronized V remove(Object key) throws java.lang.ClassCastException{
	Entry tab[] = table;
	int hash = key.hashCode();
	int index = (hash & 0x7FFFFFFF) % tab.length;
	for (Entry<K,V> e = tab[index], prev = null ; e != null ; prev = e, e = e.next) {
	    if ((e.hash == hash) && e.key.equals(key)) {
		modCount++;
		if (prev != null) {
		    prev.next = e.next;
		} else {
		    tab[index] = e.next;
		}
		count--;
		V oldValue = e.value;
		e.value = null;
		return oldValue;
	    }
	}
	return null;
    }

    /**
     * Copies all of the mappings from the specified map to this hashtable.
     * These mappings will replace any mappings that this hashtable had for any
     * of the keys currently in the specified map.
     *
     * @param t mappings to be stored in this map
     * @throwsa NullPointerException if the specified map is null
     * @since 1.2
     */
    public synchronized void putAll(Map<? extends K, ? extends V> t) throws java.lang.ClassCastException, java.lang.IllegalArgumentException{
    	Contracts.ensureHashCodeCollection(t.keySet());
        for (Map.Entry<? extends K, ? extends V> e : t.entrySet())
            put(e.getKey(), e.getValue());
    }

    /**
     * Clears this hashtable so that it contains no keys.
     */
    public synchronized void clear() {
	Entry tab[] = table;
	modCount++;
	for (int index = tab.length; --index >= 0; )
	    tab[index] = null;
	count = 0;
    }

    /**
     * Creates a shallow copy of this hashtable. All the structure of the
     * hashtable itself is copied, but the keys and values are not cloned.
     * This is a relatively expensive operation.
     *
     * @return  a clone of the hashtable
     */
    public synchronized Object clone() {
	try {
	    Hashtable<K,V> t = (Hashtable<K,V>) super.clone();
	    t.table = new Entry[table.length];
	    for (int i = table.length ; i-- > 0 ; ) {
		t.table[i] = (table[i] != null)
		    ? (Entry<K,V>) table[i].clone() : null;
	    }
	    t.keySet = null;
	    t.entrySet = null;
            t.values = null;
	    t.modCount = 0;
	    return t;
	} catch (CloneNotSupportedException e) {
	    // this shouldn't happen, since we are Cloneable
	    throw new InternalError();
	}
    }

    /**
     * Returns a string representation of this <tt>Hashtable</tt> object
     * in the form of a set of entries, enclosed in braces and separated
     * by the ASCII characters "<tt>,&nbsp;</tt>" (comma and space). Each
     * entry is rendered as the key, an equals sign <tt>=</tt>, and the
     * associated element, where the <tt>toString</tt> method is used to
     * convert the key and element to strings.
     *
     * @return  a string representation of this hashtable
     */
    public synchronized String toString() {
	int max = size() - 1;
	if (max == -1)
	    return "{}";

	StringBuilder sb = new StringBuilder();
	Iterator<Map.Entry<K,V>> it = entrySet().iterator();

	sb.append('{');
	for (int i = 0; ; i++) {
	    Map.Entry<K,V> e = it.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key.toString());
	    sb.append('=');
	    sb.append(value == this ? "(this Map)" : value.toString());

	    if (i == max)
		return sb.append('}').toString();
	    sb.append(", ");
	}
    }


    private <T> Enumeration<T> getEnumeration(int type) {
	if (count == 0) {
	    return (Enumeration<T>)emptyEnumerator;
	} else {
	    return new Enumerator<T>(type, false);
	}
    }

    private <T> Iterator<T> getIterator(int type) {
	if (count == 0) {
	    return (Iterator<T>) emptyIterator;
	} else {
	    return new Enumerator<T>(type, true);
	}
    }

    // Views

    /**
     * Each of these fields are initialized to contain an instance of the
     * appropriate view the first time this view is requested.  The views are
     * stateless, so there's no reason to create more than one of each.
     */
    private transient volatile Set<K> keySet = null;
    private transient volatile Set<Map.Entry<K,V>> entrySet = null;
    private transient volatile Collection<V> values = null;

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     *
     * @since 1.2
     */
    public Set<K> keySet() {
	if (keySet == null)
	    keySet = Collections.synchronizedSet(new KeySet(), this);
	return keySet;
    }

    private class KeySet extends AbstractSet<K> {
        public Iterator<K> iterator() {
	    return getIterator(KEYS);
        }
        public int size() {
            return count;
        }
        public boolean contains(Object o) throws java.lang.ClassCastException{
            return containsKey(o);
        }
        public boolean remove(Object o) throws java.lang.ClassCastException{
            return Hashtable.this.remove(o) != null;
        }
        public void clear() {
            Hashtable.this.clear();
        }
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @since 1.2
     */
    public Set<Map.Entry<K,V>> entrySet() {
	if (entrySet==null)
	    entrySet = Collections.synchronizedSet(new EntrySet(), this);
	return entrySet;
    }

    private class EntrySet extends AbstractSet<Map.Entry<K,V>> {
        public Iterator<Map.Entry<K,V>> iterator() {
	    return getIterator(ENTRIES);
        }

	public boolean add(Map.Entry<K,V> o) throws java.lang.IllegalStateException{
	    return super.add(o);
	}

        public boolean contains(Object o) throws java.lang.ClassCastException{
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry entry = (Map.Entry)o;
            Object key = entry.getKey();
            Entry[] tab = table;
            int hash = key.hashCode();
            int index = (hash & 0x7FFFFFFF) % tab.length;

            for (Entry e = tab[index]; e != null; e = e.next)
                if (e.hash==hash && e.equals(entry))
                    return true;
            return false;
        }

        public boolean remove(Object o) throws java.lang.ClassCastException{
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<K,V> entry = (Map.Entry<K,V>) o;
	    K key = entry.getKey();
            Entry[] tab = table;
            int hash = key.hashCode();
            int index = (hash & 0x7FFFFFFF) % tab.length;

            for (Entry<K,V> e = tab[index], prev = null; e != null;
                 prev = e, e = e.next) {
                if (e.hash==hash && e.equals(entry)) {
                    modCount++;
                    if (prev != null)
                        prev.next = e.next;
                    else
                        tab[index] = e.next;

                    count--;
                    e.value = null;
                    return true;
                }
            }
            return false;
        }

        public int size() {
            return count;
        }

        public void clear() {
            Hashtable.this.clear();
        }
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @since 1.2
     */
    public Collection<V> values() {
	if (values==null)
	    values = Collections.synchronizedCollection(new ValueCollection(),
                                                        this);
        return values;
    }

    private class ValueCollection extends AbstractCollection<V> {
        public Iterator<V> iterator() {
	    return getIterator(VALUES);
        }
        public int size() {
            return count;
        }
        public boolean contains(Object o) throws java.lang.ClassCastException{
            return containsValue(o);
        }
        public void clear() {
            Hashtable.this.clear();
        }
    }

    // Comparison and hashing

    /**
     * Compares the specified Object with this Map for equality,
     * as per the definition in the Map interface.
     *
     * @param  o object to be compared for equality with this hashtable
     * @return true if the specified Object is equal to this Map
     * @see Map#equals(Object)
     * @since 1.2
     */
    public synchronized boolean equals(Object o) {
	if (o == this)
	    return true;

	if (!(o instanceof Map))
	    return false;
	Map<K,V> t = (Map<K,V>) o;
	if (t.size() != size())
	    return false;

        try {
            Iterator<Map.Entry<K,V>> i = entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry<K,V> e = i.next();
                K key = e.getKey();
                V value = e.getValue();
                if (value == null) {
                    if (!(t.get(key)==null && t.containsKey(key)))
                        return false;
                } else {
                    if (!value.equals(t.get(key)))
                        return false;
                }
            }
        } catch (ClassCastException unused)   {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }

	return true;
    }

    /**
     * Returns the hash code value for this Map as per the definition in the
     * Map interface.
     *
     * @see Map#hashCode()
     * @since 1.2
     */
    public synchronized int hashCode() {
        /*
         * This code detects the recursion caused by computing the hash code
         * of a self-referential hash table and prevents the stack overflow
         * that would otherwise result.  This allows certain 1.1-era
         * applets with self-referential hash tables to work.  This code
         * abuses the loadFactor field to do double-duty as a hashCode
         * in progress flag, so as not to worsen the space performance.
         * A negative load factor indicates that hash code computation is
         * in progress.
         */
        int h = 0;
        if (count == 0 || loadFactor < 0)
            return h;  // Returns zero

        loadFactor = -loadFactor;  // Mark hashCode computation in progress
        Entry[] tab = table;
        for (int i = 0; i < tab.length; i++)
            for (Entry e = tab[i]; e != null; e = e.next)
                h += e.key.hashCode() ^ e.value.hashCode();
        loadFactor = -loadFactor;  // Mark hashCode computation complete

	return h;
    }

    /**
     * The put method used by readObject. This is provided because put
     * is overridable and should not be called in readObject since the
     * subclass will not yet be initialized.
     *
     * <p>This differs from the regular put method in several ways. No
     * checking for rehashing is necessary since the number of elements
     * initially in the table is known. The modCount is not incremented
     * because we are creating a new instance. Also, no return value
     * is needed.
     */
    private void reconstitutionPut(Entry[] tab, K key, V value)
        throws StreamCorruptedException
    {
        if (value == null) {
            throw new java.io.StreamCorruptedException();
        }
        // Makes sure the key is not already in the hashtable.
        // This should not happen in deserialized version.
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (Entry<K,V> e = tab[index] ; e != null ; e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                throw new java.io.StreamCorruptedException();
            }
        }
        // Creates the new entry.
        Entry<K,V> e = tab[index];
        tab[index] = new Entry<K,V>(hash, key, value, e);
        count++;
    }

    /**
     * Hashtable collision list.
     */
    private static class Entry<K,V> implements Map.Entry<K,V> {
	int hash;
	K key;
	V value;
	Entry<K,V> next;

	protected Entry(int hash, K key, V value, Entry<K,V> next) {
	    this.hash = hash;
	    this.key = key;
	    this.value = value;
	    this.next = next;
	}

	protected Object clone() {
	    return new Entry<K,V>(hash, key, value,
				  (next==null ? null : (Entry<K,V>) next.clone()));
	}

	// Map.Entry Ops

	public K getKey() throws java.lang.IllegalStateException{
	    return key;
	}

	public V getValue() throws java.lang.IllegalStateException{
	    return value;
	}

	public V setValue(V value) throws java.lang.ClassCastException, java.lang.IllegalArgumentException, java.lang.IllegalStateException{
	    if (value == null)
		throw new NullPointerException();

	    V oldValue = this.value;
	    this.value = value;
	    return oldValue;
	}

	public boolean equals(Object o) {
	    if (!(o instanceof Map.Entry))
		return false;
	    Map.Entry e = (Map.Entry)o;

	    return (key==null ? e.getKey()==null : key.equals(e.getKey())) &&
	       (value==null ? e.getValue()==null : value.equals(e.getValue()));
	}

	public int hashCode() {
	    return hash ^ (value==null ? 0 : value.hashCode());
	}

	public String toString() {
	    return key.toString()+"="+value.toString();
	}
    }

    // Types of Enumerations/Iterations
    private static final int KEYS = 0;
    private static final int VALUES = 1;
    private static final int ENTRIES = 2;

    /**
     * A hashtable enumerator class.  This class implements both the
     * Enumeration and Iterator interfaces, but individual instances
     * can be created with the Iterator methods disabled.  This is necessary
     * to avoid unintentionally increasing the capabilities granted a user
     * by passing an Enumeration.
     */
    private class Enumerator<T> implements Enumeration<T>, Iterator<T> {
	Entry[] table = Hashtable.this.table;
	int index = table.length;
	Entry<K,V> entry = null;
	Entry<K,V> lastReturned = null;
	int type;

	/**
	 * Indicates whether this Enumerator is serving as an Iterator
	 * or an Enumeration.  (true -> Iterator).
	 */
	boolean iterator;

	/**
	 * The modCount value that the iterator believes that the backing
	 * Hashtable should have.  If this expectation is violated, the iterator
	 * has detected concurrent modification.
	 */
	protected int expectedModCount = modCount;

	Enumerator(int type, boolean iterator) {
	    this.type = type;
	    this.iterator = iterator;
	}

	public boolean hasMoreElements() {
	    Entry<K,V> e = entry;
	    int i = index;
	    Entry[] t = table;
	    /* Use locals for faster loop iteration */
	    while (e == null && i > 0) {
		e = t[--i];
	    }
	    entry = e;
	    index = i;
	    return e != null;
	}

	public T nextElement() throws java.util.NoSuchElementException{
	    Entry<K,V> et = entry;
	    int i = index;
	    Entry[] t = table;
	    /* Use locals for faster loop iteration */
	    while (et == null && i > 0) {
		et = t[--i];
	    }
	    entry = et;
	    index = i;
	    if (et != null) {
		Entry<K,V> e = lastReturned = entry;
		entry = e.next;
		return type == KEYS ? (T)e.key : (type == VALUES ? (T)e.value : (T)e);
	    }
	    throw new NoSuchElementException("Hashtable Enumerator");
	}

	// Iterator methods
	public boolean hasNext() {
	    return hasMoreElements();
	}

	public T next()  throws NoSuchElementException {
	    if (modCount != expectedModCount)
		throw new ConcurrentModificationException();
	    return nextElement();
	}

	public void remove() throws java.lang.IllegalStateException, java.lang.UnsupportedOperationException{
	    if (!iterator)
		throw new UnsupportedOperationException();
	    if (lastReturned == null)
		throw new IllegalStateException("Hashtable Enumerator");
	    if (modCount != expectedModCount)
		throw new ConcurrentModificationException();

	    synchronized(Hashtable.this) {
		Entry[] tab = Hashtable.this.table;
		int index = (lastReturned.hash & 0x7FFFFFFF) % tab.length;

		for (Entry<K,V> e = tab[index], prev = null; e != null;
		     prev = e, e = e.next) {
		    if (e == lastReturned) {
			modCount++;
			expectedModCount++;
			if (prev == null)
			    tab[index] = e.next;
			else
			    prev.next = e.next;
			count--;
			lastReturned = null;
			return;
		    }
		}
		throw new ConcurrentModificationException();
	    }
	}
    }


    private static Enumeration emptyEnumerator = new EmptyEnumerator();
    private static Iterator emptyIterator = new EmptyIterator();

    /**
     * A hashtable enumerator class for empty hash tables, specializes
     * the general Enumerator
     */
    private static class EmptyEnumerator implements Enumeration<Object> {

	EmptyEnumerator() {
	}

	public boolean hasMoreElements() {
	    return false;
	}

	public Object nextElement() throws java.util.NoSuchElementException{
	    throw new NoSuchElementException("Hashtable Enumerator");
	}
    }


    /**
     * A hashtable iterator class for empty hash tables
     */
    private static class EmptyIterator implements Iterator<Object> {

	EmptyIterator() {
	}

	public boolean hasNext() {
	    return false;
	}

	public Object next()  throws NoSuchElementException {
	    throw new NoSuchElementException("Hashtable Iterator");
	}

	public void remove() throws java.lang.IllegalStateException, java.lang.UnsupportedOperationException{
	    throw new IllegalStateException("Hashtable Iterator");
	}

    }

}
