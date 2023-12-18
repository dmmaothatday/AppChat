package com.example.projectappchat.controller;

import com.example.projectappchat.entity.Message;
import com.example.projectappchat.entity.User;
import com.example.projectappchat.model.MessageModel;
import com.example.projectappchat.repository.UserRepository;
import com.example.projectappchat.service.message.MessageService;
import com.example.projectappchat.service.user.UserService;
import com.example.projectappchat.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel messageModel) {
        System.out.println("handling send message: " + messageModel + " to " + to);

        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, messageModel);
        Message message = new Message();

        User userSend = userService.findUserById(messageModel.getMessageSendId());
        User userReceiver = userService.findUserById(messageModel.getMessageReceiverId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));

        message.setMessageSendId(userSend);
        message.setMessageReceiverId(userReceiver);
        message.setMessageBody(messageModel.getMessageBody());
        message.setTypeMessage(AppUtils.nonFile);
        message.setMessageDateCreation(date);


        messageService.save(message);

    }


    @PostMapping("/send-multipartFile/chat/{to}")
    public @ResponseBody
    MessageModel sendMultipartFile(@PathVariable String to,
                                   @RequestParam("messageSendId") Long messageSendId,
                                   @RequestParam("messageReceiverId") Long messageReceiverId,
                                   @RequestParam("file") MultipartFile multipartFile) {

        System.out.println("handling send message: " + messageSendId + ", " +
                messageReceiverId + ", " + multipartFile + ", " + " to " + to);

        byte[] imageData = new byte[0];
        try {
            imageData = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));

        User userSend = userService.findUserById(messageSendId);
        User userReceiver = userService.findUserById(messageReceiverId);


        message.setMessageSendId(userSend);
        message.setMessageReceiverId(userReceiver);
        message.setMessageBodyFile(imageData);

        message.setTypeMessage(AppUtils.isFile);
        message.setMessageDateCreation(date);
        messageService.save(message);


        MessageModel messageModel = new MessageModel();
        messageModel.setMessageSendId(messageSendId);
        messageModel.setMessageReceiverId(messageReceiverId);
        messageModel.setMessageBody(String.valueOf(message.getMessageId()));
        messageModel.setTypeMessage(AppUtils.isFile);
        System.out.println("message id: " + messageModel.getMessageBody());


        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, messageModel);


        return messageModel;

    }
}