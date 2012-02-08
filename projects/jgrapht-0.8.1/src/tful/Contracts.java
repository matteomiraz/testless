package tful;

import testful.model.faults.PreconditionViolationException;
import testful.model.faults.PreconditionViolationException.Impl;

public class Contracts {

	private static final int SMALL = 100;

	public static short assertSmall(short value) {
		if(value >= SMALL) throw new PreconditionViolationException.Impl("", null);
		return value;
	}

	public static int assertSmall(int value) {
		if(value >= SMALL) throw new PreconditionViolationException.Impl("", null);
		return value;
	}

	public static long assertSmall(long value) {
		if(value >= SMALL) throw new PreconditionViolationException.Impl("", null);
		return value;
	}

	public static float assertSmall(float value) {
		if(value >= SMALL) throw new PreconditionViolationException.Impl("", null);
		return value;
	}

	public static double assertSmall(double value) {
		if(value >= SMALL) throw new PreconditionViolationException.Impl("", null);
		return value;
	}

	public static double assertLessThan(double expected, double value) {
		if(value >= expected) throw new PreconditionViolationException.Impl("", null);
		return value;
	}

	public static Object ensureType(Object o, Class<?> type) {
		if(o == null) return null;

		if(!type.isAssignableFrom(o.getClass())) throw new PreconditionViolationException.Impl("", null);

		return o;
	}
}
