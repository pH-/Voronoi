package pkg.voronoi;

public class Cell {
	
	HalfEdge halfEdge;
	Coordinates focusPt;
	EventQueueNode assocEvent;
	
	Cell next=null;
	Cell prev=null;
	
	public Cell getNext() {
		return next;
	}
	
	public void setNext(Cell next) {
		this.next = next;
	}
	
	public Cell getPrev() {
		return prev;
	}
	
	public void setPrev(Cell prev) {
		this.prev = prev;
	}
	
	Cell(EventQueueNode assocEvent)
	{
		this.assocEvent=assocEvent;
	}
	public HalfEdge getHalfEdge() {
		return halfEdge;
	}

	public void setHalfEdge(HalfEdge halfEdge) {
		this.halfEdge = halfEdge;
	}
	
	

}
