package tful;

import java.util.Comparator;

import testful.model.faults.PreconditionViolationException;

public class GenericComparator implements Comparator<Object> {

	public int compare(Object o1, Object o2) {
		check(o1);
		check(o2);

		if(o1 != null && o2 != null) return o1.toString().compareTo(o2.toString());

		if(o1 == null && o2 != null) return -1;

		if(o1 != null && o2 == null) return  1;

		// o1 == null && o2 == null
		return 0;
	}

	private void check(Object o1) {
		if(o1 == null) return;

		try {
			if(o1.getClass().getMethod("toString").getDeclaringClass().equals(Object.class))
				throw new PreconditionViolationException.Impl("toString not implemented", null);
		} catch (NoSuchMethodException e) {
			throw new PreconditionViolationException.Impl("cannot inspect toString", e);
		} catch (SecurityException e) {
			throw new PreconditionViolationException.Impl("cannot inspect toString", e);
		}
	}
}
