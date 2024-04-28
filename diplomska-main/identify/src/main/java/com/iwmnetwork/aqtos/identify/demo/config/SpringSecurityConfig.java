package com.iwmnetwork.aqtos.identify.demo.config;

import com.iwmnetwork.aqtos.identify.demo.config.filters.JwtAuthenticationFidoFilter;
import com.iwmnetwork.aqtos.identify.demo.config.filters.JwtAuthenticationFilter;
import com.iwmnetwork.aqtos.identify.demo.config.filters.JwtAuthorizationFilter;
import com.iwmnetwork.aqtos.identify.demo.service.UserService;
import com.iwmnetwork.aqtos.identify.demo.service.impl.RelyingPartyService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RelyingPartyService relyingPartyService;
    private final CustomFidoAuthenticatorProvider fidoAuthenticatorProvider;
    private final CustomUsernameAndPasswordAuthenticatorProvider usernameAndPasswordAuthenticatorProvider;
    private final Environment environment;

    public SpringSecurityConfig(PasswordEncoder passwordEncoder,
                                UserService userService,
                                RelyingPartyService relyingPartyService,
                                CustomFidoAuthenticatorProvider fidoAuthenticatorProvider,
                                CustomUsernameAndPasswordAuthenticatorProvider usernameAndPasswordAuthenticatorProvider,
                                Environment environment) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.relyingPartyService = relyingPartyService;
        this.fidoAuthenticatorProvider = fidoAuthenticatorProvider;
        this.usernameAndPasswordAuthenticatorProvider = usernameAndPasswordAuthenticatorProvider;
        this.environment = environment;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JwtAuthenticationFidoFilter(authenticationManager(),
                        relyingPartyService,
                        userService,
                        environment))
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),
                        userService,
                        passwordEncoder,
                        environment))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.fidoAuthenticatorProvider)
                .authenticationProvider(this.usernameAndPasswordAuthenticatorProvider);
    }
}
