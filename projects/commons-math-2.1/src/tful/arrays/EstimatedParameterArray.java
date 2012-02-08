package tful.arrays;

import java.util.Arrays;
import testful.model.faults.PreconditionViolationException;

@SuppressWarnings("deprecation")
public class EstimatedParameterArray {
	private org.apache.commons.math.estimation.EstimatedParameter[] array = {};
	private int n = 0;

	public EstimatedParameterArray() { }
	public EstimatedParameterArray(EstimatedParameterArray a) {
		if(a == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = a.toArray();
		this.n = array.length;
	}
	public EstimatedParameterArray(org.apache.commons.math.estimation.EstimatedParameter[] array) {
		if(array == null) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);
		this.array = array;
		this.n = array.length;
	}

	public org.apache.commons.math.estimation.EstimatedParameter[] toArray() {
		return array;
	}

	public void addHead(org.apache.commons.math.estimation.EstimatedParameter o) {
		org.apache.commons.math.estimation.EstimatedParameter[] old = array;
		array = new org.apache.commons.math.estimation.EstimatedParameter[n+1];
		array[0] = o;
		for (int i = 0; i < n; i++)
			array[i+1] = old[i];

		n += 1;
	}

	public void addTail(org.apache.commons.math.estimation.EstimatedParameter o) {
		org.apache.commons.math.estimation.EstimatedParameter[] old = array;
		array = new org.apache.commons.math.estimation.EstimatedParameter[n + 1];
		for (int i = 0; i < n; i++)
			array[i] = old[i];

		array[n] = o;

		n += 1;
	}

	public org.apache.commons.math.estimation.EstimatedParameter getHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[0];
	}

	public org.apache.commons.math.estimation.EstimatedParameter get(int n) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[n];
	}

	public org.apache.commons.math.estimation.EstimatedParameter getTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		return array[array.length - 1];
	}

	public void setHead(org.apache.commons.math.estimation.EstimatedParameter o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[0] = o;
	}

	public void set(int n, org.apache.commons.math.estimation.EstimatedParameter o) {
		if(n < 0 || n >= this.n) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[n] = o;
	}

	public void setTail(org.apache.commons.math.estimation.EstimatedParameter o) {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		array[array.length-1] = o;
	}

	public void delHead() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		org.apache.commons.math.estimation.EstimatedParameter[] old = array;
		n -= 1;
		array = new org.apache.commons.math.estimation.EstimatedParameter[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i+1];
	}

	public void delTail() {
		if(n <= 0) throw new PreconditionViolationException.Impl("Invalid precondition while manipulating the array", null);

		org.apache.commons.math.estimation.EstimatedParameter[] old = array;
		n -= 1;
		array = new org.apache.commons.math.estimation.EstimatedParameter[n];
		for (int i = 0; i < n; i++)
			array[i] = old[i];
	}
}

