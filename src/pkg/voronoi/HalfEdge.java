package pkg.voronoi;

public class HalfEdge {
	
	Vertex sourceVertex, targetVertex;
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

}
