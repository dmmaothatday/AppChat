package com.example.projectappchat.controller;

import com.example.projectappchat.dto.BoxChatFriendDTO;
import com.example.projectappchat.dto.FriendOnlineDTO;
import com.example.projectappchat.entity.Friend;
import com.example.projectappchat.entity.Message;
import com.example.projectappchat.service.friend.FriendService;
import com.example.projectappchat.service.message.MessageService;
import com.example.projectappchat.service.user.UserService;
import com.example.projectappchat.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class ChatController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private MessageService messageService;

    ModelMapper modelMapper = new ModelMapper();


    @GetMapping({"/box-chat/{userFriendId}", "/box-chat"})
    public ModelAndView boxChatDetail(Principal principal,
                                      @PathVariable(value = "userFriendId", required = false) Long userFriendId) {
        ModelAndView modelAndView = new ModelAndView("user/boxChat");

        /*User spring trả cho server khi đăng nhập thành công*/
        User userPrincipal = (User) ((Authentication) principal).getPrincipal();

        /*Tìm thông tin user từ principal*/
        com.example.projectappchat.entity.User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

        /*Tìm danh sách bạn bè của user*/
        List<Friend> friendList = friendService
                .findFriendByUserIdAndFriendStatus(userInfo.getUserId(), AppUtils.AcceptFriend);

        List<BoxChatFriendDTO> boxChatFriendDTOList = friendList
                .stream()
                .map(friend -> modelMapper.map(friend, BoxChatFriendDTO.class))
                .collect(Collectors.toList());

        /*Duyệt danh sách bạn bè của user để tìm message của tất cả các bạn bè nhắn tin với user*/
        for (BoxChatFriendDTO friend : boxChatFriendDTOList) {
            List<Message> messageListOfAllFriend = messageService.
                    findMessageByMessageSendId1AndMessageReceiverId1OrByMessageSendId2AndMessageReceiverId2
                            (userInfo.getUserId(), friend.getFriendReceiverId().getUserId());

            if (!messageListOfAllFriend.isEmpty()) {
                Message lastMessage = messageListOfAllFriend.get(messageListOfAllFriend.size() - 1);

                if (lastMessage.getMessageBody() == null) {
                    friend.setLastMessage("Tin nhắn chứa file đính kèm");
                } else {
                    friend.setLastMessage(lastMessage.getMessageBody());
                }
            }
        }



        /*Tìm message giữa user với 1 friend được gọi*/
        List<Message> messageListOfOneFriend = messageService.
                findMessageByMessageSendId1AndMessageReceiverId1OrByMessageSendId2AndMessageReceiverId2(userInfo.getUserId(), userFriendId);


        if (userFriendId != null) {
            modelAndView.addObject("friendInfo", userService.findUserById(userFriendId));
        }





        /*=============================================================================*/


        modelAndView.addObject("messageListOfOneFriend", messageListOfOneFriend);
        modelAndView.addObject("userInfo", userInfo);
        modelAndView.addObject("friendList", boxChatFriendDTOList);
        return modelAndView;
    }


    @GetMapping("/phonebook")
    public ModelAndView phonebook(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("user/phonebook");

        /*User spring trả cho server khi đăng nhập thành công*/
        User userPrincipal = (User) ((Authentication) principal).getPrincipal();
        /*Tìm thông tin user từ principal*/
        com.example.projectappchat.entity.User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

        List<Friend> listFriend = friendService.findFriendOnline(userInfo.getUserId());

        List<FriendOnlineDTO> listFriendOnlineDTO = listFriend
                .stream()
                .map(friend -> modelMapper.map(friend, FriendOnlineDTO.class))
                .collect(Collectors.toList());


        modelAndView.addObject("userInfo", userInfo);
        modelAndView.addObject("listFriendOnlineDTO", listFriendOnlineDTO);


        return modelAndView;
    }


}
