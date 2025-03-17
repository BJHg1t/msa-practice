package com.example.spring.springsecurity.config;

import com.example.spring.springsecurity.config.filter.TokenAuthenticationFilter;
import com.example.spring.springsecurity.config.jwt.TokenProvider;
import com.example.spring.springsecurity.mapper.UserRepository;
import com.example.spring.springsecurity.model.Member;
import com.example.spring.springsecurity.model.User;
import com.example.spring.springsecurity.service.CustomOAuth2UserService;
import com.example.spring.springsecurity.type.Role;
import com.example.spring.springsecurity.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.time.Duration;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/static/**",
                        "/css/**",
                        "/js/**"
                ); // 정적 리소스 경로 무시
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(
                                        new AntPathRequestMatcher("/member/join", GET.name()),
                                        new AntPathRequestMatcher("/member/login", GET.name()),
                                        new AntPathRequestMatcher("/", GET.name()),
                                        new AntPathRequestMatcher("/write", GET.name()),
                                        new AntPathRequestMatcher("/detail", GET.name()),
                                        new AntPathRequestMatcher("/access-denied", GET.name()),
                                        new AntPathRequestMatcher("/update/*", GET.name()),
                                        new AntPathRequestMatcher("/join", POST.name()),
                                        new AntPathRequestMatcher("/login", POST.name()),
                                        new AntPathRequestMatcher("/logout", POST.name()),
                                        new AntPathRequestMatcher("/refresh-token", POST.name()),
                                        new AntPathRequestMatcher("/api/board/file/download/*", GET.name())
                                )
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling( exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .headers(
                        (headerConfig) -> headerConfig.frameOptions(
                                frameOptionsConfig -> frameOptionsConfig.disable()
                        )
                )
                .oauth2Login(
                        (oauth) ->
                                oauth.userInfoEndpoint(
                                        (endpoint) -> endpoint.userService(customOAuth2UserService)
                                )
                                        .successHandler(oAuth2SuccessHandler())
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/access-denied");
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/access-denied");
        };
    }

    @Bean
    public AuthenticationSuccessHandler oAuth2SuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            String picture = oAuth2User.getAttribute("picture");

            User user = userRepository.findByEmail(email)
                    .map(entity -> entity.update(name, picture))
                    .orElse(User.builder()
                            .name(name)
                            .email(email)
                            .picture(picture)
                            .role(Role.ROLE_USER)
                            .build());

            user = userRepository.save(user);

            Member member = Member.builder()
                    .id(user.getId())
                    .userId(user.getEmail())
                    .userName(user.getName())
                    .role(user.getRole())
                    .build();

            String accessToken = tokenProvider.generateToken(member, Duration.ofHours(2));
            String refreshToken = tokenProvider.generateToken(member, Duration.ofDays(2));

            CookieUtil.addCookie(response, "refreshToken", refreshToken, 7*24*60*60);

            response.sendRedirect("http://localhost:1001/member/login?accessToken=" + accessToken);
        };
    }
}
