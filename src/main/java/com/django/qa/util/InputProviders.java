package com.django.qa.util;

import org.testng.annotations.DataProvider;

public class InputProviders {
	
	@DataProvider(name="TC")
    public static Object[][] getData(){
		return new Object[][] {{"hbhj","hbjh"}};
	}
}
