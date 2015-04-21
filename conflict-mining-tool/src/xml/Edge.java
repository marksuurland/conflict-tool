package xml;


public class Edge
{
	private Node _parent;
	private Node _child;
	
	public Edge(Node parentNode, Node childNode)
	{
		_parent = parentNode;
		_child = childNode;
	}

	public Node getParent()
	{
		return _parent;
	}

	public Node getChild()
	{
		return _child;
	}

}
