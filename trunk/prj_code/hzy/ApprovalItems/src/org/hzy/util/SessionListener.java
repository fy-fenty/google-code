package org.hzy.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * @author        xuliang
 * @date          2011-08-19
 * @class         SessionListener
 * @extends       Object
 * @description   session监听器
 */
public class SessionListener implements HttpSessionListener
{
    private static Map<String, HttpSession> sessionMaps = new HashMap<String, HttpSession>();

    public void sessionCreated(HttpSessionEvent event)
    {
//        HttpSession session = event.getSession();
//        sessionMaps.put(session.getId(), session);
//        logger.debug("A new session has created. [" + session.getId() + "].");
    }

    public void sessionDestroyed(HttpSessionEvent event)
    {
        sessionMaps.remove(event.getSession().getId());
    }
    
    public static Map<String, HttpSession> getSessionMaps()
    {
        return sessionMaps;
    }
    
    public static void addSession(String id, HttpSession session)
    {
        if(sessionMaps.get(id) == null)
        {
            sessionMaps.put(id, session);
        }
    }
    
    public static void removeSession(String id)
    {
        if(sessionMaps.get(id) != null)
        {
            sessionMaps.remove(id);
        }
    }

}
