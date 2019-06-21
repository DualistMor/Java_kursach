package com.artcoversapp.configs;

import com.artcoversapp.keys.AccountUtils;
import com.artcoversapp.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class AccountConfiguration {

    @Bean
    UserDetailsService userDetailsService(AccountRepository accountRepository) {
        return username -> accountRepository.findByUsername(username)
                .map(account -> {
                    boolean active = account.isActive();
                    return new User(account.getUsername(),
                            account.getPassword(),
                            active,
                            active,
                            active,
                            active,
                            AccountUtils.getGrantedAuthorityByAccount(account));
                }).orElseThrow(() -> new UsernameNotFoundException(String.format("username %s not found", username)));
    }

}
