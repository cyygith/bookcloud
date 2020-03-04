package com.elling.book.flowable.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * flowable配置，为了防止生成的流程图中中文乱码
 * @author Administrator
 *
 */
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration>{

	public void configure(SpringProcessEngineConfiguration engineConfiguration) {
		engineConfiguration.setActivityFontName("宋体");
		engineConfiguration.setLabelFontName("宋体");
		engineConfiguration.setAnnotationFontName("宋体");
	}

}
