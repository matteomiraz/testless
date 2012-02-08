package tful.arrays;

import java.util.Arrays;
import testful.model.faults.PreconditionViolationException;

public class StringArrayArray {
	private String[][] array = {};
	private int n = 0;

	public StringArrayArray() { }
	public StringArrayArray(StringArrayArray a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public StringArrayArray(String[][] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public String[][] toArray() {
		return array;
	}

	public void addHead(StringArray o) {
		String[][] old = array;
		array = new String[n+1][];
		array[0] = o.toArray();
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(StringArray o) {
		String[][] old = array;
		array = new String[n + 1][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o.toArray();

		n += 1;
	}

	public StringArray getHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new StringArray(array[0]);
	}

	public StringArray get(int n) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new StringArray(array[n]);
	}

	public StringArray getTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new StringArray(array[array.length - 1]);
	}

	public void setHead(StringArray o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o.toArray();
	}

	public void set(int n, StringArray o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o.toArray();
	}

	public void setTail(StringArray o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o.toArray();
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		String[][] old = array;
		n -= 1;
		array = new String[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		String[][] old = array;
		n -= 1;
		array = new String[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}

