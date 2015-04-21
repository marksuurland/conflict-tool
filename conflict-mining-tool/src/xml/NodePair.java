package xml;


//TODO remove this class, instead have the edge class
public class NodePair
{
	private Node _first, _second;

	public NodePair(Node firstNode, Node secondNode)
	{
		_first = firstNode;
		_second = secondNode;
	}
	
	public Node first() {
		return _first;
	}

	public Node second() {
		return _second;
	}

	public void setConflict(boolean parseBoolean)
	{
		// TODO Auto-generated method stub
		
	}

}
