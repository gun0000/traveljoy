package com.traveljoy.common.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfig {

    /*
    보안 필터 체인 정의: filterChain(HttpSecurity http) 메소드에서 HTTP 요청에 대한 보안 정책을 정의합니다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        //로그인하지 않아도 들어갈 수 있거나 보여야할 예외
                        .requestMatchers("/","admin/**","/room/**","/reserve/**","/member/**","/status","/css/**","/js/**", "/images/**", "/view/join", "/auth/join","/view/login").permitAll()
                        .anyRequest().authenticated()	// 어떠한 요청이라도 인증필요
                )
                //.formLogin(AbstractHttpConfigurer::disable)
                .formLogin(login -> login	// form 방식 로그인 사용
                        .loginPage("/member/login")	// [A] 커스텀 로그인 페이지 지정
                        .loginProcessingUrl("/member/loginProcess")	// [B] submit 받을 url
                        .usernameParameter("memberId")	// [C] submit할 아이디
                        .passwordParameter("memberPwd")	// [D] submit할 비밀번호
                        .defaultSuccessUrl("/room/main", true)	// 성공 시
                        .permitAll()	// 이동이 막히면 안되므로 얘는 허용
                )
                .logout(withDefaults());    // 로그아웃은 기본설정으로 (/logout으로 인증해제)

        return http.build();
    }

    /*
    메소드에서 CORS(Cross-Origin Resource Sharing) 설정
    CORS는 다른 도메인의 리소스에 접근할 수 있도록 허용하는 메커니즘
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); //인증 정보(Credentials)를 포함한 요청도 허용하도록 설정
        configuration.addAllowedOriginPattern("http://localhost:8080"); //도메인에서 오는 요청을 허용하도록 설정
        //configuration.addAllowedOriginPattern("실제서버주소");
        configuration.addAllowedHeader("*");// 모든 HTTP 헤더를 허용하도록 설정
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Refresh");
        configuration.addExposedHeader("Access-Control-Allow-Credentials"); //"Authorization", "Refresh", "Access-Control-Allow-Credentials"라는 이름의 HTTP 응답 헤더를 클라이언트(브라우저)에서 접근가능
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));//GET, POST, PATCH, DELETE 메소드로 온 요청만을 수락하게끔
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //모든 경로('**')에 대해 위에서 만든 CORS 구성(configuration)을 적용(register)

        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
