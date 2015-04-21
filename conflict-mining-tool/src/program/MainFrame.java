package program;

import java.io.File;
import java.util.HashSet;

import xml.Edge;
import xml.Node;
import xml.VCSGraph;
import git.Constants;
import git.TimeUtility;
import graph.GitGraphGenerator;

public class MainFrame {
	
	private VCSGraph vcsGraph;
	
	 public MainFrame() {
		 setUp();
		 xmlRecovery();
		 xmlData();
	 }
	 
	 private void setUp()
	 {
		 System.out.println("Working path: " + Constants.REPOSITORY_PREFIX);
		 System.out.println(Constants.PROJECT + " project path: " + Constants.PROJECT_PATH);		 
		 System.out.println("Starting up time " + TimeUtility.getCurrentLSMRDateString());
	 }
	 
	 private void xmlRecovery()
	 {				 
		 if (new File(Constants.XML_FINAL).exists()) 
		 {
			 vcsGraph = VCSGraph.readXML(Constants.XML_FINAL);
			 System.out.println("GitController - Final XML recovered ( " + Constants.XML_FINAL + " )");
		 } 
		 else if (new File(Constants.XML_TMP).exists()) 
		 {
			 vcsGraph = VCSGraph.readXML(Constants.XML_TMP);
			 System.out.println("GitController - in-progress XML recovered ( " + Constants.XML_TMP + " )");
		 } 
		 else 
		 {
			 System.out.println("GitController - XML not recovered ( " + Constants.XML_TMP + " ), starting from scratch");
			 
			 String gitPath = Constants.PROJECT_PATH + ".git";
			 GitGraphGenerator graphGenerator = new GitGraphGenerator(gitPath);
			 vcsGraph = graphGenerator.generateGraph();
			 VCSGraph.writeXML(Constants.XML_TMP, vcsGraph);
		 }		 
	 }
	 
	 private void xmlData()
	 {
			final HashSet<Edge> mergePairs = vcsGraph.getKnownMerges();
			final HashSet<Edge> speculativePairs = vcsGraph.getSpeculativeMerges();
			
			System.out.println("Commits: " + vcsGraph.getVertexCount());
			
			HashSet<String> committers = new HashSet<String>();
			for (Node node : vcsGraph.getVertices()) {
				committers.add(node.getCommitter());
			}
			
			System.out.println("Committers: " + committers.size());
			System.out.println("Speculative Pairs: " + speculativePairs.size());
			System.out.println("Merge Pairs: " + mergePairs.size());		 
	 }
	 
	 
}
