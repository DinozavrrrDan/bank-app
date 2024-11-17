package com.bankapp.api.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест проверяет создание аккаунта
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ApiTestCreateAccount {
    private static final String LOGIN = RandomString.make(10);
    private static final String PASSWORD = RandomString.make(10);

    private Long accountId;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void apiTestLogin() throws Exception {
        String requestBody = String.format("""
                {
                    "login": "%s",
                    "password": "%s"
                }
                """, LOGIN, PASSWORD);

        String response = mockMvc.perform(post("/api/account/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        accountId = objectMapper.readTree(response).get("id").asLong();
    }

    @AfterEach
    public void cleanUp() throws Exception {
        mockMvc.perform(delete("/api/account/" + accountId))
                .andExpect(status().isOk());
    }
}
