package org.keniu.services;

import org.keniu.entities.User;
import org.keniu.exceptions.InsufficientBalanceException;
import org.keniu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Autowired
    public UserService(UserRepository userRepository, PlatformTransactionManager platformTransactionManager) {
        this.userRepository = userRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Transactional
    public void declarativeTransferMoney(String fromUser, String toUser, BigDecimal amount)
            throws InsufficientBalanceException {
        executeTransfer(fromUser, toUser, amount);
    }

    public void programmaticTransferMoney(String fromUser, String toUser, BigDecimal amount)
            throws InsufficientBalanceException {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);
        try {
            executeTransfer(fromUser, toUser, amount);
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }

    private void executeTransfer(String fromUser, String toUser, BigDecimal amount)
            throws InsufficientBalanceException {
        User sender = userRepository.findByLogin(fromUser);
        User recipient = userRepository.findByLogin(toUser);

        if (sender.getBalance().compareTo(amount) >= 0) {
            sender.setBalance(sender.getBalance().subtract(amount));
            recipient.setBalance(recipient.getBalance().add(amount));
            userRepository.save(sender);
            userRepository.save(recipient);
        } else {
            throw new InsufficientBalanceException("Not enough balance to transfer");
        }
    }
}
