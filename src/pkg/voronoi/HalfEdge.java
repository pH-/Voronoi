package pkg.voronoi;

public class HalfEdge {
	
	Vertex sourceVertex, targetVertex;
	Coordinates upperArcFocus = new Coordinates();
	Coordinates	lowerArcFocus = new Coordinates();
	Coordinates dirVector	  = new Coordinates();
	int 		dirty=0;
	int 		assocFlag;
	Cell   parentCell;
	HalfEdge next, prev;
	
	
	public Cell getParentCell() {
		return parentCell;
	}

	public void setParentCell(Cell parentCell) {
		this.parentCell = parentCell;
	}

	public void setSourceVertex(Vertex srcVertex)
	{
		sourceVertex = srcVertex;
	}
	
	public void setTargetVertex(Vertex trgtVertex)
	{
		targetVertex = trgtVertex;
	}
	
	public Vertex getTargetVertex()
	{
		return targetVertex;
	}
	
	public Vertex getSourceVertex()
	{
		return sourceVertex;
	}
	
	public void setNextHe(HalfEdge nxtHalfEdge)
	{
		next = nxtHalfEdge;
	}
	
	public void setPrevHe(HalfEdge prevHalfEdge)
	{
		prev = prevHalfEdge;
	}
	
	public HalfEdge getNextHe()
	{
		return next;
	}
	
	public HalfEdge getPrevHe()
	{
		return prev;
	}

	public Coordinates getUpperArcFocus() {
		return upperArcFocus;
	}

	public void setUpperArcFocus(Coordinates upperArcFocus) {
		this.upperArcFocus.setXYcoords(upperArcFocus.getXcoord(),upperArcFocus.getYcoord());
	}

	public Coordinates getLowerArcFocus() {
		return lowerArcFocus;
	}

	public void setLowerArcFocus(Coordinates lowerArcFocus) {
		this.lowerArcFocus.setXYcoords(lowerArcFocus.getXcoord(), lowerArcFocus.getYcoord());
	}

	public int getAssocFlag() {
		return assocFlag;
	}

	public void setAssocFlag(int assocFlag) {
		this.assocFlag = assocFlag;
	}

	public boolean isDirty() {
		if(dirty==1)
			return true;
		else
			return false;
	}

	public void makeDirty() {
		this.dirty = 1;
	}

	public Coordinates getDirVector() {
		return dirVector;
	}

	public void setDirVector(Coordinates dirVector) {
		this.dirVector.setXYcoords(dirVector.getXcoord(), dirVector.getYcoord());
	}

}
