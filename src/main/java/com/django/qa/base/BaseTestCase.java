package com.django.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import com.django.qa.util.ReportingUtility;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTestCase {
	public static WebDriver driver;
	private static String endFilePath="/resources/properties/portal.properties";
	private static String logResourcePath="/resources/properties/log4j.xml";
	public static InputStream responseStream;
	public static Properties prop;
	public static HttpResponse response;
	public final static Logger log=Logger.getLogger(BaseTestCase.class.getName());

	public BaseTestCase() { try { 
		String propertiesPath=System.getProperty("user.dir")+endFilePath;
		log.info(propertiesPath);
		String logpropertiesPath=System.getProperty("user.dir")+logResourcePath;
		log.info(logpropertiesPath);
		prop =new Properties(); 
		FileInputStream fs=new FileInputStream(propertiesPath); 
		prop.load(fs); 

		DOMConfigurator.configure(logpropertiesPath);
	}catch(FileNotFoundException
			eFileNotFound) { eFileNotFound.printStackTrace(); } catch (Exception e) {

				e.printStackTrace(); }

	}


	public void init() {
		try {
			initDriver(System.getProperty("browser"));
			getUrl();

		}catch(UnreachableBrowserException ub) {
			ReportingUtility.test.log(Status.INFO, " browser issues in initialization");
		}
		catch(WebDriverException we) {
			ReportingUtility.test.log(Status.INFO, "Error in initialization");
		}

	}
	public WebDriver initDriver(String browserName) {
		if(browserName.equals("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}else if (browserName.equals("Firefox")) {
			WebDriverManager.firefoxdriver().avoidAutoVersion();
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
			driver.manage().window().maximize();
		}
		return driver;
	}
	public void getUrl() {
		driver.get(prop.getProperty("UI_URL"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void waitForElement(WebDriver driver,int timeOutInSeconds,WebElement element) {
		WebDriverWait wait =new WebDriverWait(driver,timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void waitForPageLoad(WebDriver driver,int timeOutInSeconds) {
		WebDriverWait wait =new WebDriverWait(driver,timeOutInSeconds);
		wait.until(ExpectedConditions.titleIs("Django · GitHub"));
	}
	public void quitdriver() {
		driver.manage().deleteAllCookies();
		try {
			Thread.sleep(1000);
			driver.close();
		}catch(InterruptedException e) {
			ReportingUtility.test.log(Status.FAIL, "interrupted exception while quiting driver" );
		}

	}
	public static HttpResponse initGetHttpConnection() {
		try {

			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(prop.getProperty("base_uri")+prop.getProperty("/django/repos"));
			response = client.execute(request);

			log.info("connection To endpoint established");

		}catch (IOException e) {
			ReportingUtility.test.log(Status.FAIL, "initiating http connection fails");
		}
		return response;}
}