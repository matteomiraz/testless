package tful.arrays;

import java.util.Arrays;
import testful.model.faults.PreconditionViolationException;

public class doubleArrayArray {
	private double[][] array = {};
	private int n = 0;

	public doubleArrayArray() { }
	public doubleArrayArray(doubleArrayArray a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public doubleArrayArray(double[][] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public double[][] toArray() {
		return array;
	}

	public void addHead(doubleArray o) {
		double[][] old = array;
		array = new double[n+1][];
		array[0] = o.toArray();
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(doubleArray o) {
		double[][] old = array;
		array = new double[n + 1][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o.toArray();

		n += 1;
	}

	public doubleArray getHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new doubleArray(array[0]);
	}

	public doubleArray get(int n) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new doubleArray(array[n]);
	}

	public doubleArray getTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new doubleArray(array[array.length - 1]);
	}

	public void setHead(doubleArray o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o.toArray();
	}

	public void set(int n, doubleArray o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o.toArray();
	}

	public void setTail(doubleArray o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o.toArray();
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		double[][] old = array;
		n -= 1;
		array = new double[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		double[][] old = array;
		n -= 1;
		array = new double[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}

