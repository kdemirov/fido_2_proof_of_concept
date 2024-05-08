package com.fido2_proof_of_concepts.identify.config.filters.authentication_ceremony;

import com.fido2_proof_of_concepts.common.service.DefaultService;
import com.fido2_proof_of_concepts.identify.api.commands.authentication_ceremony.VerifyThatCredentialIdExistsCommand;
import com.fido2_proof_of_concepts.identify.bootstrap.Constants;
import com.fido2_proof_of_concepts.identify.config.filters.interfaces.CeremonyFilterInterface;
import com.fido2_proof_of_concepts.identify.model.FidoUser;
import com.fido2_proof_of_concepts.identify.model.identifiers.AuthenticationCeremonyId;
import com.fido2_proof_of_concepts.identify.repository.AuthenticationCeremonyInMemoryRepository;
import com.fido2_proof_of_concepts.identify.repository.FidoUserRepository;
import com.fido2_proof_of_concepts.identify.repository.webauthn.exceptions.VerificationFailedException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Verify that credential exists filter.
 */
@AllArgsConstructor
public class VerifyThatCredentialExistsFilter extends UsernamePasswordAuthenticationFilter
        implements CeremonyFilterInterface {

    private final DefaultService defaultIdentifyService;
    private final FidoUserRepository fidoUserRepository;
    private final AuthenticationCeremonyInMemoryRepository repository = new AuthenticationCeremonyInMemoryRepository();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CeremonyFilterInterface.super.doFilterInternal(
                (HttpServletRequest) request,
                (HttpServletResponse) response,
                chain,
                Constants.AUTHENTICATION_CEREMONY_URL, HttpStatus.UNAUTHORIZED.value());
    }

    @Override
    public void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        AuthenticationCeremonyId authenticationCeremonyId = (AuthenticationCeremonyId) request.getAttribute(Constants.AUTHENTICATION_CEREMONY_ID_KEY);
        byte[] credentialId = repository.getCredentialId(authenticationCeremonyId);
        boolean verified = fidoUserRepository.existsByCredentialId(credentialId);
        if (verified) {
            FidoUser fidoUser = fidoUserRepository.findByCredentialId(credentialId).orElse(null);
            VerifyThatCredentialIdExistsCommand cmd = new VerifyThatCredentialIdExistsCommand(
                    authenticationCeremonyId,
                    verified,
                    fidoUser
            );
            defaultIdentifyService.dispatch(cmd);
            filterChain.doFilter(request, response);
        } else {
            throw new VerificationFailedException();
        }
    }
}
