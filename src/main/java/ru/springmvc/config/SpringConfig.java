package ru.springmvc.config;

import java.util.Objects;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

// @Configuration - замена для applicationContext.xml
@Configuration
@ComponentScan("ru.springmvc")  // пакет с компонентами <context:component-scan base-package="ru.springmvc"/>
@EnableWebMvc // Включает необходимы аннотации для spring-mvc приложения <mvc:annotation-driven/>
//@PropertySource("classpath:database.properties") // путь до конфигов
@PropertySource("classpath:hibernate.properties") // путь до конфигов hibernate
 @EnableTransactionManagement // начинаем и завершаем транзакцию автоматом (не надо явно прописывать)
@EnableJpaRepositories("ru.springmvc.repositories") // поддержка JPA репозиториев
public class SpringConfig implements WebMvcConfigurer {
  private final ApplicationContext applicationContext;

  // Environment - бин из спринга.
  // С его помощью получаем свойства, которые подключили через @PropertySource
  private final Environment environment;

  @Autowired
  public SpringConfig(ApplicationContext applicationContext, Environment environment) {
    this.applicationContext = applicationContext;
    this.environment = environment;
  }

  // 2 бина и один переопределенный метод для шаблонизатора
  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setApplicationContext(applicationContext);
    templateResolver.setPrefix("/WEB-INF/views/");
    templateResolver.setSuffix(".html");
    return templateResolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    templateEngine.setEnableSpringELCompiler(true);
    return templateEngine;
  }

  // из интерфейса WebMvcConfigurer
  // для реализации шаблонизатора thymeleaf-spring5 вместо стандартного
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(templateEngine());
    registry.viewResolver(resolver);
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName(environment.getRequiredProperty("hibernate.driver_class"));
    dataSource.setUrl(environment.getRequiredProperty("hibernate.connection.url"));
    dataSource.setUsername(environment.getRequiredProperty("hibernate.connection.username"));
    dataSource.setPassword(environment.getRequiredProperty("hibernate.connection.password"));

    return dataSource;
  }

  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));

    return properties;
  }

  // создаем сессию для Hibernate
//  @Bean
//  public LocalSessionFactoryBean sessionFactory() {
//    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//    sessionFactory.setDataSource(dataSource());
//    // указываем сущности вместо Configuration configuration = new Configuration().addAnnotatedClass(Person.class)
//    sessionFactory.setPackagesToScan("ru.springmvc.models");
//    sessionFactory.setHibernateProperties(hibernateProperties());
//
//    return sessionFactory;
//  }

  // тоже, что LocalSessionFactoryBean sessionFactory
  // только sessionFactory реализация entityManagerFactory для Hibernate
  // а это JPA (Hibernate - одна из реализаций JPA)
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan("ru.springmvc.models");

    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(hibernateProperties());

    return em;
  }

  // автоматом делаем транзакции для Hibernate
//  @Bean
//  public PlatformTransactionManager hibernateTransactionManager() {
//    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//    transactionManager.setSessionFactory(sessionFactory().getObject());
//
//    return transactionManager;
//  }


  // тоже, что PlatformTransactionManager hibernateTransactionManager
  // только hibernateTransactionManager реализация transactionManager для Hibernate
  // а это JPA (Hibernate - одна из реализаций JPA)
  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

    return transactionManager;
  }

//  // Указывает, к какой базе данных подключаться
//  @Bean
//  public DataSource dataSource() {
//    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//    // Objects.requireNonNull выкидываем ошибку, если свойство environment.getProperty("driver") содержит null
//    dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("driver")));
//    dataSource.setUrl(environment.getProperty("url"));
//    dataSource.setUsername(environment.getProperty("username"));
//    dataSource.setPassword(environment.getProperty("password"));
//
//    return dataSource;
//  }

  // бин для JdbcTemplate спринга - тонкая обертка JDBC API
//  @Bean
//  public JdbcTemplate jdbcTemplate() {
//    return new JdbcTemplate(dataSource());
//  }

}


/*
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
		http://www.springframework.org/schema/beans
    	http://www.springframework.org/schema/beans/spring-beans.xsd
    	http://www.springframework.org/schema/context
    	http://www.springframework.org/schema/context/spring-context.xsd
    	http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--Путь до компонентов-->
    <context:component-scan base-package="ru.springmvc"/>

    <!--Включает необходимы аннотации для spring-mvc приложения-->
    <mvc:annotation-driven/>


    <bean
            class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/" />
        <property name="suffix" value=".html" />
    </bean>

    <!--3 бина для шаблонизатора-->
    <bean id="templateResolver"
          class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
        <property name="prefix" value="/WEB-INF/views/"/><!--путь до вьюх-->
        <property name="suffix" value=".html"/><!--расширение вьюх-->
    </bean>
    <bean id="templateEngine" class="org.thymeleaf.spring5.SpringTemplateEngine">
        <property name="templateResolver" ref="templateResolver"/>
        <property name="enableSpringELCompiler" value="true"/>
    </bean>
    <bean class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="templateEngine" ref="templateEngine"/>
        <property name="order" value="1"/>
        <property name="viewNames" value="*"/>
    </bean>

</beans>
 */
