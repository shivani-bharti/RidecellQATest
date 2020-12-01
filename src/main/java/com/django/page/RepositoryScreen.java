package com.django.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.django.qa.base.BaseTestCase;
import com.django.qa.util.ReportingUtility;

public class RepositoryScreen extends BaseTestCase {
	public final static Logger log = Logger.getLogger(RepositoryScreen.class.getName());
	WebDriver driver;
	@FindBy(xpath = "//a[@itemprop=\"name codeRepository\"]")
	List<WebElement> repoNames;
	@FindBy(xpath = "//p[@itemprop=\"description\"]")
	List<WebElement> repoDesc;
	

	public RepositoryScreen(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitForPageLoad(driver, 10);
	}

	public void getListOfRepoName() {
		try {
			
			repoNames.forEach((n) -> System.out.println(n.getText()));
			log.info("repo names printed");
		} catch (Exception e) {
			ReportingUtility.test.log(Status.FAIL, "Error while reading repo names");
		}
	}

	public void getListOfRepoDescription() {
		try {
			repoDesc.forEach((n) -> System.out.println(n.getText()));
			log.info("repo description printed");
		} catch (Exception e) {
			ReportingUtility.test.log(Status.FAIL, "Error while reading repo Description");
		}
	}
	/*
	 * This method will fetch repo name and desc from portal side
	 */

	public Map<List<String>,List<String>> fetchRepoNameAndDescription() {
		Map<List<String>,List<String>> hm=new HashMap<List<String>,List<String>>();
		List<String> repoName= new ArrayList<String>();
		List<String> repoDescription= new ArrayList<String>();
		try {
	
		for (int i=0;i<repoNames.size();i++) {
			repoName.add(repoNames.get(i).getText());
		}
		for (int i=0;i<repoDesc.size();i++) {
			repoDescription.add(repoDesc.get(i).getText());
		}
		Collections.sort(repoName);
		Collections.sort(repoDescription);
		hm.put(repoName, repoDescription);
		
		
		}catch(Exception e) {
			ReportingUtility.test.log(Status.FAIL, "Error while adding list of repo name and description");
		}
		return hm;
	}
}
