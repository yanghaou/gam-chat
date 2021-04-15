package com.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        //在配置好的配置类中增加此段代码即可
        List<Parameter> pars = new ArrayList<Parameter>();
        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name("Authorization").description("token 登录和注册不用传，登录后所有接口都必须传")//name表示名称，description表示描述
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).defaultValue("").build();//required表示是否必填，defaultvalue表示默认值

        ParameterBuilder uid = new ParameterBuilder();
        uid.name("Uid").description("用户名 登录和注册不用传，登录后所有接口都必须传")
                .modelRef(new ModelRef("Long")).parameterType("header")
                .required(false).defaultValue("").build();

        ParameterBuilder appType = new ParameterBuilder();
        appType.name("AppType").description("App类型 所有接口都必须传")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).defaultValue("A").build();

        ParameterBuilder clientType = new ParameterBuilder();
        clientType.name("ClientType").description("客户端类型 所有接口都必须传")
                .modelRef(new ModelRef("int")).parameterType("header")
                .required(true).defaultValue("1").build();

        ParameterBuilder version = new ParameterBuilder();
        version.name("AppVersion").description("app版本号 所有接口都必须传")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).defaultValue("1.0.0").build();

        ParameterBuilder device = new ParameterBuilder();
        device.name("Device").description("设备类型 所有接口都必须传")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).defaultValue("android").build();

        ParameterBuilder deviceVersion = new ParameterBuilder();
        deviceVersion.name("DeviceVersion").description("设备版本 所有接口都必须传")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).defaultValue("7.0.0").build();

        ParameterBuilder lan = new ParameterBuilder();
        lan.name("Language").description("语言类型 en ch 所有接口都必须传")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).defaultValue("EN").build();

        pars.add(ticketPar.build());//添加完此处一定要把下边的带***的也加上否则不生效
        pars.add(uid.build());
        pars.add(appType.build());
        pars.add(clientType.build());
        pars.add(version.build());
        pars.add(device.build());
        pars.add(deviceVersion.build());
        pars.add(lan.build());



        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))                         //这里采用包含注解的方式来确定要显示的接口
                .apis(RequestHandlerSelectors.basePackage("com.chat.controller"))    //这里采用包扫描的方式来确定要显示的接口
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);//************把消息头添加
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl",// url
                "none",       // docExpansion          => none | list
                "alpha",      // apiSorter             => alpha
                "schema",     // defaultModelRendering => schema
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                false,        // enableJsonEditor      => true | false
                true);        // showRequestHeaders    => true | false
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("flux后台服务")
                .description("flux-chat api")
                .termsOfServiceUrl("")
                //.contact(contact)
                .version("1.0")
                .build();
    }

}
