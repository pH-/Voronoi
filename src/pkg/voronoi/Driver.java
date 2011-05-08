package pkg.voronoi;
import java.util.*;
import java.lang.Math;

public class Driver {
	
	public static void main(String[] args)
	{
		ArrayList<Coordinates> inputPoints = new ArrayList<Coordinates>(5);
		Coordinates tempvar= new Coordinates(0,4);
		inputPoints.add(tempvar);
		tempvar= new Coordinates(0,0);
		inputPoints.add(tempvar);
		tempvar= new Coordinates(-1,2);
			inputPoints.add(tempvar);
		tempvar= new Coordinates(1,2);
			inputPoints.add(tempvar);
		/*	tempvar.setXYcoords(-3, -4);
			inputPoints.add(tempvar);*/
			
		plotVoronoi(inputPoints);
	}

	private static void plotVoronoi(ArrayList<Coordinates> inputPts) //,EventQueue eventQ)
	{
		EventQueue eventQ = new EventQueue();
		BeachLine  status = new BeachLine();
		
		initEventQ(inputPts,eventQ);
		while(eventQ.getRootNode()!=null)
		{
			EventQueueNode currEvent;
			currEvent = eventQ.findTreeMin();
			if(currEvent.getArcToKill() == null)
				handleSiteEvent(eventQ, status, currEvent);
			else
				handleCircleEvent(eventQ,status,currEvent);
			eventQ.deleteNode(currEvent);
		}
		status.setHalfEdges(200.0);
	}
	
	private static void initEventQ(ArrayList<Coordinates> inputPts,EventQueue eventQ)
	{
		for(Coordinates coord:inputPts)
		{
			EventQueueNode newEvent = new EventQueueNode(coord,null);
			eventQ.insertSiteEvent(newEvent);
			Cell newCell = new Cell(newEvent);
			VoronoiMesh.cellInsert(newCell);
		}
	}
	
	private static void handleSiteEvent(EventQueue eventQ,BeachLine statusTree,EventQueueNode currEvent)
	{
		BeachLineLeafNode newArc = new BeachLineLeafNode(currEvent.getXycoord(),null);
		statusTree.insertArc(newArc);
		checkForCircleKiller(newArc,statusTree,eventQ);
	}
	private static void handleCircleEvent(EventQueue eventQ,BeachLine statusTree,EventQueueNode currEvent)
	{
		EventQueueNode hoaxEvent;
		Vertex vertex;
		hoaxEvent = currEvent.getArcToKill().getRightSibling(statusTree).getKillerCircleEvent();
		if(hoaxEvent!=null)
		{
			hoaxEvent.setArcToKill(null);
			eventQ.deleteNode(hoaxEvent);
		}
		hoaxEvent = currEvent.getArcToKill().getLeftSibling(statusTree).getKillerCircleEvent();
		
		if(hoaxEvent!=null)
		{
			hoaxEvent.setArcToKill(null);
			eventQ.deleteNode(hoaxEvent);
		}
		Coordinates vertexCoords = new Coordinates(currEvent.getX()-currEvent.getRadius(), currEvent.getY());
		vertex= new Vertex(vertexCoords,currEvent.getArcToKill().getLeftSibling(statusTree).getParent().getAssocHe());
		currEvent.getArcToKill().getLeftSibling(statusTree).setKillerCircleEvent(null);
		currEvent.getArcToKill().getRightSibling(statusTree).setKillerCircleEvent(null);
		HalfEdge temphePtr = currEvent.getArcToKill().getRightSibling(statusTree).getParent().getAssocHe();
		if(temphePtr.getTargetVertex()==null)
			temphePtr.setTargetVertex(vertex);
		else
			temphePtr.setSourceVertex(vertex);
		temphePtr = currEvent.getArcToKill().getLeftSibling(statusTree).getParent().getAssocHe();
		if(temphePtr.getTargetVertex()==null)
			temphePtr.setTargetVertex(vertex);
		else
			temphePtr.setSourceVertex(vertex);
		
		HalfEdge newHe = new HalfEdge();
		currEvent.getArcToKill().getLeftSibling(statusTree).getParent().setAssocHe(newHe);
		currEvent.getArcToKill().getRightSibling(statusTree).getParent().setAssocHe(newHe);
		
		statusTree.deleteArc(currEvent.getArcToKill());
		newHe.setSourceVertex(vertex);
		VoronoiMesh.heInsert(newHe);	
	}
	private static void checkForCircleKiller(BeachLineLeafNode newArc,BeachLine tree,EventQueue eventQ)
	{	
		if(tree.getCount()>=3)
		{
			Coordinates point1 = new Coordinates(newArc.getFocusX(), newArc.getFocusY());
			Coordinates point2, point3,point4,point5;
			point2=point3=point4=point5=null;
			if(newArc.getRightSibling(tree)!=null)
				point2 = new Coordinates(newArc.getRightSibling(tree).getFocusX(), newArc.getRightSibling(tree).getFocusY());
			if(newArc.getRightSibling(tree).getRightSibling(tree)!=null)
				point3 = new Coordinates(newArc.getRightSibling(tree).getRightSibling(tree).getFocusX(), newArc.getRightSibling(tree).getRightSibling(tree).getFocusY());
			if(newArc.getLeftSibling(tree)!=null)
				point4 = new Coordinates(newArc.getLeftSibling(tree).getFocusX(),newArc.getLeftSibling(tree).getFocusY());
			if(newArc.getLeftSibling(tree).getLeftSibling(tree)!=null)
				point5 = new Coordinates(newArc.getLeftSibling(tree).getLeftSibling(tree).getFocusX(), newArc.getLeftSibling(tree).getLeftSibling(tree).getFocusY());

			EventQueueNode newCircleEvent1=null, newCircleEvent2=null;
			if(point2!=null)
				if(point3!=null)
					newCircleEvent1 = getCircleEvent(point1, point2, point3);
			if(point4!=null)
				if(point5!=null)
					newCircleEvent2 = getCircleEvent(point1, point4, point5);
			
			if(newCircleEvent1!=null)
			{
				newCircleEvent1.setArcToKill(newArc.getRightSibling(tree));
				newArc.getRightSibling(tree).setKillerCircleEvent(newCircleEvent1);
				eventQ.insertSiteEvent(newCircleEvent1);
			}
			if(newCircleEvent2!=null)
			{
				newCircleEvent2.setArcToKill(newArc.getLeftSibling(tree));
				newArc.getLeftSibling(tree).setKillerCircleEvent(newCircleEvent2);
				eventQ.insertSiteEvent(newCircleEvent2);
			}
		}
		
	}
	private static EventQueueNode getCircleEvent(Coordinates pt1, Coordinates pt2, Coordinates pt3)
	{
		double d,x,y,radius;
		d=2*(pt1.getYcoord()*pt3.getXcoord()+pt2.getYcoord()*pt1.getXcoord()-pt2.getYcoord()*pt3.getXcoord()-pt1.getYcoord()*pt2.getXcoord() -pt3.getYcoord()*pt1.getXcoord() + pt3.getYcoord()*pt2.getXcoord());
		x= (pt2.getYcoord()*Math.pow(pt1.getXcoord(),2.0) - pt3.getYcoord()*Math.pow(pt1.getXcoord(),2.0) - Math.pow(pt2.getYcoord(),2.0)*pt1.getYcoord() + Math.pow(pt3.getYcoord(),2.0)*pt1.getYcoord() + Math.pow(pt2.getXcoord(),2.0)*pt3.getYcoord() + Math.pow(pt1.getYcoord(),2.0)*pt2.getYcoord() + Math.pow(pt3.getXcoord(),2.0)*pt1.getYcoord() - Math.pow(pt3.getYcoord(),2.0)*pt2.getYcoord() - Math.pow(pt3.getXcoord(),2.0)*pt2.getYcoord() - Math.pow(pt2.getXcoord(),2.0)*pt1.getYcoord() + Math.pow(pt2.getYcoord(),2.0)*pt3.getYcoord() - Math.pow(pt1.getYcoord(),2.0)*pt3.getYcoord() ) / d;
		y= (Math.pow(pt1.getXcoord(),2.0)*pt3.getXcoord() + Math.pow(pt1.getYcoord(),2.0)*pt3.getXcoord() + Math.pow(pt2.getXcoord(),2.0)*pt1.getXcoord() - Math.pow(pt2.getXcoord(),2.0)*pt3.getXcoord() + Math.pow(pt2.getYcoord(),2.0)*pt1.getXcoord() - Math.pow(pt2.getYcoord(),2.0)*pt3.getXcoord() - Math.pow(pt1.getXcoord(),2.0)*pt2.getXcoord() - Math.pow(pt1.getYcoord(),2.0)*pt2.getXcoord() - Math.pow(pt3.getXcoord(),2.0)*pt1.getXcoord() + Math.pow(pt3.getXcoord(),2.0)*pt2.getXcoord() - Math.pow(pt3.getYcoord(),2.0)*pt1.getXcoord() + Math.pow(pt3.getYcoord(),2.0)*pt2.getXcoord()) / d;
		radius = Math.sqrt(Math.pow((x-pt1.getXcoord()),2.0)+Math.pow((y-pt1.getYcoord()), 2.0));
		if(x+radius>=pt1.getXcoord())
		{
			Coordinates newCircCoords = new Coordinates(x+radius,y);
			EventQueueNode newCircEvent = new EventQueueNode(newCircCoords,radius,null);
			return newCircEvent;
		}
		else
			return null;
	}
	
	/*private static void createHalfEdge()
	{
		
	}*/
}
