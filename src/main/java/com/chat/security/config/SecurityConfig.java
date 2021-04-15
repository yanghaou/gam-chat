package com.chat.security.config;

import com.chat.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author chenlong
 * @date 2020/12/7
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static String[] whiteUri = {
            "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/webjars/**",
            "/user/login","/user/register", "/user/forget/password","/user/send/verifyCode","/getUid"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(whiteUri);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                // 禁用session （因为用jwt来鉴权和认证，不需要session）
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login","/user/register").permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                // 其他所有请求都要鉴权
                .anyRequest()
                .authenticated();
        // 禁用缓存
        http.headers().cacheControl();
        // 添加jwt过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    /**
//     * 配置userDetailsService和passwordEncoder
//     * @param auth auth
//     * @throws Exception e
//     */
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService())
////                .passwordEncoder(passwordEncoder());
////    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     *  取出用户权限
     * @return userDetailService
     */
//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            User user = userService.getUserByUsername(username);
//            if (user != null) {
//                Set<Permission> permissions = userService.getPermissionList(user.getId());
//                return new UserDetailsImpl(user, new ArrayList<>(permissions));
//            }
//            throw new UsernameNotFoundException("用户名或密码错误");
//        };
//    }

    /**
     *  自定义拦截器
     * @return jwtAuthenticationTokenFilter
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
}