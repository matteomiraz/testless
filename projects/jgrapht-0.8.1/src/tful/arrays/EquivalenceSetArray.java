package tful.arrays;

import java.util.Arrays;
import testful.model.faults.PreconditionViolationException;

public class EquivalenceSetArray {
	private org.jgrapht.experimental.equivalence.EquivalenceSet[] array = {};
	private int n = 0;

	public EquivalenceSetArray() { }
	public EquivalenceSetArray(EquivalenceSetArray a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public EquivalenceSetArray(org.jgrapht.experimental.equivalence.EquivalenceSet[] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public org.jgrapht.experimental.equivalence.EquivalenceSet[] toArray() {
		return array;
	}

	public void addHead(org.jgrapht.experimental.equivalence.EquivalenceSet o) {
		org.jgrapht.experimental.equivalence.EquivalenceSet[] old = array;
		array = new org.jgrapht.experimental.equivalence.EquivalenceSet[n+1];
		array[0] = o;
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(org.jgrapht.experimental.equivalence.EquivalenceSet o) {
		org.jgrapht.experimental.equivalence.EquivalenceSet[] old = array;
		array = new org.jgrapht.experimental.equivalence.EquivalenceSet[n + 1];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o;

		n += 1;
	}

	public org.jgrapht.experimental.equivalence.EquivalenceSet getHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[0];
	}

	public org.jgrapht.experimental.equivalence.EquivalenceSet get(int n) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[n];
	}

	public org.jgrapht.experimental.equivalence.EquivalenceSet getTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[array.length - 1];
	}

	public void setHead(org.jgrapht.experimental.equivalence.EquivalenceSet o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o;
	}

	public void set(int n, org.jgrapht.experimental.equivalence.EquivalenceSet o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o;
	}

	public void setTail(org.jgrapht.experimental.equivalence.EquivalenceSet o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o;
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		org.jgrapht.experimental.equivalence.EquivalenceSet[] old = array;
		n -= 1;
		array = new org.jgrapht.experimental.equivalence.EquivalenceSet[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		org.jgrapht.experimental.equivalence.EquivalenceSet[] old = array;
		n -= 1;
		array = new org.jgrapht.experimental.equivalence.EquivalenceSet[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}

