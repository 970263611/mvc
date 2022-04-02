# dahuaboke-mvc
### 1.支持的注解（使用方式完全同spring-mvc）：

- MvcController
- MvcRestController
- MvcRequestBody（List、Map类型参数默认带有MvcRequestBody注解）
- MvcResponseBody
- MvcRequestMapping
- MvcRequestHeader
- MvcRequestParam
- 可自定义扩展，参考5.0和7.6

### 2.支持web容器：

- tomcat
- 可自定义扩展，方式参考7.3

### 3.支持返回值解析器：

- fastjson
- 可自定义扩展，方式参考7.4

### 4.支持json解析器：

- fastjson
- 可自定义扩展，方式参考7.5

### 5.支持参数解析器：

- 支持MvcRequestBody，MvcRequestHeader，MvcRequestParam注解解析和参数赋值
- 可自定义扩展（支持解析自定义注解，可扩展注解），方式参考7.6

### 6.支持视图解析器：

- thymeleaf（弃用）
- jsp（后续支持）
- 默认静态文件
- 可自定义扩展，方式参考7.7

### 7.功能点介绍：

##### 1.支持自定义扩展filter（javax.servlet.Filter），示例如下：


  ```java
  import com.dahuaboke.mvc.model.MvcFilter;
  import com.dahuaboke.mvc.web.filter.MvcFilterFactory;
  /**
   * 需要注册一个MvcFilterFactory的bean，然后添加MvcFilter，可以是多个
   * MvcFilter可以添加多个，但是MvcFilterFactory的bean只能有一个
   */
  @Bean
  public MvcFilterFactory mvcFilterFactory() {
      MvcFilterFactory mvcFilterFactory = new MvcFilterFactory();
      MvcFilter mvcFilter = new MvcFilter();
      mvcFilter.setName("mvcFilter");
      mvcFilter.setOrder(1);
      mvcFilter.setPattern("/*");
      mvcFilter.setFilter((request, response, chain) -> {
          System.out.println("filter go");
          chain.doFilter(request, response);
      });
      mvcFilterFactory.addFilter(mvcFilter);
      return mvcFilterFactory;
  }
  
  public class MvcFilter {
      private String name;
      private String pattern;
      private Filter filter;
      private int order;
  }
  ```

##### 2.支持自定义扩展listener（javax.servlet.ServletContextListener）

  ```java
  import com.dahuaboke.mvc.model.MvcListener;
  import com.dahuaboke.mvc.web.listener.MvcListenerFactory;
  /**
   * 需要注册一个MvcListenerFactory，然后添加MvcListener
   * MvcListener可以添加多个，但是MvcListenerFactory的bean只能有一个
   */
  @Bean
  public MvcListenerFactory mvcListenerFactory(){
      MvcListenerFactory mvcListenerFactory = new MvcListenerFactory();
      MvcListener mvcListener = new MvcListener();
      mvcListener.setListener(new Listener());
      mvcListener.setOrder(1);
      mvcListenerFactory.addListener(mvcListener);
      return mvcListenerFactory;
  }
  
  class Listener implements ServletContextListener{
      @Override
      public void contextInitialized(ServletContextEvent sce) {
          System.out.println("listener init");
      }
  }
  
  public class MvcListener {
      private ServletContextListener listener;
      private int order;
  }
  ```

##### 3.支持自定义web容器，示例如下：

  ```java
  /**
   * 容器类需要继承com.dahuaboke.mvc.server.MvcAbstractWebServer
   * 重写init()方法
   */
  @Bean
  public MvcWebServer mvcWebServer() {
      return new MvcTomcatServer(8080);
  }
  ```

##### 4.支持自定义返回值解析器，示例如下：

```java
/**
 * 解析器需要实现com.dahuaboke.mvc.config.parse.MvcResultParser
 * 重写init()方法
 */
@Bean
public MvcResultParser mvcResultParser() {
    return new MvcResultFastjsonParser();
}
```

##### 5.支持自定义json解析器，示例如下：

```java
/**
 * 解析器需要实现com.dahuaboke.mvc.config.parse.MvcJsonParser
 * 重写toJSONString()、toObject()、toArray()方法
 */
@Bean
public MvcJsonParser mvcJsonParser() {
    return new MvcFastjsonParser();
}
```

##### 6.支持自定义参数解析器，示例如下：

```java
/**
 * 解析器需要实现com.dahuaboke.mvc.config.parse.MvcParamParser
 * 重写parse()方法
 */
@Bean
public MvcParamParser mvcParamParser() {
    return new MvcDefaultParamParser();
}
```

##### 7.支持自定义视图解析器，示例如下：

```java
/**
 * 解析器需要实现com.dahuaboke.mvc.MvcViewResolver
 * 重写resolve()方法
 */
@Bean
public MvcViewResolver mvcViewResolver() {
    return new MvcThymeleafViewResolver();
}
```

### 8.参数配置

- mvc.tomcat.port=8080（服务启动端口，参数值必须为数字，且理论应在0-65535之间）
- mvc.view.prefix=web/（静态资源路径前缀）
- mvc.view.suffix=.html（静态资源路径后缀）
- mvc.view.debugPath=C:/Users （因为静态资源编译后无法动态刷新，这个参数指定到项目静态资源路径可以动态刷新）

### 9.使用示例

```java
@MvcController
//@MvcRestController(使用次注解则下列a-f方法可以省略MvcResponseBody注解，但是g方法无法响应请求到指定页面，会返回字符串view)
@MvcRequestMapping("mvc")
public class TestController {
    @Autowired
    private TestService testService;
    /**
     * 此方法要求body体中含有指定类型的json
     */
    @MvcRequestMapping("a/")
    @MvcResponseBody
    public TestUser a(@MvcRequestBody TestUser user) {
        return user;
    }
    /**
     * 此方法要求请求头含有对应字段
     */
    @MvcRequestMapping("/b/")
    @MvcResponseBody
    public String b(@MvcRequestHeader("Content-Type") String type) {
        return type;
    }
    
    /**
     * 此方法要求请求参数含有对应字段
     */
    @MvcRequestMapping("/c")
    @MvcResponseBody
    public String c(@MvcRequestParam("param") String param) {
        return param;
    }
    /**
     * List类型参数默认带有MvcRequestBody注解
     * 此方法要求body体中含有指定类型的json
     */
    @MvcRequestMapping("e")
    @MvcResponseBody
    public List e(List list) {
        return list;
    }
    /**
     * Map类型参数默认带有MvcRequestBody注解
     * 此方法要求body体中含有指定类型的json
     */
    @MvcRequestMapping("f")
    @MvcResponseBody
    public Map f(Map map) {
        return map;
    }
    /**
     * 此方法需要静态资源包含view.html(后缀可配置)
     */
    @MvcRequestMapping("/g")
    public String g() {
        return "view";
    }
}
```
