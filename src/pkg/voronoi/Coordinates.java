package pkg.voronoi;

public class Coordinates {
	
	double xcoord;
	double ycoord;
	
	Coordinates()
	{
		xcoord=0;
		ycoord=0;
	}
	Coordinates(double x, double y)
	{
		xcoord = x;
		ycoord = y;
	}
	public void setXYcoords(double x, double y)
	{
		xcoord = x;
		ycoord = y;
	}
	public double getXcoord()
	{
		return xcoord;
	}
	public double getYcoord()
	{
		return ycoord;
	}
	public boolean equals(Coordinates secondObj)
	{
		if(this.xcoord==secondObj.getXcoord() && this.ycoord==secondObj.getYcoord())
			return true;
		else
			return false;
	}
}
