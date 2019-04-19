package org.tubez.springmvc;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Spring namespace handler for groovy related tags
 *  
 * @author assaf
 */
public class GroovyNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("scan", new GroovyScanBeanDefinitionParser());
	}

}