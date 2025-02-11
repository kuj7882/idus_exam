package com.example.idus.config;

import com.example.idus.config.filter.JwtFilter;
import com.example.idus.config.filter.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration configuration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Component vs @Bean
    // @Component : 프로젝트가 실행될 때 컴포넌트 스캔을 통해서 객체를 생성해서 스프링 컨테이너에 Bean으로 등록
    //              개발자가 직접 개발한 클래스의 객체를 등록할 때 사용
    // @Bean : 컴포넌트 스캔 X, 개발자가 직접 객체를 생성해서 스프링 컨테이너에 Bean으로 등록
    //              라이브러리를 가져와서 라이브러리의 객체를 등록할 때 사용
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // <script>alert("asd")</script>, 일반적으로 XSS
        // <script>게시글 작성 스크립트</script>, 일반적으로 CSRF
        // CSRF : 크로스 사이트 요청 변조
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.formLogin(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                (auth) -> auth

                        .requestMatchers("/login", "/user/signup", "/user/verify").permitAll()

                        .anyRequest().authenticated()
        );

        http.addFilterAt(new LoginFilter(configuration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
