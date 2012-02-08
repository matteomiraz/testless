package tful.arrays;

import java.math.BigDecimal;
import java.util.Arrays;

import testful.model.faults.PreconditionViolationException;

public class BigDecimalArrayArray {
	private BigDecimal[][] array = {};
	private int n = 0;

	public BigDecimalArrayArray() { }
	public BigDecimalArrayArray(BigDecimalArrayArray a) {
		if(a == null)throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public BigDecimalArrayArray(BigDecimal[][] array) {
		if(array == null)throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public BigDecimal[][] toArray() {
		return array;
	}

	public void addHead(BigDecimalArray o) {
		BigDecimal[][] old = array;
		array = new BigDecimal[n+1][];
		array[0] = o.toArray();
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(BigDecimalArray o) {
		BigDecimal[][] old = array;
		array = new BigDecimal[n + 1][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o.toArray();

		n += 1;
	}

	public BigDecimalArray getHead() {
		if(n <= 0)throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new BigDecimalArray(array[0]);
	}

	public BigDecimalArray get(int n) {
		if(n < 0 || n >= this.n)throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new BigDecimalArray(array[n]);
	}

	public BigDecimalArray getTail() {
		if(n <= 0)throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return new BigDecimalArray(array[array.length - 1]);
	}

	public void setHead(BigDecimalArray o) {
		if(n <= 0)throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o.toArray();
	}

	public void set(int n, BigDecimalArray o) {
		if(n < 0 || n >= this.n)throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o.toArray();
	}

	public void setTail(BigDecimalArray o) {
		if(n <= 0)throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o.toArray();
	}

	public void delHead() {
		if(n <= 0)throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		BigDecimal[][] old = array;
		n -= 1;
		array = new BigDecimal[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0)throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		BigDecimal[][] old = array;
		n -= 1;
		array = new BigDecimal[n][];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}

