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

}
