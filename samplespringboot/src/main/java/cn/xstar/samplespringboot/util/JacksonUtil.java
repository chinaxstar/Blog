package cn.xstar.samplespringboot.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JacksonUtil {

	private static ObjectMapper mapper;
	private static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

	public static ObjectMapper getMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
		return mapper;
	}

	public static String toJson(Object value) {
		String temp = null;
		try {
			if (value != null)
				temp = getMapper().writeValueAsString(value);
		} catch (JsonProcessingException e) {
			logger.info(e.getMessage());
		}
		return temp;
	}

	public static <T> T toObj(String json, Class<T> tClass) {
		T t = null;
		try {
			t = getMapper().readValue(json, tClass);
		} catch (IOException e) {

		}
		return t;
	}

}
