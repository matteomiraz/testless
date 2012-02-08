package tful.arrays;

import java.util.Arrays;

import testful.model.faults.PreconditionViolationException;

public class ObjectArrayArray {
	private Object[][] array = {};
	private int n = 0;

	public ObjectArrayArray() { }
	public ObjectArrayArray(ObjectArrayArray a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public ObjectArrayArray(Object[][] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public Object[][] toArray() {
		return array;
	}

	public void addHead(ObjectArray o) {
		Object[][] old = array;
		array = new Object[n+1][];
		array[0] = o.toArray();
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(ObjectArray o) {
		Object[][] old = array;
		array = new Object[n + 1][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o.toArray();

		n += 1;
	}

	public ObjectArray getHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new ObjectArray(array[0]);
	}

	public ObjectArray get(int n) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new ObjectArray(array[n]);
	}

	public ObjectArray getTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new ObjectArray(array[array.length - 1]);
	}

	public void setHead(ObjectArray o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o.toArray();
	}

	public void set(int n, ObjectArray o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o.toArray();
	}

	public void setTail(ObjectArray o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o.toArray();
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		Object[][] old = array;
		n -= 1;
		array = new Object[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		Object[][] old = array;
		n -= 1;
		array = new Object[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}

