package cn.edu.neusoft.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 赵林阳
 * @Date 2023/7/8 14:51
 * @Version 1.0
 */
@Configuration
public class SpringMvcConfigure implements WebMvcConfigurer {

    @Value("${upload.file.location}")
    private String fileName;
//    添加静态资源访问
//    @param registry 静态资源注册中心

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        解决附件上传需要重新部署才能访问到文件的问题
        if (!registry.hasMappingForPattern("/upload/**")) {
            registry.addResourceHandler("/upload/**").addResourceLocations("file:".concat(fileName));
        }
    }
}