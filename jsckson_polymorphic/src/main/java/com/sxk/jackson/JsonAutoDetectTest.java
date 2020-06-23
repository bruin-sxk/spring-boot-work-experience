package com.sxk.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * jackson 默认发现类中所有public 字段和 `public getter` 以及 `public setter`
 *  可以使用 @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) 更改规则
 *  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE) 将完全禁止字段搜索，除非有
 *  `get*()` 方法
 * @author suxingkang
 */
public class JsonAutoDetectTest {

	public static void main(String[] args) throws JsonProcessingException {
		M m = new M();
		m.setName("quan bu dou shi ni");

		ObjectMapper om = new ObjectMapper();

		String string = om.writeValueAsString(m);

		System.out.println(string);
	}

}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class M {
	String name;

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}
}
