package ru.springmvc.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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

  // 2 метода для фильтра запросов
  // позволяют обрабатывать post + скрытое поле с методом, как put/patch/delete
  @Override
  public void onStartup(ServletContext aServletContext) throws ServletException {
    super.onStartup(aServletContext);
    registerHiddenFieldFilter(aServletContext);
  }
  private void registerHiddenFieldFilter(ServletContext aContext) {
    aContext.addFilter("hiddenHttpMethodFilter",
            new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
  }
}
