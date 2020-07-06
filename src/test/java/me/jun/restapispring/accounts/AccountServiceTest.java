package me.jun.restapispring.accounts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findByUserName() {
        // Given
        HashSet<AccountRole> roles = new HashSet<>();
        roles.add(AccountRole.ADMIN);
        roles.add(AccountRole.USER);
        String password = "pass";

        String userName = "user@email.com";
        Account account = Account.builder()
                .email(userName)
                .password(password)
                .roles(roles)
                .build();

        accountRepository.save(account);

        // When
        UserDetails userDetails = accountService.loadUserByUsername(userName);

        // Then
        assertThat(userDetails.getPassword()).isEqualTo(password);
    }
}