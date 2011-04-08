package pkg.voronoi;

public class BeachLineNode {
	
	Coordinates focusOfArc=null;						//value present for leaf node only
	int 		id;
	int			neighbour1Id;
	int			neighbour2Id;

	public void setId(int idNumber)
	{
		id=idNumber;
	}
	public void setFocus(double x, double y)
	{
		focusOfArc.setXYcoords(x, y);
	}
	public void setNeighbours(int nId1,int nId2)
	{
		neighbour1Id=nId1;
		neighbour2Id=nId2;
	}
	public int getNeighbour1()
	{
		return neighbour1Id;
	}
	public int getNeighbour2()
	{
		return neighbour2Id;
	}
	public int getId()
	{
		return id;
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
