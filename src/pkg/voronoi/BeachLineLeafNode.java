package pkg.voronoi;

public class BeachLineLeafNode {
	Coordinates focusOfArc=new Coordinates();
	EventQueueNode	killerCircleEvent;
	BeachLineInternalNode parent;
	int color = 0;
	
	public BeachLineLeafNode(Coordinates focus, EventQueueNode killerCircle)
	{
		focusOfArc.setXYcoords(focus.getXcoord(), focus.getYcoord());
		killerCircleEvent = killerCircle;
	}
	public BeachLineLeafNode() {}
	public void setFocus(double x, double y)
	{
		focusOfArc.setXYcoords(x, y);
	}
	public double getFocusX()
	{
		return focusOfArc.getXcoord();
	}
	public double getFocusY()
	{
		return focusOfArc.getYcoord();
	}
	public Coordinates getFocusObj()
	{
		return focusOfArc;
	}
	public EventQueueNode getKillerCircleEvent() {
		return killerCircleEvent;
	}
	
	public void setKillerCircleEvent(EventQueueNode killerCircleEvent) {
		this.killerCircleEvent = killerCircleEvent;
	}
	
	public void cloneit(BeachLineLeafNode cloneObj)
	{
		cloneObj.setFocus(this.getFocusX(), this.getFocusY());
		cloneObj.setKillerCircleEvent(this.getKillerCircleEvent());
	}
	
	public BeachLineInternalNode getParent() {
		return parent;
	}
	
	public void setParent(BeachLineInternalNode parent) {
		this.parent = parent;
	}

	public BeachLineLeafNode getRightSibling(BeachLine tree)
	{
		if(parent.getLeftLeaf()==this)
		{
			if(parent.getRightLeaf()!=null)
				return parent.getRightLeaf();
			else
				return tree.getTreemin(parent.getRightChild());
		}
		else
		{
			BeachLineInternalNode successor = tree.getSuccessor(this);
			if(successor==null)
				return null;
			if(successor.getRightChild()==null)
				return successor.getRightLeaf();
			else
				return tree.getTreemin(successor.getRightChild());
		}
	}
	
	public BeachLineLeafNode getLeftSibling(BeachLine tree)
	{
		if(parent.getRightLeaf()==this)
		{
			if(parent.getLeftLeaf()!=null)
				return parent.getLeftLeaf();
			else
				return tree.getTreemax(parent.getLeftChild());
		}
		else
		{
			BeachLineInternalNode predecessor = tree.getPredecessor(this);
			if(predecessor==null)
				return null;
			if(predecessor.getLeftChild()==null)
				return predecessor.getLeftLeaf();
			else
				return tree.getTreemax(predecessor.getLeftChild());
		}
	}
}
