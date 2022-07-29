package com.spring.transactional.controller;

import com.spring.transactional.model.User;
import com.spring.transactional.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping("/transfer/{senderId}/{receiverId}/{money}")
    public List<User> moneyTransfer(@PathVariable int senderId, @PathVariable int receiverId, @PathVariable double money){
        return transferService.sendMoney(senderId,receiverId,money);
    }
}
