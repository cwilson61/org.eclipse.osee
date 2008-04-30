/*
 * Created on Apr 15, 2008
 *
 * PLACE_YOUR_DISTRIBUTION_STATEMENT_RIGHT_HERE
 */
package org.eclipse.osee.framework.osgitestrunner;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.textui.TestRunner;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;

/**
 * @author b1528444
 *
 */
public class JunitTestRunner implements CommandProvider {

	private List<Test> tests;
	
	public JunitTestRunner(){
		tests = new CopyOnWriteArrayList<Test>();
	}
	
	public synchronized void _test(CommandInterpreter ci) {
		TestRunner tr = new TestRunner();
		List<Test> testsToRun = getTestsToRun(ci);
		List<TestResult> results = new ArrayList<TestResult>();
		long time = System.currentTimeMillis();
		for(Test test:testsToRun){
			results.add(tr.doRun(test));
		}
		printResults(results, testsToRun, System.currentTimeMillis()-time);
	}
	
	/**
	 * @param results
	 * @param testsToRun 
	 */
	private void printResults(List<TestResult> results, List<Test> testsToRun, long elapsedTime) {
		System.out.println("Summary --------------------------------------------------\n");
		int runCount = 0;
		int errorCount = 0;
		int failureCount = 0;
		for(TestResult result:results){
			runCount+=result.runCount();
			errorCount+=result.errorCount();
			failureCount+=result.failureCount();
			Enumeration<?> errors = result.errors();
			while(errors.hasMoreElements()){
				System.out.println(String.format("ERROR: %s", errors.nextElement()));
			}
			Enumeration<?> failures = result.failures();
			while(failures.hasMoreElements()){
				System.out.println(String.format("FAILURE: %s", failures.nextElement()));
			}
		}
		if(errorCount + failureCount > 0){
			System.out.println("\nFAILURES!!!");
			System.out.println(String.format("Tests run: %d,  Failures: %d,  Errors: %d", runCount, failureCount, errorCount));
		} else {
			System.out.println(String.format("\nOK (%d tests)", runCount));
		}
		System.out.println(String.format("Elapsed Time: %d ms", elapsedTime));
	}

	/**
	 * @param ci
	 * @return
	 */
	private List<Test> getTestsToRun(CommandInterpreter ci) {
		List<Test> testsToRun = new ArrayList<Test>();
		String arg = null;
		List<String> itemsToMatch = new ArrayList<String>();
		while((arg = ci.nextArgument()) != null){
			itemsToMatch.add(arg);
		}
		for(Test t:tests){
			if(itemsToMatch.size() == 0){
				testsToRun.add(t);
			} else {
				for(String str:itemsToMatch){
					if(t.toString().contains(str)){
						testsToRun.add(t);
						break;
					}
				}
			}
		}
		return testsToRun;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.osgi.framework.console.CommandProvider#getHelp()
	 */
	@Override
	public String getHelp() {
		return "\ttest <MatchString>... - run any TestSuite classes registered as services.\n\n";
	}
	
	public void addTest(Test test){
		tests.add(test);
	}
	
	public void removeTest(Test test){
		tests.remove(test);
	}
}
