package pkg.voronoi;

public class BeachLine {
	
	BeachLineInternalNode  root=null;
	
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
		}
		else
		{
			recursiveTreeInsert(root,newArc); 
		}
	}
	
	private void recursiveTreeInsert(BeachLineInternalNode root, BeachLineLeafNode newArc)
	{
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

}
