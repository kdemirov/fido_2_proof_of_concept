package com.iwmnetwork.aqtos.internship.identify.bootstrap;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

/**
 * Constants.
 */
public class Constants {
    public final static int COSEAlgorithmIdentifier = -7;
    public static final String RP_ID_ORIGIN = "http://localhost:3000";
    public static final String RP_ID = "localhost";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final Integer TIMEOUT = 60000;
    public static final String TYPE_OF_AUTHENTICATION = "Type";
    public static final String FIDO_AUTHENTICATION_TYPE = "Fido";
    public static final String REGISTRATION_CEREMONY_ID_KEY = "registrationCeremonyId";
    public static final String AUTHENTICATION_CEREMONY_ID_KEY = "authenticationCeremonyId";
    public static final String REGISTRATION_CEREMONY_URL = "/api/identity/registration_finish";
    public static final String AUTHENTICATION_CEREMONY_URL = "/api/identity/login_finish";
    public static final String JWT_ISSUER = "auth0";

    //order of filter starts from 16 because there are already defined filters from spring such as
    // the spring security filter chain for more information debug ApplicationFilterChain#doFilterInternal
    public static final int FIRST_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 16;
    public static final int SECOND_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 15;
    public static final int THIRD_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 14;
    public static final int FORTH_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 13;
    public static final int FIFTH_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 12;
    public static final int SIXTH_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 11;
    public static final int SEVENTH_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 10;
    public static final int EIGHTH_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 9;
    public static final int NINTH_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 8;
    public static final int TENTH_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 7;
    public static final int ELEVENTH_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 6;
    public static final int TWELFTH_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 5;
    public static final int THIRTEENTH_FILTER_REGISTRATION_CEREMONY = LOWEST_PRECEDENCE - 4;

}
