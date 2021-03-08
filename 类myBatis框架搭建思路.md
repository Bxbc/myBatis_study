## Java 数据库操作

#### 流行的ORM框架 （Object Relation Mapping）
* Hibernate
	* 非常成熟的ORM框架
	* 完成对象的持久化操作
	* Hibernate允许开发者采用面向对象的方式来操作关系型数据库
	* 消除那些针对特定数据库厂商的SQL代码

* MyBatis
	* 相比HIbernate灵活高，运行速度快
	* 开发速度慢，不支持纯粹的面相对象操作，需要熟悉SQL语句，并且熟悉SQL语句优化

* TopLink

* OJB

#### JDBC

##### JDBC操作

```java
public static void main(String[] args){
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		Resultset resultset = null;
		try {
			//加载数据库驱动
			Class. forName("com.mysql.jdbc.Driver");
			//通过驱动管理类获取数据库链接
			connection = DriverManager.getconnection("jdbc:mysgl://localhost:3306/mybatis?characterEncoding=utf-8", "root", "root");
			//定义sql语句？表示占位符
			String sql ="select * from user where username= ?";
			//获取预处理statement
			preparedstatement = connection.prepareStatement(sql);
			//设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
			preparedStatement.setstring(l, "tom");
			//向数据库发出sql执行查询，查询出结果集
			resultset = preparedstatement.executeQuery();
			//遍历查询结果集
			while (resultset.next()){
				int id = resultset.getInt("id");
				String username = resultset.getstring("username");
				//封装User
				user .setId(id);
				user.setUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 资源释放
		}
}
```

##### JDBC 问题分析
1. 数据库配置信息存在硬编码问题，更改数据库需要重新编译打包，十分不方便
2. 频繁创建释放数据库链接
3. sql语句，参数设置，获取结果集参数均存在硬编码问题
4. 需要手动封装返回的结果集，比较繁琐

###### 解决思路：
1. 使用配置文件
2. 使用连接池子
3. 仍然采用配置文件
4. 使用反射和内省

#### 自定义持久层框架设计
根本思路就是自己实现对 JDBC 进行封装

##### 思路
* 使用端：
	提供两部分配置信息：数据库配置信息，sql配置信息，sql语句，参数类型，返回值类型，且这些信息不能写在代码中，否则又出现硬编码问题。所以使用端需要提供配置文件：
	1. sqlMapConfig.xml：存放数据库配置信息，存放mapper.xml的全路径
	2. mapper.xml：存放sql配置信息

* 自定义持久层框架本身：
	1. 加载配置文件：根据配置文件的路径，加载配置文件成为字节输入流，存储在内存中。
		创建Resource类，拥有方法：getResourceAsStream(String path）
	2. 创建两个 `javaBean`：容器对象，存放的就是对配置文件解析出来的内容
		Configuration：核心配置类，存放sqlMapConfig.xml解析出来的内容
		MappedStatement：映射配置类，存放mapper.xml解析出来的内容
	3. 解析配置文件：dom4j
		* 创建一个类— SqlSessionFactoryBuilder，拥有方法 build（InputStream in）。
		* 使用 dom4j 解析配置文件，并将解析出来的内容封装到容器对象中。 
		* 创建SqlSessionFactory对象，返回一个sqlSession 会话对象（工厂模式）
	4. 创建SqlSessionFactory接口及实现类DefaultSqlSessionFactory
		* openSession()：生成sqlSession
	5. 创建SqlSession接口及实现类DefaultSession
		* 定义对数据库的crud操作：
			* selectList()
			* selectOne()
			* update()
			* delete()
	6. 创建Executor接口及实现类SimpleExecutor实现类
		* query(Configuration, MappedStatement, Object…params)：执行的就是JDBC代码。因为传入参数应该是灵活可变的，所以传入的应该是一个可变参。

##### 使用自定义框架存在的问题：
1. 存在代码冗余，整个操作过程模板重复
2. 对于statementId存在硬编码问题

##### 进一步改进思路：
* 使用代理模式生成Dao层接口的代理实现类
* 对持久层接口用动态代理进行实现

#### MyBatis

MyBatis是一款优秀的持久层框架，它支持定制化SQL、存储过程以及高级映射。MyBatis避免了几乎所有的JDBC代码和手动设置参数以及获取结果集。MyBatis可以使用简单的XML或注解来配置和映射原生类型、接口和Java的POJO （Plain Old Java Objects，普通老式Java对象）为数据库中的记录。

##### 特点
* 基于ORM：Object Relation Mapping
* 半自动
* 轻量级

##### 优势
核心sql语句还是需要开发人员优化，sql和java编码分开，功能边界清晰，一个专注数据，一个专注业务。



