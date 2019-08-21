package life.majiang.community.interceptor;/**
 *
 *将自定义 SessionInterception 加入拦截中
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterception sessionInterception;


    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //放行不需登录即可访问的接口url   其他都要拦截  excludePathPatterns(“/”)不需要拦截设置
        registry.addInterceptor(sessionInterception).addPathPatterns("/**");
    }



}
