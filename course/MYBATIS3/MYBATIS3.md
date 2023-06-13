# MYBATIS3

## 简介

MyBatis前身是apache的一个开源项目iBatis 于2010年由apache迁移到了google code 并改名为MyBatis 后于2013年迁移到GitHub

iBatis是一个基于Java的持久层框架（ORM）

ORM  -> Object Relational  Mapping（对象关系映射）

Relational [rɪˈleɪʃənl] 

## 功能介绍

MyBatis 支持普通 SQL查询，存储过程和高级映射的优秀持久层框架

MyBatis消除了几乎所有的JDBC代码和参数的手工设置及结果集的检索

MyBatis使用简单的XML或注解用于配置和原始映射，将javaBean和数据库进行映射关联

## MyBatis特点

小巧，简单易学，不需要任何第三方依赖，也就是说 最简单安装只要两个jar包+配置几个sql映射文件

## 功能架构

```
我们把Mybatis的功能架构分为三层：
(1)API接口层：提供给外部使用的接口API，开发人员通过这些本地API来操纵数据库。接口层一接收到调用请求就会调用数据处理层来完成具体的数据处理。
(2)数据处理层：负责具体的SQL查找、SQL解析、SQL执行和执行结果映射处理等。它主要的目的是根据调用的请求完成一次数据库操作。
(3)基础支撑层：负责最基础的功能支撑，包括连接管理、事务管理、配置加载和缓存处理，这些都是共用的东西，将他们抽取出来作为最基础的组件。为上层的数据处理层提供最基础的支撑
```

![image](https://user-images.githubusercontent.com/47961027/230050399-d1534c4d-ca67-4130-b525-8217ec045843.png)

## MyBatis官网

```
https://github.com/mybatis/mybatis-3/releases
```

## HelloWorld(Eclipse)

### 新建Java项目并添加对应的jar包

![image-20230328201828010](https://user-images.githubusercontent.com/47961027/230050516-1a3772a8-06d2-4e72-9ece-ffcf882fc293.png)


### 项目目录介绍

![image-20230328202510452](https://user-images.githubusercontent.com/47961027/230050609-0666d879-ab2b-40e8-b801-da04b1e41b5c.png)


### 创建数据库和表

![image-20230328201912639](https://user-images.githubusercontent.com/47961027/230050695-aa985f6c-ce39-4cbf-85fb-4b5e79c91d3f.png)


### 添加mybatis配置文件

mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<properties resource="jdbc.properties"/>
	<!-- 别名 -->
	<typeAliases>
		<!-- type 指定需要映射的实体类 包名+类名
			alias 别名
		 -->
		<typeAlias type="com.etjava.model.Student" alias="Student"/>
	</typeAliases>
	<!-- 配置环境 例如 development开发环境-->
	<environments default="development">
		<environment id="development">
			<!-- 事务管理 -->
			<transactionManager type="JDBC"/>
			<!-- 数据源 POOLED连接池 -->
			<dataSource type="POOLED">
				<property name="driver" value="${jdbc.driverClassName}"/>
				<property name="url" value="${jdbc.url}"/>
				<property name="username" value="${jdbc.username}"/>
				<property name="password" value="${jdbc.password}"/>
			</dataSource>
		</environment>
	</environments>
	<!-- 映射器 映射实体类与数据库SQL -->
	<mappers>
		<mapper resource="com/etjava/mappers/StudentMapper.xml"/>
	</mappers>
</configuration>
```

### 添加数据库连接配置文件

```
jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/db_mybatis?characterEncoding=utf-8
jdbc.username=root
jdbc.password=Karen@1234
```

### 编写对应的实体类

```java
package com.etjava.model;

public class Student {

	private Integer id;
	private String stuName;
	private Integer age;
	
	public Student() {
		super();
	}
	public Student(String stuName, Integer age) {
		super();
		this.stuName = stuName;
		this.age = age;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}

```

### 编写Mapper接口

```java
package com.etjava.mappers;

import com.etjava.model.Student;

public interface StudentMapper {

	/**
	 * 添加学生信息
	 * @param stu
	 * @return
	 */
	public int add(Student stu);
}

```

### 编写Mapper接口对应的映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 映射  namespace 对应的是接口的完整名称-->
<mapper namespace="com.etjava.mappers.StudentMapper">
	<!-- id 需要与接口中的方法名保持一直
		parameterType 入参
		Student 对应的是mybatis-config中的别名 如果那边没有配置别名 
		这里需要写完整的类名  包名+类名
	 -->
	<insert id="add" parameterType="Student">
		insert into t_student values (null,#{stuName},#{age})
	</insert>
</mapper> 
```

### 创建SqlSessionFactoryUtil工具类

该工具类为获取SQL session数据库连接的工具类

```java
package com.etjava.util;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 获取数据库连接 操作数据库工具类
 *
 */
public class SqlSessionFactoryUtil {

	private static SqlSessionFactory sqlSessionFactory;
	// 创建SqlSessionFactory
	private static SqlSessionFactory getSqlSessionFactory() {
		if(sqlSessionFactory==null) {
			InputStream is = null;
			try {
				is = Resources.getResourceAsStream("mybatis-config.xml");
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlSessionFactory;
	}
	// 打开连接
	public static SqlSession openSession() {
		return getSqlSessionFactory().openSession();
	}
	
	public static void main(String[] args) {
		openSession();
	}
}

```

### 编写测试类

向数据库中添加一条数据

```java
package com.etjava.service;

import org.apache.ibatis.session.SqlSession;

import com.etjava.mappers.StudentMapper;
import com.etjava.model.Student;
import com.etjava.util.SqlSessionFactoryUtil;

public class StudentTest {

	public static void main(String[] args) {
		// 获取SqlSession
		SqlSession sqlSession = SqlSessionFactoryUtil.openSession();
		// 获取Mapper mybatis帮我们做的映射接口
		StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
		// 添加数据到数据库
		Student stu = new Student("Tom",21);
		int res = studentMapper.add(stu);
		// 执行完成需要手动提交事务
		sqlSession.commit();
		System.out.println(res);
	}
}

```

## HelloWorld(IDEA)

### 创建Maven项

![image-20230328202734003](https://user-images.githubusercontent.com/47961027/230051261-ac36635f-97f5-41af-9760-e9e41f93fcc3.png)


### 项目结构
![image-20230328204746831](https://user-images.githubusercontent.com/47961027/230051324-73999bc9-1cdb-435d-a3f4-aa9a713be9b1.png)


### 添加mybatis支持

pom.xml

```xml
    <dependencies>
        <!-- 添加mybatis支持 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.3.0</version>
        </dependency>
        <!-- jdbc驱动包  -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.37</version>
        </dependency>
    </dependencies>
```

### resources目录下添加mybatis配置文件

mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<properties resource="jdbc.properties"/>
	<!-- 别名 -->
	<typeAliases>
		<!-- type 指定需要映射的实体类 包名+类名
			alias 别名
		 -->
		<typeAlias type="com.etjava.model.Student" alias="Student"/>
	</typeAliases>
	<!-- 配置环境 例如 development开发环境-->
	<environments default="development">
		<environment id="development">
			<!-- 事务管理 -->
			<transactionManager type="JDBC"/>
			<!-- 数据源 POOLED连接池 -->
			<dataSource type="POOLED">
				<property name="driver" value="${jdbc.driverClassName}"/>
				<property name="url" value="${jdbc.url}"/>
				<property name="username" value="${jdbc.username}"/>
				<property name="password" value="${jdbc.password}"/>
			</dataSource>
		</environment>
	</environments>
	<!-- 映射器 映射实体类与数据库SQL -->
	<mappers>
		<mapper resource="com/etjava/mappers/StudentMapper.xml"/>
	</mappers>
</configuration>

```

### 添加JDBC连接配置文件

```
jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/db_mybatis?characterEncoding=utf-8
jdbc.username=root
jdbc.password=Karen@1234
```

### 创建实体类

```java
package com.etjava.model;

public class Student {

	private Integer id;
	private String stuName;
	private Integer age;
	
	public Student() {
		super();
	}
	public Student(String stuName, Integer age) {
		super();
		this.stuName = stuName;
		this.age = age;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}

```

### 创建SqlSessionFactoryUtil工具类

该工具类为数据库连接相关的工具类

```java
package com.etjava.util;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 获取数据库连接 操作数据库工具类
 *
 */
public class SqlSessionFactoryUtil {

	private static SqlSessionFactory sqlSessionFactory;
	// 创建SqlSessionFactory
	private static SqlSessionFactory getSqlSessionFactory() {
		if(sqlSessionFactory==null) {
			InputStream is = null;
			try {
				is = Resources.getResourceAsStream("mybatis-config.xml");
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlSessionFactory;
	}
	// 打开连接
	public static SqlSession openSession() {
		return getSqlSessionFactory().openSession();
	}
	
	public static void main(String[] args) {
		openSession();
	}
}

```

### 创建Mapper接口

```java
package com.etjava.mappers;

import com.etjava.model.Student;

public interface StudentMapper {

	/**
	 * 添加学生信息
	 * @param stu
	 * @return
	 */
	public int add(Student stu);
}

```

### 创建接口的映射文件

该文件存放在resources/mappers/student/下

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 映射  namespace 对应的是接口的完整名称-->
<mapper namespace="com.etjava.mappers.StudentMapper">
	<!-- id 需要与接口中的方法名保持一直
		parameterType 入参
		Student 对应的是mybatis-config中的别名 如果那边没有配置别名 
		这里需要写完整的类名  包名+类名
	 -->
	<insert id="add" parameterType="Student">
		insert into t_student values (null,#{stuName},#{age})
	</insert>
</mapper> 
```

### 创建测试类

```java
package com.etjava.service;

import org.apache.ibatis.session.SqlSession;

import com.etjava.mappers.StudentMapper;
import com.etjava.model.Student;
import com.etjava.util.SqlSessionFactoryUtil;

public class StudentTest {

	public static void main(String[] args) {
		// 获取SqlSession
		SqlSession sqlSession = SqlSessionFactoryUtil.openSession();
		// 获取Mapper mybatis帮我们做的映射接口
		StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
		// 添加数据到数据库
		Student stu = new Student("Tom2",21);
		int res = studentMapper.add(stu);
		// 执行完成需要手动提交事务
		sqlSession.commit();
		System.out.println(res);
	}
}

```

## MyBatis配置文件

### environments 多环境配置

在environments 中可以配置不同的环境 只需要修改default="development"值改变引用不同的环境即可

```xml
<!-- 配置环境 例如 development开发环境-->
	<environments default="development">
		<environment id="development">
			<!-- 事务管理 -->
			<transactionManager type="JDBC"/>
			<!-- 数据源 POOLED连接池 -->
			<dataSource type="POOLED">
				<property name="driver" value="${jdbc.driverClassName}"/>
				<property name="url" value="${jdbc.url}"/>
				<property name="username" value="${jdbc.username}"/>
				<property name="password" value="${jdbc.password}"/>
			</dataSource>
		</environment>
		<!-- 测试环境 -->
		<environment id="test">
			<!-- 事务管理 -->
			<transactionManager type="JDBC"/>
			<!-- 数据源 POOLED连接池 -->
			<dataSource type="POOLED">
				<property name="driver" value="${jdbc.driverClassName}"/>
				<property name="url" value="${jdbc.url}"/>
				<property name="username" value="${jdbc.username}"/>
				<property name="password" value="${jdbc.password}"/>
			</dataSource>
		</environment>
	</environments>
```

### transactionManager 事务管理器

MyBatis支持两种事务管理 JDBC和MANAGED(托管)

JDBC 是由应用程序负责管理数据库连接的生命周期

MANAGED 是由应用服务器负责管理数据库连接的声明周期（只有商业版的应用服务器才有此功能，例如Jboss ，WebLogic等）

配置transactionManager 需要在environment具体的某个环境下进行配置

```
<transactionManager type="JDBC"/>
```

### dataSource数据源配置

dataSource是用来配置数据库连接相关的信息 类型有 UNPOOLED	POOLED	JNDI   druid

UNPOOLED 没有连接池，每次操作数据库 MyBatis都会新建一个连接，用完后关闭，适合小并发量项目

POOLED 有连接池

JNDI 使用应用服务器的配置 通过JNDI获取数据库连接

druid 阿里巴巴连接池

dataSource的使用同样需要配置在每个具体的环境下(environment)

```
<!-- 数据源 POOLED连接池 -->
			<dataSource type="POOLED">
				<property name="driver" value="${jdbc.driverClassName}"/>
				<property name="url" value="${jdbc.url}"/>
				<property name="username" value="${jdbc.username}"/>
				<property name="password" value="${jdbc.password}"/>
			</dataSource>
```

### properties 配置属性

可以直接用来关联外部的properties文件也可以进行配置

```
	<!--<properties resource="jdbc.properties"/>-->
	<!-- 配置属性 -->
	<properties>
		<property name="jdbc.driverClassName" value="com.mysql.jdbc.Driver"/>
		<property name="jdbc.url" value="jdbc:mysql://localhost:3306/db_mybatis?characterEncoding=utf-8"/>
		<property name="username" value="root"/>
		<property name="password" value="Karen@1234"/>
	</properties>
```

### typeAlias 给类的完整名称取别名 方便使用

单个类取别名时 需要指定到类 另外 别名可以自定义

扫描包下的所有类进行取别名时 别名就是类的名字

```
单个类取别名
<typeAliases>
    <!-- type 指定需要映射的实体类 包名+类名  alias 别名 -->
    <typeAlias type="com.etjava.model.Student" alias="Student"/>
</typeAliases>

扫描指定包下的所有类取别名
<typeAliases>
		<!-- 
			扫描该包下的所有类进行取别名
		 -->
	<package name="com.etjava.model"/>
</typeAliases>
```

### mappers 引入映射文件

所有的ORM框架都会有对应的映射

mappers中可以配置单个接口的映射文件 也可以通过扫描的形式配置多个

```
单个映射文件配置
<!-- 映射器 映射实体类与数据库SQL -->
<mappers>
	<mapper resource="mappers/student/StudentMapper.xml"/>
</mappers>
扫描指定包下的多个映射文件
<!-- 映射器 映射实体类与数据库SQL -->
<mappers>
	// 接口映射文件需要与接口在同一个包下才可以被扫描到
	<package name="com.etjava.mappers"/>
	<!--
    扫描包时 如果是放在resources目录下的话 必须与mapper接口报名字保持一致 否则编译后无法将其放入到	src目录下对应的包中
    如果你的接口映射文件与接口文件在同一个包下 直接扫描即可
    Eclipse中不会存在这些问题 直接使用即可
	-->
	<package name="com.etjava.mappers"/>
</mappers>

另外需要在pom中额外添加不要忽略src目录下的xml文件
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
        </resources>
    </build>
```

## Log4j配置

### 添加log4j依赖

```
<dependency>
<groupId>log4j</groupId>
<artifactId>log4j</artifactId>
<version>1.2.17</version>
</dependency>
```

### 添加log4j配置文件

resources目录下

```
log4j.rootLogger=debugger,appender1,appender2 # 定义各种输出级别
log4j.appender.appender2.Encoding=UTF-8
log4j.appender.appender1.encoding=UTF-8
log4j.appender.File.encoding=UTF-8
log4j.appender.appender1=org.apache.log4j.ConsoleAppender # 控制台输出
log4j.appender.appender2=org.apache.log4j.FileAppender # 文件输出
log4j.appender.appender2.File=D:/abc.log # 需要输出到的文件

# log4j的类型
log4j.appender.appender1.layout=org.apache.log4j.TTCCLayout
log4j.appender.appender2.layout=org.apache.log4j.TTCCLayout


```

### 测试类中添加日志记录

```java
package com.etjava.service;

import org.apache.ibatis.session.SqlSession;

import com.etjava.mappers.StudentMapper;
import com.etjava.model.Student;
import com.etjava.util.SqlSessionFactoryUtil;
import org.apache.log4j.Logger;

public class StudentTest {

	private static Logger logger = Logger.getLogger(StudentTest.class);

	public static void main(String[] args) {
		// 获取SqlSession
		SqlSession sqlSession = SqlSessionFactoryUtil.openSession();
		// 获取Mapper mybatis帮我们做的映射接口
		StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
		// 添加数据到数据库
		Student stu = new Student("Jerry",21);
		int res = studentMapper.add(stu);
		// 执行完成需要手动提交事务
		sqlSession.commit();
		logger.info("添加成功");
		System.out.println(res);
	}
}

```

测试 可以看到文件及控制台均有输出表示成功

## IDEA配置XML文件头信息

每次新建一个实体类就要创建一个映射文件，由于创建映射文件不会提供头部声明，复制粘贴起来也很麻烦，所以这里我们可以通过创建一个映射文件模板的方式，新建映射文件。

File → Settings → Editor → File and Code Templates → 加号

![image-20230328233250374](https://user-images.githubusercontent.com/47961027/230051534-b5dbb06a-6d7e-436c-8e9c-dccd8d919883.png)
![image-20230328233322513](https://user-images.githubusercontent.com/47961027/230051618-7281baa8-6593-4c58-8ced-13c2e96f3d8d.png)

MyBatis配置文件

http://c.biancheng.net/mybatis/config.html

## XML方式配置SQL映射器

### INSERT映射语句

StudentMapper接口中添加add方法

```
public interface StudentMapper {

	/**
	 * 添加学生信息
	 * @param stu
	 * @return
	 */
	public int add(Student stu);
}
```

StudentMapper.xml映射文件中添加对应的映射SQL

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 映射  namespace 对应的是接口的完整名称-->
<mapper namespace="com.etjava.mappers.StudentMapper">
	<!-- id 需要与接口中的方法名保持一直
		parameterType 入参
		Student 对应的是mybatis-config中的别名 如果那边没有配置别名 
		这里需要写完整的类名  包名+类名
	 -->
	<insert id="add" parameterType="Student">
		insert into t_student values (null,#{stuName},#{age})
	</insert>
</mapper> 
```

测试

```
package com.etjava.junit;

import com.etjava.mappers.StudentMapper;
import com.etjava.model.Student;
import com.etjava.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// 测试SQL映射器
public class TestSQLMapping {
    private static Logger logger = Logger.getLogger(TestSQLMapping.class);
    // 定义SQLSession 用来操作数据库
    SqlSession sqlSession = null;
    // 获取Student映射文件
    StudentMapper studentMapper = null;
    // 测试方法执行之前
    @Before
    public void setUp(){
        sqlSession = SqlSessionFactoryUtil.openSession();
        studentMapper = sqlSession.getMapper(StudentMapper.class);
    }

    // 测试方法执行之后
    @After
    public void tearDown(){
        sqlSession.close();
    }

    // 添加学生
    @Test
    public void teseAdd(){
        int res = studentMapper.add(new Student("Jerry", 13));
        System.out.println(res>0?"添加成功":"添加失败");
    }
}

```

### UPDATE映射语句

StudentMapper接口中添加update方法

```
package com.etjava.mappers;

import com.etjava.model.Student;

public interface StudentMapper {

	/**
	 * 添加学生信息
	 * @param stu
	 * @return
	 */
	public int add(Student stu);

	/**
	 * 修改学生信息
	 * @param stu
	 * @return
	 */
	public int update(Student stu);
}

```

StudentMapper.xml中添加SQL映射

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 映射  namespace 对应的是接口的完整名称-->
<mapper namespace="com.etjava.mappers.StudentMapper">
	<!-- id 需要与接口中的方法名保持一直
		parameterType 入参
		Student 对应的是mybatis-config中的别名 如果那边没有配置别名 
		这里需要写完整的类名  包名+类名
	 -->
	<insert id="add" parameterType="Student">
		insert into t_student values (null,#{stuName},#{age})
	</insert>
	<!--修改学生信息SQL映射-->
	<update id="update" parameterType="Student">
		update t_student set stuName=#{stuName},age=#{age} where id=#{id}
	</update>
</mapper> 
```

测试

```
package com.etjava.junit;

import com.etjava.mappers.StudentMapper;
import com.etjava.model.Student;
import com.etjava.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// 测试SQL映射器
public class TestSQLMapping {
    private static Logger logger = Logger.getLogger(TestSQLMapping.class);
    // 定义SQLSession 用来操作数据库
    SqlSession sqlSession = null;
    // 获取Student映射文件
    StudentMapper studentMapper = null;
    // 测试方法执行之前
    @Before
    public void setUp(){
        sqlSession = SqlSessionFactoryUtil.openSession();
        studentMapper = sqlSession.getMapper(StudentMapper.class);
    }

    // 测试方法执行之后
    @After
    public void tearDown(){
        sqlSession.close();
    }

    // 添加学生
    @Test
    public void teseAdd(){
        int res = studentMapper.add(new Student("Jerry", 13));
        System.out.println(res>0?"添加成功":"添加失败");
    }
	// 修改学生信息
    @Test
    public void teseUpdate(){
        int res = studentMapper.update(new Student(1, "Andy", 12));
        System.out.println(res>0?"修改成功":"修改失败");
    }

}

```

### DELETE映射语句

StudentMapper接口中添加删除方法

```
package com.etjava.mappers;

import com.etjava.model.Student;

public interface StudentMapper {

	/**
	 * 添加学生信息
	 * @param stu
	 * @return
	 */
	public int add(Student stu);

	/**
	 * 修改学生信息
	 * @param stu
	 * @return
	 */
	public int update(Student stu);

	/**
	 * 删除学生信息
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
}

```

StudentMapper.xml中添加删除的映射语句

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 映射  namespace 对应的是接口的完整名称-->
<mapper namespace="com.etjava.mappers.StudentMapper">
	<!-- id 需要与接口中的方法名保持一直
		parameterType 入参
		Student 对应的是mybatis-config中的别名 如果那边没有配置别名 
		这里需要写完整的类名  包名+类名
	 -->
	<insert id="add" parameterType="Student">
		insert into t_student values (null,#{stuName},#{age})
	</insert>
	<!--修改学生信息SQL映射-->
	<update id="update" parameterType="Student">
		update t_student set stuName=#{stuName},age=#{age} where id=#{id}
	</update>
	<!--删除学生信息-->
	<delete id="delete" parameterType="Integer">
		delete from t_student where id=#{id}
	</delete>
</mapper> 
```

测试

```
package com.etjava.junit;

import com.etjava.mappers.StudentMapper;
import com.etjava.model.Student;
import com.etjava.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// 测试SQL映射器
public class TestSQLMapping {
    private static Logger logger = Logger.getLogger(TestSQLMapping.class);
    // 定义SQLSession 用来操作数据库
    SqlSession sqlSession = null;
    // 获取Student映射文件
    StudentMapper studentMapper = null;
    // 测试方法执行之前
    @Before
    public void setUp(){
        sqlSession = SqlSessionFactoryUtil.openSession();
        studentMapper = sqlSession.getMapper(StudentMapper.class);
    }

    // 测试方法执行之后
    @After
    public void tearDown(){
        sqlSession.close();
    }

    // 添加学生
    @Test
    public void teseAdd(){
        int res = studentMapper.add(new Student("Jerry", 13));
        System.out.println(res>0?"添加成功":"添加失败");
    }

    // 修改学生信息
    @Test
    public void teseUpdate(){
        int res = studentMapper.update(new Student(1, "Andy", 12));
        System.out.println(res>0?"修改成功":"修改失败");
    }

    // 删除学生信息
    @Test
    public void testDelete(){
        int res = studentMapper.delete(2);
        System.out.println(res>0?"删除成功":"删除失败");
    }

}

```

### SELECT映射语句

#### 根据ID查询学生信息 返回学生对象

StudentMapper接口中定义根据ID查询的方法 部分代码片段

```
/**
	 * 根据ID查询学生信息
	 * @param id
	 * @return
	 */
	public Student findById(Integer id);
```

StudentMapper.xml中定义查询SQL 部分代码片段

```
	<!--根据ID查询学生信息-->
	<select id="findById" parameterType="Integer" resultType="Student">
		select * from t_student where id=#{id}
	</select>
```

测试 部分代码片段

```
    // 根据ID查询学生信息
    @Test
    public void testFindById(){
        Student stu = studentMapper.findById(1);
        System.out.println(stu);
    }
```

#### 模糊查询 返回集合数据

需要注意 返回的是集合数据

StudentMapper接口中定义根据姓名查询

```
	// 根据姓名模糊匹配学生信息
	public List<Student> findByName(String name);
```

StudentMapper.xml中定义查询SQL

```
<!--定义返回的结果集-->
	<resultMap id="StudentMap" type="Student">
		<id property="id" column="id"/>
		<result property="stuName" column="stuName"/>
		<result property="age" column="age"/>
	</resultMap>

<!--根据姓名模糊匹配学生信息-->
	<select id="findByName" parameterType="String" resultMap="StudentMap">
		select * from t_student where stuName like concat('%',#{name},'%');
	</select>
```

测试

```
    // 根据姓名模糊匹配学生信息
    @Test
    public void testFindByName(){
        List<Student> stuList = studentMapper.findByName("T");
        System.out.println(stuList.size());
    }
```

#### 分页查询返回集合数据

StudentMapper接口中定义查询所有学生信息方法 带分页

```
// 带分页的查询
	public List<Student> findAll();
```

StudentMapper.xml中定义带有分页的SQL语句

```
<!--定义返回的结果集-->
	<resultMap id="StudentMap" type="Student">
		<id property="id" column="id"/>
		<result property="stuName" column="stuName"/>
		<result property="age" column="age"/>
	</resultMap>

<!--带分页条件的查询-->
	<select id="findAll" resultMap="StudentMap">
		select * from t_student limit 0,2
	</select>
```

测试

```
  // 查询全部学生信息 带分页
    @Test
    public void testFindAll(){
        List<Student> stuList = studentMapper.findAll();
        System.out.println(stuList.size());
    }
```

### 一对一查询

新增地址表  每个学生对应不同的行政区 并学生表中添加关联外键
![image-20230401191149962](https://user-images.githubusercontent.com/47961027/230051890-3755a933-4f60-4a43-8ba9-b84f53fd7d56.png)

#### 添加地址表实体类

```
public class Address {
    private Integer id;
    private String sheng;
    private String shi;
    private String qu;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public void setQu(String qu) {
        this.qu = qu;
    }

    public Integer getId() {
        return id;
    }

    public String getSheng() {
        return sheng;
    }

    public String getShi() {
        return shi;
    }

    public String getQu() {
        return qu;
    }
}

```



#### 修改学生实体类

关联地址实体 这样查询的时候可以直接将地址一并获取到

```
package com.etjava.model;

public class Student {

	private Integer id;
	private String stuName;
	private Integer age;
	private Address address;// 查询学生是可以直接将地址信息一并获取
	
	public Student() {
		super();
	}

	public Student(Integer id, String stuName, Integer age) {
		this.id = id;
		this.stuName = stuName;
		this.age = age;
	}

	public Student(String stuName, Integer age) {
		super();
		this.stuName = stuName;
		this.age = age;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}

```

#### StudentMapper接口中添加查询方法

```
	// 根据学生ID查询学生信息 带地址
	public Student findStudentWithAddress(Integer stuId);
```

#### SQL映射

##### 方式1 直接使用对象级联

StudentMapper.xml中需要在resultMap中添加外键映射

这种方式可重用性不高

```
	<!--定义返回的结果集-->
	<resultMap id="StudentMap" type="Student">
		<id property="id" column="id"/>
		<result property="stuName" column="stuName"/>
		<result property="age" column="age"/>
		<!--address.id 对象级联方式-->
		<result property="address.id" column="addressId"/>
		<result property="address.sheng" column="sheng"/>
		<result property="address.shi" column="shi"/>
		<result property="address.qu" column="qu"/>

	</resultMap>

	<!--根据ID查询学生信息 带地址-->
	<select id="findStudentWithAddress" parameterType="Integer" resultMap="StudentMap">
		SELECT * FROM t_student t1,t_address t2 WHERE t1.addressId=t2.id AND t1.id=#{id}
	</select>
```

##### 方式2 多结果集引用

将地址实体对象独立出来 然后在学生对象中引用

这种方式可以提高resultMap的可重用性

```
<!--引入其他的结果集-->
	<resultMap id="StudentMap" type="Student">
		<id property="id" column="id"/>
		<result property="stuName" column="stuName"/>
		<result property="age" column="age"/>
		<!--引用其他的resultmap-->
		<association property="address" resultMap="AddressMap"/>
	</resultMap>

	<!--定义地址实体类映射-->
	<resultMap id="AddressMap" type="Address">
		<id property="id" column="id"/>
		<result property="sheng" column="sheng"/>
		<result property="shi" column="shi"/>
		<result property="qu" column="qu"/>
	</resultMap>

	<!--根据ID查询学生信息 带地址-->
	<select id="findStudentWithAddress" parameterType="Integer" resultMap="StudentMap">
		SELECT * FROM t_student t1,t_address t2 WHERE t1.addressId=t2.id AND t1.id=#{id}
	</select>
```

##### 方式3 内嵌式

```
<!--内嵌式-->
	<resultMap id="StudentMap" type="Student">
		<id property="id" column="id"/>
		<result property="stuName" column="stuName"/>
		<result property="age" column="age"/>
		<!--直接内嵌一个对象进来-->
		<association property="address" javaType="Address">
			<id property="id" column="id"/>
			<result property="sheng" column="sheng"/>
			<result property="shi" column="shi"/>
			<result property="qu" column="qu"/>
		</association>
	</resultMap>
	
		<!--根据ID查询学生信息 带地址-->
	<select id="findStudentWithAddress" parameterType="Integer" resultMap="StudentMap">
		SELECT * FROM t_student t1,t_address t2 WHERE t1.addressId=t2.id AND t1.id=#{id}
	</select>
```

##### 方式4 高可用的结果集引入

###### 定义Address的Mapper接口 

根据ID查询

```
public interface AddressMapper {

    // 根据ID查询地址信息
    public Address findById(Integer id);
}
```

###### 定义AddressMapper接口的映射文件

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 映射  namespace 对应的是接口的完整名称-->
<mapper namespace="com.etjava.mappers.AddressMapper">


	<resultMap id="AddressMap" type="Address">
		<id property="id" column="id"/>
		<result property="sheng" column="sheng"/>
		<result property="shi" column="shi"/>
		<result property="qu" column="qu"/>
	</resultMap>

	<select id="findById" parameterType="Integer" resultMap="AddressMap">
		select * from t_address where id=#{id}
	</select>
</mapper> 
```

###### 在StudentMapper.xml中添加映射

```
<resultMap id="StudentMap" type="Student">
		<id property="id" column="id"/>
		<result property="stuName" column="stuName"/>
		<result property="age" column="age"/>
		<!--级联查询
		property 当前实体类中的属性
		column 对应的是当前类中的外键属性 addressId
		select 指定要执行的方法
		-->
		<association property="address" column="addressId" select="com.etjava.mappers.AddressMapper.findById"></association>
	</resultMap>
```

#### 测试

```
    @Test
    public void testFindStudentWithAddress(){
        Student stu = studentMapper.findStudentWithAddress(1);
        System.out.println(stu.getStuName()+"==="+stu.getAddress().getSheng());
    }

```

### 一对多查询

#### 查询年级带学生信息

查询年级的时候将年级中所有的学生带出来

##### 新建年级表

一个年级中有多个学生
![image-20230401210036199](https://user-images.githubusercontent.com/47961027/230052249-e7afc4c2-4c6d-4320-8b40-91a25176626b.png)


##### 添加年级实体类

```java
package com.etjava.model;

import java.util.List;

public class Grade {
    private Integer id;
    private String gradeName;
    private List<Student> studentList;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Integer getId() {
        return id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public List<Student> getStudentList() {
        return studentList;
    }
}

```

##### StudentMapper接口中添加根据年级ID查询的方法

```
// 根据年级查询学生信息
	public Student findByGradeId(Integer gradeId);
```

##### StudentMapper映射文件中添加SQL

```
<!--根据年级ID查询学生信息-->
	<select id="findByGradeId" parameterType="Integer" resultMap="StudentMap">
		select * from t_student where gradeId=#{gradeId}
	</select>
```

##### GradeMapper接口添加根据ID查询的方法

```
import com.etjava.model.Grade;

public interface GradeMapper {

    // 根据ID查询年级
    Grade findById(Integer id);
}
```

##### GradeMapper映射文件中添加对应的SQL映射

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 映射  namespace 对应的是接口的完整名称-->
<mapper namespace="com.etjava.mappers.GradeMapper">


	<resultMap id="GradeMap" type="Grade">
		<id property="id" column="id"/>
		<result property="gradeName" column="gradeName"/>
		<!--
		collection 一对多查询映射关系
		property 当前Grade中的属性
		column 对应的是Student类中的主键ID
		select 指向StudentMapper中根据年级ID查询的语句
		-->
		<collection property="studentList" column="id" select="com.etjava.mappers.StudentMapper.findByGradeId"></collection>
	</resultMap>

	<select id="findById" parameterType="Integer" resultMap="GradeMap">
		select * from t_grade where id=#{id}
	</select>
</mapper> 
```

##### 测试

```
package com.etjava.junit;

import com.etjava.mappers.GradeMapper;
import com.etjava.mappers.StudentMapper;
import com.etjava.model.Grade;
import com.etjava.model.Student;
import com.etjava.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

// 测试SQL映射器
public class TestGradeSQLMapping {
    private static Logger logger = Logger.getLogger(TestGradeSQLMapping.class);
    // 定义SQLSession 用来操作数据库
    SqlSession sqlSession = null;
    // 获取Student映射文件
    GradeMapper gradeMapper = null;
    // 测试方法执行之前
    @Before
    public void setUp(){
        sqlSession = SqlSessionFactoryUtil.openSession();
        gradeMapper = sqlSession.getMapper(GradeMapper.class);
    }

    // 测试方法执行之后
    @After
    public void tearDown(){
        sqlSession.close();
    }

    @Test
    public void testFindByGradeId(){
        Grade grade = gradeMapper.findById(1);
        System.out.println(grade);
    }
}

```

#### 查询学生带年级

一个学生只对应一个年级 因此是一对一关系查询

##### 修改学生实体类

添加年级属性

```
package com.etjava.model;

public class Student {

	private Integer id;
	private String stuName;
	private Integer age;
	private Address address;

	private Grade grade;
	
	public Student() {
		super();
	}

	public Student(Integer id, String stuName, Integer age) {
		this.id = id;
		this.stuName = stuName;
		this.age = age;
	}

	public Student(String stuName, Integer age) {
		super();
		this.stuName = stuName;
		this.age = age;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", stuName='" + stuName + '\'' +
				", age=" + age +
				", address=" + address +
				", grade=" + grade +
				'}';
	}
}
	
```

##### StudentMapper接口中添加根据ID查询学生信息

这个findById是需要使用定义的StudentMap结果集作为返回值的

```
	// 根据学生ID查询学生信息 带年级
	Student findByIdWithGrade(int id);
```

修改StudentMapper映射文件 

添加年级映射 及添加StudentMapper中的findByIdWithGrade方法

```
<resultMap id="StudentMap" type="Student">
		<id property="id" column="id"/>
		<result property="stuName" column="stuName"/>
		<result property="age" column="age"/>
		<!--级联查询
		一对一带的是本类的熟悉ID 
		一对多带的是对向类的主键ID
		property 当前实体类中的属性
		column 对应的是当前类中的外键属性 addressId
		select 指定要执行的方法
		-->
        <association property="address" column="addressId" select="com.etjava.mappers.AddressMapper.findById"></association>
        <!-- 查询学生信息时带年级信息 -->
        <association property="grade" column="gradeId" select="com.etjava.mappers.GradeMapper.findById"></association>
        </resultMap>
	
    <select id="findByIdWithGrade" resultMap="StudentMap" parameterType="Integer">
    	select * from t_student where id=#{id}
    </select>
```



##### 测试

```
 // 查询学生信息带年级 一对一
    @Test
    public void testStudentWithGrade(){
        Student stu = studentMapper.findByIdWithGrade(1);
        System.out.println(stu.getStuName()+"==="+stu.getAddress().getSheng()+"=="+stu.getGrade().getGradeName());
    }
```

![image-20230401214549294](https://user-images.githubusercontent.com/47961027/230052665-9367efab-7088-442c-ad40-53d13a433d1e.png)



## 多对多

两个一对多实现

多对多没有提供对应的映射标签 需要借助两个一对多实现

例如查询所有年级的时候把每个年级下的学生带出来

### GradeMapper接口中添加findAll

```
    // 查询所有年级信息 带每个年级下的所有学生
    List<Grade> findAll();
```

### GradeMapper映射文件中添加映射SQL

```
<select id="findAll" resultMap="GradeMap">
		select * from t_grade
	</select>
```

### 测试

要注意实体类中的toString不能出现相互引用 否则栈内存溢出

```
    @Test
    public void test(){
        List<Grade> gradeList = gradeMapper.findAll();
        for (Grade grade : gradeList) {
            System.out.println(grade);
        }
    }
```
![image-20230401215927613](https://user-images.githubusercontent.com/47961027/230052755-8cb829d1-b08d-4f12-ac5f-6ee80e3243f9.png)


## 多对多2

一个用户多个角色 一个角色可以赋给多个用户

### 建立两张表

```
CREATE DATABASE mybatis;  -- 创建数据库
USE mybatis;         -- 使用数据库
CREATE TABLE `user` (  -- 创建user表
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) NOT NULL COMMENT '用户名称',
  `birthday` DATETIME DEFAULT NULL COMMENT '生日',
  `sex` CHAR(1) DEFAULT NULL COMMENT '性别',
  `address` VARCHAR(256) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY  (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
-- 插入数据
INSERT  INTO `user`(`id`,`username`,`birthday`,`sex`,`address`) VALUES (1,'老王','2018-02-27 17:47:08','男','北京'),(2,'小二王','2018-03-02 15:09:37','女','北京金燕龙'),(3,'小二王','2018-03-04 11:34:34','女','北京金燕龙'),(5,'学无止路','2018-03-04 12:04:06','男','贵州省六盘水市'),(6,'老王','2018-03-07 17:37:26','男','北京'),(7,'小马宝莉','2018-03-08 11:44:00','女','北京修正');
-- 创建role表
CREATE TABLE `role` (
  `ID` INT(11) NOT NULL COMMENT '编号',
  `ROLE_NAME` VARCHAR(30) DEFAULT NULL COMMENT '角色名称',
  `ROLE_DESC` VARCHAR(60) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY  (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
-- 插入数据
INSERT  INTO `role`(`ID`,`ROLE_NAME`,`ROLE_DESC`) VALUES (1,'院长','管理整个学院'),(2,'总裁','管理整个公司'),(3,'校长','管理整个学校');
-- 创建user_role表
CREATE TABLE `user_role` (
  `UID` INT(11) NOT NULL COMMENT '用户编号',
  `RID` INT(11) NOT NULL COMMENT '角色编号',
  PRIMARY KEY  (`UID`,`RID`),
  KEY `FK_Reference_10` (`RID`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`RID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`UID`) REFERENCES `user` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
-- -- 插入数据
INSERT  INTO `user_role`(`UID`,`RID`) VALUES (1,1),(2,1),(1,2);
-- 查询数据
SELECT * FROM USER;
SELECT * FROM role;
SELECT * FROM user_role;

```

## 动态SQL

### if条件

当查询条件可能不存在时 需要通过if进行判断是否需要添加到查询语句中

#### 新建教员表

```
CREATE TABLE `t_teacher` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `teaName` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
   `teaAge` int(11) DEFAULT NULL,
   `curriculum` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '课程',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
```

#### 新建教员实体类

```
public class Teacher {
    private Integer id;
    private String teaName;
    private Integer teaAge;
    private String curriculum;
}
```

#### 新建TeacherMapper接口

```
public interface TeacherMapper {
    
    List<Teacher> find(Map<String,Object> map);
}
```

#### 创建TeacherMapper接口的映射文件

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 映射  namespace 对应的是接口的完整名称-->
<mapper namespace="com.etjava.mappers.TeacherMapper">
	<resultMap id="TeacherMap" type="Teacher">
		<id property="id" column="id"/>
		<result property="teaName" column="teaName"/>
		<result property="teaAge" column="teaAge"/>
		<result property="curriculum" column="curriculum"/>
	</resultMap>

	<select id="find" parameterType="Integer" resultMap="TeacherMap">
		select * from t_teacher where 1=1
		<if test="teaName!=null ">
			and teaName=#{teaName}
		</if>
		<if test="teaAge!=null">
			and teaAge=#{teaAge}
		</if>
		<if test="curriculum!=null">
			and curriculum=#{curriculum}
		</if>
	</select>
</mapper> 
```

#### 新建测试类

```
package com.etjava.junit;

import com.etjava.mappers.StudentMapper;
import com.etjava.mappers.TeacherMapper;
import com.etjava.model.Teacher;
import com.etjava.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherTest {
    private static Logger logger = Logger.getLogger(TeacherTest.class);
    // 定义SQLSession 用来操作数据库
    SqlSession sqlSession = null;
    // 获取Student映射文件
    TeacherMapper teacherMapper = null;
    // 测试方法执行之前
    @Before
    public void setUp(){
        sqlSession = SqlSessionFactoryUtil.openSession();
        teacherMapper = sqlSession.getMapper(TeacherMapper.class);
    }

    // 测试方法执行之后
    @After
    public void tearDown(){
        sqlSession.close();
    }

    // 测试多条件查询 if判断
    @Test
    public void testFind(){
        Map<String,Object> map = new HashMap<>();
        //map.put("teaName","Tom");
        List<Teacher> list = teacherMapper.find(map);
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }
}

```

### choose when otherwhise

类似if else if条件 但只有一个条件 例如 页面中用户在下拉框中选择一个查询条件时 动态获取对应的数据

#### TeacherMapper接口中添加查询方法

```
List<Teacher> find2(Map<String,Object> map);
```

#### TeacherMapper.xml中添加映射语句

```
<select id="find2" parameterType="Map" resultMap="TeacherMap">
		select * from t_teacher 
		<choose>
			<!-- 相当于if -->
			<when test="searchBy=='name'">
				where teaName=#{teaName}
			</when>
			<when test="searchBy=='age'">
				where teaAge=#{teaAge}
			</when>
			<!--相当于else-->
			<otherwise>
				where curriculum='Java'
			</otherwise>
		</choose>
	</select>
```

#### 测试

```
@Test
    public void testFind2(){
        Map<String,Object> map = new HashMap<>();
        map.put("searchBy","name");
        map.put("teaName","Tom");
        List<Teacher> list = teacherMapper.find2(map);
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }
```

### where条件

如果只使用if 我们必须有一个固定条件 如果写where 1=1这样是非常不好的 因为会全表扫描

自动添加where关键字，如果where子句以and或or开头会自动删除第一个and或or

#### TeacherMapper接口中添加查询方法

```
List<Teacher> find3(Map<String,Object> map);
```

TeacherMapper.xml中添加映射语句

```
<select id="find3" parameterType="Map" resultMap="TeacherMap">
		select * from t_teacher
		<where>
			<if test="teaName!=null">
				and teaName=#{teaName}
			</if>
			<if test="teaAge!=null">
				and teaAge=#{teaAge}
			</if>
		</where>
	</select>
```

#### 测试

```
 @Test
    public void testFind3(){
        Map<String,Object> map = new HashMap<>();
        map.put("teaName","Tom");
        List<Teacher> list = teacherMapper.find3(map);
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }
```

### trim条件

与where类似 提供了SQL中的前缀和后缀功能 例如 将第一个and替换为where等

同时提供了后缀功能 suffix 实际开发中极少情况SQL会需要添加后缀

#### TeacherMapper接口添加查询方法

```
 List<Teacher> find4(Map<String,Object> map);
```

#### TeacherMaper.xml中添加映射语句

```
<select id="find4" parameterType="Map" resultMap="TeacherMap">
		select * from t_teacher
		<!-- prefix="where" 添加前缀  prefixOverrides="and|or" 覆盖前缀第一个and或or-->
		<trim prefix="where" prefixOverrides="and|or">
			<if test="teaName!=null">
				and teaName=#{teaName}
			</if>
			<if test="teaAge!=null">
				and teaAge=#{teaAge}
			</if>
		</trim>
	</select>
```

#### 测试

```
 @Test
    public void testFind4(){
        Map<String,Object> map = new HashMap<>();
        map.put("teaName","Tom");
        List<Teacher> list = teacherMapper.find4(map);
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }
```

### foreach

SQL中传入集合时用来编辑集合中数据的

#### TeacherMapper接口中添加查询方法

```
List<Teacher> find5(Map<String,Object> map);
```

#### TeacherMapper.xml中添加SQL映射语句

```
 <select id="find5" parameterType="Map" resultMap="TeacherMap">
		select * from t_teacher
		<if test="ids!=null">
		    <!--
				collection="ids"  传入的集合
				item="id" 每次变量获取的数据存放到id中
				open="(" 添加前缀 小括号
				separator="," 添加分隔符
				close=")" 添加后缀小括号
		    -->
			<where>
				id in
		         <foreach item="id" collection="ids"  open="(" separator="," close=")">
					#{id}
				 </foreach>
			</where>
		</if>
	</select>
```

#### 测试

```
@Test
    public void testFind5(){
        Map<String,Object> map = new HashMap<>();
        map.put("teaName","Tom");
        List<Integer> ids = new ArrayList<>();
        Collections.addAll(ids,1,2);
        map.put("ids",ids);
        List<Teacher> list = teacherMapper.find5(map);
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }
```

### set条件

自动添加set关键字 并自动剔除最后一个逗号

#### TeacherMapper接口中添加更新数据的方法

```
void update(Teacher teacher);
```

#### TeacherMapper.xml中添加映射SQL语句

```
<update id="update" parameterType="Teacher">
		update t_teacher
		<set>
			<if test="teaName!=null">
				teaName=#{teaName},
			</if>
		    <if test="teaAge!=null">
				teaAge=#{teaAge},
			</if>
		</set>
		where id=#{id}
	</update>
```

#### 测试

```
@Test
    public void testUpdate(){
       Teacher teacher = new Teacher();
       teacher.setId(1);
       teacher.setTeaAge(22);
       teacher.setTeaName("Tom2");
       teacherMapper.update(teacher);
       sqlSession.commit();// 更新操作 junti测试时需要手动提交
    }
```

## 处理CLOB、BLOG类型数据

CLOB 文本类型 但数据流较大  mysql中使用longtext类型 因为text存放的数据较小

BLOB 媒体文件 mysql中使用longblob类型 因为blob存放的数据较小

### 修改教员表

新增pic教员图片 remark教员简介字段

```
CREATE TABLE `t_teacher` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `teaName` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
   `teaAge` int(11) DEFAULT NULL,
   `curriculum` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '课程',
   `pic` longblob,
   `remark` longtext COLLATE utf8_unicode_ci,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
```

### Teacher实体类中新增两个字段对应的属性

```
public class Teacher {
    private Integer id;
    private String teaName;
    private Integer teaAge;
    private String curriculum;
    private byte[] pic;// blob 字节形式
    private String remark;// clob 字符串
}
```

### TeacherMapper接口中新增添加和修改方法

```
int addTeacher(Teacher teacher);
Teacher findById(Integer id);
```

### TeacherMapper.xml新增添加和修改的映射SQL

```
<insert id="addTeacher" parameterType="Teacher">
insert into t_teacher values(null,#{teaName},#{teaAge},#{curriculum},#{pic},#{remark})
</insert>

<select id="findById" parameterType="Integer" resultMap="TeacherMap">
select * from t_teacher where id=#{id}
</select>
```

### 测试添加

```
@Test
    public void testAdd(){
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setTeaAge(22);
        teacher.setTeaName("Tom2");
        teacher.setRemark("这是一个很长的文本内容...........");
        byte[] pic = null;
        try (FileInputStream fis = new FileInputStream("d:/a.jpg");){
            pic = new byte[fis.available()];// available 文件的长度
            fis.read(pic);// 将文件存放到字节数组中
        }catch (Exception e){
            e.printStackTrace();
        }
        teacher.setPic(pic);
        int res = teacherMapper.addTeacher(teacher);
        System.out.println(res);
        sqlSession.commit();
    }
```
![image-20230405004853395](https://user-images.githubusercontent.com/47961027/230052903-7775cef6-9135-4353-a582-0028948efb47.png)


### 测试读取

```
@Test
    public void testFindById(){
        Teacher tea = teacherMapper.findById(6);
        System.out.println(tea);// 普通属性直接输出
        // 图片数据需要保存到本地磁盘
        byte[] pic = tea.getPic();
        try(FileOutputStream fos = new FileOutputStream("d:/1.jpg")){
            fos.write(pic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
```

## 多参数查询

多参数查询时通常使用Map或List 参考动态SQL内容的实现

这里在讲一个不推荐的方式

例如 一个查询方法中有两个参数 此时需要使用param1  param2方式实现

### TeacherMapper接口中添加查询方法

```
 Teacher findA3(String teaName,int age);
```

### TeacherMapper.xml中添加映射SQL

```
<!-- 如果入参是多个 这里就不能直接指定参数类型 而改为#{param1} #{param2} 方式实现-->
	<select id="findA3" resultMap="TeacherMap">
		select * from t_teacher where teaName=#{param1} and teaAge=#{param2}
	</select>
```

### 测试

```
    @Test
    public void test(){
        Teacher tom = teacherMapper.findA3("Tom", 12);
        System.out.println(tom);
    }
```

## MyBatis分页

MyBatis分页分为逻辑分页和物理分页

逻辑分页是在内存中进行分页 例如一百条数据 每页显示十条 那么每次都会获取这一百条数据 然后在从内存中把需要的数据单独拿出来 这种性能较低

物理分页 我们实际怎么查询就怎么返回数据

### 逻辑分页[不推荐]

逻辑分页也叫内存分页

#### TeacherMapper接口添加查询方法

```
// 逻辑分页 RowBounds
List<Teacher> findA4(RowBounds rowBounds);
```

#### TeacherMapper.xml中添加映射SQL

```
<!-- 逻辑分页 参数RowBounds可以不用定义数据类型-->
	<select id="findA4" resultMap="TeacherMap" >
		select * from t_teacher
	</select>
```

#### 测试

```
@Test
    public void test2(){
        // RowBounds 构造方法有两个参数
        // 第一个相当于start  起始页
        // 第二个相当于截止页码
        // 相当于 limit 0,3  注意 这是在内存中进行分页的
        List<Teacher> list = teacherMapper.findA4(new RowBounds(3, 3));
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }
```

### 物理分页

实际需要多少就直接取多少数据[推荐]

#### TeacherMapper接口中添加分页查询方法

```
// 物理分页
    List<Teacher> findA4(Map<String,Object> map);
```

#### TeacherMapper.xml中添加分页SQL映射

```
<!-- 物理分页 真正的分页-->
	<select id="findA5" resultMap="TeacherMap" >
		select * from t_teacher 
		<if test="start!=null and size!=null">
			limit #{start},#{size}
		</if>
	</select>
```

#### 测试

```
@Test
    public void test3(){
        Map<String,Object> map = new HashMap<>();
        map.put("start",0);
        map.put("size",2);
        List<Teacher> list = teacherMapper.findA5(map);
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }
```

## MyBatis缓存

MyBatis的缓存机制是用来提高查询效率的，默认启用的是一级缓存，即同一个sqlsession接口对象调用了相同的select语句 则直接在缓存中返回结果 而不是在查询一次数据库

我们可以配置二级缓存，二级缓存是全局的

默认情况下只有 select语句使用缓存，insert，update，delete不使用缓存

### 配置二级缓存

在接口的Mapper映射文件中添加如下内容

TeacherMapper.xml

```
<!--
size="1024"  表示cache中能容纳最大元素的数量 默认1024
flushInterval="60000" 定义缓存刷新周期 单位毫秒
eviction="LRU" 定义缓存的移除机制，默认LRU(Least Recently Used) 最近最少使用
还有FIFO(First In First Out) 先进先出
readOnly="false" 是否只读  默认false 可读可写   true 缓存只能读
-->
<cache size="1024" flushInterval="60000" eviction="LRU" readOnly="false"/>
	
然后在select中添加使用缓存配置
<!--
useCache="true" 定义使用缓存 默认就是true
flushCache="false" 定义是否清空缓存  false为不清空缓存
insert 语句中默认是清空缓存的
-->
<select id="find" parameterType="Map" resultMap="TeacherMap" useCache="true" flushCache="false">

</select>
添加修改删除语句默认清空缓存的
<insert id="addTeacher" parameterType="Teacher" flushCache="true">
</insert>
```

## 使用注解配置SQL映射器

### 新建测试表 t_user2

```
CREATE TABLE `t_user2` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
   `age` int(11) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
```

### 新建实体类

```
public class User2 {
    private Integer id;
    private String name;
    private Integer age;
}
```

### 新建Mapper接口

```
public interface User2Mapper {

    Integer add(User2 user2);
    Integer update(User2 user2);
    Integer delete(Integer id);
    List<User2> list();

}
```

### 基本映射注解

所有注解都在Mapper接口中添加

#### @Insert

```
@Insert("insert into t_user2 values(null,#{name},#{age})")
Integer add(User2 user2);
```

#### 测试

```
 @Test
    public void testInsert(){
        User2 u = new User2();
        u.setName("Judy");
        u.setAge(16);
        Integer res = userMapper.add(u);
        System.out.println(res);
        sqlSession.commit();
    }
```

#### @Update

```
@Update("update t_user2 set name=#{name},age=#{age} where id=#{id}")
Integer update(User2 user2);
```

#### 测试

```
   @Test
    public void testUpdate(){
        User2 u = new User2();
        u.setName("Judy2");
        u.setAge(16);
        u.setId(6);
        Integer res = userMapper.update(u);
        System.out.println(res);
        sqlSession.commit();
    }
```

#### @Delete

```
@Delete("delete from t_user2 where id=#{id}")
Integer delete(Integer id);
```

#### 测试

```
    @Test
    public void testDelete(){
        Integer res = userMapper.delete(3);
        System.out.println(res);
        sqlSession.commit();
    }
```



#### @Select

基本映射及结果集映射

```
@Select("select * from t_user2 where id=#{id}")
User2 findById(Integer id);
结果集映射注解
@Select("select * from t_user2")
    @Results(// 映射结果集 相当于配置文件中的resultMap
            {
                    @Result(id = true,column = "id",property = "id"),
                    @Result(column = "name",property = "name"),
                    @Result(column = "age",property = "age")
            }
    )
    List<User2> list();
```

#### 测试

```
    @Test
    public void testQuery(){
        User2 user = userMapper.findById(1);
        System.out.println(user);
    }
    
    @Test
    public void testQuery(){
        List<User2> list = userMapper.list();
        for (User2 user2 : list) {
            System.out.println(user2);
        }
    }
```

### 关系映射

#### 一对一

##### 更改user2表 添加地址外键

```
CREATE TABLE `t_user2` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
   `age` int(11) DEFAULT NULL,
   `addressId` int(11) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
```

##### 地址表

```
CREATE TABLE `t_address` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `sheng` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
   `shi` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
   `qu` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
```

##### 修改实体

```
public class User2 {
    private Integer id;
    private String name;
    private Integer age;
    private Address address;
}
```

##### User2Mapper接口中添加查询语句

关联的外键属性

```
@Select("select * from t_user2")
    @Results(// 映射结果集 相当于配置文件中的resultMap
            {
                    @Result(id = true,column = "id",property = "id"),
                    @Result(column = "name",property = "name"),
                    @Result(column = "age",property = "age"),
                    // one = @One 表示一对一 需要关联其他Mapper接口中的查询方法
                   	// column 表中的外键 property实体类中的属性
                   // 如果AddressMapper中可以使用xml映射文件 也可以使用注解方式根据ID查询
                    @Result(column = "addressId",property = "address"
                    ,one = @One(select = "com.etjava.mappers.AddressMapper.findById"))
            }
    )
    List<User2> findUserWithAddress();
```

##### 测试

```
    @Test
    public void testQuery3(){
        List<User2> u = userMapper.findUserWithAddress();
        for (User2 user2 : u) {
            System.out.println(user2);
        }
    }
```

#### 一对多

查询部门 需要将部门下所有员工信息获取到

##### 部门表

将User2看作雇员表  新增一个部门表  一个部门下有多个雇员

user2修改 添加雇员表外键

```
CREATE TABLE `t_user2` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
   `age` int(11) DEFAULT NULL,
   `addressId` int(11) DEFAULT NULL,
   `deptId` int(11) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
```

一个部门下有多个雇员

```
CREATE TABLE `t_dept` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `deptName` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
```

##### 新建实体

```
public class Dept {
    private Integer id;
    private String deptName;
    private List<User2> userList;
}
```

##### 新建DeptMapper

```
public interface DeptMapper {
    @Select("select * from t_dept where id=#{id}")
    Dept findById(Integer id);
}
```

##### User2实体中添加雇员属性

```
public class User2 {
    private Integer id;
    private String name;
    private Integer age;
    private Address address;
    private Dept dept;
}
```

##### DeptMapper接口添加查询方法

```
public interface DeptMapper {

    @Select("select * from t_dept where id=#{id}")
    @Results(
            {
                    @Result(id = true,column = "id",property = "id"),
                    @Result(column = "deptName",property = "deptName"),
                    // 关联查询 需要带入当前表的主键 到User表中根据外键查询
                    @Result(column = "id",property = "userList"
                    ,many = @Many(select = "com.etjava.mappers.User2Mapper.findUserWithDeptId"))
            }
    )
    Dept findById(Integer id);
}
```

##### User2Mapper接口中添加根据deptId查询的方法

```
@Select("select * from t_user2 where deptId=#{deptId}")
    @Results(// 映射结果集 相当于配置文件中的resultMap
            {
                    @Result(id = true,column = "id",property = "id"),
                    @Result(column = "name",property = "name"),
                    @Result(column = "age",property = "age"),
                    // one = @One 表示一对一 需要关联其他Mapper接口中的查询方法
                    @Result(column = "addressId",property = "address"
                            ,one = @One(select = "com.etjava.mappers.AddressMapper.findById"))
            }
    )
    List<User2> findUserWithDeptId(Integer deptId);
```

##### 测试

```
package com.etjava.junit;

import com.etjava.mappers.DeptMapper;
import com.etjava.mappers.User2Mapper;
import com.etjava.model.Dept;
import com.etjava.model.User2;
import com.etjava.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DeptTest {
    private static Logger logger = Logger.getLogger(DeptTest.class);
    // 定义SQLSession 用来操作数据库
    SqlSession sqlSession = null;
    // 获取Student映射文件
    DeptMapper deptMapper = null;
    // 测试方法执行之前
    @Before
    public void setUp(){
        sqlSession = SqlSessionFactoryUtil.openSession();
        deptMapper = sqlSession.getMapper(DeptMapper.class);
    }

    // 测试方法执行之后
    @After
    public void tearDown(){
        sqlSession.close();
    }


    @Test
    public void testQuery3(){
        Dept d = deptMapper.findById(1);
        System.out.println(d);
    }
}

```

#### 作业练习

查询用户时带部门信息

##### User2实体类

```
public class User2 {
    private Integer id;
    private String name;
    private Integer age;
    private Address address;
    private Dept dept;
}
```

##### Dept实体类

```
public class Dept {
    private Integer id;
    private String deptName;

    private List<User2> userList;
}
```



##### User2Mapper接口中添加查询

```
 @Select("select * from t_user2 where id=#{id}")
    @Results(// 映射结果集 相当于配置文件中的resultMap
            {
                    @Result(id = true,column = "id",property = "id"),
                    @Result(column = "name",property = "name"),
                    @Result(column = "age",property = "age"),
                    // one = @One 表示一对一 需要关联其他Mapper接口中的查询方法
                    @Result(column = "addressId",property = "address"
                            ,one = @One(select = "com.etjava.mappers.AddressMapper.findById")),
                    // 查询用户带部门信息
                    @Result(column = "deptId",property = "dept",one = @One(select = "com.etjava.mappers.DeptMapper.findById"))
            }
    )
    List<User2> findUserWithDept(Integer id);
```

##### DeptMapper接口不需要改动

```
public interface DeptMapper {

    @Select("select * from t_dept where id=#{id}")
    @Results(
            {
                    @Result(id = true,column = "id",property = "id"),
                    @Result(column = "deptName",property = "deptName"),
                    // 关联查询 需要带入当前表的主键 到User表中根据外键查询
                    @Result(column = "id",property = "userList"
                    ,many = @Many(select = "com.etjava.mappers.User2Mapper.findUserWithDeptId"))
            }
    )
    Dept findById(Integer id);
}
```

##### 测试

```
    @Test
    public void findUserWithDept(){
        List<User2> u = userMapper.findUserWithDept(1);
        for (User2 user2 : u) {
            System.out.println(user2);
            Dept dept = user2.getDept();
            System.out.println(dept);
        }
    }
```

#### 动态SQL

以Dept为例 动态拼接SQL

动态SQL需要额外指定实现类(SQL的拼装类)

##### @InsertProvider

###### 创建动态SQL拼接类

```
package com.etjava.mappers;

import com.etjava.model.Dept;
import org.apache.ibatis.jdbc.SQL;

public class DeptSQLProvider {

    // 这里如果传递参数 必须final修饰
    public String insertDept(final Dept dept){
        return new SQL(){
            // 这里需要动态拼接是SQL
            {
                INSERT_INTO("t_dept");
                if(dept.getDeptName()!=null){
                    // 如果insertDept方法 没有传递参数 可以直接通过#{name} 方式赋值
                    // 如果是直接赋值 需要两边添加单引号
                    VALUES("deptName","#{deptName}");
                }
            }
        }.toString();
    }
}

```

###### DeptMapper 新增insert方法

```
// type 指定SQL提供者
// method 指定SQL提供者中的具体某个方法
@InsertProvider(type = DeptSQLProvider.class,method = "insertDept")
Integer add(Dept dept);
```

###### 测试

```
    @Test
    public void testInsert(){
        Dept d = new Dept();
        d.setDeptName("教学5部门");
        Integer res = deptMapper.add(d);
        System.out.println(res);
        sqlSession.commit();
    }
```



##### @UpdateProvider

###### DeptSQLProvider中添加SQL拼装

```
public String updateDept(final Dept d){
        return new SQL(){
            {
                UPDATE("t_dept");
                if(d.getDeptName()!=null){
                    SET("deptName=#{deptName}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }
```

###### DeptMapper接口添加update方法

```
@UpdateProvider(type=DeptSQLProvider.class,method = "updateDept")
Integer update(Dept d);
```

###### 测试

```
    @Test
    public void testUpdate(){
        Dept d = new Dept();
        d.setDeptName("教学5部门2");
        d.setId(4);
        Integer res = deptMapper.update(d);
        System.out.println(res);
        sqlSession.commit();
    }
```

##### @DeleteProvider

###### DeptSQLProvider添加SQL拼装

```
   // 根据ID删除学生 final Integer id  可以不写 因为可以使用#{id} 方式直接取值
    public String deleteDept(){
        return new SQL(){
            {
                DELETE_FROM("t_dept");
                WHERE("id=#{id}");
            }
        }.toString();
    }
```

###### DeptMapper接口中添加delete方法

```
@DeleteProvider(type=DeptSQLProvider.class,method = "deleteDept")
Integer delete(Integer id);
```

###### 测试

```
    @Test
    public void testDelete(){
        Integer res = deptMapper.delete(4);
        System.out.println(res);
        sqlSession.commit();
    }
```

##### @SelectProvider 查询单个

###### DeptSQLProvider添加SQL拼接

```
public String selectById(){
        return new SQL(){
            {
                SELECT("id,deptName");// 可以使用通配符 也可以手动写所需要的列
                FROM("t_dept");
                WHERE("id=#{id}");
            }
        }.toString();
    }
```

DeptMapper接口添加查询方法

```
@SelectProvider(type=DeptSQLProvider.class,method = "selectById")
Dept findById2(Integer id);
```

###### 测试

```
  @Test
    public void testSelectById(){
        Dept de = deptMapper.findById(1);
        System.out.println(de);
    }
```

##### @SelectProvider 查询全部

###### DeptSQLProvider拼接SQL

```
public String findAll(final Map<String,Object> map){
        return new SQL(){
            {
                SELECT("*");
                FROM("t_dept");
                // 自己拼接SQL
                StringBuffer buf = new StringBuffer();
                if(map.get("deptName")!=null){
                    buf.append("and deptName='"+map.get("deptName")+"'");
                }
                // 不为空时在拼接where条件
                if(!buf.toString().equals("")){
                    WHERE(buf.toString().replaceFirst("and",""));
                }
            }
        }.toString();
    }
```

###### DeptMapper接口添加查询全部的方法

```
 @SelectProvider(type=DeptSQLProvider.class,method = "findAll")
 List<Dept> findAll(Map<String,Object> map);
```

###### 测试

```
   @Test
    public void testSelectAll(){
        Map<String,Object> map = new HashMap<>();
        map.put("deptName","教学1部");
        List<Dept> all = deptMapper.findAll(map);
        for (Dept dept : all) {
            System.out.println(dept);
        }
    }
```

## MyBatis与Spring,SpringMVC整合

Spring5.x

```
web.xml
	用来配置spring和springmvc

springmvc.xml
	用来配置controller和视图解析 静态资源映射等

application.xml
	spring的配置文件 用来管理bean及数据源配置及sessionFactory
	整合mybatis(mybatis-spring.jar)
	配置事务切面

mybatis-config.xml
	mybatis的配置文件 主要用来扫描实体类
	

分层
	entity  实体类
	dao/mappers 持久层 （mappers 持久层 映射dao接口的SQL）
	service/impl 业务处理层
	controller 控制层
	
```





在启动项目时 如果出现ClassNotFoundException: org.springframework.web.context.ContextLoaderListener

解决方案

File->Project Structure->Artifacts->选中右侧的Avaliable Elements 然后右键 在点击ok即可

这样做的目的是将项目中的jar生成到webinf下的lib目录中

![image-20230405200604472](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230405200604472.png)
