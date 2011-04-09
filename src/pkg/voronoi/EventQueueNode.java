package pkg.voronoi;

public class EventQueueNode {
	Coordinates xycoord;						// site event or tip of circle (event)
	int 		id;
	EventQueueNode leftChild = null;
	EventQueueNode rightChild= null;
	EventQueueNode parent = null;
	BeachLineLeafNode  assocArc=null;
	
	public void setAssocArc(BeachLineLeafNode associatedArc)
	{
		assocArc = associatedArc;
	}
	public BeachLineLeafNode getAssocArc()
	{
		return assocArc;
	}
	public void setXY(float x, float y)
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
}

