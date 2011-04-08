package pkg.voronoi;

public class GlobalVariable {
	
	static int eventIdCtr=-1;
	static int beachLineIdCtr=-1;
	
	public static int getEventId()
	{
		eventIdCtr++;
		return eventIdCtr;
	}
	public static int getBeachLineId()
	{
		beachLineIdCtr++;
		return beachLineIdCtr;
	}
}
