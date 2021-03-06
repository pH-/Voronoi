package pkg.voronoi;

public class EventQueueNode {
	Coordinates xycoord= new Coordinates();						// site event or tip of circle (event)
	double radius=0;
	int 		id;
	EventQueueNode leftChild = null;
	EventQueueNode rightChild= null;
	EventQueueNode parent = null;
	//BeachLineLeafNode  assocArc=null;
	BeachLineLeafNode  arcToKill=null;
	EventQueue tree=null;
	
	public EventQueueNode(Coordinates coord,BeachLineLeafNode arcToKill)
	{
		this.setXY(coord.getXcoord(), coord.getYcoord());
		this.arcToKill = arcToKill;
	}
	
	public EventQueueNode(Coordinates coord,double radius,BeachLineLeafNode arcToKill)
	{
		this.setXY(coord.getXcoord(), coord.getYcoord());
		this.arcToKill = arcToKill;
		this.radius=radius;
	}
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Coordinates getXycoord() {
		return xycoord;
	}

	public void setXycoord(Coordinates xycoord) {
		this.xycoord = xycoord;
	}

	public BeachLineLeafNode getArcToKill() {
		return arcToKill;
	}

	public void setArcToKill(BeachLineLeafNode arcToKill) {
		this.arcToKill = arcToKill;
	}
/*
	public void setAssocArc(BeachLineLeafNode associatedArc)
	{
		assocArc = associatedArc;
	}
	public BeachLineLeafNode getAssocArc()
	{
		return assocArc;
	}*/
	public void setXY(double x, double y)
	{
		xycoord.setXYcoords(x, y);
	}
	public double getX()
	{
		return xycoord.getXcoord();
	}
	public double getY()
	{
		return xycoord.getYcoord();
	}
	public EventQueueNode getLeftChild()
	{
		return leftChild;
	}
	public EventQueueNode getRightChild()
	{
		return rightChild;
	}
	public void setLeftChild (EventQueueNode childNode)
	{
		leftChild = childNode;
	}
	public void setRightChild (EventQueueNode childNode)
	{
		rightChild = childNode;
	}
	public EventQueueNode getParent()
	{
		return parent;
	}
	public void setParent(EventQueueNode parentNode)
	{
		parent = parentNode;
	}
	public void setId()
	{
		id=GlobalVariable.getEventId();
	}
	public int getId()
	{
		return id;
	}
	public boolean isLeftChild()
	{
		if(this.getParent()==null)
			return false;
		else if(this == this.getParent().getLeftChild())
			return true;
		else
			return false;
	}
	public boolean isRightChild()
	{
		if(this.getParent()==null)
			return false;
		else if(this == this.getParent().getRightChild())
			return true;
		else
			return false;
	}
	
	public EventQueueNode getRootOfTree()
	{
		if(this.getParent()==null)
			return this;
		else
			return this.getParent().getRootOfTree();
	}

	public EventQueue getTree() {
		return tree;
	}

	public void setTree(EventQueue tree) {
		this.tree = tree;
	}
}

