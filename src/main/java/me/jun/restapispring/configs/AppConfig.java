package me.jun.restapispring.configs;

import me.jun.restapispring.accounts.Account;
import me.jun.restapispring.accounts.AccountRole;
import me.jun.restapispring.accounts.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Add admin when Application start.
    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Set<AccountRole> roles = new HashSet<>();
                roles.add(AccountRole.ADMIN);
                roles.add(AccountRole.USER);

                Account admin = Account.builder()
                        .email("admin@email.com")
                        .password("pass")
                        .roles(roles)
                        .build();

                accountService.saveAccount(admin);
            }
        };
    }
}
