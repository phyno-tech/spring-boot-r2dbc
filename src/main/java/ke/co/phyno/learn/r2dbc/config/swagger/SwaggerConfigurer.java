package ke.co.phyno.learn.r2dbc.config.swagger;

import ke.co.phyno.learn.r2dbc.data.http.response.HandlerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ExampleBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfigurer {
    @Value("${app.version:UNKNOWN}")
    private String version;

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(false)
                .displayOperationId(false)
                .defaultModelExpandDepth(0)
                .defaultModelsExpandDepth(0)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(true)
                .docExpansion(DocExpansion.NONE)
                .filter(true)
                .tagsSorter(TagsSorter.ALPHA)
                .maxDisplayedTags(null)
                .operationsSorter(null)
                .showExtensions(false)
                .showCommonExtensions(false)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    @Bean
    public Docket docket() {
        List<Response> responses = new ArrayList<>(Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.OK.value()))
                        .description("Success")
                        .examples(Collections.singletonList(new ExampleBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .summary("Success response")
                                .value(HandlerResponse.builder()
                                        .code(HttpStatus.OK.value())
                                        .message("Success")
                                        .data(new Object())
                                        .build())
                                .build()))
                        .isDefault(true)
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .examples(Collections.singletonList(new ExampleBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .summary("Bad request response")
                                .value(HandlerResponse.builder()
                                        .code(HttpStatus.BAD_REQUEST.value())
                                        .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                        .build())
                                .build()))
                        .isDefault(true)
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
                        .examples(Collections.singletonList(new ExampleBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .summary("Unsupported media type response")
                                .value(HandlerResponse.builder()
                                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                                        .message(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
                                        .build())
                                .build()))
                        .isDefault(true)
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .description(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .examples(Collections.singletonList(new ExampleBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .summary("Not found response")
                                .value(HandlerResponse.builder()
                                        .code(HttpStatus.NOT_FOUND.value())
                                        .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                                        .build())
                                .build()))
                        .isDefault(true)
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                        .description(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .examples(Collections.singletonList(new ExampleBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .summary("Unauthorized response")
                                .value(HandlerResponse.builder()
                                        .code(HttpStatus.UNAUTHORIZED.value())
                                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                        .build())
                                .build()))
                        .isDefault(true)
                        .build()
        ));
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(new ApiInfoBuilder()
                        .title("Learn R2DBC")
                        .description("Crud app for learning R2DBC")
                        .contact(new Contact("Phelix Ochieng", "https://github.com/ochibooh", "phelix@phyno.co.ke"))
                        .version(version)
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ke.co.phyno.learn.r2dbc.handler"))
                .build()
                .tags(
                        new Tag("Customer", "Customer CRUD", 1)
                )
                .forCodeGeneration(true)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, responses)
                .globalResponses(HttpMethod.POST, responses)
                .securitySchemes(Collections.singletonList(new ApiKey("jwt", "Authorization", "header")));
    }
}
