package com.django.qa.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTestcases implements IRetryAnalyzer  {
	public int maxRetryCount=2;
	   private int retryCnt = 0;

	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
	if(retryCnt<maxRetryCount) {
		 System.out.println("Retrying " + result.getName() + " again and the count is " + (retryCnt+1));
         retryCnt++;
         return true;
	}
	return false;
	}

}
