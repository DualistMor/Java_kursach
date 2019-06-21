package com.artcoversapp.keys;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AuthKeys {

    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String GRANT_TYPE = "grant_type";
    public static final String SCOPE = "scope";


    public static final String DEFAULT_CLIENT_ID = "html5";
    public static final String DEFAULT_CLIENT_SECRET = "password";
    public static final String DEFAULT_USERNAME = "";
    public static final String DEFAULT_PASSWORD = "";
    public static final String DEFAULT_GRANT_TYPE = "password",
            AUTHORIZATION_CODE_GRANT_TYPE = "authorization_code",
            REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    public static final String DEFAULT_SCOPE = "openid";

    public static final Map<String, String> PARAMS =
            Collections.unmodifiableMap(new HashMap<String, String>(){{
                put(AuthKeys.CLIENT_ID, AuthKeys.DEFAULT_CLIENT_ID);
                put(AuthKeys.CLIENT_SECRET, AuthKeys.DEFAULT_CLIENT_SECRET);
                put(AuthKeys.USERNAME, AuthKeys.DEFAULT_USERNAME);
                put(AuthKeys.PASSWORD, AuthKeys.DEFAULT_PASSWORD);
                put(AuthKeys.GRANT_TYPE, AuthKeys.DEFAULT_GRANT_TYPE);
                put(AuthKeys.SCOPE, AuthKeys.DEFAULT_SCOPE);
            }});

    public static final String ROLE_ADMIN = "ROLE_ADMIN", ROLE_USER = "ROLE_USER";

    public static final Collection<GrantedAuthority> AUTHORITIES =
            Collections.unmodifiableList(AuthorityUtils.createAuthorityList(ROLE_ADMIN, ROLE_USER));

    private AuthKeys() {}
}

