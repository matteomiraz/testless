package tful.arrays;

import java.util.Arrays;

import org.apache.commons.math.FieldElement;
import testful.model.faults.PreconditionViolationException;

@SuppressWarnings("unchecked")
public class FieldElementArrayArray<T extends FieldElement<T>> {
	private Object[][] array = {};
	private int n = 0;

	public FieldElementArrayArray() { }
	public FieldElementArrayArray(FieldElementArrayArray<T> a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public FieldElementArrayArray(FieldElement<T>[][] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public T[][] toArray() {
		return (T[][]) array;
	}

	public void addHead(FieldElementArray<T> o) {
		Object[][] old = array;
		array = new FieldElement[n+1][];
		array[0] = o.toArray();
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(FieldElementArray<T> o) {
		Object[][] old = array;
		array = new FieldElement[n + 1][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o.toArray();

		n += 1;
	}

	public FieldElementArray<T> getHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new FieldElementArray<T>((T[]) array[0]);
	}

	public FieldElementArray<T> get(int n) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new FieldElementArray<T>((T[]) array[n]);
	}

	public FieldElementArray<T> getTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new FieldElementArray<T>((T[]) array[array.length - 1]);
	}

	public void setHead(FieldElementArray<T> o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o.toArray();
	}

	public void set(int n, FieldElementArray<T> o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o.toArray();
	}

	public void setTail(FieldElementArray<T> o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o.toArray();
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		Object[][] old = array;
		n -= 1;
		array = new FieldElement[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		Object[][] old = array;
		n -= 1;
		array = new FieldElement[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}

