package me.jun.restapispring.accounts;

import me.jun.restapispring.common.AppProperties;
import me.jun.restapispring.common.BaseTest;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceTest extends BaseTest {

    @Autowired
    AccountService accountService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppProperties appProperties;

    @Test
    public void findByUserName() {
        // Given
        // Initialized in Application Runner in AppConfig class.

        // When
        UserDetails userDetails = accountService.loadUserByUsername(appProperties.getUserEmail());

        // Then
        assertThat(passwordEncoder.matches(appProperties.getUserPassword(), userDetails.getPassword())).isTrue();
    }

    @Test
    public void findByUserNameFail() {
        // Expected
        String userName = "someone@email.com";
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(userName));

        // when
        accountService.loadUserByUsername(userName);
    }
}