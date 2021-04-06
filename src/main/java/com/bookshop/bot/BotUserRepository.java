package com.bookshop.bot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    Optional<BotUser> findByChatId(long chatId);

    List<BotUser> findAllByAdminTrue();
}
