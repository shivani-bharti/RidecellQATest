package com.django.page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import com.aventstack.extentreports.Status;
import com.django.qa.base.BaseTestCase;
import com.django.qa.util.ReportingUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class FetchRepoAPI extends BaseTestCase {
	public final static Logger log=Logger.getLogger(FetchRepoAPI.class.getName());
	JsonNode jsonNode;
	HashMap<List<String>,List<String>> hm;
	List<String> repoName;
	List<String> repoDesc;
	public static HttpResponse response;

	public FetchRepoAPI()  {
		try {
		response=initGetHttpConnection();	
   
        }catch(UnsupportedOperationException e) {
        	ReportingUtility.test.log(Status.FAIL, "Connection initiated in page class");
        }
	}
	/*
	 * This method will fetch repo name and desc
	 */
	public HashMap<List<String>,List<String>> fetchRepoNamdAndDesc() {
		
try {
	  HttpEntity entity = response.getEntity();
	  log.info("Response status of request is "+response.getStatusLine());
	          if (entity != null && response.getStatusLine().getStatusCode()==200) {
	   responseStream = entity.getContent();
        ObjectMapper mapper=new ObjectMapper();
	   jsonNode=mapper.readTree(responseStream);
		repoName=new ArrayList<String>();
		repoDesc=new ArrayList<String>();
		hm=new HashMap<List<String>,List<String>>();
		if (jsonNode.isArray()) {

			for (final JsonNode objNode : jsonNode) {

				repoName.add(objNode.get("name").asText());

				if(!objNode.get("description").asText().equals("null")) {
					repoDesc.add(objNode.get("description").asText());

				}

			}
			Collections.sort(repoName);
			Collections.sort(repoDesc);
			hm.put(repoName, repoDesc);

		}
	          }

	}catch(IOException e) {
		ReportingUtility.test.log(Status.FAIL, "Error while fetching repo names and description");
	}
return hm;	}

}
