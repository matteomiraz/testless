/*
 * ArrayPartition
 *
 *  - Mar 7, 2006
 *
 * Copyright (c) 2003 Kansas State University, Laboratory for the Specification,
 * Analysis, and Transformation of Software
 *
 * This software is licensed under the SAnToS Laboratory Open Academic License.  You
 * should have received a copy of the license with the distribution.  A copy can be
 * found at:
 * http://www.cis.ksu.edu/santos/license.shtml
 * or you can contact the lab at:
 * SAnToS Laboratory
 * 234 Nichols Hall
 * Manhattan, KS 66506, USA
 */
package intelement;

//import static edu.ksu.cis.projects.spex.java.SpExJava.post;
//import static edu.ksu.cis.projects.spex.java.SpExJava.pre;
//import static edu.ksu.cis.projects.spex.java.SpExJava.signals;
//import edu.ksu.cis.projects.spex.java.annotation.Assertion;
//import edu.ksu.cis.projects.spex.java.annotation.Auto;
//import edu.ksu.cis.projects.spex.java.annotation.Case;
//import static edu.ksu.cis.projects.spex.java.SpExJava.*;

public class ArrayPartition {
    private final int[] a;

    private final int n;

    public ArrayPartition(int[] arr) {
    	a = arr;
    	n = arr.length;
    }

//    int lo;
//
//    int hi;
//    @Assertion(value = { @Case(
//            pre = "a !=null &&  n>0 && a.length==n ",
//            post = "isWeaklySorted()") })
    public void partition() {
        int tmp, pivot;
        // assume (n > 2);
        pivot = a[0];
        int lo = 1;
        int hi = n - 1;
        while (lo <= hi) {
            // while(a[lo] <= pivot) //bug found by k=3
            while (lo <= hi && a[lo] <= pivot)
                // fix
                lo++;
            while (a[hi] > pivot)
                hi--;
            if (lo < hi) {
                tmp = a[hi];
                a[hi] = a[lo];
                a[lo] = tmp;
            }
        }
    }
    boolean isWeaklySorted() {
        //assert a.length>2;
        boolean firstPart=true;
        for(int i=1;i<a.length;i++) {
            if(firstPart && a[i]>a[0]) {
                firstPart=false;
            }else if(!firstPart && a[i]<=a[0]){
                return false;
            }
        }
        return true;
    }

    public ArrayPartition(tful.arrays.intArray p0)  { this(p0.toArray() ); }
}
