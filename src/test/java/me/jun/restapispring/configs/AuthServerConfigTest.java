package me.jun.restapispring.configs;

import me.jun.restapispring.accounts.Account;
import me.jun.restapispring.accounts.AccountRole;
import me.jun.restapispring.accounts.AccountService;
import me.jun.restapispring.common.BaseControllerTest;
import me.jun.restapispring.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    @TestDescription("인증 토큰 발급 테스트")
    public void getAuthToken() throws Exception {
        // Given
        String email = "user@email.com";
        String password = "pass";
        Set<AccountRole> roles = new HashSet<>();
        roles.add(AccountRole.USER);
        roles.add(AccountRole.ADMIN);

        Account account = Account.builder()
                .email(email)
                .password(password)
                .roles(roles)
                .build();
        accountService.saveAccount(account);

        String clientId = "myApp";
        String clientSecret = "pass";

        // When & Then
        mockMvc.perform(post("/oauth/token")
                    // base auth header
                    .with(httpBasic(clientId, clientSecret))
                    .param("username", email)
                    .param("password", password)
                    .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }
}