package com.bankapp.api.category;

import com.bankapp.api.auth.ApiTestLogin;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест проверяет получение всех категорий расходов по id пользователя
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApiTestGetCategories {
    private static final String LOGIN = RandomString.make(10);
    private static final String PASSWORD = RandomString.make(10);

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiTestGetCategories.class);

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

        LOGGER.info("Сохраняем категорию");
        response = mockMvc.perform(post("/api/category")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Long categoryId = objectMapper.readTree(response).get("id").asLong();
        LOGGER.info("Успешно сохранили категорию с id {}", categoryId);
    }

    @Test
    void apiTestGetCategories() throws Exception {
        LOGGER.info("Тест проверяет получение всех категорий расходов по id пользователя");

        LOGGER.info("Получаем все категории расходов пользователя с id {}", accountId);
        mockMvc.perform(get("/api/categories")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty());
        LOGGER.info("Успешно получили все категории расходов пользователя");

        LOGGER.info("Успешно проверили получение всех категорий расходов по id пользователя");
    }

    @AfterEach
    public void cleanUp() throws Exception {
        LOGGER.info("Удаляем аккаунт с id {}", accountId);
        mockMvc.perform(delete("/api/account/" + accountId))
                .andExpect(status().isOk());
        LOGGER.info("Успешно удалили аккаунт");
    }
}