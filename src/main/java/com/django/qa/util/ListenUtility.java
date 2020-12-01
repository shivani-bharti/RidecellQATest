package com.django.qa.util;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.django.qa.base.BaseTestCase;


public class ListenUtility  extends BaseTestCase implements ITestListener{

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ReportingUtility.test.log(Status.INFO, "Test Case Execution finished");
		
	}

	public void onTestSuccess(ITestResult result) {
		ReportingUtility.test.log(Status.PASS, "Test Case Execution passed");
		
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ReportingUtility.captureScreenshot();
		ReportingUtility.test.log(Status.FAIL, "Test Case Execution failed");
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ReportingUtility.test.log(Status.SKIP, "Test Case Execution skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("Test Failed but within success percentage" +result.getName());}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("This is onFinish method" +context.getPassedTests());
		System.out.println("This is onFinish method" +context.getFailedTests());
		
	}

}
