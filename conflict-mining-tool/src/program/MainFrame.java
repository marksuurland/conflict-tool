package program;
import git.Constants;
import git.TimeUtility;

public class MainFrame {
	
	 public MainFrame() {
		 setUp();
	 }
	 
	 private void setUp()
	 {
		 System.out.println("Constants - Working path: " + Constants.REPOSITORY_PREFIX);
		 
		 final long start = System.currentTimeMillis();

		 System.out.println("GitController - Starting up ( " + TimeUtility.getCurrentLSMRDateString() + " )");
	 }
}
