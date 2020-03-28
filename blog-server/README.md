# BLOG-SERVER （服务端）

## 全文搜索

+ 数据结构
 `结构化数据` ：有固定格式或者有限长度的数据 如：数据库，元数据
 `非结构化数据` ：不定长或者无固定格式的数据 如：邮件，word文档

+ 非结构化数据的检索
   - 顺序扫描
   - 全文搜索
  
### Elasticsearch

> Elasticsearch （ES）是一个基于 Lucene 的开源搜索引擎，它不但稳定、可靠、快速，而且也具有良好的水平扩展能力，是专门为分布式环境设计的。
> 特点： 1.分布式 2.高可用 3.多类型 4.多API 5.面向文档 6.异步写入 7.近实时


### SpringBoot 和 Elasticsearch

> SpringBoot 默认支持两种技术来实现和ES交互
> 1. Jest(默认不生效)
> 2. SpringData ElasticSearch
>    1) clusterNodes 节点信息
>    2) ElasticsearchRepository 操作ES (类似JPA)
>

### docker安装Elasticsearch以及简单的运行
+ pull 拉取镜像
    ```
    docker pull docker.elastic.co/elasticsearch/elasticsearch:6.3.2
    ``` 
+ 启动es

    名字为es, 9200端口, 启动单节点 ，限制虚拟内存大小
    ```
    docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -e “discovery.type=single-node” -d -p 9201:9200 -p 9301:9300 --name es elasticsearch:6.8.4
    ```
+ 配置es的文件
    
    ```
    docker exec -it es /bin/bash
    ```
    在elasticsearch.yml中添加, 配置跨域
    ```
    http.cors.enabled: true
    http.cors.allow-origin: "*"
    ```
    最后 `curl 'http://localhost:9201/'`（使用域名同样可以，注意开启对应的端口：9201）, 得到如下：

    ```
    {
      "name" : "9WjTW_p",
      "cluster_name" : "docker-cluster",
      "cluster_uuid" : "L_z1NHRuTbyB2f_s8A9glw",
      "version" : {
        "number" : "6.8.4",
        "build_flavor" : "default",
        "build_type" : "docker",
        "build_hash" : "bca0c8d",
        "build_date" : "2019-10-16T06:19:49.319352Z",
        "build_snapshot" : false,
        "lucene_version" : "7.7.2",
        "minimum_wire_compatibility_version" : "5.6.0",
        "minimum_index_compatibility_version" : "5.0.0"
      },
      "tagline" : "You Know, for Search"
    }
    
    ```

### 正式使用前的需知的基本概念

`索引`: es中的一个逻辑存储（关系型数据库的数据库）

`索引类型`:索引对象可以存储多个不同用途的对象，通过索引类型（index_type）可以区分单个索引中的不同对象，(关系型数据库中的表)

`文档`:存储在es中的主要实体叫文档（document），可以理解为关系型数据库中表的一行记录。每个文档由多个字段构成

`映射`:描述了文档可能具有的字段或属性。每个字段的数据类型（如 string, integer 或 date

推荐到查看文档：`Elasticsearch: 权威指南`
https://www.elastic.co/guide/cn/elasticsearch/guide/current/_analytics.html


## 爬虫
> WebMagic是一个简单灵活的Java爬虫框架。基于WebMagic，你可以快速开发出一个高效、易维护的爬虫

### 整体思路

1. 配置初始化的URL。访问URL-->解析URL-->获取用户的URL

2. 抓取用户的数据（WebMagic）并分页处理。

3. 抓取文章数据（WebMagic），如果抓取过程中出现失败，则采用selenium+Chrome 的方式抓取页面，并进行cookie重置

4. 抓取的Html数据解析（解析器），封装并持久化

5. 定时抓取。

### 涉及到技术
#### WebMagic基本的概念
+ `Downloader`

    Downloader负责从互联网上下载页面，以便后续处理。WebMagic默认使用了[Apache HttpClient](http://hc.apache.org/index.html)作为下载工具。
+ `PageProcessor`
    
    PageProcessor负责解析页面，抽取有用信息，以及发现新的链接。WebMagic使用[Jsoup](http://jsoup.org/)作为HTML解析工具，并基于其开发了解析XPath的工具[Xsoup](https://github.com/code4craft/xsoup)。
+ `Scheduler`

    Scheduler负责管理待抓取的URL，以及一些去重的工作。WebMagic默认提供了JDK的内存队列来管理URL，并用集合来进行去重。也支持使用Redis进行分布式管理。
+ `Pipeline`

     Pipeline负责抽取结果的处理，包括计算、持久化到文件、数据库等。WebMagic默认提供了“输出到控制台”和“保存到文件”两种结果处理方案。

整体实现的框架流程 ：

![流程](http://code4craft.github.io/images/posts/webmagic.png)

#### Selenium
> Selenium 是一个用于 Web 应用程序测试的工具。它的优点在于，浏览器能打开的页面，使用 selenium 就一定能获取到。

> 调用chrome浏览器来获取Cookie，例如CSDN这类网站的cookie通过js来生成的，需要浏览器才能得到Cookie。

#### Xpath

#### 代理IP

> 当我们对某些网站进行爬去的时候，我们经常会换IP来避免爬虫程序被封锁。其实也是一个比较简单的操作，目前网络上有很多IP代理商，例如西刺，芝麻，犀牛等等。这些代理商一般都会提供透明代理，匿名代理，高匿代理。


## JPA配置信息

hibernate.hbm2ddl.auto 参数的作用主要用于：自动创建、更新、验证数据库表结构，有四个值。

create：每次加载 Hibernate 时都会删除上一次生成的表，然后根据 model 类再重新来生成新表，哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。
create-drop：每次加载 Hibernate 时根据 model 类生成表，但是 sessionFactory 一关闭，表就自动删除。
update：最常用的属性，第一次加载 Hibernate 时根据 model 类会自动建立起表的结构（前提是先建立好数据库），以后加载 Hibernate 时根据 model 类自动更新表结构，即使表结构改变了，但表中的行仍然存在，不会删除以前的行。要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等应用第一次运行起来后才会。
validate ：每次加载 Hibernate 时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。
配置文件中:

dialect 主要是指定生成表名的存储引擎为 InnoDB
show-sql 是否在日志中打印出自动生成的 SQL，方便调试的时候查看

### BaseRepository
`@NoRepositoryBean`:在服务器启动的时候，jpa的启动管理类会自动扫荡继承了JpaRepository的接口，然后添加到动态代理管理中，
然后注入到spring的容器中确保添加了该注解的 repository 接口不会在运行时被创建实例。也就是说，使用了该注解的接口不会被单独创建实例，只会作为其他接口的父接口而被使用。



## 角色管理系统
`RBAC`: Role-Based Access Control  基于角色的访问控制
### Spring Security 



### 其他

`@NotNull` 是 JSR303（Bean的校验框架）的注解，用于运行时检查一个属性是否为空，如果为空则不合法。

`@NonNull` 是JSR 305（缺陷检查框架）的注解，是告诉编译器这个域不可能为空，当代码检查有空值时会给出一个风险警告，目前这个注解只有IDEA支持。
可以参考的网址https://segmentfault.com/a/1190000018862320?utm_source=tag-newest
 
 
  






