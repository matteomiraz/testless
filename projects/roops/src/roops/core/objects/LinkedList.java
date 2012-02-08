package roops.core.objects;

//Authors: Marcelo Frias
import roops.util.RoopsArray; @roops.util.BenchmarkClass
/**
 * @Invariant 
 *		( this.header!=null ) &&
 *		( this.header.next!=null ) &&
 *		( this.header.previous!=null ) &&
 *		( this.size==#(this.header.*next @- null)-1 ) &&
 *		( this.size>=0 ) &&
 *		(all n: LinkedListNode | ( n in this.header.*next @- null ) => (
 *				  n!=null &&
 *				  n.previous!=null &&
 *				  n.previous.next==n &&
 *				  n.next!=null &&
 *				  n.next.previous==n )) ; 
 *
 */
public class LinkedList {
	
	@roops.util.NrOfGoals(3)
	@roops.util.BenchmarkMethod static
	public void addLastTest(LinkedList list, Object o) {
		if (list!=null) {
		  boolean ret_val = list.addLast(o);
		}
	}

	@roops.util.NrOfGoals(3)
	@roops.util.BenchmarkMethod static
	public void containsTest(LinkedList list, Object arg) {
		if (list!=null) {
		  boolean ret_val = list.contains(arg);
		}
	}

	@roops.util.NrOfGoals(10)
	@roops.util.BenchmarkMethod static
	public void removeIndexTest(LinkedList list, int index) {
		if (list!=null) {
		  Object ret_val = list.removeIndex(index);
		}
	}


	public /*@ nullable @*/LinkedListNode header;
	
	public int size;
	
	public int modCount;

	private void init() {
		header = createHeaderNode();
	}


	//-----------------------------------------------------------------------
	private int indexOf(Object value) {
		int i = 0;
		for (LinkedListNode node = header.next; node != header; node = node.next) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			if (isEqualValue(node.value, value)) {
				{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
				return i;
			}
			i++;
		}
		{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
		return -1;
	}

	
	public boolean contains(Object arg) {
		return indexOf(arg) != -1;
	}

	
	public Object removeIndex(int index) {
		LinkedListNode node = getNode(index, false);
		Object oldValue = node.value;
		removeNode(node);
		
		{roops.util.Goals.reached(9, roops.util.Verdict.REACHABLE);}
		return oldValue;
	}

	
	public boolean addLast(Object o) {
		addNodeBefore(header, o);
		return true;
	}

	private boolean isEqualValue(Object value1, Object value2) {
		boolean ret_val;
		if (value1 == value2) {
			ret_val=true;
		} else {
			if (value1==null) {
				ret_val = false;
			} else {
				ret_val = value1.equals(value2);
			}
		}
		return ret_val;
	}

	private LinkedListNode createHeaderNode() {
		LinkedListNode linkedListNode = new LinkedListNode();
		linkedListNode.next = linkedListNode;
		linkedListNode.previous = linkedListNode;
		return linkedListNode;
	}


	private LinkedListNode createNode(Object value) {
		{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		LinkedListNode node = new LinkedListNode();
		node.previous = node;
		node.next = node;
		node.value = value;
		return node;
	}

	

	private void addNodeBefore(LinkedListNode node, Object value) {
		LinkedListNode newNode = createNode(value);
		{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
		
		addNode(newNode, node);
	}

	private void addNode(LinkedListNode nodeToInsert,
			LinkedListNode insertBeforeNode) {
		nodeToInsert.next = insertBeforeNode;
		nodeToInsert.previous = insertBeforeNode.previous;
		insertBeforeNode.previous.next = nodeToInsert;
		insertBeforeNode.previous = nodeToInsert;
		size++;
		modCount++;
		
		{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
	}


	private void removeNode(LinkedListNode node) {
		node.previous.next = node.next;
		node.next.previous = node.previous;
		size--;
		modCount++;
		{roops.util.Goals.reached(8, roops.util.Verdict.REACHABLE);}
	}


	private LinkedListNode getNode(int index, boolean endMarkerAllowed)
			 {
		// Check the index is within the bounds
		if (index < 0) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			throw new RuntimeException();
		}
		if (!endMarkerAllowed && index == size) {
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
			throw new RuntimeException();
		}
		if (index > size) {
			{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
			throw new RuntimeException();
		}
		// Search the list and get the node
		LinkedListNode node;
		int size_div_2 = size >> 1;
		
		if (index < size_div_2) {
			{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}
			// Search forwards
			node = header.next;
			for (int currentIndex = 0; currentIndex < index; currentIndex++) {
				{roops.util.Goals.reached(4, roops.util.Verdict.REACHABLE);}
				node = node.next;
			}
		} else {
			
			{roops.util.Goals.reached(5, roops.util.Verdict.REACHABLE);}
			
			// Search backwards
			node = header;
			for (int currentIndex = size; currentIndex > index; currentIndex--) {
				{roops.util.Goals.reached(6, roops.util.Verdict.REACHABLE);}
				node = node.previous;
			}
		}
		{roops.util.Goals.reached(7, roops.util.Verdict.REACHABLE);}
		return node;
	}


}
/* end roops.core.objects */
