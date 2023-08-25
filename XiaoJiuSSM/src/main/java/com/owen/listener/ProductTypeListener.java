package com.owen.listener;

import com.owen.Service.ProductTypeService;
import com.owen.pojo.ProductType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;


@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService= (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> list =productTypeService.getAll();

        servletContextEvent.getServletContext().setAttribute("typeList",list);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
