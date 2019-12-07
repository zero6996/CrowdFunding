package com.zero.config;

import com.zero.exception.CrowdFundingAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: zero
 * @Description: 安全配置类
 * Date: Create in 2019/12/1 21:51
 * Modified By:
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用全局方法权限管理
public class CrowdFundingSecurityConfig extends WebSecurityConfigurerAdapter {

    // todo: 完成个人信息页面

    @Autowired
    private UserDetailsService userDetailsService;

    // Spring在调用该方法前会检查IOC容器中是否已经有了对应的bean，如果有则不调用该方法，而是直接把IOC容器中的bean返回。
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
                .antMatchers("/index.html","/404Page.jsp","/project.html","/bootstrap/**","/css/**","/fonts/**",
                        "/img/**","/jquery/**","/layer/**","/script/**","/ztree/**")
                .permitAll()
                .antMatchers("/admin/query/for/search.html")
                .hasRole("董事长")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CrowdFundingAccessDeniedHandler())
                .and()
                .formLogin()
                .loginPage("/admin/to/login/page.html")
                .permitAll()
                .loginProcessingUrl("/admin/security/do/login.html")
                .permitAll()
                .usernameParameter("loginAcct")
                .passwordParameter("userPwd")
                .defaultSuccessUrl("/admin/to/main/page.html")
                .and()
                .logout()
                .logoutUrl("/admin/security/do/logout.html")
                .logoutSuccessUrl("/index.html")
                .and()
                .csrf().disable();
    }
}
