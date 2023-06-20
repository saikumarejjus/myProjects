package com.virtusa.mainpack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;

@Configuration
public class Cacheconfig {

	//this method is hazelcast cache configuration method
	@Bean
	public Config cachcofiguration()
	{
		return new Config().setInstanceName("hazel-instance")
							.addMapConfig(new MapConfig()
									.setName("admcuscache")
									.setTimeToLiveSeconds(3000)
									.setEvictionConfig(new EvictionConfig()
											.setSize(200)
											.setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
											.setEvictionPolicy(EvictionPolicy.LFU)
											
											));
							       
	}
}
