package com.tmall.asshole.schedule.node.helper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.tmall.asshole.schedule.node.Node;

public class ProcessTemplateHelperTest {
	 @Test
     public void testDeploybyPath(){
		 try {
			ProcessTemplateHelper.deploy("/process/testProcess.xml");
			ProcessTemplateHelper.deploy("/process/testProcess2.xml");
			
			Assert.assertTrue(ProcessTemplateHelper.processes.size()>0);
		    Assert.assertNotNull(ProcessTemplateHelper.getProcessTemplate("order_card"));
		    
		    
		    Node order_create = ProcessTemplateHelper.find("order_card", "order_create");
		    Assert.assertEquals(true, order_create.getSyn());
		    
		    
		    Node order_execute = ProcessTemplateHelper.find("order_card", "order_execute");
		    Assert.assertEquals(false, order_execute.getSyn()); 
		    
		    
		    try{
		          ProcessTemplateHelper.deploy("/process/fake.xml");
		          Assert.assertNotNull(ProcessTemplateHelper.getProcessTemplate("testing"));
		          Assert.fail();
		    }catch (Exception e) {
			}
		    
		 } catch (Exception e) {
			  e.printStackTrace();
			  Assert.fail();
		}
     }
	   
	 @Test
	   public void testDeploybyFolder(){
		 List<String> folders = new ArrayList<String>();
		 folders.add("/process/processTemplateHelper/");
		 try {
			ProcessTemplateHelper.deploy(folders);
			Assert.assertEquals(2, ProcessTemplateHelper.processes.size());
			
		 
		 } catch (Exception e) {
			  Assert.fail();
		  }
		   
	   }
	 
}
