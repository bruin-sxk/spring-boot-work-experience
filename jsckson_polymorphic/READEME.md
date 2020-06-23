## jackson 注解 @JsonTypeInfo 解决多态反序列化问题

## jackson 版本
```xml
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.9</version>
        </dependency>
```


###  如果需要读取和写入具有多个可能的子类型（也就是多态性的子类型）的对象的值，则需要启用序列化时包含这些类信息，这是必须有的，以便 jackson 在反序列化的时候（将json字符串读入成对象）能够正确的获取对象类型。

> 可以选择在基类上添加 @JsonTypeInfo 注解开启这项功能的支持
```java

// Include Java class name ("com.myempl.ImplClass") as JSON property "class"
@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
public abstract class BaseClass {
}

public class Impl1 extends BaseClass {
  public int x;
}
public class Impl2 extends BaseClass {
  public String name;
}

public class PojoWithTypedObjects {
  public List<BaseClass> items;
}

```

上述对象序列化之后结果如下：

```json
{ "items" : [
  { "class":"Impl2", "name":"Bob" },
  { "class":"Impl1", "x":13 }
]}
```

### @JsonTypeInfo 注解解释
- use：在序列化的时候标志处不同的类型使用什么区分，在反序列化的时候转换成相应的类型
- include：包含类型元素的一种机制
- property：自定义的区分类型的id，默认是 `@class`
- @JsonSubTypes: 可以用来表明这个父类的子类型有哪些。
- visible 默认为false 也就是标示字段不会影响 json 的序列化以及反序列化，如果设置为true，反序列化处没有进行显示的声明，则会报错

```java
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = StringPage.class, name = "input"),
		@JsonSubTypes.Type(value = NumberPage.class, name = "number")})
public class Page {

	private String type;
	private String name;
	private String uiType;
	private String label;

}

@Data
public class NumberPage extends Page{

	private String number;

}


@Data
public class StringPage extends Page {

	private String str;

}
```

