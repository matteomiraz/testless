public class Dumb {

	public static boolean nextChar(tful.arrays.intArray ia) {
		int[] a = ia.toArray();

		if(a.length != 9) return false;

		if(a[0] == '[' && a[1] == '(' &&  a[2] == '{' && a[3] == '~' &&  a[4] == 'a' && a[5] == 'x' && a[6] == '}' &&  a[7] == ')' && a[8] == ']') return true;

		return false;
	}
}

