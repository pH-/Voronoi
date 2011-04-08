package pkg.voronoi;

public class EventQueue {
	EventQueueNode rootNode=null;
	
	public void insertSiteEvent(EventQueueNode newNode)
	{
		if(rootNode==null)
		{
			rootNode = newNode;
		}
		else
		{
			traverseTreeAndInsert(rootNode, newNode);
		}
	}
	
	public void traverseTreeAndInsert(EventQueueNode subRootNode, EventQueueNode newNode)
	{
		if(subRootNode.getX()<newNode.getX())
		{
			if(subRootNode.getRightChild() == null)
			{
				subRootNode.setRightChild(newNode);
				newNode.setParent(subRootNode);
			}
			else
				traverseTreeAndInsert(subRootNode.getRightChild(), newNode);
		}
		else
		{
			if(subRootNode.getLeftChild() == null)
			{
				subRootNode.setLeftChild(newNode);
				newNode.setParent(subRootNode);
			}
			else
				traverseTreeAndInsert(subRootNode.getLeftChild(), newNode);
		}
			
	}
	
	public void deleteNode(EventQueueNode node)
	{
		if(rootNode!=null)
		{
			if(node.getLeftChild() == null && node.getRightChild() == null)
				if(node.isLeftChild())
					node.getParent().setLeftChild(null);
				else if(node.isRightChild())
					node.getParent().setRightChild(null);
				else
					rootNode=null;
			else
			{
				replaceNode(node);
			}
			
		}
	}
	public void replaceNode(EventQueueNode nodeToDelete)
	{
		EventQueueNode newNode=null;
		if(nodeToDelete != findTreeMax(rootNode))
		{
			newNode = findSuccessor(nodeToDelete);
			if(newNode!=nodeToDelete.getRightChild())
			{
				if(newNode == newNode.getParent().getLeftChild())
					newNode.getParent().setLeftChild(newNode.getRightChild());
				newNode.setLeftChild(nodeToDelete.getLeftChild());
				newNode.setRightChild(nodeToDelete.getRightChild());
			}
			else
			{
				newNode.setLeftChild(nodeToDelete.getLeftChild());
			}
		}
		newNode.setParent(nodeToDelete.getParent());
		
		if(nodeToDelete == nodeToDelete.getParent().getLeftChild())
			newNode.getParent().setLeftChild(newNode);
		else
			newNode.getParent().setRightChild(newNode);
	}
	public EventQueueNode findTreeMin(EventQueueNode root)
	{
		if(root.getLeftChild() == null)
		{
			return root;
		}
		else
			return findTreeMin(root.getLeftChild());
	}
	
	public EventQueueNode findTreeMax(EventQueueNode root)
	{
		if(root.getRightChild() == null)
		{
			return root;
		}
		else
			return findTreeMax(root.getRightChild());
	}
	
	public EventQueueNode findSuccessor(EventQueueNode node)
	{
		if(node.getRightChild() != null)
			return findTreeMin(node.getRightChild());
		else
			return findGrandParentSuccessor(node);
	}
	
	public EventQueueNode findPredecessor(EventQueueNode node)
	{
		if(node.getLeftChild()!=null)
			return findTreeMax(node.getLeftChild());
		else
			return findGrandParentPredecessor(node);
	}
	
	public EventQueueNode findGrandParentSuccessor(EventQueueNode node)
	{
		if(node.getParent() == null)
			return null;
		if(node == node.getParent().getLeftChild())
			return node.getParent();
		else
			return findGrandParentSuccessor(node.getParent());
	}
	
	public EventQueueNode findGrandParentPredecessor(EventQueueNode node)
	{
		if(node.getParent() == null)
			return null;
		if(node == node.getParent().getRightChild())
			return node.getParent();
		else
			return findGrandParentPredecessor(node.getParent());
	}
	
	

}
