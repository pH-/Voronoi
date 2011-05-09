package pkg.voronoi;
import java.util.*;
import java.lang.Math;

public class Driver {
	
	/*public static void main(String[] args)
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
	/*		
		plotVoronoi(inputPoints);
	}*/

	public static void plotVoronoi(ArrayList<Coordinates> inputPts) //,EventQueue eventQ)
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
		VoronoiMesh.setHalfEdges(400.0);
		Canvas.drawEdges();
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
		BeachLineLeafNode leftNeighbour;
		BeachLineLeafNode rightNeighbour;
		leftNeighbour = currEvent.getArcToKill().getLeftSibling(statusTree);
		rightNeighbour = currEvent.getArcToKill().getRightSibling(statusTree);
		
		hoaxEvent = rightNeighbour.getKillerCircleEvent();
		if(hoaxEvent!=null)
		{
			hoaxEvent.setArcToKill(null);
			eventQ.deleteNode(hoaxEvent);
		}
		hoaxEvent = leftNeighbour.getKillerCircleEvent();
		
		if(hoaxEvent!=null)
		{
			hoaxEvent.setArcToKill(null);
			eventQ.deleteNode(hoaxEvent);
		}
		Coordinates vertexCoords = new Coordinates(currEvent.getX()-currEvent.getRadius(), currEvent.getY());
		vertex= new Vertex(vertexCoords,leftNeighbour.getParent().getAssocHe());
		leftNeighbour.setKillerCircleEvent(null);
		rightNeighbour.setKillerCircleEvent(null);
		
		HalfEdge temphePtr = rightNeighbour.getParent().getAssocHe();
		if(temphePtr.getTargetVertex()==null)
			temphePtr.setTargetVertex(vertex);
		else
			temphePtr.setSourceVertex(vertex);
		
		temphePtr = leftNeighbour.getParent().getAssocHe();
		if(temphePtr.getTargetVertex()==null)
			temphePtr.setTargetVertex(vertex);
		else
			temphePtr.setSourceVertex(vertex);
		
		HalfEdge newHe = new HalfEdge();
		
		//leftNeighbour.getParent().setAssocHe(newHe);
		//rightNeighbour.getParent().setAssocHe(newHe);
		
		BeachLineInternalNode newBrkPtNode;
		
		if(statusTree.getSuccessor(currEvent.getArcToKill())==currEvent.getArcToKill().getParent())
		{	
			newBrkPtNode = statusTree.getPredecessor(currEvent.getArcToKill());
		}
		else
		{
			newBrkPtNode= statusTree.getSuccessor(currEvent.getArcToKill());
		}
		
		statusTree.deleteArc(currEvent.getArcToKill());
		newBrkPtNode.setAssocHe(newHe);
		newHe.setLowerArcFocus(newBrkPtNode.getLowerFocus());
		newHe.setLowerArcFocus(newBrkPtNode.getUpperFocus());
		newHe.setAssocFlag(newBrkPtNode.getBrkPtFlg());
		newHe.setSourceVertex(vertex);
		VoronoiMesh.heInsert(newHe);
		getNewCircleEvents(leftNeighbour, rightNeighbour, eventQ, statusTree);
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
	
	private static void getNewCircleEvents(BeachLineLeafNode leftArc, BeachLineLeafNode rightArc, EventQueue eventQ, BeachLine statusTree)
	{
		BeachLineLeafNode leftMost = leftArc.getLeftSibling(statusTree);
		BeachLineLeafNode rightMost = rightArc.getRightSibling(statusTree);
		Coordinates point1, point2, point3, point4;
		EventQueueNode newCircleEvent1=null, newCircleEvent2=null;
		point1=point2=point3=point4=null;
		point1 = new Coordinates(leftArc.getFocusX(),leftArc.getFocusY());
		point2 = new Coordinates(rightArc.getFocusX(),rightArc.getFocusY());
		if(rightMost!=null)
			point3 = new Coordinates(rightMost.getFocusX(),rightMost.getFocusY());
		if(leftMost!=null)
			point4 = new Coordinates(leftMost.getFocusX(),leftMost.getFocusY());
		if(point3!=null)
			newCircleEvent1 = getCircleEvent(point1, point2, point3);
		if(point4!=null)
			newCircleEvent2 = getCircleEvent(point1, point2, point4);
		
		if(newCircleEvent1!=null)
		{
			newCircleEvent1.setArcToKill(rightArc);
			rightArc.setKillerCircleEvent(newCircleEvent1);
			eventQ.insertSiteEvent(newCircleEvent1);
		}
		if(newCircleEvent2!=null)
		{
			newCircleEvent2.setArcToKill(leftArc);
			leftArc.setKillerCircleEvent(newCircleEvent2);
			eventQ.insertSiteEvent(newCircleEvent2);
		}
	}
}
