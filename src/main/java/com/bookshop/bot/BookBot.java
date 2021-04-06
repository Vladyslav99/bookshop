package com.bookshop.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class BookBot extends TelegramLongPollingBot {

    @Autowired
    private UserService userService;

    private static final String SECRET_MSG = "/make_me_admin";
    private static final String BROADCAST = "/broadcast";

    @Override
    public String getBotUsername() {
        return "AnTaBookWorldBot";
    }

    @Override
    public String getBotToken() {
        return "1648464470:AAHtw1kVfv3QE7H0jZMDiGY1RYZaDpN_nI8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() && !update.getMessage().hasText())
            return;

        String text = update.getMessage().getText();
        final long chatId = update.getMessage().getChatId();

        BotUser user;
        Optional<BotUser> botUserOptional = userService.findByChatId(chatId);

        if (botUserOptional.isPresent()) {
            user = botUserOptional.get();
        } else {
            user = userService.save(new BotUser(null, chatId, text.equals(SECRET_MSG)));
        }

        isAdminCommand(user, text);
    }

    private void isAdminCommand(BotUser user, String text) {
        if (text.contains(SECRET_MSG)) {
            user.setAdmin(true);
            userService.save(user);
        }
        if (text.contains(BROADCAST) && user.isAdmin()) {
            text = text.substring(BROADCAST.length());
            broadcast(text);
        }
    }

    @SneakyThrows
    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), text);
        execute(sendMessage);
    }

    public void broadcast(String text) {
        userService.findAll().forEach((user) -> sendMessage(user.getChatId(), text));
    }
}
