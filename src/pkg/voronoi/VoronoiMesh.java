package pkg.voronoi;

public class VoronoiMesh {
	
	static HalfEdge heListRoot=null;
	static Cell 	cellListRoot=null;
	
	public static void cellInsert(Cell newCell)
	{
		if(cellListRoot==null)
			cellListRoot=newCell;
		else
		{
			Cell cellptr = cellListRoot;
			while(cellptr.getNext()!=null)
				cellptr=cellptr.getNext();
			cellptr.setNext(newCell);
			newCell.setPrev(cellptr);
		}
	}
	
	public static void heInsert(HalfEdge newHe)
	{
		if(heListRoot==null)
			heListRoot=newHe;
		else
		{
			HalfEdge heptr=heListRoot;
			while(heptr.getNextHe()!=null)
				heptr=heptr.getNextHe();
			heptr.setNextHe(newHe);
			newHe.setPrevHe(heptr);
		}
	}

	public static HalfEdge getHeListRoot() {
		return heListRoot;
	}

	public static void setHeListRoot(HalfEdge heListRoot) {
		VoronoiMesh.heListRoot = heListRoot;
	}
	
	public static void setHalfEdges(double directrixPos)
	{
		HalfEdge he = heListRoot;
		while(he!=null)
		{
			if(he.getSourceVertex()==null && he.getTargetVertex()==null)
			{
				Coordinates heCoords = BeachLineInternalNode.getBrkPoint(he.getLowerArcFocus(), he.getUpperArcFocus(),directrixPos, he.getAssocFlag());
				Coordinates heCoords2 = BeachLineInternalNode.getBrkPoint(he.getLowerArcFocus(), he.getUpperArcFocus(),directrixPos,1-he.getAssocFlag());
				Vertex newVertex = new Vertex(heCoords,he);
				he.setSourceVertex(newVertex);
				newVertex = new Vertex(heCoords2,he);
				he.setTargetVertex(newVertex);
			}
			else if(he.getSourceVertex()==null)
			{
				Coordinates heCoords = BeachLineInternalNode.getBrkPoint(he.getLowerArcFocus(), he.getUpperArcFocus(),directrixPos, he.getAssocFlag());
				Vertex newVertex = new Vertex(heCoords,he);
				he.setSourceVertex(newVertex);
			}
			else
			{
				Coordinates heCoords = BeachLineInternalNode.getBrkPoint(he.getLowerArcFocus(), he.getUpperArcFocus(),directrixPos, he.getAssocFlag());
				Vertex newVertex = new Vertex(heCoords,he);
				he.setTargetVertex(newVertex);
			}

		}
	}
}
