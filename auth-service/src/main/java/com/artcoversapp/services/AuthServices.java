package com.artcoversapp.services;

import com.artcoversapp.entities.Account;
import com.artcoversapp.entities.AccountRole;
import com.artcoversapp.keys.AuthKeys;
import com.artcoversapp.repository.AccountRepository;
import com.artcoversapp.repository.AccountRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServices {

    private final TokenEndpoint tokenEndpoint;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;

    @Transactional
    public OAuth2AccessToken registration(Account account) {

        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new RuntimeException("User with this email already exist!");
        }

        AccountRole accountRole = accountRoleRepository.findByRole(AuthKeys.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role is not found"));

        Account newAccount = new Account();
        newAccount.setUsername(account.getUsername());
        newAccount.setPassword(account.getPassword());
        newAccount.setActive(true);
        newAccount.setRoles(new ArrayList<AccountRole>() {{
            add(accountRole);
        }});
        newAccount = accountRepository.save(newAccount);

        return getToken(newAccount);
    }


    public OAuth2AccessToken login(Account account) {
        return getToken(account);
    }

    private OAuth2AccessToken getToken(Account account) {
        User principal = new User(AuthKeys.PARAMS.get(AuthKeys.CLIENT_ID),
                AuthKeys.PARAMS.get(AuthKeys.CLIENT_SECRET), AuthKeys.AUTHORITIES);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, null, AuthKeys.AUTHORITIES);

        Map<String, String> parameters = new HashMap<>(AuthKeys.PARAMS);
        parameters.replace(AuthKeys.USERNAME, account.getUsername());
        parameters.replace(AuthKeys.PASSWORD, account.getPassword());

        try {
            return tokenEndpoint.postAccessToken(authenticationToken, parameters).getBody();
        } catch (HttpRequestMethodNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
