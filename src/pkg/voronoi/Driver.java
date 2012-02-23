package pkg.voronoi;
import java.util.*;
import java.lang.Math;

public class Driver {
	
//	public static void main(String[] args)
//	{
//		ArrayList<Coordinates> inputPoints = new ArrayList<Coordinates>(5);
//		Coordinates tempvar= new Coordinates(167,224);
//		inputPoints.add(tempvar);
//		tempvar= new Coordinates(168,139);
//		inputPoints.add(tempvar);
//		tempvar= new Coordinates(131,184);
//			inputPoints.add(tempvar);
//		tempvar= new Coordinates(260,186);
//			inputPoints.add(tempvar);
////		tempvar= new Coordinates(114,117);
////			inputPoints.add(tempvar);	
	//	Canvas.init();
//		plotVoronoi(inputPoints);
//	}

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
		VoronoiMesh.setHalfEdges(100000.0);
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
		
		HalfEdge temphePtr = statusTree.getSuccessor(currEvent.getArcToKill()).getAssocHe();
		if(temphePtr.getTargetVertex()==null)
			temphePtr.setTargetVertex(vertex);
		else
			temphePtr.setSourceVertex(vertex);
		
		temphePtr = statusTree.getPredecessor(currEvent.getArcToKill()).getAssocHe();
		if(temphePtr.getTargetVertex()==null)
			temphePtr.setTargetVertex(vertex);
		else
			temphePtr.setSourceVertex(vertex);
		
		HalfEdge newHe = new HalfEdge();
		newHe.makeDirty();
		
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
		newHe.setUpperArcFocus(newBrkPtNode.getUpperFocus());
		newHe.setAssocFlag(newBrkPtNode.getBrkPtFlg());
		newHe.setSourceVertex(vertex);
		Coordinates tempBrkPt = new Coordinates();
		Coordinates tempDirectrix = new Coordinates(currEvent.getX()+1,0);
		tempBrkPt = BeachLineInternalNode.getBrkPoint(newBrkPtNode.getLowerFocus(), newBrkPtNode.getUpperFocus(), tempDirectrix, 0, newBrkPtNode.getBrkPtFlg());
		newHe.setDirVector(statusTree.getDirection(tempBrkPt, currEvent.getXycoord()));
		VoronoiMesh.heInsert(newHe);
		getNewCircleEvents(leftNeighbour, rightNeighbour, eventQ, statusTree,currEvent.getX());
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
				{
					if(checkConvergence(newArc,tree,newArc.getFocusX(),1))
						newCircleEvent1 = getCircleEvent(point1, point2, point3,point1.getXcoord());
				}

				if(point4!=null)
					if(point5!=null)	
					{
						if(checkConvergence(newArc,tree,newArc.getFocusX(),0))
							newCircleEvent2 = getCircleEvent(point1, point4, point5,point1.getXcoord());
					}
			
			if(newCircleEvent1!=null && newCircleEvent2!=null && newCircleEvent1.getXycoord().equals(newCircleEvent2.getXycoord()))
			{
				if(newCircleEvent1.getY()<point1.getYcoord())
					newCircleEvent1=null;
				else
					newCircleEvent2=null;
			}
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
	private static EventQueueNode getCircleEvent(Coordinates pt1, Coordinates pt2, Coordinates pt3, double Xdirectrix)
	{
		double d,x,y,radius;
		d=2*(pt1.getYcoord()*pt3.getXcoord()+pt2.getYcoord()*pt1.getXcoord()-pt2.getYcoord()*pt3.getXcoord()-pt1.getYcoord()*pt2.getXcoord() -pt3.getYcoord()*pt1.getXcoord() + pt3.getYcoord()*pt2.getXcoord());
		x= (pt2.getYcoord()*Math.pow(pt1.getXcoord(),2.0) - pt3.getYcoord()*Math.pow(pt1.getXcoord(),2.0) - Math.pow(pt2.getYcoord(),2.0)*pt1.getYcoord() + Math.pow(pt3.getYcoord(),2.0)*pt1.getYcoord() + Math.pow(pt2.getXcoord(),2.0)*pt3.getYcoord() + Math.pow(pt1.getYcoord(),2.0)*pt2.getYcoord() + Math.pow(pt3.getXcoord(),2.0)*pt1.getYcoord() - Math.pow(pt3.getYcoord(),2.0)*pt2.getYcoord() - Math.pow(pt3.getXcoord(),2.0)*pt2.getYcoord() - Math.pow(pt2.getXcoord(),2.0)*pt1.getYcoord() + Math.pow(pt2.getYcoord(),2.0)*pt3.getYcoord() - Math.pow(pt1.getYcoord(),2.0)*pt3.getYcoord() ) / d;
		y= (Math.pow(pt1.getXcoord(),2.0)*pt3.getXcoord() + Math.pow(pt1.getYcoord(),2.0)*pt3.getXcoord() + Math.pow(pt2.getXcoord(),2.0)*pt1.getXcoord() - Math.pow(pt2.getXcoord(),2.0)*pt3.getXcoord() + Math.pow(pt2.getYcoord(),2.0)*pt1.getXcoord() - Math.pow(pt2.getYcoord(),2.0)*pt3.getXcoord() - Math.pow(pt1.getXcoord(),2.0)*pt2.getXcoord() - Math.pow(pt1.getYcoord(),2.0)*pt2.getXcoord() - Math.pow(pt3.getXcoord(),2.0)*pt1.getXcoord() + Math.pow(pt3.getXcoord(),2.0)*pt2.getXcoord() - Math.pow(pt3.getYcoord(),2.0)*pt1.getXcoord() + Math.pow(pt3.getYcoord(),2.0)*pt2.getXcoord()) / d;
		radius = Math.sqrt(Math.pow((x-pt1.getXcoord()),2.0)+Math.pow((y-pt1.getYcoord()), 2.0));
		if((int)(x+radius)>=(int)Xdirectrix)
		{
			Coordinates newCircCoords = new Coordinates(x+radius,y);
			EventQueueNode newCircEvent = new EventQueueNode(newCircCoords,radius,null);
			return newCircEvent;
		}
		else
			return null;
	}
	
	private static void getNewCircleEvents(BeachLineLeafNode leftArc, BeachLineLeafNode rightArc, EventQueue eventQ, BeachLine statusTree,double Xdirectrix)
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
		{
			if(checkConvergence(leftArc,statusTree,Xdirectrix,1))
				newCircleEvent1 = getCircleEvent(point1, point2, point3,Xdirectrix);
		}
		if(point4!=null)
		{
			if(checkConvergence(rightArc,statusTree,Xdirectrix,0))
				newCircleEvent2 = getCircleEvent(point1, point2, point4,Xdirectrix);
		}
		if(newCircleEvent1!=null && newCircleEvent2!=null && newCircleEvent1.getXycoord().equals(newCircleEvent2.getXycoord()))
		{
			if(newCircleEvent1.getY()<point1.getYcoord())
				newCircleEvent1=null;
			else
				newCircleEvent2=null;
		}
		if(newCircleEvent1!=null && (int)(newCircleEvent1.getX())>(int)Xdirectrix)
		{
			newCircleEvent1.setArcToKill(rightArc);
			rightArc.setKillerCircleEvent(newCircleEvent1);
			eventQ.insertSiteEvent(newCircleEvent1);
		}
		if(newCircleEvent2!=null && (int)(newCircleEvent2.getX())>(int)Xdirectrix)
		{
			newCircleEvent2.setArcToKill(leftArc);
			leftArc.setKillerCircleEvent(newCircleEvent2);
			eventQ.insertSiteEvent(newCircleEvent2);
		}
	}
	private static boolean checkConvergence(BeachLineLeafNode newArc, BeachLine tree,double Xdirectrix, int posneg)
	{
		BeachLineInternalNode bp1,bp2;
		Coordinates q1,p1,q2,p2,q3;
		double dist1,dist2,q3x,q3y;
		Coordinates tempDirectrix = new Coordinates(Xdirectrix+0.002,0);
		Coordinates tempDirectri = new Coordinates(Xdirectrix+0.0001,0);
		if(posneg==1)
		{
			bp1 = tree.getSuccessor(newArc);
			bp2 = tree.getSuccessor(newArc.getRightSibling(tree));
		}
		else
		{
			bp1=tree.getPredecessor(newArc);
			bp2=tree.getPredecessor(newArc.getLeftSibling(tree));
		}
		
		p2 = BeachLineInternalNode.getBrkPoint(bp1.getLowerFocus(), bp1.getUpperFocus(), tempDirectrix, 0, bp1.getBrkPtFlg());
		q2 = BeachLineInternalNode.getBrkPoint(bp2.getLowerFocus(), bp2.getUpperFocus(), tempDirectrix, 0, bp2.getBrkPtFlg());
		p1 = BeachLineInternalNode.getBrkPoint(bp1.getLowerFocus(), bp1.getUpperFocus(), tempDirectri, 0, bp1.getBrkPtFlg());
		q1 = BeachLineInternalNode.getBrkPoint(bp2.getLowerFocus(), bp2.getUpperFocus(), tempDirectri, 0, bp2.getBrkPtFlg());
		dist1 = distance(p1,q1);
		
		double m1 = (p1.getYcoord() - q1.getYcoord())/(p1.getXcoord() - q1.getXcoord());
		double c1 = (p2.getYcoord()-m1*p2.getXcoord());
		double m2 = (q1.getYcoord()-q2.getYcoord())/(q1.getXcoord()-q2.getXcoord());
		double c2 = q2.getYcoord()-m2*q2.getXcoord();
		q3x = (c2-c1)/(m1-m2);
		q3y = ((c2*m1)-(c1*m2))/(m1-m2);
		q3 = new Coordinates(q3x, q3y);
		dist2 = distance(p2,q3);
		
		
		if(dist1>dist2)
			return true;
		else return false;
	}
	private static double distance(Coordinates bp1, Coordinates bp2)
	{
		return Math.pow(bp1.getXcoord()-bp2.getXcoord(), 2) + Math.pow(bp1.getYcoord()-bp2.getYcoord(), 2);
	}
}
