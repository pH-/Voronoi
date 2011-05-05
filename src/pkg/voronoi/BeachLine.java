package pkg.voronoi;

public class BeachLine {
	
	BeachLineInternalNode  root=null;
	static int count=0;
	
	public BeachLineInternalNode getRoot() {
		return root;
	}

	public void setRoot(BeachLineInternalNode root) {
		this.root = root;
	}

	public void insertArc(BeachLineLeafNode newArc)
	{
		if(root == null)
		{
			BeachLineInternalNode dummyNode = new BeachLineInternalNode();
			dummyNode.setLeftLeaf(newArc);
			root = dummyNode;
		}
		else if(root.getId()==-1)
		{
			BeachLineInternalNode newTuple1 = new BeachLineInternalNode();
			BeachLineInternalNode newTuple2 = new BeachLineInternalNode();
			BeachLineLeafNode newArc2 = new BeachLineLeafNode();
			HalfEdge halfEdge = new HalfEdge();
			halfEdge.setSourceVertex(srcVertex)
			VoronoiMesh.heInsert(halfEdge);
			root.getLeftLeaf().cloneit(newArc2);
			newTuple1.setId();
			newTuple2.setId();
			
			newTuple1.setRightChild(newTuple2);
			newTuple1.setLeftLeaf(root.getLeftLeaf());
			newTuple1.getLeftLeaf().setParent(newTuple1);
			
			root = newTuple1;
			newTuple2.setLeftLeaf(newArc);
			newTuple2.setRightLeaf(newArc2);
			
			newArc.setParent(newTuple2);
			newArc2.setParent(newTuple2);
			Coordinates upperF = new Coordinates(newTuple1.getLeftLeaf().getFocusX(),newTuple1.getLeftLeaf().getFocusY());
			Coordinates lowerF = new Coordinates(newTuple2.getLeftLeaf().getFocusX(),newTuple2.getLeftLeaf().getFocusY());
			newTuple1.setTuple(upperF, lowerF);
			upperF.setXYcoords(newTuple2.getLeftLeaf().getFocusX(), newTuple2.getLeftLeaf().getFocusY());
			lowerF.setXYcoords(newTuple2.getRightLeaf().getFocusX(), newTuple2.getRightLeaf().getFocusY());
			newTuple2.setTuple(upperF, lowerF);
		}
		else
		{
			recursiveTreeInsert(root,newArc); 
		}
		count++;
	}
	
	private void recursiveTreeInsert(BeachLineInternalNode root, BeachLineLeafNode newArc)
	{
		////// insert half edge.......
		Coordinates breakPoint = root.getBrkPoint(root.getLowerFocus(), root.getUpperFocus(),newArc.getFocusX());
		if(breakPoint.getYcoord() >= newArc.getFocusY())
		{
			if(root.getLeftChild()!=null)
				recursiveTreeInsert(root.getLeftChild(),newArc);
			else
				insertSubTree(root.getLeftLeaf(), newArc);
		}
		else
		{
			if(root.getRightChild()!=null)
				recursiveTreeInsert(root.getRightChild(),newArc);
			else
				insertSubTree(root.getRightLeaf(),newArc);
		}
	}
	
	public void insertSubTree(BeachLineLeafNode oldArc, BeachLineLeafNode newArc)
	{
		BeachLineInternalNode newTuple1 = new BeachLineInternalNode();
		BeachLineInternalNode newTuple2 = new BeachLineInternalNode();
		BeachLineLeafNode newArc2 = new BeachLineLeafNode();
		oldArc.cloneit(newArc2);
		newTuple1.setId();
		newTuple2.setId();
		if(oldArc.getKillerCircleEvent()!=null)
		{
			oldArc.getKillerCircleEvent().setArcToKill(null);
			oldArc.setKillerCircleEvent(null);
		}
		
		if(oldArc.getParent().getLeftLeaf() == oldArc)
		{
			oldArc.getParent().setLeftChild(newTuple1);
			oldArc.getParent().setLeftLeaf(null);
		}
		else
		{
			oldArc.getParent().setRightChild(newTuple1);
			oldArc.getParent().setRightLeaf(null);
		}
		
		newTuple1.setRightChild(newTuple2);
		newTuple1.setLeftLeaf(oldArc);
		newTuple2.setLeftLeaf(newArc);
		newTuple2.setRightLeaf(newArc2); 
		
		newArc2.setParent(newTuple2);
		newArc.setParent(newTuple2);
		oldArc.setParent(newTuple1);
		
		Coordinates upperF = new Coordinates(newTuple1.getLeftLeaf().getFocusX(),newTuple1.getLeftLeaf().getFocusY());
		Coordinates lowerF = new Coordinates(newTuple2.getLeftLeaf().getFocusX(),newTuple2.getLeftLeaf().getFocusY());
		newTuple1.setTuple(upperF, lowerF);
		upperF.setXYcoords(newTuple2.getLeftLeaf().getFocusX(), newTuple2.getLeftLeaf().getFocusY());
		lowerF.setXYcoords(newTuple2.getRightLeaf().getFocusX(), newTuple2.getRightLeaf().getFocusY());
		newTuple2.setTuple(upperF, lowerF);
	}
	
	public void leftRotate(BeachLineInternalNode xnode)
	{
		BeachLineInternalNode ynode;
		ynode = xnode.getRightChild();
		xnode.setRightChild(ynode.getLeftChild());
		if(ynode.getLeftChild()!=null)
			ynode.getLeftChild().setParent(xnode);
		ynode.setParent(xnode.getParent());
		if(xnode.getParent()==null)
			root=ynode;
		else
		{
			if(xnode==xnode.getParent().getLeftChild())
				xnode.getParent().setLeftChild(ynode);
			else
				xnode.getParent().setRightChild(ynode);
		}
		ynode.setLeftChild(xnode);
		xnode.setParent(ynode);
	}
	public void rightRotate(BeachLineInternalNode ynode)
	{
		BeachLineInternalNode xnode;
		xnode=ynode.getLeftChild();
		ynode.setLeftChild(xnode.getRightChild());
		if(xnode.getRightChild()!=null)
			xnode.getRightChild().setParent(ynode);
		xnode.setParent(ynode.getParent());
		if(ynode.getParent()==null)
			root=xnode;
		else
		{
			if(ynode == ynode.getParent().getLeftChild())
				ynode.getParent().setLeftChild(xnode);
			else
				ynode.getParent().setRightChild(xnode);
		}
		ynode.setParent(xnode);
		xnode.setRightChild(ynode);
	}

	public BeachLineLeafNode getTreemin(BeachLineInternalNode root)
	{
		if(root.getLeftChild()==null && root.getLeftLeaf()!=null)
			return root.getLeftLeaf();
		else
			return getTreemin(root.getLeftChild());
	}
	
	public BeachLineLeafNode getTreemax(BeachLineInternalNode root)
	{
		if(root.getRightChild()==null && root.getRightLeaf()!=null)
			return root.getRightLeaf();
		else
			return getTreemax(root.getRightChild());
	}
	
	public BeachLineInternalNode getSuccessor(BeachLineLeafNode leaf)
	{
		if(leaf==leaf.getParent().getLeftLeaf())
			return leaf.getParent();
		else
			return findGrandParentSuccessor(leaf.getParent());
	}
	
	public BeachLineInternalNode getPredecessor(BeachLineLeafNode leaf)
	{
		if(leaf==leaf.getParent().getRightLeaf())
			return leaf.getParent();
		else
			return findGrandParentPredecessor(leaf.getParent());
	}
	
	public BeachLineInternalNode findGrandParentSuccessor(BeachLineInternalNode parent)
	{
		if(parent == null)
			return null;
		if(parent == parent.getParent().getLeftChild())
			return parent.getParent();
		else
			return findGrandParentSuccessor(parent.getParent());
	}
	
	public BeachLineInternalNode findGrandParentPredecessor(BeachLineInternalNode parent)
	{
		if(parent==null)
			return null;
		if(parent == parent.getParent().getRightChild())
			return parent.getParent();
		else
			return findGrandParentPredecessor(parent.getParent());
	}
	
	public int getCount()
	{
		return count;
	}
}
