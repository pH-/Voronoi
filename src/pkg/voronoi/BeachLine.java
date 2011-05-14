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
			//newTuple1.setParent(oldArc.getParent());
			newTuple2.setParent(newTuple1);
			BeachLineLeafNode newArc2 = new BeachLineLeafNode();
			HalfEdge halfEdge = new HalfEdge();
			//halfEdge.setSourceVertex(srcVertex)
			VoronoiMesh.heInsert(halfEdge);
			root.getLeftLeaf().cloneit(newArc2);
			newTuple1.setId();
			newTuple2.setId();
			
			newTuple1.setRightChild(newTuple2);
			newTuple1.setLeftLeaf(root.getLeftLeaf());
			newTuple1.getLeftLeaf().setParent(newTuple1);
			newTuple1.setAssocHe(halfEdge);
			newTuple1.setBrkPtFlg(0);
			
			root = newTuple1;
			newTuple2.setLeftLeaf(newArc);
			newTuple2.setRightLeaf(newArc2);
			newTuple2.setAssocHe(halfEdge);
			newTuple2.setBrkPtFlg(1);
			
			newArc.setParent(newTuple2);
			newArc2.setParent(newTuple2);
			Coordinates lowerF = new Coordinates(newTuple1.getLeftLeaf().getFocusX(),newTuple1.getLeftLeaf().getFocusY());
			Coordinates upperF = new Coordinates(newTuple2.getLeftLeaf().getFocusX(),newTuple2.getLeftLeaf().getFocusY());
			newTuple1.setTuple(upperF, lowerF);
			lowerF.setXYcoords(newTuple2.getLeftLeaf().getFocusX(), newTuple2.getLeftLeaf().getFocusY());
			upperF.setXYcoords(newTuple2.getRightLeaf().getFocusX(), newTuple2.getRightLeaf().getFocusY());
			newTuple2.setTuple(upperF, lowerF);
			halfEdge.setLowerArcFocus(lowerF);
			halfEdge.setUpperArcFocus(upperF);
			
		}
		else
		{
			recursiveTreeInsert(root,newArc); 
		}
		count++;
		if(count>1)
		{
			BeachLineInternalNode bp1, bp2;
			bp1 = this.getSuccessor(newArc);
			bp2 = this.getPredecessor(newArc);
			Coordinates tempDirectrix = new Coordinates(newArc.getFocusX()+1,newArc.getFocusY());
			Coordinates endp1 = BeachLineInternalNode.getBrkPoint(bp1.getLowerFocus(), bp1.getUpperFocus(), tempDirectrix, 0, bp1.getBrkPtFlg());
			Coordinates endp2 = BeachLineInternalNode.getBrkPoint(bp2.getLowerFocus(), bp2.getUpperFocus(), tempDirectrix, 0, bp2.getBrkPtFlg());
			Coordinates dirVector = getDirection(endp1,endp2);
			newArc.getParent().getAssocHe().setDirVector(dirVector);
		}
	}
	
	private void recursiveTreeInsert(BeachLineInternalNode root, BeachLineLeafNode newArc)
	{
		Coordinates breakPoint = BeachLineInternalNode.getBrkPoint(root.getLowerFocus(), root.getUpperFocus(),newArc.getFocusObj(), 0, root.getBrkPtFlg());
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
		newTuple1.setParent(oldArc.getParent());
		newTuple2.setParent(newTuple1);
		BeachLineLeafNode newArc2 = new BeachLineLeafNode();
		oldArc.cloneit(newArc2);
		newTuple1.setId();
		newTuple2.setId();
		if(oldArc.getKillerCircleEvent()!=null)
		{
			EventQueueNode root = oldArc.getKillerCircleEvent().getRootOfTree();
			root.getTree().deleteNode(oldArc.getKillerCircleEvent());
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
		HalfEdge halfEdge = new HalfEdge();
		VoronoiMesh.heInsert(halfEdge);
		
		newTuple1.setRightChild(newTuple2);
		newTuple1.setLeftLeaf(oldArc);
		newTuple1.setBrkPtFlg(0);
		newTuple1.setAssocHe(halfEdge);
		
		newTuple2.setAssocHe(halfEdge);
		newTuple2.setLeftLeaf(newArc);
		newTuple2.setRightLeaf(newArc2); 
		newTuple2.setBrkPtFlg(1);
		
		newArc2.setParent(newTuple2);
		newArc.setParent(newTuple2);
		oldArc.setParent(newTuple1);
		
		Coordinates lowerF = new Coordinates(newTuple1.getLeftLeaf().getFocusX(),newTuple1.getLeftLeaf().getFocusY());
		Coordinates upperF = new Coordinates(newTuple2.getLeftLeaf().getFocusX(),newTuple2.getLeftLeaf().getFocusY());
		newTuple1.setTuple(upperF, lowerF);
		lowerF.setXYcoords(newTuple2.getLeftLeaf().getFocusX(), newTuple2.getLeftLeaf().getFocusY());
		upperF.setXYcoords(newTuple2.getRightLeaf().getFocusX(), newTuple2.getRightLeaf().getFocusY());
		newTuple2.setTuple(upperF, lowerF);
		halfEdge.setLowerArcFocus(lowerF);
		halfEdge.setUpperArcFocus(upperF);
	}
	
	public void deleteArc(BeachLineLeafNode deadArc)
	{
		if(this.getSuccessor(deadArc)!=null && this.getPredecessor(deadArc)!=null)
		{
			BeachLineInternalNode inode = this.getPredecessor(deadArc);
			BeachLineInternalNode inode2 = this.getSuccessor(deadArc);
			double flagVal;
			if(!inode.getAssocHe().isDirty())
			{
				flagVal = BeachLineInternalNode.getBrkPoint(inode.getUpperFocus(), inode.getLowerFocus(), deadArc.getKillerCircleEvent().getXycoord(), deadArc.getKillerCircleEvent().getRadius(), 2).getXcoord();
				inode.getAssocHe().setAssocFlag(1-(int)flagVal);
				inode.getAssocHe().makeDirty();
			}
			if(!inode2.getAssocHe().isDirty())
			{
				flagVal = BeachLineInternalNode.getBrkPoint(inode2.getUpperFocus(), inode2.getLowerFocus(), deadArc.getKillerCircleEvent().getXycoord(), deadArc.getKillerCircleEvent().getRadius(), 2).getXcoord();
				inode2.getAssocHe().setAssocFlag(1-(int)flagVal);
				inode2.getAssocHe().makeDirty();
			}
			if(this.getSuccessor(deadArc)==deadArc.getParent())
			{
				if(this.getPredecessor(deadArc).getLowerFocus().equals(deadArc.getFocusObj()))
				{
					this.getPredecessor(deadArc).setLowerFocus(deadArc.getRightSibling(this).getFocusObj());
				}
				else
				{
					this.getPredecessor(deadArc).setUpperFocus(deadArc.getRightSibling(this).getFocusObj());	
				}
				
				
				/*if(deadArc.getKillerCircleEvent().getX()<inode.getUpperFocus().getXcoord() && deadArc.getKillerCircleEvent().getX()<inode.getLowerFocus().getXcoord())
				{
					inode.getAssocHe().setAssocFlag(inode.getBrkPtFlg());
					inode2.getAssocHe().setAssocFlag(inode2.getBrkPtFlg());
				}
				else
				{
					inode.getAssocHe().setAssocFlag(1-inode.getBrkPtFlg());
					inode2.getAssocHe().setAssocFlag(1-inode2.getBrkPtFlg());
				}*/
				flagVal = BeachLineInternalNode.getBrkPoint(inode.getUpperFocus(), inode.getLowerFocus(), deadArc.getKillerCircleEvent().getXycoord(), deadArc.getKillerCircleEvent().getRadius(), 2).getXcoord();
				//if(deadArc.getKillerCircleEvent().getX()<inode.getUpperFocus().getXcoord() && deadArc.getKillerCircleEvent().getX()<inode.getLowerFocus().getXcoord())
					//flagVal=1-flagVal;
				inode.setBrkPtFlg((int)flagVal);
			}
			else
			{
				if(this.getSuccessor(deadArc).getLowerFocus().equals(deadArc.getFocusObj()))
				{
					this.getSuccessor(deadArc).setLowerFocus(deadArc.getLeftSibling(this).getFocusObj());
				}
				else
				{
					this.getSuccessor(deadArc).setUpperFocus(deadArc.getLeftSibling(this).getFocusObj());
				}
				/*BeachLineInternalNode inode = this.getSuccessor(deadArc);
				BeachLineInternalNode inode2 = this.getPredecessor(deadArc);
				inode.getAssocHe().setAssocFlag(1-inode.getBrkPtFlg());
				inode2.getAssocHe().setAssocFlag(1-inode2.getBrkPtFlg());*/
				flagVal = BeachLineInternalNode.getBrkPoint(inode2.getUpperFocus(), inode2.getLowerFocus(), deadArc.getKillerCircleEvent().getXycoord(),deadArc.getKillerCircleEvent().getRadius(), 2).getXcoord();
				//if(deadArc.getKillerCircleEvent().getX()<inode.getUpperFocus().getXcoord() && deadArc.getKillerCircleEvent().getX()<inode.getLowerFocus().getXcoord())
					//flagVal=1-flagVal;
				inode2.setBrkPtFlg((int)flagVal);
			}
		}
		if(deadArc.getParent().getParent()==null)
		{
			if(deadArc.getParent().getLeftLeaf()==deadArc)
			{
				if(deadArc.getParent().getRightChild()!=null)
				{
					root = deadArc.getParent().getRightChild();
					deadArc.getParent().getRightChild().setParent(null);
					deadArc.getParent().setParent(null);
				}
				else
				{
					BeachLineInternalNode dummyNode = new BeachLineInternalNode();
					root = dummyNode;
					dummyNode.setLeftLeaf(deadArc.getParent().getRightLeaf());
					deadArc.getParent().setParent(null);
				}
			}
			else
			{
				if(deadArc.getParent().getLeftChild()!=null)
				{
					root = deadArc.getParent().getLeftChild();
					deadArc.getParent().getLeftChild().setParent(null);
					deadArc.getParent().setParent(null);
				}
				else
					root.setId(-1);
			}
		}
		else if(deadArc.getParent().getParent().getRightChild()==deadArc.getParent())
		{
			if(deadArc.getParent().getLeftLeaf()==deadArc)
			{
				if(deadArc.getParent().getRightChild()==null)
				{
					deadArc.getParent().getParent().setRightLeaf(deadArc.getParent().getRightLeaf());
					deadArc.getParent().getRightLeaf().setParent(deadArc.getParent().getParent());
					deadArc.getParent().getParent().setRightChild(null);
					deadArc.getParent().setParent(null);
				}
				else
				{
					deadArc.getParent().getParent().setRightChild(deadArc.getParent().getRightChild());
					deadArc.getParent().getRightChild().setParent(deadArc.getParent().getParent());
					deadArc.getParent().setParent(null);
				}
			}
			else
			{
				if(deadArc.getParent().getLeftChild()==null)
				{
					deadArc.getParent().getParent().setRightLeaf(deadArc.getParent().getLeftLeaf());
					deadArc.getParent().getLeftLeaf().setParent(deadArc.getParent().getParent());
					deadArc.getParent().getParent().setRightChild(null);
					deadArc.getParent().setParent(null);
				}
				else
				{
					deadArc.getParent().getParent().setRightChild(deadArc.getParent().getLeftChild());
					deadArc.getParent().getLeftChild().setParent(deadArc.getParent().getParent());
					deadArc.getParent().setParent(null);
				}
			}
		}
		else
		{
			if(deadArc.getParent().getLeftLeaf()==deadArc)
			{
				if(deadArc.getParent().getRightChild()==null)
				{
					deadArc.getParent().getParent().setLeftLeaf(deadArc.getParent().getRightLeaf());
					deadArc.getParent().getRightLeaf().setParent(deadArc.getParent().getParent());
					deadArc.getParent().getParent().setLeftChild(null);
					deadArc.getParent().setParent(null);
				}
				else
				{
					deadArc.getParent().getParent().setLeftChild(deadArc.getParent().getRightChild());
					deadArc.getParent().getRightChild().setParent(deadArc.getParent().getParent());
					deadArc.getParent().setParent(null);
				}
			}
			else
			{
				if(deadArc.getParent().getLeftChild()==null)
				{
					deadArc.getParent().getParent().setLeftLeaf(deadArc.getParent().getLeftLeaf());
					deadArc.getParent().getLeftLeaf().setParent(deadArc.getParent().getParent());
					deadArc.getParent().getParent().setLeftChild(null);
					deadArc.getParent().setParent(null);
				}
				else
				{
					deadArc.getParent().getParent().setLeftChild(deadArc.getParent().getLeftChild());
					deadArc.getParent().getLeftChild().setParent(deadArc.getParent().getParent());
					deadArc.getParent().setParent(null);
				}
			}
		}
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
		if(root==null)
			return null;
		if(root.getLeftChild()==null && root.getLeftLeaf()!=null)
			return root.getLeftLeaf();
		else
			return getTreemin(root.getLeftChild());
	}
	
	public BeachLineLeafNode getTreemax(BeachLineInternalNode root)
	{
		if(root==null)
			return null;
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
		if(parent.getParent()==null)
			return null;
		if(parent == parent.getParent().getLeftChild())
			return parent.getParent();
		else
			return findGrandParentSuccessor(parent.getParent());
	}
	
	public BeachLineInternalNode findGrandParentPredecessor(BeachLineInternalNode parent)
	{
		if(parent.getParent()==null)
			return null;
		if(parent == parent.getParent().getRightChild())
			return parent.getParent();
		else
			return findGrandParentPredecessor(parent.getParent());
	}
	
	public Coordinates getDirection(Coordinates ep1, Coordinates ep2)
	{
		Coordinates direction = new Coordinates();
		double x,y;
		x = ep1.getXcoord()-ep2.getXcoord();
		y = ep1.getYcoord()-ep2.getYcoord();
		direction.setXYcoords(x, y);
		return direction;
	}
	
	public int getCount()
	{
		return count;
	}
}
