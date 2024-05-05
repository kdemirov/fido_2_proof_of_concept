package com.iwmnetwork.aqtos.internship.identify.config.filters.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.commands.authentication_ceremony.VerifyTheValueOfStoredSignatureCountCommand;
import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.AuthenticationCeremonyInMemoryRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.FidoUserRepository;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.exceptions.VerificationFailedException;
import com.iwmnetwork.aqtos.internship.identify.service.DefaultIdentifyService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Verify value of stored signature filter.
 */
@AllArgsConstructor
public class VerifyValueOfStoredSignatureFilter extends UsernamePasswordAuthenticationFilter
        implements CeremonyFilterInterface {

    private final DefaultIdentifyService defaultIdentifyService;
    private final FidoUserRepository fidoUserRepository;
    private final AuthenticationCeremonyInMemoryRepository repository = new AuthenticationCeremonyInMemoryRepository();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CeremonyFilterInterface.super.doFilterInternal(
                (HttpServletRequest) request,
                (HttpServletResponse) response,
                chain,
                Constants.AUTHENTICATION_CEREMONY_URL,
                401);
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthenticationCeremonyId id = (AuthenticationCeremonyId) request.getAttribute(Constants.AUTHENTICATION_CEREMONY_ID_KEY);
        byte[] credentialId = repository.getCredentialId(id);
        FidoUser fidoUser = fidoUserRepository.findByCredentialId(credentialId)
                .orElseThrow(VerificationFailedException::new);
        VerifyTheValueOfStoredSignatureCountCommand cmd = new VerifyTheValueOfStoredSignatureCountCommand(
                id,
                fidoUser.getSignCount()
        );
        defaultIdentifyService.dispatch(cmd);
        filterChain.doFilter(request, response);
    }
}
