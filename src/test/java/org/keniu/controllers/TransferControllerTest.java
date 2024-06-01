package org.keniu.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.keniu.exceptions.InsufficientBalanceException;
import org.keniu.services.UserService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testDeclarativeTransferMoneySuccess() throws Exception {
        Map<String, String> payload = createPayload();

        mockMvc.perform(sendPostRequest(payload, "/declarative-transfer"))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer successful!"));
    }

    @Test
    void testDeclarativeTransferMoneyInsufficientBalance() throws Exception {
        Mockito.doThrow(new InsufficientBalanceException("Not enough balance to transfer"))
                .when(userService).declarativeTransferMoney(anyString(), anyString(), any(BigDecimal.class));

        Map<String, String> payload = createPayload();

        mockMvc.perform(sendPostRequest(payload, "/declarative-transfer"))
                .andExpect(status().isOk())
                .andExpect(content().string("Not enough balance to transfer"));
    }

    @Test
    void testProgrammaticTransferMoneySuccess() throws Exception {
        Map<String, String> payload = createPayload();

        mockMvc.perform(sendPostRequest(payload, "/programmatic-transfer"))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer successful!"));
    }

    @Test
    void testProgrammaticTransferMoneyInsufficientBalance() throws Exception {
        Mockito.doThrow(new InsufficientBalanceException("Not enough balance to transfer"))
                .when(userService).programmaticTransferMoney(anyString(), anyString(), any(BigDecimal.class));

        Map<String, String> payload = createPayload();

        mockMvc.perform(sendPostRequest(payload, "/programmatic-transfer"))
                .andExpect(status().isOk())
                .andExpect(content().string("Not enough balance to transfer"));
    }

    private Map<String, String> createPayload() {
        Map<String, String> payload = new HashMap<>();
        payload.put("fromUser", "user1");
        payload.put("toUser", "user2");
        payload.put("amount", "100.00");
        return payload;
    }

    private MockHttpServletRequestBuilder sendPostRequest(Map<String, String> payload, String url)
            throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(payload);
        return post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload);
    }
}
