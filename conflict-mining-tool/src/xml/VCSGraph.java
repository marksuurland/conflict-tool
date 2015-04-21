package xml;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import program.XMLTools;

public class VCSGraph
{

	public static VCSGraph readXML(String xml)
	{
		System.out.println("VCSGraph::readXML( " + xml + " )");

		Document doc = XMLTools.readXMLDocument(xml);

		Hashtable<String, Node> nodes = new Hashtable<String, Node>();
		HashSet<Edge> edges = new HashSet<Edge>();
		HashSet<NodePair> speculativeMerges = new HashSet<NodePair>();
		HashSet<NodePair> actualMerges = new HashSet<NodePair>();

		Element rootElement = doc.getRootElement();
		Element commitsElement = rootElement.getChild("commits");
		Element commitStructureElement = rootElement.getChild("commitStructure");
		Element speculativeMergesElement = rootElement.getChild("speculativeMerges");
		Element knownMergesElement = rootElement.getChild("knownMerges");

		for (Element commitElement : (List<Element>) commitsElement.getChildren()) {
			String hash = commitElement.getAttributeValue("hash");
			String committer = commitElement.getAttributeValue("dev");
			String time = commitElement.getAttributeValue("time");

			// XXX: should be base node or some such
			Node node = new BaseNode(hash, committer, new Date(Long.parseLong(time)));
			nodes.put(hash, node);
		}

		for (Element relationshipElement : (List<Element>) commitStructureElement.getChildren()) {
			String child = relationshipElement.getAttributeValue("child");
			String parent = relationshipElement.getAttributeValue("parent");

			Node childNode = nodes.get(child);
			Node parentNode = nodes.get(parent);
			childNode.addParent(parentNode);

			Edge edge = new Edge(parentNode, childNode);
			edges.add(edge);
		}

		for (Element mergeElement : (List<Element>) speculativeMergesElement.getChildren()) {
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

		for (Element mergeElement : (List<Element>) knownMergesElement.getChildren()) {
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
		// gitGraph.setNodes(nodes);
		for (Node node : nodes.values()) {
			vcsGraph.addVertex(node);
		}

		for (Edge edge : edges) {
			vcsGraph.addEdge(edge, edge.getParent(), edge.getChild());
		}

		vcsGraph.setKnownMerges(actualMerges);
		vcsGraph.setSpeculativeMerges(speculativeMerges);

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
