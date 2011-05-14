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

	public BeachLineInternalNode()
	{
		upperArcFocus = new Coordinates();
		lowerArcFocus = new Coordinates();
	}
	public void setId()
	{
		id=GlobalVariable.getBeachLineId();
	}
	public void setId(int i)
	{
		id = i;
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
	
	public void setUpperFocus(Coordinates newCoords)
	{
		upperArcFocus.setXYcoords(newCoords.getXcoord(), newCoords.getYcoord());
	}
	public void setLowerFocus(Coordinates newCoords)
	{
		lowerArcFocus.setXYcoords(newCoords.getXcoord(), newCoords.getYcoord());
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
	
	public static Coordinates getBrkPoint(Coordinates focus1, Coordinates focus2, Coordinates directrix,double radius, int whichBrkPt)
	{
		Coordinates[] brkPoint = new Coordinates[2];
		brkPoint[0] = new Coordinates();
		brkPoint[1] = new Coordinates();
		double 	 Xdirectrix = directrix.getXcoord();
		double a1,b1,a2,b2,a3,brkx1,brkx2,brky1,brky2,termx1,termx2,termx3,termy1,termy2,termy3;
		
		a1=focus1.getXcoord(); b1=focus1.getYcoord();
		a2=focus2.getXcoord(); b2=focus2.getYcoord();
		a3=Xdirectrix;
		
		if(a1==a2 && b1!=b2)
		{
			brkx1=brkx2=((-4*a2*a2)+(4*a3*a3)-(b1*b1)-(b2*b2)+(2*b1*b2))/(8*a3-a2);
			brky1=brky2=0.5*(b1+b2);
		}
		else if(b1==b2 && a1!=a2)
		{
			brkx1=brkx2=0.5*(a1+a2);
			brky1=b2-(Math.sqrt((a3-a1)*(a3-a2)));
			brky2=b2+(Math.sqrt((a3-a1)*(a3-a2)));
		}
		else
		{
			termx1=(a1*b1*b1)+(a1*b2*b2)-(2*a1*b1*b2)+(a2*b1*b1)-(2*a3*b1*b1)+(a2*b2*b2)-(2*a3*b2*b2)-(2*a2*b1*b2)+(4*a3*b1*b2);
			termx2=2*Math.sqrt((a3*a3 - (a1*a3) - (a2*a3) + (a1*a2)) * (b2-b1)*(b2-b1) * (a1*a1-(2*a2*a1) + (a2*a2) + (b1*b1) + (b2*b2) - (2*b1*b2)));
			termx3=a1*a1*a1 - a2*a1*a1 -a2*a2*a1 + a2*a2*a2;
			brkx1 = (0.5/((a2-a1)*(a2-a1)))*(termx1-termx2+termx3);
			brkx2 = (0.5/((a2-a1)*(a2-a1)))*(termx1+termx2+termx3);
			
			termy1= -a2*b1*b1 + a3*b1*b1 + (a1 + a2 - 2*a3)*b2*b1 - (a1-a3)*b2*b2;
			termy2= (a3*a3-a1*a3-a2*a3+a1*a2)*(b2-b1)*(b2-b1)*(a1*a1-2*a2*a1+a2*a2+b1*b1+b2*b2-2*b1*b2);
			termy3= (a2-a1)*(b2-b1);
			brky1 = (termy1+Math.sqrt(termy2))/termy3;
			brky2 = (termy1-Math.sqrt(termy2))/termy3;
		}
		
		brkPoint[0].setXYcoords(brkx1, brky1);
		brkPoint[1].setXYcoords(brkx2, brky2);
		
		if(whichBrkPt==0)
		{
			if(brky1<brky2)	
				return brkPoint[0];
			else
				return brkPoint[1];
		}
		else if(whichBrkPt==1)
		{
			if(brky1>brky2)	
				return brkPoint[0];
			else
				return brkPoint[1];
		}
		else
			if((int)brkx1==(int)brkx2)
			{
				if(brky1==directrix.getYcoord())
				{
					if(brky1>brky2)
						brkPoint[0].setXYcoords(1, 1);
					else
						brkPoint[0].setXYcoords(0, 0);
					return brkPoint[0];
						
				}
				else
				{
					if(brky2>brky1)
						brkPoint[1].setXYcoords(1, 1);
					else
						brkPoint[1].setXYcoords(0, 0);
					return brkPoint[1];
				}
			}
			else if((int)brkx1== (int)(Xdirectrix-radius))
			{
				if(brky1>brky2)
					brkPoint[0].setXYcoords(1, 1);
				else
					brkPoint[0].setXYcoords(0, 0);
				return brkPoint[0];
			}
			else
			{
				if(brky2>brky1)
					brkPoint[1].setXYcoords(1, 1);
				else
					brkPoint[1].setXYcoords(0, 0);
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
