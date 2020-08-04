## Spring Data MongoDB

## 版本控制

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-releasetrain</artifactId>
      <version>Lovelace-SR2</version>
      <scope>import</scope>
      <type>pom</type>
    </dependency>
  </dependencies>
</dependencyManagement>
```

- BUILD-SNAPSHOT: 当前快照版
- M1, M2, and so on: 里程碑版
- RC1, RC2, and so on: 候选发布版
- RELEASE: GA 发布版
- SR1, SR2, and so on: 服务发布版本

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
  </dependency>
<dependencies>
```



## 基本CRUD

`CrudRepository`


## 分页排序 
`PagingAndSortingRepository `

```java
PagingAndSortingRepository<User, Long> repository = // … get access to a bean
Page<User> users = repository.findAll(new PageRequest(1, 20));
```

## 按条件计数
```java
interface UserRepository extends CrudRepository<User, Long> {
  long countByLastname(String lastname);
}
```

## 按条件删除
```java
interface UserRepository extends CrudRepository<User, Long> {
  long deleteByLastname(String lastname);
  List<User> removeByLastname(String lastname);
}
```

## 使用Spring通过JavaConfig或XML配置，为`XXXRepository`接口创建代理实例。

```java
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
class Config {
    
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:jpa="http://www.springframework.org/schema/data/jpa"
   xsi:schemaLocation="http://www.springframework.org/schema/beans
     http://www.springframework.org/schema/beans/spring-beans.xsd
     http://www.springframework.org/schema/data/jpa
     http://www.springframework.org/schema/data/jpa/spring-jpa.xsd">

   <jpa:repositories base-package="com.acme.repositories"/>

</beans>
```

基于JavaConfig中，`@Enable${store}Repositories` 跟 `basePackage`二者保留其一

## XXXRepository的使用：
```java
class SomeClient {

  private final PersonRepository repository;

  SomeClient(PersonRepository repository) {
    this.repository = repository;
  }

  void doSomething() {
    List<Person> persons = repository.findByLastname("Matthews");
  }
}
```

### 定义Repository接口

一般XXXRepository继承`Repository`, `CrudRepository`, 或者 `PagingAndSortingRepository`，如果不想继承这些接口，可以通过声明`@RepositoryDefinition`来实现自定义，具体实现可从`CrudRepository`中选择方法加入即可：
```java
@NoRepositoryBean
interface MyBaseRepository<T, ID extends Serializable> extends Repository<T, ID> {
  Optional<T> findById(ID id);
  <S extends T> S save(S entity);
}

interface UserRepository extends MyBaseRepository<User, Long> {
  User findByEmailAddress(EmailAddress emailAddress);
}
```

中间存储库接口使用`@NoRepositoryBean`注释。确保将该注释添加到Spring Data不应在运行时创建实例的所有存储库接口。

### Repository方法空值处理：

从Spring Data 2.0开始，Repository返回单个聚合实例的CRUD方法使用Java 8的Optional来指示可能缺少值。Spring Data支持在查询方法上返回以下包装类型：

- `com.google.common.base.Optional`
- `scala.Option`
- `io.vavr.control.Option`
- `javaslang.control.Option`

查询方法可以选择根本不使用包装类型。然后通过返回null来指示缺少查询结果。对于返回结果类型为`collections`, `collection alternatives`, `wrappers`, `streams`时，结果返回这些空类型的集合

- `@NonNullApi`：包级别，声明参数和返回值的默认不接受空值
- `@NonNull`：不能为null的参数或返回值（对于`@NonNullApi`适用的参数和返回值不需要该注解）
- `@Nullable`：可以为null的参数或返回值

Spring注释是使用`JSR305`注释进行元注释的（一种休眠但广泛传播的JSR）。要为查询方法启用运行时检查可空性约束，需要在`package-info.java`中使用Spring的`@NonNullApi`来激活包级别的非可空性：
```java
@org.springframework.lang.NonNullApi
package com.acme;
```

如果想再次选择可以为空的结果，请在各个方法上有选择地使用`@Nullable`：
```java
package com.acme;                                                       

import org.springframework.lang.Nullable;
interface UserRepository extends Repository<User, Long> {
  // 执行的查询未产生结果时，抛出EmptyResultDataAccessException，当传递给方法的emailAddress为null时，抛出IllegalArgumentException
  User getByEmailAddress(EmailAddress emailAddress);                    
  // 当执行的查询未产生结果时返回null，同时接受null作为emailAddress的值
  @Nullable
  User findByEmailAddress(@Nullable EmailAddress emailAdress);          
  // 当执行的查询未产生结果时，返回Optional.empty（），当传递给方法的emailAddress为null时，抛出IllegalArgumentException。
  Optional<User> findOptionalByEmailAddress(EmailAddress emailAddress); 
}
```

### 使用包含多个SpringData模块的Repository

使用module-specific接口定义Repository
```java
interface MyRepository extends JpaRepository<User, Long> { 
    
}

@NoRepositoryBean
interface MyBaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
}

interface UserRepository extends MyBaseRepository<User, Long> {
}
```

使用generic接口定义Repository
```java
interface AmbiguousRepository extends Repository<User, Long> {
 
}

@NoRepositoryBean
interface MyBaseRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
  
}

interface AmbiguousUserRepository extends MyBaseRepository<User, Long> {
  
}
```

使用带注解的对象定义Repository
```java
@Entity
class Person {}

interface PersonRepository extends Repository<Person, Long> {
 
}

@Document
class User {}

interface UserRepository extends Repository<User, Long> {
 
}
```

使用带有混合注解的对象定义Repository（不推荐）
```java
@Entity
@Document
class Person {}

interface JpaPersonRepository extends Repository<Person, Long> {
}

interface MongoDBPersonRepository extends Repository<Person, Long> {
}
```

### 多模块SpringData配置的区分：
```java
@EnableJpaRepositories(basePackages = "com.acme.repositories.jpa")
@EnableMongoRepositories(basePackages = "com.acme.repositories.mongo")
interface Configuration { }
```

## 定义查询方法
- 直接从方法名称派生查询
- 使用手动定义的查询

###  查询策略指定：
- 基于XML配置：`query-lookup-strategy`
- Java配置：在`Enable${store}Repositories`注解中指定`queryLookupStrategy`属性值

### 查询策略：
- CREATE
- USE_DECLARED_QUERY
- CREATE_IF_NOT_FOUND(default)

### 查询创建：

该机制会从方法名中剥离 `find…By`, `read…By`, `query…By`, `count…By`, `get…By` 这些前缀，然后开始解析，第一个`By`用作分隔符以指示实际条件的开始，在最基本的层面上，可以在实体属性上定义条件，并将它们与And和Or连接起来

## 通过方法名称创建查询：
```java
interface PersonRepository extends Repository<User, Long> {

  List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);

  // Enables the distinct flag for the query
  List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
  List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);

  // Enabling ignoring case for an individual property
  List<Person> findByLastnameIgnoreCase(String lastname);
  // Enabling ignoring case for all suitable properties
  List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);

  // Enabling static ORDER BY for a query
  List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
  List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
}
```

### 属性表达式：

假定Person包含属性Address，Address包含属性ZipCode：
```java
List<Person> findByAddressZipCode(ZipCode zipCode);
```

如果Person包含AddressZip属性，可通过`_`来手动定义遍历点：
```java
List<Person> findByAddress_ZipCode(ZipCode zipCode);
```

### 特殊参数处理：

在查询方法中使用Pageable，Slice和Sort：
```java
Page<User> findByLastname(String lastname, Pageable pageable);

Slice<User> findByLastname(String lastname, Pageable pageable);

List<User> findByLastname(String lastname, Sort sort);

List<User> findByLastname(String lastname, Pageable pageable);
```

### 限制查询结果：

使用`Top`和`First`限制查询的结果大小
```java
User findFirstByOrderByLastnameAsc();
User findTopByOrderByAgeDesc();

Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);

Slice<User> findTop3ByLastname(String lastname, Pageable pageable);

List<User> findFirst10ByLastname(String lastname, Sort sort);
List<User> findTop10ByLastname(String lastname, Pageable pageable);
```

### 用Java8`Stream<T>`流传输查询结果:
```java
@Query("select u from User u")
Stream<User> findAllByCustomQueryAndStream();

Stream<User> readAllByFirstnameNotNull();

@Query("select u from User u")
Stream<User> streamAllPaged(Pageable pageable);
```

Stream可能会包装基础数据来存储特定的资源，因此必须在使用后关闭。可使用`close()`方法或使用Java7`try-with-resources`块手动关闭Stream：
```java
try (Stream<User> stream = repository.findAllByCustomQueryAndStream()) {
  stream.forEach();
}
```

### 异步查询结果：
异步方法在调用时立即返回，而实际的查询执行发生在已提交给`Spring TaskExecutor`的任务中，异步查询执行与反应式查询执行不同，不应混合使用

```java
@Async
Future<User> findByFirstname(String firstname);               

@Async
CompletableFuture<User> findOneByFirstname(String firstname); 

@Async
ListenableFuture<User> findOneByLastname(String lastname);  
```

创建Repository实例：

XML启用SpringDataRepository
```java
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns:beans="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns="http://www.springframework.org/schema/data/jpa"
  xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/data/jpa
    http://www.springframework.org/schema/data/jpa/spring-jpa.xsd">

  <repositories base-package="com.acme.repositories" />

</beans:beans>
```

使用exclude-filter元素：
```java
<repositories base-package="com.acme.repositories">
  <context:exclude-filter type="regex" expression=".*SomeRepository" />
</repositories>
```

Java配置
```java
@Configuration
@EnableJpaRepositories("com.acme.repositories")
class ApplicationConfiguration {

  @Bean
  EntityManagerFactory entityManagerFactory() {
    // …
  }
}
```

SpringDataRepository自定义实现：

```java
interface CustomizedUserRepository {
  void someCustomMethod(User user);
}

class CustomizedUserRepositoryImpl implements CustomizedUserRepository {
  public void someCustomMethod(User user) {
    // Your custom implementation
  }
}
```
与接口对应的类名最重要的部分是Impl后缀，实现本身不依赖于Spring Data，可以是常规的Spring bean

可以让`repository`接口扩展片段接口，如以下示例所示：

```java
interface UserRepository extends CrudRepository<User, Long>, CustomizedUserRepository {

  // Declare query methods here
}
```

```java
interface HumanRepository {
  void someHumanMethod(User user);
}

class HumanRepositoryImpl implements HumanRepository {

  public void someHumanMethod(User user) {
    // Your custom implementation
  }
}

interface ContactRepository {

  void someContactMethod(User user);
  User anotherContactMethod(User user);
}

class ContactRepositoryImpl implements ContactRepository {

  public void someContactMethod(User user) {
    // Your custom implementation
  }

  public User anotherContactMethod(User user) {
    // Your custom implementation
  }
}
```

扩展CrudRepository的自定义Repository的接口
```java
interface UserRepository extends CrudRepository<User, Long>, HumanRepository, ContactRepository {

  // Declare query methods here
}
```

自定义存储库接口
```java
interface CustomizedSave<T> {
  <S extends T> S save(S entity);
}

class CustomizedSaveImpl<T> implements CustomizedSave<T> {

  public <S extends T> S save(S entity) {
    // Your custom implementation
  }
}

interface UserRepository extends CrudRepository<User, Long>, CustomizedSave<User> {
}

interface PersonRepository extends CrudRepository<Person, Long>, CustomizedSave<Person> {
}
```

使用默认后缀的Repository和为后缀设置自定义值的Repository：
```xml
<!-- 尝试查找名为com.acme.repository.CustomizedUserRepositoryImpl的类。 -->
<repositories base-package="com.acme.repository" />
<!--尝试查找com.acme.repository.CustomizedUserRepositoryMyPostfix-->
<repositories base-package="com.acme.repository" repository-impl-postfix="MyPostfix" />
```

解决歧义

```java
package com.acme.impl.one;

class CustomizedUserRepositoryImpl implements CustomizedUserRepository {

  // Your custom implementation
}
```

```java
package com.acme.impl.two;

@Component("specialCustomImpl")
class CustomizedUserRepositoryImpl implements CustomizedUserRepository {

  // Your custom implementation
}
```

手动连接自定义实现

```xml
<repositories base-package="com.acme.repository" />

<beans:bean id="userRepositoryImpl" class="…">
  <!-- further configuration -->
</beans:bean>
```

自定义Base Repository：
```java
class MyRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> {

  private final EntityManager entityManager;

  MyRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);

    // Keep the EntityManager around to used from the newly introduced methods.
    this.entityManager = entityManager;
  }

  @Transactional
  public <S extends T> S save(S entity) {
    // implementation goes here
  }
}
```

从Aggregate Roots发布事件：

每次调用Spring Data存储库的save（...）方法之一时，都会调用这些方法。
```java
class AnAggregateRoot {
    // 返回单个事件实例或事件集合，它不能采取任何论点
    @DomainEvents 
    Collection<Object> domainEvents() {
        // … return events you want to get published here
    }
    // 可用于潜在地清除要发布的事件列表（以及其他用途）
    @AfterDomainEventPublication 
    void callbackMethod() {
       // … potentially clean up domain events list
    }
}
```
Spring Data 扩展

Querydsl扩展:

Querydsl：一个框架，可以通过其流畅的API构建静态类型的SQL类查询

```java
public interface QuerydslPredicateExecutor<T> {

  Optional<T> findById(Predicate predicate);  

  Iterable<T> findAll(Predicate predicate);   

  long count(Predicate predicate);            

  boolean exists(Predicate predicate);        

  // … more functionality omitted.
}
```

要使用Querydsl支持，需在存储库接口上扩展QuerydslPredicateExecutor接口：
```java
interface UserRepository extends CrudRepository<User, Long>, QuerydslPredicateExecutor<User> {
}
```
```java
Predicate predicate = user.firstname.equalsIgnoreCase("dave")
	.and(user.lastname.startsWithIgnoreCase("mathews"));

userRepository.findAll(predicate);
```

Web 支持：

Java代码配置：

```java
@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
class WebConfiguration {}
```

XML配置：

将`SpringDataWebConfiguration`或`HateoasAwareSpringDataWebConfiguration`注册为Spring Bean

```xml
<bean class="org.springframework.data.web.config.SpringDataWebConfiguration" />

<!-- If you use Spring HATEOAS, register this one *instead* of the former -->
<bean class="org.springframework.data.web.config.HateoasAwareSpringDataWebConfiguration" />
```

基本Web支持：

- `DomainClassConverter`让Spring MVC从请求参数或路径变量中解析repository-managed的类的实例

```java
@Controller
@RequestMapping("/users")
class UserController {

  @RequestMapping("/{id}")
  String showUserForm(@PathVariable("id") User user, Model model) {

    model.addAttribute("user", user);
    return "userForm";
  }
}
```
该方法直接接收User实例，无需进一步查找。通过让Spring MVC首先将路径变量转换为域类的id类型来解析实例，并最终通过在为域类型注册的存储库实例上调用`findById（...）`来访问实例。

Repository必须实现CrudRepository才有资格被发现进行转换

- `HandlerMethodArgumentResolver`实现让Spring MVC从请求参数中解析Pageable和Sort实例

```java
@Controller
@RequestMapping("/users")
class UserController {

  private final UserRepository repository;

  UserController(UserRepository repository) {
    this.repository = repository;
  }

  @RequestMapping
  String showUsers(Model model, Pageable pageable) {

    model.addAttribute("users", repository.findAll(pageable));
    return "users";
  }
}
```
Spring MVC尝试使用以下默认配置从请求参数派生Pageable实例：
- page：检索的页面，0索引，默认为0
- size：要检索的页面大小，默认为20
- sort：在格式property，property（，ASC|DESC）中排序的属性，默认排序方向是升序，如果切换排序方式，可使用多个排序参数：`?sort=firstname&sort=lastname,asc`

要自定义，可分别注册实现`PageableHandlerMethodArgumentResolverCustomizer`接口或`SortHandlerMethodArgumentResolverCustomizer`接口,实现customize()方法：

```java
@Bean 
SortHandlerMethodArgumentResolverCustomizer sortCustomizer() {
    return s -> s.setPropertyDelimiter("<-->");
}
```