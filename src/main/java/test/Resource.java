package test;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

public class Resource {

    private static WebClient webClient =  WebClient.builder()
            .clientConnector(
                    new ReactorClientHttpConnector(HttpClient.create()
                            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                            .responseTimeout(Duration.ofSeconds(10))
                            .doOnConnected(connection -> connection
                                    .addHandler(new ReadTimeoutHandler(10))
                                    .addHandler(new WriteTimeoutHandler(10))
                            )))
            .build();



    public static WebClient getWebClient(){
        return webClient;
    }

    public static Mono<ResponseEntity<String>> callWebClient(){
        return Resource.getWebClient().get().uri("http://localhost:9096/protected").retrieve().toEntity(String.class);
    }

}
