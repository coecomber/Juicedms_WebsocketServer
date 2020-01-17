package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Setup;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ServiceBean implements ApplicationContextAware
{
    private static ApplicationContext appCxt;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCxt = applicationContext;
    }

    public static WebSocketService getService() throws BeansException {
        return (WebSocketService) appCxt.getAutowireCapableBeanFactory().getBean("WebSocketService");
    }
}
