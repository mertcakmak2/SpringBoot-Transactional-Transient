package com.spring.transactional.service;

import com.spring.transactional.model.User;
import com.spring.transactional.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService{

    private final UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public List<User> sendMoney(int senderId, int receiverId, double money) {

        User sender = userRepository.findById(senderId).orElseThrow( () -> new EntityNotFoundException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow( () -> new EntityNotFoundException("Receiver not found"));

        if(money <= 0) throw new IllegalArgumentException("Invalid value");

        sender.setOldMoney(sender.getMoney());
        receiver.setOldMoney(receiver.getMoney());

        double newSenderMoney = sender.getMoney() - money;
        double newReceiverMoney = receiver.getMoney() + money;
        sender.setMoney(newSenderMoney);
        receiver.setMoney(newReceiverMoney);

        var result = userRepository.saveAll(List.of(sender, receiver));

        //throw new IllegalArgumentException("error...");
        return result;
    }

    @PostConstruct
    private void createUser(){

        User sender = User.builder().name("sender").money(1000).build();
        User receiver = User.builder().name("receiver").money(500).build();

        userRepository.saveAll(List.of( sender, receiver ));
    }
}
