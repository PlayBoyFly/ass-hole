package com.tmall.asshole.engine.process;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.asshole.engine.IEngine;
import com.tmall.asshole.event.common.Event;
import com.tmall.asshole.event.common.EventContext;
import com.tmall.asshole.event.common.EventStatus;
import com.tmall.asshole.event.filter.codec.ProtocolCodecFactory;
import com.tmall.asshole.schedule.IDataLoader;
import com.tmall.asshole.schedule.IDataProcessor;


/****
 * 
 * @author tangjinou
 * 
 * @param <Event>
 */
public class EventSchedulerProcessor implements IDataLoader<com.tmall.asshole.event.common.Event>,IDataProcessor<com.tmall.asshole.event.common.Event> {

	private static transient Log logger = LogFactory
			.getLog(EventSchedulerProcessor.class);
	
	@Autowired
	private IEngine<Event, EventContext> eventEngine;

	public IEngine<Event, EventContext> getEventEngine() {
		return eventEngine;
	}

	public void setEventEngine(IEngine<Event, EventContext> eventEngine) {
		this.eventEngine = eventEngine;
	}

	@Autowired
	private ProtocolCodecFactory protocolCodecFactory;

	/****
	 * 获取本地的IP
	 * 
	 * @return
	 */
	private String getLocalIPAddress() {
		String ip = "";
		try {
			Enumeration<?> e1 = (Enumeration<?>) NetworkInterface
					.getNetworkInterfaces();
			while (e1.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) e1.nextElement();
				Enumeration<?> e2 = ni.getInetAddresses();
				while (e2.hasMoreElements()) {
					InetAddress ia = (InetAddress) e2.nextElement();
					// ip = ia.getHostAddress ();
					if (!ia.isLoopbackAddress()) {
						if (ia instanceof Inet4Address) {
							ip = ia.getHostAddress();
						}
					}
				}
			}

		} catch (SocketException e) {
			e.printStackTrace();

		}
		return ip;

	}

	public void process(Event data) throws Exception {
		EventContext context = new EventContext();
		try {
		     if (eventEngine.fire(data, context)) {
		           data.setStatus(EventStatus.EVENT_STATUS_SUCCESS);
		           data.setMemo(StringUtils.isBlank(context.getMemo())?"":context.getMemo());
	               data.setOperator(context.getOperator());
		     } else {
	                data.setExec_count(data.getExec_count() + 1);
	                data.setStatus(EventStatus.EVENT_STATUS_FAILED);
	                data.setMemo(StringUtils.isBlank(context.getMemo())?"":context.getMemo());
	                data.setOperator(context.getOperator());
	          }
			
		} catch (Exception e) {
			   data.setExec_count(data.getExec_count()+1);
	            data.setStatus(EventStatus.EVENT_STATUS_EXCEPTION);
	            data.setMemo(StringUtils.isBlank(context.getMemo())?"":context.getMemo());
	            data.setOperator(context.getOperator());
			    

			if (logger.isErrorEnabled()) {
				logger.error("update status failed", e);
			}
			throw e;
		}
	}

	public List<Event> getDataList(int start, int end, int rownum,
			String envionmentGroup) throws Exception {
		return null;
	}

}
