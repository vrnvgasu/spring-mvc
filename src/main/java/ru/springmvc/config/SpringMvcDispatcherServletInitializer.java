package ru.springmvc.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// AbstractAnnotationConfigDispatcherServletInitializer конфигурирет сервер вместо web.xml
// реализует WebApplicationInitializer
public class SpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  // пока не нужен. Вернем null
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }

  // Путь до файла со spring конфигурацией
  // <param-value>/WEB-INF/applicationContext.xml</param-value>
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[]{SpringConfig.class};
  }

  // Любой url (/) перенаправляем на dispatcher servlet
  @Override
  protected String[] getServletMappings() {
    return new String[]{"/"};
  }
}
