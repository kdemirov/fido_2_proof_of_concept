package com.iwmnetwork.aqtos.internship.identify.config;

import com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony.VerifyTheValueOfStoredSignatureCountCommand;
import com.iwmnetwork.aqtos.internship.identify.config.filters.authentication_ceremony.*;
import com.iwmnetwork.aqtos.internship.identify.config.filters.JwtAuthenticationFilter;
import com.iwmnetwork.aqtos.internship.identify.config.filters.JwtAuthorizationFilter;
import com.iwmnetwork.aqtos.internship.identify.repository.FidoUserRepository;
import com.iwmnetwork.aqtos.internship.identify.service.DefaultIdentifyService;
import com.iwmnetwork.aqtos.internship.identify.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final DefaultIdentifyService defaultIdentifyService;
    private final CustomFidoAuthenticatorProvider fidoAuthenticatorProvider;
    private final CustomUsernameAndPasswordAuthenticatorProvider usernameAndPasswordAuthenticatorProvider;
    private final Environment environment;
    private final FidoUserRepository fidoUserRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new StartAuthenticationCeremonyFilter(
                        defaultIdentifyService,
                        userService))
                .addFilterAfter(new VerifyThatCredentialExistsFilter(authenticationManager(),
                        defaultIdentifyService, fidoUserRepository), StartAuthenticationCeremonyFilter.class)
                .addFilterAfter(new DeserializeClientDataFilter(defaultIdentifyService), StartAuthenticationCeremonyFilter.class)
                .addFilterAfter(new VerifyClientDataTypeFilter(defaultIdentifyService), DeserializeClientDataFilter.class)
                .addFilterAfter(new VerifyChallengeFilter(defaultIdentifyService), VerifyClientDataTypeFilter.class)
                .addFilterAfter(new VerifyRpIdFilter(defaultIdentifyService), VerifyChallengeFilter.class)
                .addFilterAfter(new VerifyRpIdHashInAuthDataFilter(defaultIdentifyService), VerifyRpIdFilter.class)
                .addFilterAfter(new VerifyThatUserIsPresentFilter(defaultIdentifyService), VerifyRpIdHashInAuthDataFilter.class)
                .addFilterAfter(new VerifyThatUserVerifiedFilter(defaultIdentifyService), VerifyThatUserIsPresentFilter.class)
                .addFilterAfter(new ComputeHashOverClientDataFilter(defaultIdentifyService), VerifyThatUserVerifiedFilter.class)
                .addFilterAfter(new VerifyAssertionResponseSignatureFilter(defaultIdentifyService), ComputeHashOverClientDataFilter.class)
                .addFilterAfter(new VerifyValueOfStoredSignatureFilter(defaultIdentifyService, fidoUserRepository), VerifyAssertionResponseSignatureFilter.class)
                .addFilterAfter(new AuthenticationCeremonySuccessfulFilter(authenticationManager(), userService), VerifyValueOfStoredSignatureFilter.class)
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),
                        userService,
                        passwordEncoder,
                        environment))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.usernameAndPasswordAuthenticatorProvider)
                .authenticationProvider(this.fidoAuthenticatorProvider);
    }
}
