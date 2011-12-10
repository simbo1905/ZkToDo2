package org.zkforge.zktodo2.loadtest;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;

/**
 * Adapted from http://books.zkoss.org/wiki/Small_Talks/2007/July/ZK_Using_Grinder_3.0#IdGenerator
 * 
 * //NOTE: Limited to one page per desktop SO YOU CANOT USED THIS ON ENYTHING OTHER THAN A TRIVIAL PAGE
 * IF YOU WANT TO LOAD TEST A REAL APP YOU NEED TO FIGURE OUT THE CORRECT WAY OF GENERATING REPEATABLE 
 * UUIDs FOR YOUR REAL APP.
 */
public class GrinderIdGenerator extends DHtmlLayoutServlet implements org.zkoss.zk.ui.sys.IdGenerator {
	 
	private static final long serialVersionUID = -7124474398656691937L;
	private static AtomicInteger desktop = new AtomicInteger();
    private static ThreadLocal<AtomicInteger> page = new ThreadLocal<AtomicInteger>();
    private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
 
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
    	System.err.println(" WARNING WARNING !!! Using a very crude GrinderIdGenerator which is for crude testing only !!! see http://books.zkoss.org/wiki/Small_Talks/2007/July/ZK_Using_Grinder_3.0#IdGenerator"); // NOPMD by simbo on 12/10/11 12:02 PM
        response.set(res);//soon to be used...
        super.doGet(req,  res);
    }
    
    public String nextComponentUuid(Desktop d, Component component) {
        //NOTE: Limited to one page per desktop
 
        Page p = (Page) d.getPages().iterator().next();
        HttpServletRequest hsr = (HttpServletRequest) p.getDesktop().getExecution().getNativeRequest();
        String pageURI = pageUuid(hsr);
 
        AtomicInteger ai = (AtomicInteger)page.get();
        if( ai == null ){
        	ai = desktop;
        }
        String compid = pageURI +"_"+ component.getClass().getName() +"_"+ ai.getAndIncrement();
        compid = replaceIllegalUuidChar(compid);
        System.out.println("component id: " + compid); // NOPMD by simbo on 12/10/11 12:02 PM
        return compid;
    }
 
    public String nextPageUuid(Page p) {
        HttpServletRequest hsr = (HttpServletRequest) p.getDesktop().getExecution().getNativeRequest();
        page.set(new AtomicInteger());//not needed for thread saftey as it is a threadlocal but easier api. 
        String uuid = pageUuid(hsr);
        return uuid;
    }

	private String pageUuid(HttpServletRequest hsr) {
		String uuid = hsr.getRequestURI();
        uuid = replaceIllegalUuidChar(uuid);
		return uuid;
	}

	private String replaceIllegalUuidChar(final String id) {
		String uuid = id.replace('.', '0');
        uuid = uuid.replace('/', '0');
        uuid = uuid.replace('$', '0');
		return uuid;
	}
 
    
    public String nextDesktopId(Desktop d) {
 
        String dtid = "Desktop_"+desktop.getAndIncrement();
        System.out.println("desktop id: " + dtid); // NOPMD by simbo on 12/10/11 12:06 PM
        
        HttpServletResponse response = (HttpServletResponse)GrinderIdGenerator.response.get();
        response.addHeader("Desktop", dtid);// ...and here it is!
        return dtid;
    }
}