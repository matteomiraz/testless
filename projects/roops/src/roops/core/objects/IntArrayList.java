package roops.core.objects;

//Authors: Marcelo Frias
/**
 * source Apache Harmony software http://harmony.apache.org/
 *         http://www.docjar.com/html/api/java/util/ArrayList.java.html
 */
import roops.util.RoopsArray; @roops.util.BenchmarkClass
public class IntArrayList {

	@roops.util.NrOfGoals(6)
	@roops.util.BenchmarkMethod static
	public void containsTest(IntArrayList arrayList, int o) {
		if (arrayList != null) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			boolean ret_val = arrayList.contains(o);
		} else {
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
		}
	}
	
	@roops.util.NrOfGoals(12)
	@roops.util.BenchmarkMethod static
	public void removeTest(IntArrayList arrayList, int index) {
		if (arrayList != null) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			int ret_val = arrayList.remove(index);
		} else {
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
		}
	}

	@roops.util.NrOfGoals(18)
	@roops.util.BenchmarkMethod static
	public void addTest(IntArrayList arrayList, int o) {
		if (arrayList != null) {
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
			boolean ret_val = arrayList.add(o);
		} else {
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
		}
	}

	private int firstIndex;

	private int lastIndex;

	private int[] array;

	private int modCount;

	public boolean contains(int elem) {

		for (int i = firstIndex; i < lastIndex; i++) {
			{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
			if (elem== array[i]) {
				{roops.util.Goals.reached(4, roops.util.Verdict.REACHABLE);}
				return true;
			} else {
				{roops.util.Goals.reached(5, roops.util.Verdict.REACHABLE);}
			}
		}
		{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}

		return false;
	}

	public boolean add(int elem) {
		if (lastIndex == RoopsArray.getLength(array)) {
			{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
			growAtEnd(1);
		} else {
			{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}
		}
		array[lastIndex] = elem;
		lastIndex++;
		modCount++;
		return true;
	}

	private void growAtEnd(int required) {
		int size = lastIndex - firstIndex;
		int sufix_length = RoopsArray.getLength(array) - lastIndex;
		if (firstIndex >= required - sufix_length) {
			{roops.util.Goals.reached(4, roops.util.Verdict.REACHABLE);}
			
			int newLast = lastIndex - firstIndex;
			if (size > 0) {
				{roops.util.Goals.reached(6, roops.util.Verdict.REACHABLE);}
				add_system_arraycopy(array, firstIndex, array, 0, size);

				int start;
				if (newLast < firstIndex) {
					{roops.util.Goals.reached(8, roops.util.Verdict.REACHABLE);}
					start = firstIndex;
				} else {
					{roops.util.Goals.reached(9, roops.util.Verdict.REACHABLE);}
					start = newLast;
				}

			} else {
				{roops.util.Goals.reached(7, roops.util.Verdict.REACHABLE);}
			}
			firstIndex = 0;
			lastIndex = newLast;
		} else {

			{roops.util.Goals.reached(5, roops.util.Verdict.REACHABLE);}

			int increment = size >> 1;

			if (required > increment) {
				{roops.util.Goals.reached(10, roops.util.Verdict.REACHABLE);}
				increment = required;
			} else {
				{roops.util.Goals.reached(11, roops.util.Verdict.REACHABLE);}
			}
			if (increment < 12) {
				{roops.util.Goals.reached(12, roops.util.Verdict.REACHABLE);}
				increment = 12;
			} else {
				{roops.util.Goals.reached(13, roops.util.Verdict.REACHABLE);}
			}
			int[] newArray = newElementArray(size + increment);
			if (size > 0) {
				{roops.util.Goals.reached(14, roops.util.Verdict.REACHABLE);}				
				add_system_arraycopy(array, firstIndex, newArray, 0, size);
				firstIndex = 0;
				lastIndex = size;
			} else {
				{roops.util.Goals.reached(15, roops.util.Verdict.REACHABLE);}
			}
			array = newArray;
			
		}
		
	}

	private static int[] newElementArray(int size) {
		int[] result = new int[size];
		return result;
	}

	public int remove(int location) {
		int result;
		int size = lastIndex - firstIndex;
		if (0 <= location && location < size) {
			{roops.util.Goals.reached(2, roops.util.Verdict.REACHABLE);}
			if (location == size - 1) {
				{roops.util.Goals.reached(4, roops.util.Verdict.REACHABLE);}
				lastIndex--;
				result = array[lastIndex];
			} else {
				{roops.util.Goals.reached(5, roops.util.Verdict.REACHABLE);}

			  if (location == 0) {
				{roops.util.Goals.reached(6, roops.util.Verdict.REACHABLE);}
				result = array[firstIndex];
				firstIndex++;
			  } else {
				{roops.util.Goals.reached(7, roops.util.Verdict.REACHABLE);}
     			int elementIndex = firstIndex + location;
				result = array[elementIndex];
				int size_div_2 = size >> 1;
				if (location < size_div_2) {
					{roops.util.Goals.reached(8, roops.util.Verdict.REACHABLE);}
					system_arraycopy(array, firstIndex, array, firstIndex + 1, location);
					firstIndex++;
				} else {
					{roops.util.Goals.reached(9, roops.util.Verdict.REACHABLE);}
					system_arraycopy(array, elementIndex + 1, array, elementIndex, size - location - 1);
					lastIndex--;
				}
			  }
		    }
			
			if (firstIndex == lastIndex) {
				firstIndex = lastIndex = 0;
			}
		} else {
			{roops.util.Goals.reached(3, roops.util.Verdict.REACHABLE);}
			throw new IndexOutOfBoundsException();
		}

		modCount++;
		return result;
	}

	private void system_arraycopy(int[] src, int srcPos, int[] dest, int destPos, int length) {
		for (int i = 0; i < length; i++) {
			{roops.util.Goals.reached(10, roops.util.Verdict.REACHABLE);}
			int srcIndex = srcPos + i;
			int destIndex = destPos + i;
			dest[destIndex] = src[srcIndex];
		}
		{roops.util.Goals.reached(11, roops.util.Verdict.REACHABLE);}
	}

	private void add_system_arraycopy(int[] src, int srcPos, int[] dest, int destPos, int length) {
		for (int i = 0; i < length; i++) {
			{roops.util.Goals.reached(16, roops.util.Verdict.REACHABLE);}
			int srcIndex = srcPos + i;
			int destIndex = destPos + i;
			dest[destIndex] = src[srcIndex];
		}
		{roops.util.Goals.reached(17, roops.util.Verdict.REACHABLE);}
	}


	public IntArrayList() {}

}
/* end roops.core.objects */

