package com.django.qa.test;

import java.io.IOException;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.django.page.FetchRepoAPI;
import com.django.page.RepositoryScreen;
import com.django.qa.base.BaseTestCase;
import com.django.qa.util.ListenUtility;
import com.django.qa.util.ReportingUtility;

@Listeners(ListenUtility.class)
public class RepositoryScreenTest extends BaseTestCase {
	public final static Logger log=Logger.getLogger(RepositoryScreenTest.class.getName());
	ReportingUtility reportingUtility;
	RepositoryScreen repositoryScreen;
	FetchRepoAPI fetchRepoAPI;
	String testName="";



	@BeforeMethod(alwaysRun=true)
	
	public void setup(Method method) throws InvalidFormatException,IOException{
		init();
		testName=method.getName();
		reportingUtility.generateReport();
		reportingUtility.test=reportingUtility.extent.createTest(testName);
		repositoryScreen=new RepositoryScreen(driver);
		fetchRepoAPI=new FetchRepoAPI();
	}

	@Test(enabled=false,groups="UI")
	public void printAllRepoName() {
		repositoryScreen.getListOfRepoName();
		log.info("Printed all repo names");
	}

	@Test(enabled=false,groups="UI")
	public void printAllRepoDescription() {

		repositoryScreen.getListOfRepoDescription();
		log.info("Printed all repo description");
	}
	@Test(enabled=false,groups= {"UI","API"})
	public void matchDetailsOfRepoName() {
try {
		Map<List<String>, List<String>> hm=repositoryScreen.fetchRepoNameAndDescription();
		Map<List<String>,List<String>> hm1=fetchRepoAPI.fetchRepoNamdAndDesc();
		Assert.assertEquals(hm.keySet(), hm1.keySet());
		log.info("Matched result in repo names");}
  catch(Exception e) {
	  ReportingUtility.test.log(Status.FAIL, "Failed to test matchDetailsOfRepoNames");
		}

	}
	@Test(enabled=true,groups= {"UI","API"})
	public void matchDetailsOfRepoDescription()  {
     try {
		Map<List<String>, List<String>> hm=repositoryScreen.fetchRepoNameAndDescription();
		Map<List<String>,List<String>> hm1=fetchRepoAPI.fetchRepoNamdAndDesc();

		Assert.assertEquals(hm1.values(), hm1.values());
		log.info("Matched result in repo description");
	}
catch(Exception e) {
	ReportingUtility.test.log(Status.FAIL, "Failed to test matchDetailsOfRepoDescription");
}}
	@AfterMethod(alwaysRun=true)
	public void endTest() {
		quitdriver();
	}
	@AfterClass(alwaysRun=true)
	public void printReport() {
		ReportingUtility.extent.flush();

	}
}
