package com.artcoversapp.keys;

import com.artcoversapp.entities.Account;
import com.artcoversapp.entities.AccountRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountUtils {
    public static List<String> getRolesByAccount(Account account) {
        return account.getRoles()
                .stream()
                .map(AccountRole::getRole)
                .collect(Collectors.toList());
    }

    public static Collection<GrantedAuthority> getGrantedAuthorityByAccount(Account account) {
        List<String> rolesList = getRolesByAccount(account);
        return Collections.unmodifiableList(AuthorityUtils.createAuthorityList(rolesList.toArray(new String[0])));
    }
}
