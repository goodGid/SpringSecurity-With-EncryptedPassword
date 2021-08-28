package dev.be.security_pw_encryption.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * ## Reference
 * - [1]. https://goodgid.github.io/Spring-Test-SpringBootTest-Annotation
 * - [2]. https://youngjinmo.github.io/2021/05/passwordencoder/
 * - [3]. https://velog.io/@hyeinisfree/SpringSecurity-PasswordEncoder
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // [1] Reference 참고
@AutoConfigureMockMvc // [1] Reference 참고
class SecurityControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("패스워드 암호화 테스트")
    @WithMockUser(roles = "USER, ADMIN") // Spring Security 때문에 권한을 부여해줘야 테스트를 성공한다.
    void securityPasswordTest() throws Exception {
        // given
        String rawPassword = "12345678";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("pw", rawPassword);

        // when
        ResultActions result = mockMvc.perform(get("/security/encrypt_pw")
                                                       .params(map));
        String contentAsString = result.andReturn().getResponse().getContentAsString();

        // then
        assertAll(
                () -> assertNotEquals(rawPassword, contentAsString),
                () -> assertTrue(passwordEncoder.matches(rawPassword, contentAsString))
        );
    }
}