package pkg.voronoi;

public class BeachLineInternalNode {
	
	int 		id=-1;
	Coordinates upperArcFocus;
	Coordinates	lowerArcFocus;
	int 		color=1;
	BeachLineInternalNode leftChild;
	BeachLineInternalNode rightChild;
	BeachLineInternalNode parent;
	HalfEdge 			  newHe;
	
	BeachLineLeafNode     leftLeaf;
	BeachLineLeafNode	  rightLeaf;

	public void setId()
	{
		id=GlobalVariable.getBeachLineId();
	}
	public void setTuple(Coordinates upperFocus,Coordinates lowerFocus)
	{
		upperArcFocus=upperFocus;
		lowerArcFocus=lowerFocus;
	}
	public Coordinates getUpperFocus()
	{
		return upperArcFocus;
	}
	public Coordinates getLowerFocus()
	{
		return lowerArcFocus;
	}
	public int getId()
	{
		return id;
	}
	
	public BeachLineInternalNode getParent() {
		return parent;
	}
	public void setParent(BeachLineInternalNode parent) {
		this.parent = parent;
	}
	
	public BeachLineInternalNode getRightChild() {
		return rightChild;
	}
	public void setRightChild(BeachLineInternalNode rightChild) {
		this.rightChild = rightChild;
	}
	
	public BeachLineInternalNode getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(BeachLineInternalNode leftChild) {
		this.leftChild = leftChild;
	}
	
	
	public BeachLineLeafNode getRightLeaf() {
		return rightLeaf;
	}
	
	public void setRightLeaf(BeachLineLeafNode rightLeaf) {
		this.rightLeaf = rightLeaf;
	}
	
	public BeachLineLeafNode getLeftLeaf() {
		return leftLeaf;
	}
	public void setLeftLeaf(BeachLineLeafNode leftLeaf) {
		this.leftLeaf = leftLeaf;
	}
	
	public Coordinates getBrkPoint(Coordinates focus1, Coordinates focus2, double Xdirectrix)
	{
		Coordinates brkPoint = new Coordinates();
		return brkPoint;
	}
	
}
