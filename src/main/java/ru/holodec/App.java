package ru.holodec;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.holodec.jascii.gui.StatusPresenter;


public class App 
{
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"SpringBeans.xml", 
																									  "SpringWriters.xml",
																									  "SpringProcessors.xml"});
	
    public static void main( String[] args )
    {
    	StatusPresenter presenter = (StatusPresenter)context.getBean("MainWindow");
    	presenter.startWork();
    }
} 
