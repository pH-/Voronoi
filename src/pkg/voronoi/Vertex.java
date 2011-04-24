package pkg.voronoi;

public class Vertex {
	
	int 	 VertexId;
	HalfEdge emitHalfEdge;
	Coordinates vCoord;
	Vertex   next, prev;
	
	public int getVertexId() {
		return VertexId;
	}
	public void setVertexId() {
		VertexId = GlobalVariable.getVertexId();
	}
	
	public HalfEdge getvEmitHalfEdge() {
		return emitHalfEdge;
	}
	
	public void setvEmitHalfEdge(HalfEdge emitHalfEdge) {
		this.emitHalfEdge = emitHalfEdge;
	}
	
	public Coordinates getvCoord() {
		return vCoord;
	}
	
	public void setvCoord(double x, double y) {
		this.vCoord.setXYcoords(x, y);
	}
	
	public Vertex getNext() {
		return next;
	}
	public void setNext(Vertex next) {
		this.next = next;
	}
	
	public Vertex getPrev() {
		return prev;
	}
	public void setPrev(Vertex prev) {
		this.prev = prev;
	}
	

}
