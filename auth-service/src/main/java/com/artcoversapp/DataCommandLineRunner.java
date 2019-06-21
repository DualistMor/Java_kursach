package com.artcoversapp;

import com.artcoversapp.entities.Account;
import com.artcoversapp.entities.AccountRole;
import com.artcoversapp.entities.Client;
import com.artcoversapp.repository.AccountRepository;
import com.artcoversapp.repository.AccountRoleRepository;
import com.artcoversapp.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Stream;

import static com.artcoversapp.keys.AuthKeys.ROLE_ADMIN;

@Component
@RequiredArgsConstructor
class DataCommandLineRunner implements CommandLineRunner {

    private final AccountRoleRepository accountRoleRepository;

    private final AccountRepository accountRepository;

    private final ClientRepository clientRepository;

    @Override
    public void run(String... args) throws Exception {

        final String separator = ",";

        Stream.of("ROLE_ADMIN", "ROLE_USER")
                .map(s -> s.split(separator))
                .forEach(tuple -> accountRoleRepository.save(new AccountRole(tuple[0])));

        AccountRole adminRole = accountRoleRepository.findByRole(ROLE_ADMIN).orElse(null);

        Stream.of("admin,admin")
                .map(s -> s.split(separator))
                .forEach(tuple -> accountRepository.save(new Account(tuple[0], tuple[1], true,
                        new ArrayList<AccountRole>() {
                            {
                                add(adminRole);
                            }
                        })));

        Stream.of("html5,password")
                .map(x -> x.split(separator))
                .forEach(x -> clientRepository.save(new Client(x[0], x[1])));
    }
}
