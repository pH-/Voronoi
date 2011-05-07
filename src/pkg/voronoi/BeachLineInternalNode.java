package pkg.voronoi;

public class BeachLineInternalNode {
	
	int 		id=-1;
	Coordinates upperArcFocus;
	Coordinates	lowerArcFocus;
	int			brkPtFlg;
	int 		color=1;
	BeachLineInternalNode leftChild;
	BeachLineInternalNode rightChild;
	BeachLineInternalNode parent;
	HalfEdge 			  assocHe;
	
	BeachLineLeafNode     leftLeaf;
	BeachLineLeafNode	  rightLeaf;

	public void setId()
	{
		id=GlobalVariable.getBeachLineId();
	}
	public void setTuple(Coordinates upperFocus,Coordinates lowerFocus)
	{
		upperArcFocus.setXYcoords(upperFocus.getXcoord(), upperFocus.getYcoord());
		lowerArcFocus.setXYcoords(lowerFocus.getXcoord(), lowerFocus.getYcoord());
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
	
	public Coordinates getBrkPoint(Coordinates focus1, Coordinates focus2, double Xdirectrix,int whichBrkPt)
	{
		Coordinates[] brkPoint = new Coordinates[2];
		double k1,k2,k3,a,b,c,a1,b1,a2,b2,a3,brkx1,brkx2,brky1,brky2;
		a1=focus1.getXcoord(); b1=focus1.getYcoord();
		a2=focus2.getXcoord(); b2=focus2.getYcoord();
		a3=Xdirectrix;
		k1=Math.pow(a1,2)-Math.pow(a2,2)+Math.pow(b1, 2)-Math.pow(b2, 2);
		k2=b1-b2;
		k3=k1-2*k2*b1;
		a=4*k2;
		b=-4*k2*(1-2*k2*(a3-a1));
		c=Math.pow(k3, 2)-4*Math.pow(k2, 2)*(Math.pow(a3, 2)-Math.pow(a1, 2));
		brkx1=(-b+Math.sqrt(Math.pow(b, 2)-4*a*c))/2*a;
		brkx2=(-b-Math.sqrt(Math.pow(b, 2)-4*a*c))/2*a;
		brky1=Math.sqrt(Math.pow(a3-brkx1, 2) - Math.pow(brkx1-a1,2)) +b1;
		brky2=Math.sqrt(Math.pow(a3-brkx2, 2) - Math.pow(brkx2-a1,2)) +b1;
		
		brkPoint[0].setXYcoords(brkx1, brky1);
		brkPoint[1].setXYcoords(brkx2, brky2);
		
		if(whichBrkPt==0)
		{
			if(brky1<brky2)	
				return brkPoint[0];
			else
				return brkPoint[1];
		}
		else
		{
			if(brky1>brky2)	
				return brkPoint[0];
			else
				return brkPoint[1];
		}
	}
	
	public HalfEdge getAssocHe() {
		return assocHe;
	}
	
	public void setAssocHe(HalfEdge assocHe) {
		this.assocHe = assocHe;
	}
	
	public int getBrkPtFlg() {
		return brkPtFlg;
	}
	
	public void setBrkPtFlg(int brkPtFlg) {
		this.brkPtFlg = brkPtFlg;
	}
	
}
