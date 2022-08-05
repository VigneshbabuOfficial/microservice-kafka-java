package com.kafkapublisher.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class PublisherTestController {

	@Autowired
	public KafkaTemplate<String, Object> template;
	
	Logger logger = Logger.getLogger("PublisherTestController");
	
	@GetMapping("/publish/{name}")
	public String publishMessage(@PathVariable String name) {
		
		template.send("topic-1", "Hi "+name+" Welcome !! ");

		logger.info(" publishMessage - name = "+name+" ,  topic = topic-1");
		
		return "Data Published";
	}
	
	@GetMapping("/publish/json/{name}")
	public String publishJSONMessage(@PathVariable String name) {
		
		
		ObjectNode response = JsonNodeFactory.instance.objectNode();
		
		response.put("response", "success");
		response.putArray("data").addObject().put("result", "Hi "+name+" Welcome !! ");
		
		logger.info(" publishJSONMessage - response = "+response+" ,  topic = topic-2");
		
		template.send("topic-2", response);
		
		return "Data Published";
	}
	
}
