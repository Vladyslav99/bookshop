package com.bookshop.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BotUserRepository userRepository;

    @Transactional(readOnly = true)
    public List<BotUser> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BotUser> findAllAdmins() {
        return userRepository.findAllByAdminTrue();
    }

    @Transactional(readOnly = true)
    public Optional<BotUser> findByChatId(long id) {
        return userRepository.findByChatId(id);
    }

    @Transactional
    public BotUser save(BotUser botUser) {
        return userRepository.save(botUser);
    }
}
