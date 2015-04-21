package xml;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;

public class BaseNode implements Node
{
	private HashSet<Node> _parents = new HashSet<Node>();
	protected String _committer;
	protected String _hash;
	protected Date _date;
	
	public BaseNode(String hash, String committer, Date date)
	{
		_hash = hash;
		_committer = committer;
		_date = date;
	}

	public void addParent(Node node) {
		_parents.add(node);
	}

	public String getCommitter() {
		return _committer;
	}

	public String getHash() {
		return _hash;
	}

	public HashSet<Node> getParents() {
		return _parents;
	}

	public Date getTime() {
		return _date;
	}

	private Hashtable<String, Integer> dynResults = new Hashtable<String, Integer>();

	public Hashtable<String, Integer> getDynamicRelationship() {
		return dynResults;
	}

	@Override
	public String toString() {
		return "BaseNode( " + getHash() + ", " + getTime() + " )";
	}
}
