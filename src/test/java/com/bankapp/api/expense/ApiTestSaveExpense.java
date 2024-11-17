package com.bankapp.api.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест проверяет сохранение расхода с определенным значением description
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApiTestSaveExpense {
    private static final String LOGIN = RandomString.make(10);
    private static final String PASSWORD = RandomString.make(10);
    private static final String DESCRIPTION = RandomString.make(10);

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiTestSaveExpense.class);

    private Long accountId;
    private String accessToken;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        LOGGER.info("Регистрируем аккаунт");
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
                .andReturn()
                .getResponse()
                .getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        accountId = objectMapper.readTree(response).get("id").asLong();
        LOGGER.info("Успешно зарегистрировали аккаунт с id {}", accountId);

        LOGGER.info("Логинимся в аккаунт с id {}", accountId);
        response = mockMvc.perform(post("/api/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        accessToken = objectMapper.readTree(response).get("accessToken").asText();
        LOGGER.info("Успешно залогинились в аккаунт с id {}", accountId);
    }

    @Test
    void apiTestSaveExpense() throws Exception {
        LOGGER.info("Тест проверяет сохранение расхода");

        LOGGER.info("Сохраняем расход");
        String requestBody = String.format("""
                {
                    "description": "%s"
                }
                """, DESCRIPTION);
        String response = mockMvc.perform(post("/api/expense")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value(DESCRIPTION))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Long expenseId = new ObjectMapper().readTree(response).get("id").asLong();
        LOGGER.info("Успешно сохранили расход с id {} и description {}", expenseId, DESCRIPTION);

        LOGGER.info("Успешно проверили сохранение расхода с определенным значением description");
    }

    @AfterEach
    public void cleanUp() throws Exception {
        LOGGER.info("Удаляем аккаунт с id {}", accountId);
        mockMvc.perform(delete("/api/account/" + accountId))
                .andExpect(status().isOk());
        LOGGER.info("Успешно удалили аккаунт");
    }
}
