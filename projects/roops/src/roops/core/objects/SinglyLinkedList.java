package roops.core.objects;


//Authors: Marcelo Frias
import roops.util.RoopsArray; @roops.util.BenchmarkClass
/**
 * @Invariant all n: SinglyLinkedListNode | ( ( n in this.header.*next @- null ) => ( n !in n.next.*next @- null ) ) ;
 */
public class SinglyLinkedList {

	
	@roops.util.NrOfGoals(7)
	@roops.util.BenchmarkMethod static
	public void containsTest(roops.core.objects.SinglyLinkedList list, Object value_param) {
		boolean ret_val;
		if (list!=null) {
		  ret_val = list.contains(value_param);
		}
	}

	@roops.util.NrOfGoals(4)
	@roops.util.BenchmarkMethod static
	public void insertBackTest(SinglyLinkedList list, Object arg) {
		if (list!=null) {
		  list.insertBack(arg);
		}
	}

	@roops.util.NrOfGoals(7)
	@roops.util.BenchmarkMethod static
	public void removeTest(SinglyLinkedList list, int index) {
		if (list!=null) {
		  list.remove(index);
		}
	}

	
	public /*@ nullable @*/SinglyLinkedListNode header;



	
	public boolean contains(Object value_param) {
		SinglyLinkedListNode current;
		boolean result;

		current = this.header;
		result = false;
		while (result == false && current != null) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			
			boolean equalVal;

			if (value_param == null && current.value == null){
				{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
				equalVal = true;
			} else if (value_param != null) {

				if (value_param == current.value) { 
					{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
					equalVal = true;
				} else {
					{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}
					equalVal = false;
				}
			} else {
				{roops.util.Goals.reached(4, roops.util.Verdict.REACHABLE);}
				equalVal = false;
			}

			if (equalVal == true) {
				{roops.util.Goals.reached(5, roops.util.Verdict.REACHABLE);}
				result = true;
			}
			current = current.next;
		}
		{roops.util.Goals.reached(6, roops.util.Verdict.REACHABLE);}
		return result;
	}

	


	
	public void remove(int index) {
		
		if (index<0) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			throw new RuntimeException();
		}
		
		SinglyLinkedListNode current;
		current = this.header;
		SinglyLinkedListNode previous;
		previous = null;
		int current_index;
		current_index = 0;
		
		boolean found = false;
		
		while (found==false && current != null) {
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
			
			if (index == current_index) {
				{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
				found = true;
			} else {
				{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}
				current_index = current_index + 1;
				previous = current;
				current = current.next;
			}
		}
		
		if (found==false) {
			{roops.util.Goals.reached(4, roops.util.Verdict.REACHABLE);}			
			throw new RuntimeException();
		}
		
		if (previous == null){
			{roops.util.Goals.reached(5, roops.util.Verdict.REACHABLE);}			
			this.header = current.next;
	    } else {
	    	{roops.util.Goals.reached(6, roops.util.Verdict.REACHABLE);}
			previous.next = current.next;
	    }
	}

	

	public void insertBack(Object arg) {
		SinglyLinkedListNode freshNode = new SinglyLinkedListNode();
		freshNode.value = arg;
		freshNode.next = null;

		if (this.header == null) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			this.header = freshNode;
		} else {
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
			SinglyLinkedListNode current;
			current = this.header;
			while (current.next != null) {
				{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
				current = current.next;
			}
			current.next = freshNode;
		}
		{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}
	}
	
	public SinglyLinkedList() {}
}
/* end roops.core.objects */
