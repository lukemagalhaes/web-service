package ps2.mack.springbootapi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
    @Value("${bezkoder.openapi.dev-url}")
    private String devUrl;

    @Value("${bezkoder.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");
        Info info = new Info()
                .title("Documentação da API do Projeto")
                .version("1.0")
                .description(
                        "Documentação da API do projeto de web service desenvolvido pelos alunos Guilherme Bondezan Yonamine, Luis Augusto Marques e Luke Magalhães da turma 03H do curso de Sistemas de Informação.");
        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
