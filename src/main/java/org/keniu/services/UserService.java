package org.keniu.services;

import org.keniu.entities.User;
import org.keniu.exceptions.InsufficientBalanceException;
import org.keniu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void transferMoney(String fromUser, String toUser, BigDecimal amount) throws InsufficientBalanceException {
        User sender = userRepository.findByLogin(fromUser);
        User recipient = userRepository.findByLogin(toUser);

        if (sender.getBalance().compareTo(amount) >= 0) { // Используем compareTo для сравнения BigDecimal
            sender.setBalance(sender.getBalance().subtract(amount)); // Используем subtract для вычитания
            recipient.setBalance(recipient.getBalance().add(amount)); // Используем add для добавления
            userRepository.save(sender);
            userRepository.save(recipient);
        } else {
            throw new InsufficientBalanceException("Not enough balance to transfer");
        }
    }
}
