---
title: Freemarker
date: 2020/6/30 00:00:00
summary: 一个模板引擎，基于模板和要改变的数据来生成输出文本的通用工具
comments: true
img: /medias/images/freemarker.jpg
top: true
cover: true
coverImg: /medias/images/freemarker.jpg
categories:
- 模板引擎
tags:
- Freemarker
- JAVA
- 模板
---

## 一、简介

Freemarker 一个模板引擎，基于模板和要改变的数据来生成输出文本的通用工具

## 二、说明

### 模板

#### 文本

文本会照着原样来输出

#### 插值

这部分的输出会被计算的值来替换。由 ${ 和 } 所分隔

#### 注释

由 <#-- 和 -->来分隔，会被FreeMarker直接忽略

#### 指令

- if/else

```injectedfreemarker
<#if animals.python.price < animals.elephant.price>
  Pythons are cheaper than elephants today.
<#elseif animals.elephant.price < animals.python.price>
  Elephants are cheaper than pythons today.
<#else>
  Elephants and pythons cost the same today.
</#if>
```

- list

```injectedfreemarker
<#list misc.fruits>
  <p>Fruits:
  <ul>
    <#items as fruit>
      <li>${fruit}<#sep> and</#sep>
    </#items>
  </ul>
<#else>
  <p>We have no fruits.
</#list>
```

- include

```injectedfreemarker
<#include "/copyright_footer.html">
```

#### 内建函数

- animal.protected?string("Y", "N") 基于 animal.protected 的布尔值来返回字符串 "Y" 或 "N"
- animal?item_cycle('lightRow','darkRow') 是之前介绍的 item_parity 更为常用的变体形式
- fruits?join(", ") 通过连接所有项，将列表转换为字符串， 在每个项之间插入参数分隔符(比如 "orange,banana")
- user?starts_with("J") 根据 user 的首字母是否是 "J" 返回布尔值true或false

#### 处理不存在变量

```injectedfreemarker
<h1>Welcome ${user!"visitor"}!</h1>

<#if user??><h1>Welcome ${user}!</h1></#if>
```

#### 表达式

- 字符串插值
```injectedfreemarker
<#assign s = "Hello ${user}!">
${s} <#-- Just to see what the value of s is -->
```
- 获取字符
```injectedfreemarker
${user[0]}
```
- 字符串切分
```injectedfreemarker
<#assign s = "ABCDEF">
${s[2..3]}
${s[2..<4]}
${s[2..*3]}
${s[2..*100]}
${s[2..]}
```
- 序列切分
```injectedfreemarker
<#assert seq = ["A", "B", "C", "D", "E"]>
<#list seq[1..3] as i>${i}</#list>
<#assign seq = ["A", "B", "C"]>

Slicing with length limited ranges:
- <#list seq[0..*2] as i>${i}</#list>
- <#list seq[1..*2] as i>${i}</#list>
- <#list seq[2..*2] as i>${i}</#list> <#-- Not an error -->
- <#list seq[3..*2] as i>${i}</#list> <#-- Not an error -->

Slicing with right-unlimited ranges:
- <#list seq[0..] as i>${i}</#list>
- <#list seq[1..] as i>${i}</#list>
- <#list seq[2..] as i>${i}</#list>
- <#list seq[3..] as i>${i}</#list>
```

#### 自动转义插值

```injectedfreemarker
<#escape x as x?html>
  ...
  <p>Title: ${book.title}</p>
  <p>Description: <#noescape>${book.description}</#noescape></p>
  <h2>Comments:</h2>
  <#list comments as comment>
    <div class="comment">
      ${comment}
    </div>
  </#list>
  ...
</#escape>
```

### 数据模型

- 为模板准备的数据整体
- 模板 + 数据模型 = 输出


## 三、使用

### Freemarker官方示例

test.ftl
```injectedfreemarker
<html>
<head>
    <title>Welcome!</title>
</head>
<body>
<h1>Welcome ${user}!</h1>
<p>Our latest product:
    <a href="${latestProduct.url}">${latestProduct.name}</a>!
</body>
</html>
```

Test
```java
public class Test {

    public static void main(String[] args) throws Exception {

        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */

        /* Create and adjust the configuration singleton */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir"),"ftl"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        /* ------------------------------------------------------------------------ */
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */

        /* Create a data-model */
        Map root = new HashMap();
        root.put("user", "Big Joe");
        Map latest = new HashMap();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");

        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate("test.ftl");

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        // Note: Depending on what `out` is, you may need to call `out.close()`.
        // This is usually the case for file output, but not for servlet output.
    }
}
```

### JAVA的模板生成页面和数据格式转换

#### 1、添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

#### 2、添加配置

```yaml
server:
  port: 8888
  servlet:
    context-path: /freemarker
spring:
  freemarker:
    suffix: .ftl
    cache: false
    charset: UTF-8
```

#### 3、定义FTL模板

head.ftl
```injectedfreemarker
<head>
	<meta charset="UTF-8">
	<meta name="viewport"
	      content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>freemarker</title>
</head>
```

index.ftl
```injectedfreemarker
<head>
	<meta charset="UTF-8">
	<meta name="viewport"
	      content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>freemarker</title>
</head>
```

info.ftl
```injectedfreemarker
<!doctype html>
<html lang="en">
<#include "../common/head.ftl">
<body>
<div id="app" style="margin: 20px 20%">
	<form action="/freemarker/user/info" method="post">
		姓名：<input type="text" name="name" placeholder="姓名"/>
		年龄：<input type="text" name="age" placeholder="年龄"/>
		<input type="submit" value="登录">
	</form>
</div>
</body>
</html>
```

user.ftl
```injectedfreemarker
<#list users>
{
    <#items as user>
    {
        "name": ${user.name},
        "age": ${user.age}
    }<#sep>, </#sep>
    </#items>
}
<#else>
{}
</#list>
```

#### 4、定义User模型

```java
@Data
public class User {
	private String name;
	private Integer age;
}
```

#### 5、自定义Configuration

```java
@Configuration
public class TemplateConfiguration {

    @Bean(name = "ftlConfiguraion")
    public freemarker.template.Configuration ftlConfiguraion() {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_29);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try {
            configuration.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir"), "ftl"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }
}
```

#### 6、定义Controller

IndexController
```java
@RestController
public class IndexController {

	@GetMapping("/")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute("user");
		if (ObjectUtil.isNull(user)) {
			mv.setViewName("redirect:/user/info");
		} else {
			mv.setViewName("page/index");
			mv.addObject(user);
		}
		return mv;
	}
}
```

UserController
```java
@RestController
@RequestMapping(value = "/user")
public class UserController {
	@PostMapping("/info")
	public ModelAndView login(User user, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		mv.addObject(user);
		mv.setViewName("redirect:/");

		request.getSession().setAttribute("user", user);
		return mv;
	}

	@GetMapping("/info")
	public ModelAndView login() {
		return new ModelAndView("page/info");
	}
}
```

JsonController
```java
@RestController
@RequestMapping(value = "/json")
public class JsonController {

    @Qualifier("ftlConfiguraion")
    @Resource
    private Configuration configuration;

    @PostMapping("/users")
    public String users(@RequestBody List<User> users) throws IOException, TemplateException {
        Template template = configuration.getTemplate("user.ftl");
        StringWriter writer = new StringWriter();
        Map<String, List<User>> map = Collections.singletonMap("users", users);
        template.process(map, writer);
        return writer.toString();
    }

    @GetMapping("/excelToJson")
    public String excelToJson() throws IOException, TemplateException {
        File file = new File(System.getProperty("user.dir"),"excel/user.xlsx");
        if (!file.exists() || !file.isFile()) {
            throw new NullPointerException("the file not exist or the path is not a file with path: " + file.getPath());
        }
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheet = workbook.getSheet("User");

        List<User> users = new ArrayList<User>();
        int rowNum = sheet.getLastRowNum();
        for (int i = 0; i <= rowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            User user = new User();
            user.setName(row.getCell(0).getStringCellValue());
            user.setAge((int) row.getCell(1).getNumericCellValue());
            users.add(user);
        }

        Template template = configuration.getTemplate("user.ftl");
        StringWriter writer = new StringWriter();
        Map<String, List<User>> map = Collections.singletonMap("users", users);
        template.process(map, writer);
        return writer.toString();
    }
}
```

## 四、链接

[Freemarker官网](http://freemarker.foofun.cn/ "Freemarker官网")
