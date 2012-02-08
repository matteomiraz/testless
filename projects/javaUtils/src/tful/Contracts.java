package tful;

import javux.Collection;
import testful.model.faults.PreconditionViolationException;
import testful.model.faults.PreconditionViolationException.Impl;

public class Contracts {

	private static final int SMALL = 100;

	private static final Impl EXC = new PreconditionViolationException.Impl("", null);

	public static void assertSmall(short value) {
		if(value >= SMALL) throw EXC;
	}

	public static void assertSmall(int value) {
		if(value >= SMALL) throw EXC;
	}

	public static void assertSmall(long value) {
		if(value >= SMALL) throw EXC;
	}

	public static void assertSmall(float value) {
		if(value >= SMALL) throw EXC;
	}

	public static void assertSmall(double value) {
		if(value >= SMALL) throw EXC;
	}

	public static void assertLessThan(double expected, double value) {
		if(value >= expected) throw EXC;
	}

	public static void ensureHashCodeCollection(Collection<?> coll) {
		for (Object o : coll)
			ensureHashCode(o);
	}

	public static void ensureHashCode(Object o) {
		if(o == null) return;
		try {
			if(o.getClass().getMethod("hashCode").getDeclaringClass().getName().equals("java.lang.Object")) throw EXC;
		} catch (Throwable e) {
			throw EXC;
		}
	}
}
