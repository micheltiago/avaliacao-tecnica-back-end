package avaliacao.tecnica.back.end.conf;

import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EqualsAndHashCode
public class SwaggerConf {
    private static final String PACKAGE_NAME = "avaliacao.tecnica.back.end";

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(PACKAGE_NAME))
                .paths(PathSelectors.any()).build().apiInfo(this.getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                 .title("Avaliação Técnica - BACK-END")
                .description("API para demonstração técnica.")
                .version("0.0.1")
                .contact(this.getContact()).build();
    }

    private Contact getContact() {
        return new Contact("Tiago Michel", "https://micheltiago.wordpress.com/",
                "tiago.silva@compasso.com.br");
    }
}