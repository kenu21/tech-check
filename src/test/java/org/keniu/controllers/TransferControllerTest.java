package org.keniu.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.keniu.services.UserService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
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
    void testTransferMoneySuccess() throws Exception {
        Mockito.doNothing().when(userService).transferMoney(anyString(), anyString(), any(BigDecimal.class));

        Map<String, String> payload = new HashMap<>();
        payload.put("fromUser", "user1");
        payload.put("toUser", "user2");
        payload.put("amount", "100.00");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(payload);

        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer successful!"));
    }

    @Test
    void testTransferMoneyInsufficientBalance() throws Exception {
        Mockito.doThrow(new RuntimeException("Not enough balance to transfer"))
                .when(userService).transferMoney(anyString(), anyString(), any(BigDecimal.class));

        Map<String, String> payload = new HashMap<>();
        payload.put("fromUser", "user1");
        payload.put("toUser", "user2");
        payload.put("amount", "100.00");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(payload);

        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().string("Not enough balance to transfer"));
    }
}
