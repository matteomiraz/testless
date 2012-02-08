package tful.arrays;

import java.util.Arrays;
import testful.model.faults.PreconditionViolationException;

@Deprecated
public class GenericArray<T> {
	private Object[] array = {};
	private int n = 0;

	public GenericArray() { }
	public GenericArray(GenericArray<T> a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public GenericArray(T[] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	@SuppressWarnings("unchecked")
	public T[] toArray() {
		return (T[]) array;
	}

	public void addHead(T o) {
		Object[] old = array;
		array = new Object[n+1];
		array[0] = o;
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(T o) {
		Object[] old = array;
		array = new Object[n + 1];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o;

		n += 1;
	}

	public void setHead(T o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o;
	}

	public void set(int n, T o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o;
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		Object[] old = array;
		n -= 1;
		array = new Object[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		Object[] old = array;
		n -= 1;
		array = new Object[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}
