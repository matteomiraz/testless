package tful.arrays;

import java.util.Arrays;
import testful.model.faults.PreconditionViolationException;

public class shortArray {
	private short[] array = {};
	private int n = 0;

	public shortArray() { }
	public shortArray(shortArray a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public shortArray(short[] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public short[] toArray() {
		return array;
	}

	public void addHead(short o) {
		short[] old = array;
		array = new short[n+1];
		array[0] = o;
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(short o) {
		short[] old = array;
		array = new short[n + 1];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o;

		n += 1;
	}

	public short getHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[0];
	}

	public short get(int n) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[n];
	}

	public short getTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[array.length - 1];
	}

	public void setHead(short o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o;
	}

	public void set(int n, short o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o;
	}

	public void setTail(short o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o;
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		short[] old = array;
		n -= 1;
		array = new short[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		short[] old = array;
		n -= 1;
		array = new short[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}

