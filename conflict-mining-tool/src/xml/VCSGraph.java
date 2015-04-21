package xml;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import adapter.JdomAdapter;

public class VCSGraph
{
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
		HashSet<NodePair> speculativeMerges = new HashSet<NodePair>();
		HashSet<NodePair> actualMerges = new HashSet<NodePair>();

		Element rootElement = doc.getRootElement();
		List<Element> commitsElement = rootElement.getChild("commits").getChildren();
		List<Element> relationshipElements = rootElement.getChild("commitStructure").getChildren();
		List<Element> speculativeMergesElements = rootElement.getChild("speculativeMerges").getChildren();
		List<Element> knownMerges = rootElement.getChild("knownMerges").getChildren();

		for (Element commit : commitsElement) {
			String hash = commit.getAttributeValue("hash");
			String committer = commit.getAttributeValue("dev");
			String time = commit.getAttributeValue("time");

			// XXX: should be base node or some such
			Node node = new BaseNode(hash, committer, new Date(Long.parseLong(time)));
			nodes.put(hash, node);
		}

		for (Element relationshipElement : relationshipElements) {
			String child = relationshipElement.getAttributeValue("child");
			String parent = relationshipElement.getAttributeValue("parent");

			Node childNode = nodes.get(child);
			Node parentNode = nodes.get(parent);
			childNode.addParent(parentNode);

			Edge edge = new Edge(parentNode, childNode);
			edges.add(edge);
		}

		for (Element mergeElement : speculativeMergesElements) {
			String first = mergeElement.getAttributeValue("first");
			String second = mergeElement.getAttributeValue("second");
			String conflict = mergeElement.getAttributeValue("conflict");

			Node firstNode = nodes.get(first);
			Node secondNode = nodes.get(second);

			NodePair pair = new NodePair(firstNode, secondNode);

			if (conflict != null) {
				// if the conflict hasn't been set
				pair.setConflict(Boolean.parseBoolean(conflict));
			}

			speculativeMerges.add(pair);
		}

		for (Element mergeElement : knownMerges) {
			String first = mergeElement.getAttributeValue("first");
			String second = mergeElement.getAttributeValue("second");
			String conflict = mergeElement.getAttributeValue("conflict");

			Node firstNode = nodes.get(first);
			Node secondNode = nodes.get(second);

			NodePair pair = new NodePair(firstNode, secondNode);
			if (conflict != null) {
				// if the conflict hasn't been set
				pair.setConflict(Boolean.parseBoolean(conflict));
			}

			actualMerges.add(pair);
		}

		VCSGraph vcsGraph = new VCSGraph();
		
		for (Node node : nodes.values()) {
			vcsGraph.addVertex(node);
		}

		for (Edge edge : edges) {
			vcsGraph.addEdge(edge, edge.getParent(), edge.getChild());
		}

		//vcsGraph.setKnownMerges(actualMerges);
		//vcsGraph.setSpeculativeMerges(speculativeMerges);

		return vcsGraph;
	}

	private void addVertex(xml.Node node)
	{
		// TODO Auto-generated method stub
		
	}

	public static void writeXML(String xML_TMP, VCSGraph vcsGraph)
	{
		// TODO Auto-generated method stub
		
	}

}
