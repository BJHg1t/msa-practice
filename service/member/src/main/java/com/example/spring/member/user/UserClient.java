    package com.example.spring.member.user;

    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Component;
    import org.springframework.web.reactive.function.client.WebClient;
    import org.springframework.web.reactive.function.client.WebClientResponseException;
    import reactor.core.publisher.Mono;
    import reactor.util.retry.Retry;

    import java.time.Duration;

    @Component
    @RequiredArgsConstructor
    public class UserClient {

        private static final String USERS_ROOT_API = "/users";
        private final WebClient webClient;

        public Mono<User> createUser(User user) {
            return webClient.post()
                    .uri(USERS_ROOT_API)
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(User.class)
                    .onErrorResume(Exception.class, exception -> Mono.empty());
        }

        public Mono<User> getUserByIsbn(String isbn) {
            return webClient.get()
                    .uri(USERS_ROOT_API + "/" + isbn)
                    .retrieve()
                    .bodyToMono(User.class)
                    .timeout(Duration.ofSeconds(3))
                    .onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty())
                    .retryWhen(
                            Retry.backoff(3, Duration.ofMillis(100))
                    )
                    .onErrorResume(Exception.class, exception -> Mono.empty());
        }

    }
