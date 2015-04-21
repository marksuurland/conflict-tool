package xml;

import java.util.Date;
import java.util.HashSet;

public interface Node
{
	public void addParent(Node node);

	public String getCommitter();
	
	public String getHash();
	
	public HashSet<Node> getParents();
	
	public Date getTime();

}
