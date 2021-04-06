package com.bookshop.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BookBot extends TelegramLongPollingBot {

    @Autowired
    private UserService userService;

    private static final String SECRET_MSG = "/make_me_admin";
    private static final String BROADCAST = "/broadcast";
    private static final String BROADCAST_ADMINS = "/admins";

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

        final String text = update.getMessage().getText();
        final long chatId = update.getMessage().getChatId();

        BotUser user = userService.findByChatId(chatId)
                .orElseGet(() -> userService.save(new BotUser(null, chatId, text.equals(SECRET_MSG))));

        isAdminCommand(user, text);
    }

    private void isAdminCommand(BotUser user, String text) {
        if (text.contains(SECRET_MSG)) {
            user.setAdmin(true);
            userService.save(user);
        }
        if (user.isAdmin() && text.contains(BROADCAST)) {
            text = text.substring(BROADCAST.length());
            broadcast(text);
        }
        if (user.isAdmin() && text.contains(BROADCAST_ADMINS)) {
            text = text.substring(BROADCAST_ADMINS.length());
            broadcastAdmins(text);
        }
    }

    @SneakyThrows
    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), text);
        execute(sendMessage);
    }

    public void broadcastAdmins(String text) {
        userService.findAllAdmins().forEach(user -> sendMessage(user.getChatId(), text));
    }

    public void broadcast(String text) {
        userService.findAll().forEach(user -> sendMessage(user.getChatId(), text));
    }
}
