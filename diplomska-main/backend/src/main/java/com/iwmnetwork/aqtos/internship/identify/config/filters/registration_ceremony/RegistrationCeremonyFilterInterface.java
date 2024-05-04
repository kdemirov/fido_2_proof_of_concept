package com.iwmnetwork.aqtos.internship.identify.config.filters.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.bootstrap.Constants;
import com.iwmnetwork.aqtos.internship.identify.model.exceptions.Fido2Exception;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Registration ceremony main logic interface.
 */
public interface RegistrationCeremonyFilterInterface {

    /**
     * Logic for doFilter internal for all filters that are needed for verification
     * the registration ceremony.
     *
     * @param request     given request
     * @param response    given response
     * @param filterChain filter chain
     * @throws ServletException servlet exception
     * @throws IOException      io exception
     */
    default void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().equals(Constants.REGISTRATION_CEREMONY_URL)) {
            try {
                filterLogic(request, response, filterChain);
            } catch (Exception o_O) {
                throw new Fido2Exception(o_O);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Definition of filter logic method where every one of the filters sends the appropriate
     * command for verifying the registration ceremony.
     *
     * @param request     given request
     * @param response    given response
     * @param filterChain filter chain
     * @throws ServletException servlet exception
     * @throws IOException      Io exception
     */
    void filterLogic(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException;
}
