import java.util.ArrayList;
import java.util.Collection;


public class CollectionError {

	public static void main(String[] args) {

		{
			Collection<Object> c = new ArrayList<Object>();
			c.add(c);
			System.out.println(c.hashCode());
		}

		{
Collection<Object> a = new ArrayList<Object>();
Collection<Object> b = new ArrayList<Object>();
a.add(b);
b.add(a);
System.out.println(a.hashCode());
		}



	}

}
