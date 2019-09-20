package frkr.github.emprestimos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

//https://www.vojtechruzicka.com/documenting-spring-boot-rest-api-swagger-springfox/
//https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
@Configuration
@EnableSwagger2
public class RESTConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
                .paths(PathSelectors.ant("/rs/**"))
                .build()
                .apiInfo(getApiInfo())
                ;
    }

    private ApiInfo getApiInfo() { // TODO Colocar informações do projeto
        return new ApiInfo(
                "ofertacc",
                "ofertacc",
                "beta",
                "https://",
                new Contact("Davi S. Mesquita", "http://www.fornax.com.br", "davimesquita@gmail.com"),
                "beta",
                "https://",
                Collections.emptyList()
        );
    }
}
