package com.bookshop.bot;

import com.bookshop.entity.Book;
import com.bookshop.service.OrderService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@Component
public class BookBot extends TelegramLongPollingBot {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    private static final String SECRET_MSG = "/make_me_admin";
    private static final String BROADCAST = "/broadcast";
    private static final String BROADCAST_ADMINS = "/admins";
    private static final String ORDERS = "/orders";
    private static final String COMPLETE_ORDER = "/complete_order";

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

        if (!user.isAdmin()) sendMessage(user.getChatId(), "Say with us! You will receive all news from BookWorld website!");
    }

    private void isAdminCommand(BotUser user, String text) {
        if (text.contains(SECRET_MSG)) {
            user.setAdmin(true);
            userService.save(user);
        }
        if (user.isAdmin()) {
            if (text.contains(BROADCAST)) {
                text = text.substring(BROADCAST.length());
                broadcast(text);
            }
            if (text.contains(BROADCAST_ADMINS)) {
                text = text.substring(BROADCAST_ADMINS.length());
                broadcastAdmins(text);
            }
            if (text.contains(ORDERS)) {
                orderService.findAll().forEach(i -> {
                    String newText =
                            String.format("BookWorld order #%s!\n" +
                                            "\n\n%s\n\n" +
                                            "Address:%s\n" +
                                            "Phone:%s\n" +
                                            "Total Price:%s\n" +
                                            "Date: %s", i.getId(), i.getBooks().stream().map(Book::getTitle).collect(Collectors.joining("\n")),
                                    i.getAddress(), i.getPhone(), i.getCommonPrice(), i.getDate().toString());
                    sendMessage(user.getChatId(), newText);
                });
            }
            if (text.contains(COMPLETE_ORDER)) {
                text = text.substring(COMPLETE_ORDER.length());
                orderService.delete(Long.valueOf(text.trim()));
                sendMessage(user.getChatId(), "Order completed");
            }
        } else if (text.contains(BROADCAST) ||
                text.contains(BROADCAST_ADMINS) ||
                text.contains(ORDERS) ||
                text.contains(COMPLETE_ORDER)) {
            sendMessage(user.getChatId(), "You are not admin!");
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
