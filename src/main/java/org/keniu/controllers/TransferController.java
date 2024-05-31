package org.keniu.controllers;

import org.keniu.exceptions.InsufficientBalanceException;
import org.keniu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class TransferController {
    private UserService userService;

    @Autowired
    public TransferController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/declarative-transfer")
    public String declarativeTransferMoney(@RequestBody Map<String, String> payload)
            throws InsufficientBalanceException {
        String fromUser = payload.get("fromUser");
        String toUser = payload.get("toUser");
        BigDecimal amount = new BigDecimal(payload.get("amount"));

        userService.declarativeTransferMoney(fromUser, toUser, amount);
        return "Transfer successful!";
    }

    @PostMapping("/programmatic-transfer")
    public String programmaticTransferMoney(@RequestBody Map<String, String> payload)
            throws InsufficientBalanceException {
        String fromUser = payload.get("fromUser");
        String toUser = payload.get("toUser");
        BigDecimal amount = new BigDecimal(payload.get("amount"));

        userService.programmaticTransferMoney(fromUser, toUser, amount);
        return "Transfer successful!";
    }
}
