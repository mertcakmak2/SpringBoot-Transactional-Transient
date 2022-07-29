package com.spring.transactional.service;

import com.spring.transactional.model.User;

import java.util.List;

public interface TransferService {

    List<User> sendMoney(int senderId, int receiverId, double money);

}
