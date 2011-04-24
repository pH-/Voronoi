package pkg.voronoi;

public class BeachLineLeafNode {
	Coordinates focusOfArc=null;
	EventQueueNode	killerCircleEvent;
	BeachLineInternalNode parent;
	int color = 0;
	
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

}
