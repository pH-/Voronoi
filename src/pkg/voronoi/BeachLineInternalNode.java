package pkg.voronoi;

public class BeachLineInternalNode {
	
	int 		id;
	int			neighbour1Id;
	int			neighbour2Id;
	BeachLineInternalNode leftChild;
	BeachLineInternalNode rightChild;
	BeachLineInternalNode parent;
	
	BeachLineLeafNode     leftLeaf;
	BeachLineLeafNode	  rightLeaf;

	public void setId(int idNumber)
	{
		id=idNumber;
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
	
}
