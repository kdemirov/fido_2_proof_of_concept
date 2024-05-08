package com.fido2_proof_of_concepts.identify.config;

import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.config.filters.JwtAuthenticationFilter;
import com.fido2_proof_of_concepts.identify.config.filters.JwtAuthorizationFilter;
import com.fido2_proof_of_concepts.identify.config.filters.authentication_ceremony.*;
import com.fido2_proof_of_concepts.identify.repository.FidoUserRepository;
import com.fido2_proof_of_concepts.identify.service.UserService;
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

/**
 * Spring security configuration.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final DefaultService defaultIdentifyService;
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
                .addFilterAfter(new VerifyThatCredentialExistsFilter(
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
                .addFilterAfter(new VerifyValueOfStoredSignatureFilter(defaultIdentifyService), VerifyAssertionResponseSignatureFilter.class)
                .addFilterAfter(new AuthenticationCeremonySuccessfulFilter(authenticationManager()), VerifyValueOfStoredSignatureFilter.class)
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
