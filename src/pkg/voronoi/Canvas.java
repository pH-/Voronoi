package pkg.voronoi;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.*;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings("serial")
public class Canvas extends Applet implements MouseListener,ActionListener{
	// The X-coordinate and Y-coordinate of the last click.
	int xpos;
	int ypos;
	int height;
	ArrayList<Coordinates> inputPoints = new ArrayList<Coordinates>(5);
	ArrayList<Point2D.Double> dots = new ArrayList<Point2D.Double>(5);
	static ArrayList<Line2D.Double> edges = new ArrayList<Line2D.Double>(5);
	Button getVoronoi;
	Button clear;
	
	public void init() 
	{
		getVoronoi = new Button("Get Voronoi");
		clear	   = new Button("Clear"); 
		getVoronoi.addActionListener(this);
		clear.addActionListener(this);
		add(getVoronoi);
		add(clear);
		addMouseListener(this);
		this.setSize(1000, 1000);
	}
	
	public void paint(Graphics g) 
	{
		//double x=0,y=0;
	    Graphics2D g2 = (Graphics2D) g;
	    height = this.getHeight();
	    if(dots.size()>0)
	    {
	    	for(Point2D.Double point:dots)
		    {
	    		Ellipse2D.Double circle = new Ellipse2D.Double(point.getX(),point.getY(),5,5);
	    		g2.fill(circle);
	    		/*if(dots.size()>1)
	    		{
	    			Line2D.Double line = new Line2D.Double(x, y, point.getX(), point.getY());
	    			//g2.drawLine(x1, y1, x2, y2);
	    			g2.fill(line);
	    		}
	    		x=point.getX();
	    		y=point.getY();*/
		    }
	    }
	    if(edges.size()>0)
	    {
	    	for(Line2D.Double edge:edges)
	    	{
	    		//g2.fill(edge);
	    		g2.drawLine((int)edge.getX1(), height-(int)edge.getY1(), (int)edge.getX2(), height-(int)edge.getY2());
	    	}
	    }
	    
	}
	public void mouseClicked (MouseEvent me) {
		
		xpos = me.getX();
		ypos = me.getY();
		
		Coordinates tempvar= new Coordinates(xpos,height-ypos);
		float actualy = height-ypos;
		System.out.println("point:"+xpos +":"+ actualy);
		inputPoints.add(tempvar);
		Point2D.Double newPoint = new Point2D.Double(xpos,ypos);
		dots.add(newPoint);
		repaint();
	
	}
	public void mousePressed (MouseEvent me) {}
	public void mouseReleased (MouseEvent me) {}	
	public void mouseEntered (MouseEvent me) {}
	public void mouseExited (MouseEvent me) {}	

	public static void drawEdges()
	{
		HalfEdge heList = VoronoiMesh.getHeListRoot();
		while(heList!=null)
		{
			if(heList.getSourceVertex()!=null && heList.getTargetVertex()!=null)
			{	Line2D.Double newLine = new Line2D.Double(heList.getSourceVertex().getvCoord().getXcoord(), heList.getSourceVertex().getvCoord().getYcoord(), heList.getTargetVertex().getvCoord().getXcoord(), heList.getTargetVertex().getvCoord().getYcoord());
				edges.add(newLine);
			}
			heList=heList.getNextHe();
		}
	}
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource()==getVoronoi)
		{	
			Driver.plotVoronoi(inputPoints);
			repaint();
		}
		else if(event.getSource()==clear)
		{
			inputPoints.clear();
			dots.clear();
			edges.clear();
			System.exit(0);
			repaint();
		}
	}
	
}
