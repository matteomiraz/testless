package tful.arrays;

import java.util.Arrays;

import org.apache.commons.math.FieldElement;
import testful.model.faults.PreconditionViolationException;

@SuppressWarnings("unchecked")
public class FieldElementArray<T extends FieldElement<T>> {
	private Object[] array = {};
	private int n = 0;

	public FieldElementArray() { }
	public FieldElementArray(FieldElementArray<T> a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public FieldElementArray(T[] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public T[] toArray() {
		return (T[]) array;
	}

	public void addHead(T o) {
		Object[] old = array;
		array = new org.apache.commons.math.FieldElement[n+1];
		array[0] = o;
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(T o) {
		Object[] old = array;
		array = new org.apache.commons.math.FieldElement[n + 1];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o;

		n += 1;
	}

	public T getHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return (T) array[0];
	}

	public T get(int n) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return (T) array[n];
	}

	public T getTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return (T) array[array.length - 1];
	}

	public void setHead(T o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o;
	}

	public void set(int n, T o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o;
	}

	public void setTail(T o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o;
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		Object[] old = array;
		n -= 1;
		array = new org.apache.commons.math.FieldElement[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		Object[] old = array;
		n -= 1;
		array = new org.apache.commons.math.FieldElement[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}

