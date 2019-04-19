package org.tubez.springmvc;

import java.io.File;
import java.io.FileFilter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scripting.groovy.GroovyScriptFactory;
  
public class GroovyFactory implements ApplicationContextAware {  
  
    private String directory;  
      
    public String getDirectory() {  
        return directory;  
    }  
  
    public void setDirectory(String directory) {  
        this.directory = directory;  
    }  
  
    @Override  
    public void setApplicationContext(ApplicationContext context)  
            throws BeansException {  
        // 只有这个对象才能注册bean到spring容器  
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();  
          
        // 因为spring会自动将xml解析成BeanDefinition对象然后进行实例化，这里我们没有用xml，所以自己定义BeanDefinition  
        // 这些信息跟spring配置文件的方式差不多，只不过有些东西lang:groovy标签帮我们完成了  
        final String refreshCheckDelay = "org.springframework.scripting.support.ScriptFactoryPostProcessor.refreshCheckDelay";  
        final String language = "org.springframework.scripting.support.ScriptFactoryPostProcessor.language";  
          
        String realDirectory = Thread.currentThread().getContextClassLoader().getResource(directory).getFile();  
        File root = new File(Thread.currentThread().getContextClassLoader().getResource(".").getFile());  
          
        File[] files = new File(realDirectory).listFiles(new FileFilter() {  
            @Override  
            public boolean accept(File pathname) {  
                return pathname.getName().endsWith(".groovy") ? true : false;  
            }  
        });  
        for (File file : files) {  
            GenericBeanDefinition bd = new GenericBeanDefinition();  
            bd.setBeanClass(GroovyScriptFactory.class);
            //bd.setBeanClassName("org.springframework.scripting.groovy.GroovyScriptFactory");  
            // 刷新时间  
            bd.setAttribute(refreshCheckDelay, 500);  
            // 语言脚本  
            bd.setAttribute(language, "groovy");  
            // 文件目录  
            bd.getConstructorArgumentValues().addIndexedArgumentValue(0, file.getPath().replace(root.getPath(), ""));  
            // 注册到spring容器  
            beanFactory.registerBeanDefinition(file.getName().replace(".groovy", ""), bd);  
        }  
          
    }  
      
}  
