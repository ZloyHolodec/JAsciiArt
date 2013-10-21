package ru.holodec;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Hello world!
 *
 */
public class App 
{
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"SpringBeans.xml"});
	
    public static void main( String[] args )
    {
//entry point in GuiPresenter bean
    }
}
