package com.django.qa.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.django.qa.base.BaseTestCase;


public class ReportingUtility extends BaseTestCase{
	
	public static BaseTestCase testBase;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public static void generateReport() {
		htmlReporter=new ExtentHtmlReporter("./resources/Reports/AutomationReport.html");
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Test Report");
		htmlReporter.config().setReportName("Ridecell QA cases report");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("EEEE,MMMM,dd,yyyy, hh:mm a '('zzz')'") ;
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		if(extent==null) {
			extent=new ExtentReports();
		}
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Project name", "Ridecell");
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Enviornment", "QA");
		
	}
	
	
      public static String captureScreenshot() {
        	String destination =null;
        	try {
        		String dateName=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        		TakesScreenshot ts=(TakesScreenshot) driver;
        		File source=ts.getScreenshotAs(OutputType.FILE);
        		destination="./resources/screenshots/"+
        		dateName+".png";
        		File finalDestination=new File(destination);
        		FileUtils.copyToDirectory(source, finalDestination);
        		
        	}catch(Exception e) {
        		ReportingUtility.test.log(Status.INFO, "could not capture screenshot");
        	}
	return destination;
        }
        
public void highLightWebElement(WebDriver driver,WebElement element) {
	try {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAtrribute('style','background:green;border:3px green;');", element);
		js.executeScript("arguments[0].scrollIntoView();",element);
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
}

public static void getResult(ITestResult result) {
	
	if(result.getStatus()==ITestResult.FAILURE) {
		test.log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
		test.fail(result.getThrowable());
		
	}else if(result.getStatus()==ITestResult.SUCCESS){
		test.log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
	}else {
		test.log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.ORANGE));
		test.skip(result.getThrowable());
	}
	
}
}
