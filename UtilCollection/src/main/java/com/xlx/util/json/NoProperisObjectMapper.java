package com.xlx.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @auther chen_yang
 * @create 2018-01-22-16:03
 */
@Component
public class NoProperisObjectMapper extends ObjectMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      NoProperisObjectMapper.class);
  private static final long serialVersionUID = 1847111343578713457L;

  private static final NoProperisObjectMapper jsonMapper = new NoProperisObjectMapper();

  /**
   * json toJsonString filter null value field and sort by first charaster
   */
  public NoProperisObjectMapper() {
    super.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    super.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static NoProperisObjectMapper defaultInstance() {
    return jsonMapper;
  }

  /**
   * 简单类型反序列
   *
   * @param s 待反序列字符串
   * @param tClass 参数类型
   */
  public <T> T fromJsonString(String s, Class<T> tClass) {
    try {
      return jsonMapper.readValue(s, tClass);
    } catch (IOException e) {
      LOGGER.error("fromJsonString fail,oriString [{}],classType [{}]", s, tClass, e);
      return null;
    }
  }

  /**
   * javaType 存储
   */
  private Map<String, JavaType> typeMap = Maps.newHashMap();

  /**
   * 泛型反序列
   *
   * @param s 待反序列字符串
   * @param tClass 参数类型
   * @param parameterClasses 泛型参数
   */
  public <T> T fromJsonString(String s, Class<T> tClass, Class... parameterClasses) {
    // className join with '_' as key
    StringBuilder builder = new StringBuilder(tClass.getName());
    for (Class aClass : parameterClasses) {
      builder.append("_").append(aClass.getName());
    }

    String key = builder.toString();
    JavaType type = typeMap.get(key);

    if(null == type) {
      type = TypeFactory.defaultInstance().constructParametricType(tClass, parameterClasses);
      typeMap.put(key, type);
    }

    try {
      return jsonMapper.readValue(s, type);
    } catch (IOException e) {
      LOGGER.error("jsonMapper fail,oriStr [{}]", s, e);
      return null;
    }
  }

  /**
   * @param obj 待序列化对象
   */
  public String toJsonString(Object obj) {
    try {
      return jsonMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      LOGGER.error("jsonMapper fail ", e);
      return null;
    }
  }
}
