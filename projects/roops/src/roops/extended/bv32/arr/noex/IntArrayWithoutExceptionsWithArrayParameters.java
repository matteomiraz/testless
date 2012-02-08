package roops.extended.bv32.arr.noex;

// Authors: Nikolai Tillmann and Christoph Csallner
import roops.util.RoopsArray; @roops.util.BenchmarkClass
public class IntArrayWithoutExceptionsWithArrayParameters
{
    /* begin Basic */
    @roops.util.NrOfGoals(2)
    @roops.util.BenchmarkMethod static
    public void DirectWriteFollowedByDirectRead(int[] a, int v)
    {
        if (a != null && RoopsArray.getLength(a) >= 10)
        {
            a[4] = v;
            if (a[4] == 42)
            {roops.util.Goals.reached(0);}
            else
            {roops.util.Goals.reached(1);}
        }
    }

    @roops.util.NrOfGoals(3)
    @roops.util.BenchmarkMethod static
    public void DirectWriteFollowedByIndirectRead(int[] a, int j, int v)
    {
        if (a != null && RoopsArray.getLength(a) >= 10)
        {
            if (j >= 0 && j < RoopsArray.getLength(a))
            {
                a[4] = v;
                if (a[j] == 42)
                {roops.util.Goals.reached(0);}
                else
                {roops.util.Goals.reached(1);}
            }
            else
            {roops.util.Goals.reached(2);}
        }
    }

    @roops.util.NrOfGoals(3)
    @roops.util.BenchmarkMethod static
    public void IndirectWriteFollowedByIndirectRead(int[] a, int i, int j, int v)
    {
        if (a != null)
        {
            if (i >= 0 && i < RoopsArray.getLength(a) &&
                j >= 0 && j < RoopsArray.getLength(a))
            {
                a[i] = v;
                if (a[j] == 42)
                {roops.util.Goals.reached(0);}
                else
                {roops.util.Goals.reached(1);}
            }
            else
            {roops.util.Goals.reached(2);}
        }
    }
    /* end */

    public static void testful_DirectWriteFollowedByDirectRead(tful.arrays.intArray p0, int p1)  { DirectWriteFollowedByDirectRead(p0.toArray() , p1); }
    public static void testful_DirectWriteFollowedByIndirectRead(tful.arrays.intArray p0, int p1, int p2)  { DirectWriteFollowedByIndirectRead(p0.toArray() , p1, p2); }
    public static void testful_IndirectWriteFollowedByIndirectRead(tful.arrays.intArray p0, int p1, int p2, int p3)  { IndirectWriteFollowedByIndirectRead(p0.toArray() , p1, p2, p3); }

}
/* end roops.extended.bv32.arr.noex */
