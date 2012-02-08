package tful.arrays;

import java.util.Arrays;
import testful.model.faults.PreconditionViolationException;

public class BigIntegerArray {
	private java.math.BigInteger[] array = {};
	private int n = 0;

	public BigIntegerArray() { }
	public BigIntegerArray(BigIntegerArray a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public BigIntegerArray(java.math.BigInteger[] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public java.math.BigInteger[] toArray() {
		return array;
	}

	public void addHead(java.math.BigInteger o) {
		java.math.BigInteger[] old = array;
		array = new java.math.BigInteger[n+1];
		array[0] = o;
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(java.math.BigInteger o) {
		java.math.BigInteger[] old = array;
		array = new java.math.BigInteger[n + 1];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o;

		n += 1;
	}

	public java.math.BigInteger getHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[0];
	}

	public java.math.BigInteger get(int n) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[n];
	}

	public java.math.BigInteger getTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[array.length - 1];
	}

	public void setHead(java.math.BigInteger o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o;
	}

	public void set(int n, java.math.BigInteger o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o;
	}

	public void setTail(java.math.BigInteger o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o;
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		java.math.BigInteger[] old = array;
		n -= 1;
		array = new java.math.BigInteger[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		java.math.BigInteger[] old = array;
		n -= 1;
		array = new java.math.BigInteger[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}

	public String toString() {
		return "size: " + n + "  " + Arrays.toString(array);
	}
}

