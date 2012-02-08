package tful;

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
}
