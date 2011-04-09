package pkg.voronoi;

public class BeachLineLeafNode {
	Coordinates focusOfArc=null;
	
	
	public void setFocus(double x, double y)
	{
		focusOfArc.setXYcoords(x, y);
	}
	public double getFocusX()
	{
		return focusOfArc.getXcoord();
	}
	public double getFocusY()
	{
		return focusOfArc.getYcoord();
	}


}
