package com.elink.asshole.testcase;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.EventEnv;
import com.tmall.asshole.common.EventStatus;
import com.tmall.asshole.common.IEventDAO;

public class TestEventDAO {

	private static IEventDAO eventDAO;
	
	static{
		ApplicationContext context = new ClassPathXmlApplicationContext("dal-dao.xml");
		eventDAO = (IEventDAO)context.getBean("eventDAO");
	}

	@Test
	public void insertTest() throws Exception {
		// pass
		Event eventDO = new Event();
	//	eventDO.setStatus(EventStatus.EVENT_STATUS_UNEXECUTED.getCode());
		eventDO.setEnv(EventEnv.local.getCode());
		
		eventDO.setTypeClass("com.elink.asshole.event.biz.dal.dao.testcase.TestEventDAO");
		eventDO.setProcessLogs("hello");
		eventDO.setMemo("testOnly");
		eventDO.setContext("context");
		eventDO.setExecuteMachineIp("192.168.1.2");
		eventDO.setExecuteMachineHashRange("1000");
		eventDO.setHashNum(1000);
		eventDO.setExecCount(1);
		eventDO.setSource(0);
		eventDO.setProcessName("ibatis");
		eventDO.setProcessInstanceId(2L);
		eventDO.setCurrentName("ic");
		eventDO.setExecStartTime(new Date());
		eventDO.setDelayExec(true);
		eventDO.setProcessorNumber(0);
		

		long id = 0;
		try {
			id = eventDAO.insertEventDO(eventDO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	
	@Test
	public void updateDOTest() throws Exception {
		// pass
		Event eventDO = new Event();
		eventDO.setStatus(EventStatus.EVENT_STATUS_UNEXECUTED.getCode());
		// eventDO.setEnv(EventEnv.LOCAL.getCode());
		eventDO.setId(3L);
		eventDO.setDelayExec(false);
		eventDO.setProcessorNumber(0);
		long id = 0L;
		try {
			id = eventDAO.updateEventDO(eventDO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void batchqueryDotest() throws Exception {
		// pass
		eventDAO.batchChangeEventStatus(0, 4);
		Assert.assertTrue(true);
	}

	@Test
	public void queryDotest() throws Exception {
		// pass
		List<Event> lst = eventDAO.queryEvent(0, 10000, 10, 5, 0);
		if (!lst.isEmpty())
			Assert.assertTrue(true);
	}

	@Test
	public void batchqueryByPrimeKey() throws Exception {
		// pass
		Long id = 1L;
		Event e = eventDAO.queryEventByPrimaryKey(id, 0);
		if (e != null)
			Assert.assertTrue(true);
	}

}
