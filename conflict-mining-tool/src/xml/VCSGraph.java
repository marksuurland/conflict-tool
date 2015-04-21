package xml;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import adapter.JdomAdapter;

public class VCSGraph extends DirectedSparseGraph<Node, Edge> 
{
	private static final long serialVersionUID = 1L;

	private HashSet<Edge> _knownMergePairs;

	private HashSet<Edge> _speculativeMergePairs;
	
	private void setSpeculativeMerges(HashSet<Edge> speculativeMerges)
	{
		_speculativeMergePairs = speculativeMerges;		
	}

	private void setKnownMerges(HashSet<Edge> actualMerges)
	{
		_knownMergePairs = actualMerges;	
	}
	
	public static VCSGraph readXML(String xml)
	{
		System.out.println("VCSGraph::readXML( " + xml + " )");
		Document doc = JdomAdapter.readXMLDocument(xml);
		return initGraph(doc);
	}

	private static VCSGraph initGraph(Document doc)
	{
		Hashtable<String, Node> nodes = new Hashtable<String, Node>();
		HashSet<Edge> edges = new HashSet<Edge>();
		HashSet<Edge> speculativeMerges = new HashSet<Edge>();
		HashSet<Edge> actualMerges = new HashSet<Edge>();

		Element rootElement = doc.getRootElement();
		List<Element> commitsElement = rootElement.getChild("commits").getChildren();
		List<Element> relationshipElements = rootElement.getChild("commitStructure").getChildren();
		List<Element> speculativeMergesElements = rootElement.getChild("speculativeMerges").getChildren();
		List<Element> knownMerges = rootElement.getChild("knownMerges").getChildren();

		checkCommits(nodes, commitsElement);
		
		checkRelations(nodes, edges, relationshipElements);		
	
		checkMerges(nodes, speculativeMerges,speculativeMergesElements);
		checkMerges(nodes, actualMerges, knownMerges);

		VCSGraph vcsGraph = createGraph(nodes, edges);
		
		vcsGraph.setKnownMerges(actualMerges);
		vcsGraph.setSpeculativeMerges(speculativeMerges);

		return vcsGraph;
	}

	private static VCSGraph createGraph(Hashtable<String, Node> nodes,
			HashSet<Edge> edges)
	{
		VCSGraph vcsGraph = new VCSGraph();		
		for (Node node : nodes.values()) 
		{
			vcsGraph.addVertex(node);
		}

		for (Edge edge : edges) 
		{			
			vcsGraph.addEdge(edge, edge.getFirst(), edge.getSecond());
		}
		return vcsGraph;
	}
	
	private static void checkMerges(Hashtable<String, Node> nodes,
			HashSet<Edge> merges,
			List<Element> mergesElements)
	{
		for (Element mergeElement : mergesElements)
		{
		String first = mergeElement.getAttributeValue("first");
		String second = mergeElement.getAttributeValue("second");
		String conflict = mergeElement.getAttributeValue("conflict");

		Node firstNode = nodes.get(first);
		Node secondNode = nodes.get(second);

		Edge edge = new Edge(firstNode, secondNode);
		
		if (conflict != null) {
			// if the conflict hasn't been set
			edge.setConflict(Boolean.parseBoolean(conflict));
		}		
		merges.add(edge);
		}
	}

	private static void checkRelations(Hashtable<String, Node> nodes,
			HashSet<Edge> edges, List<Element> relationshipElements)
	{
		for (Element relationshipElement : relationshipElements) {
			String child = relationshipElement.getAttributeValue("child");
			String parent = relationshipElement.getAttributeValue("parent");

			Node childNode = nodes.get(child);
			Node parentNode = nodes.get(parent);
			childNode.addParent(parentNode);

			Edge edge = new Edge(parentNode, childNode);
			edges.add(edge);
		}
	}

	private static void checkCommits(Hashtable<String, Node> nodes,
			List<Element> commitsElement)
	{
		for (Element commit : commitsElement) {
			String hash = commit.getAttributeValue("hash");
			String committer = commit.getAttributeValue("dev");
			String time = commit.getAttributeValue("time");

			// XXX: should be base node or some such
			Node node = new BaseNode(hash, committer, new Date(Long.parseLong(time)));
			nodes.put(hash, node);
		}
	}


	public static void writeXML(String xml, VCSGraph vcsGraph)
	{
		// TODO Auto-generated method stub		
	}

	public HashSet<Edge> getKnownMerges() {
		return _knownMergePairs;
	}

	public HashSet<Edge> getSpeculativeMerges() {
		return _speculativeMergePairs;
	}

}
