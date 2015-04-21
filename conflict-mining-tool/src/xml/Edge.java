package xml;


public class Edge
{
	private Node _first;
	private Node _second;
	private boolean _hasConflict;
	private boolean _conflictSet;
	
	public Edge(Node firstNode, Node secondNode)
	{
		_first = firstNode;
		_second = secondNode;
	}

	public Node getFirst()
	{
		return _first;
	}

	public Node getSecond()
	{
		return _second;
	}
	
	public void setConflict(boolean conflict) {
		_conflictSet = true;
		_hasConflict = conflict;
	}

	public boolean conflictSet() {
		return _conflictSet;
	}

	public boolean hasConflict() {
		if (_conflictSet) {
			return _hasConflict;
		} else {
			throw new RuntimeException("VCSNodePair::hasConflict() - conflicts not set for: " + this);
		}
	}

}
