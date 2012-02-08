package roops.extended.bv64;

// Authors: Marcelo Frias
import roops.util.RoopsArray; @roops.util.BenchmarkClass
public class LongArithmetics {

        /* begin Methods */
	@roops.util.NrOfGoals(2)
    	@roops.util.BenchmarkMethod static
  	public void Method1(long x1) 
	{
		if ((Long.MAX_VALUE + x1) < 0L)
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		else
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
	}



	@roops.util.NrOfGoals(2)
    	@roops.util.BenchmarkMethod static
  	public void Method2(long x1, long x2) 
	{
		if ((x1*x2)/x2 == x1)
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		else
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
	}



	@roops.util.NrOfGoals(2)
    	@roops.util.BenchmarkMethod static
  	public void Method3(long x1, long x2) 
	{
		if ((x1/x2)*x2 != x1)
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		else
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
	}



	@roops.util.NrOfGoals(2)
    	@roops.util.BenchmarkMethod static
  	public void Method4(long x1, long x2) 
	{
		if ((9223372036854770000L + x1) > (9214364837600036513L + x2))
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		else
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
	}



	@roops.util.NrOfGoals(2)
    	@roops.util.BenchmarkMethod static
  	public void Method5(long x1, long x2, long x3, long x4) 
	{
		if ((((x1/(x4+922337203600000000L)) * x2) >  ((x1/(9214364837L+x4)) * x3)) && (x4 < 1000L))
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		else
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
	}



	@roops.util.NrOfGoals(2)
    	@roops.util.BenchmarkMethod static
  	public void Method6(long x1, long x2, long x3, long x4, long x5, long x6, long x7) 
	{
		if (((((((x1*x2) * x3) * x4) * x5) * x6) * x7) == (Long.MAX_VALUE/2L))
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		else
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
	}



	@roops.util.NrOfGoals(2)
    	@roops.util.BenchmarkMethod static
  	public void Method7(long x1, long x2, long x3, long x4, long x5, long x6, long x7, long x8, long x9, long x10)
	{
		if (((((((((((x1*x1) + (x2*x2)) + (x3*x3)) + (x4*x4)) + (x5*x5)) + (x6*x6)) + (x7*x7)) + (x8*x8)) + (x9*x9)) + (x10*x10)) == (Long.MAX_VALUE/542346L))
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		else
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
	}





	@roops.util.NrOfGoals(2)
    	@roops.util.BenchmarkMethod static
  	public void Method8(long x1, long x2, long x3, long x4, long x5, long x6, long x7, long x8, long x9, long x10, long x11, long x12)
	{
		if ((((((x1<=x2) && (x2>x3)) && ((x1*3479234792L)==x4)) && (((x4+x5)/(x6*x7))>230928034985304958L)) && ((x7/((x8+x9) + x10))==x10)) && ((x10*535034850249L) <= ((34095204934093409L + x11) + x12)))
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		else
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
	}





	@roops.util.NrOfGoals(2)
    	@roops.util.BenchmarkMethod static
  	public void Method9(long x1, long x2, long x3, long x4, long x5, long x6, long x7, long x8, long x9, long x10, long x11, long x12, long x13, long x14, long x15)
	{
		if ((((((((x1+x2)/239872938472213L) + ((x3+x4)/934898348272837L)) + ((x5+x6)/8947934794879483948L)) + ((x7+x8)/343847232124345L)) + ((x9+x10)/9982728762834283L)) + ((x11+x12)/3879237429742929387L)) + (((x13+x14) + x15)/(-23842938479229293L)) == 9223372036854775807L)
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		else
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
	}




	@roops.util.NrOfGoals(2)
    	@roops.util.BenchmarkMethod static
  	public void Method10(long x1, long x2, long x3, long x4, long x5, long x6, long x7, long x8, long x9, long x10, long x11, long x12, long x13, long x14, long x15, long x16, long x17, long x18, long x19, long x20)
	{
	 
		if (((x1*x2 * x3* x4* x5* x6* x7* x8* x9* x10* x11* x12* x13* x14* x15* x16* x17* x18* x19* x20) != 0L) && (((((((((((((((((((((x1/9223372036854775807L) + (x2/9223372036854775807L)) + (x3/9223372036854775807L)) + (x4/9223372036854775807L)) + (x5/9223372036854775807L)) + (x6/9223372036854775807L)) + (x7/9223372036854775807L)) + (x8/9223372036854775807L)) + (x9/9223372036854775807L)) + (x10/9223372036854775807L)) + (x11/9223372036854775807L)) + (x12/9223372036854775807L)) + (x13/9223372036854775807L)) + (x14/9223372036854775807L)) + (x15/9223372036854775807L)) + (x16/9223372036854775807L)) + (x17/9223372036854775807L)) + (x18/9223372036854775807L)) + (x19/9223372036854775807L)) + (x20/9223372036854775807L)) == 2L))
			{roops.util.Goals.reached(0, roops.util.Verdict.REACHABLE);}
		else
			{roops.util.Goals.reached(1, roops.util.Verdict.REACHABLE);}
	}
	/* end */


}
/* end roops.extended.bv64 */

