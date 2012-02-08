package tful.arrays;

import java.util.Arrays;

import testful.model.faults.PreconditionViolationException;

public class longArrayArray {
	private long[][] array = {};
	private int n = 0;

	public longArrayArray() { }
	public longArrayArray(longArrayArray a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public longArrayArray(long[][] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public long[][] toArray() {
		return array;
	}

	public void addHead(longArray o) {
		long[][] old = array;
		array = new long[n+1][];
		array[0] = o.toArray();
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(longArray o) {
		long[][] old = array;
		array = new long[n + 1][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o.toArray();

		n += 1;
	}

	public longArray getHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new longArray(array[0]);
	}

	public longArray get(int n) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new longArray(array[n]);
	}

	public longArray getTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new longArray(array[array.length - 1]);
	}

	public void setHead(longArray o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o.toArray();
	}

	public void set(int n, longArray o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o.toArray();
	}

	public void setTail(longArray o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o.toArray();
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		long[][] old = array;
		n -= 1;
		array = new long[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		long[][] old = array;
		n -= 1;
		array = new long[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}

