package program;

import java.io.File;

import xml.VCSGraph;
import git.Constants;
import git.TimeUtility;

public class MainFrame {
	
	 public MainFrame() {
		 setUp();
		 xmlRecovery();
	 }
	 
	 private void setUp()
	 {
		 System.out.println("Working path: " + Constants.REPOSITORY_PREFIX);
		 System.out.println(Constants.PROJECT + " project path: " + Constants.PROJECT_PATH);		 
		 System.out.println("Starting up time " + TimeUtility.getCurrentLSMRDateString());
	 }
	 
	 private void xmlRecovery()
	 {
		 String gitPath = Constants.PROJECT_PATH + ".git";
		 
		 final VCSGraph vcsGraph;
		 
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
			 GitGraphGenerator graphGenerator = new GitGraphGenerator(gitPath);
			 vcsGraph = graphGenerator.generateGraph();
			 VCSGraph.writeXML(Constants.XML_TMP, vcsGraph);
		 }		 
	 }
	 
	 
}
