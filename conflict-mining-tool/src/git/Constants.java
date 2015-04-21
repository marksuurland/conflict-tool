package git;

import java.io.File;

public class Constants {

	public static String REPOSITORY_PREFIX = "C:\\Users\\Mark\\work\\" + File.separator;
		
	//System.out.println("Constants - Working path: " + REPOSITORY_PREFIX);	

/*	private final static String jGit = "jgit";
	private final static String voldemort = "voldemort";
	private final static String test = "TestProject";
	private final static String linux = "linux-2.6";
	private final static String rails = "rails";
	private final static String homebrew = "homebrew";
	private final static String mangos = "mangos";
	private final static String git = "git";*/
	private final static String jquery = "jquery";

	// Repo for analysis
	public static String PROJECT = jquery;
	public static String PROJECT_PATH = REPOSITORY_PREFIX + PROJECT + File.separator;

	//Output files
	public static String XML_TMP = REPOSITORY_PREFIX + PROJECT + "_out_tmp.xml";
	public static String XML_FINAL = REPOSITORY_PREFIX + PROJECT + "_out_final.xml";
	public static String DOT_FINAL = REPOSITORY_PREFIX + PROJECT + "_out_final.dot";

	//Settings 
	public final static int POOL_SIZE = 1;
	public final static int TMP_WRITE_FREQ = 100;

	//Git install path
	public static String GIT_PATH = "C:\\Users\\Mark\\AppData\\Local\\GitHub\\PortableGit_c2ba306e536fdf878271f7fe636a147ff37326ad\\bin\\git.exe";

	public static void setProject(String project) {
		System.out.println("Constants::setProject( " + project + " )");
		PROJECT = project;

		PROJECT_PATH = REPOSITORY_PREFIX + PROJECT + File.separator;

		XML_TMP = REPOSITORY_PREFIX + PROJECT + "_out_tmp.xml";
		XML_FINAL = REPOSITORY_PREFIX + PROJECT + "_out_final.xml";
		DOT_FINAL = REPOSITORY_PREFIX + PROJECT + "_out_final.dot";

	}
}
