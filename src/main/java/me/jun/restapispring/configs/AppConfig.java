package me.jun.restapispring.configs;

import me.jun.restapispring.accounts.Account;
import me.jun.restapispring.accounts.AccountRole;
import me.jun.restapispring.accounts.AccountService;
import me.jun.restapispring.common.AppProperties;
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

            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Set<AccountRole> adminRoles = new HashSet<>();
                Set<AccountRole> userRoles = new HashSet<>();
                adminRoles.add(AccountRole.ADMIN);
                userRoles.add(AccountRole.USER);

                Account admin = Account.builder()
                        .email(appProperties.getAdminEmail())
                        .password(appProperties.getAdminPassword())
                        .roles(adminRoles)
                        .build();
                accountService.saveAccount(admin);

                Account user = Account.builder()
                        .email(appProperties.getUserEmail())
                        .password(appProperties.getUserPassword())
                        .roles(userRoles)
                        .build();
                accountService.saveAccount(user);
            }
        };
    }
}
