package com.example.projectappchat.controller;


import com.example.projectappchat.dto.SearchFriendDTO;
import com.example.projectappchat.dto.UserDTO;
import com.example.projectappchat.entity.Friend;
import com.example.projectappchat.entity.User;
import com.example.projectappchat.service.friend.FriendService;
import com.example.projectappchat.service.user.UserService;
import com.example.projectappchat.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/search-friend")
    public ModelAndView friend(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("friend/searchFriend");
        /*Principal spring trả cho server khi user đăng nhập thành công*/
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
        /*Tìm thông tin user từ principal*/
        User userInfo = userService.findUserByAccount(userPrincipal.getUsername());
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userInfo, UserDTO.class);
        modelAndView.addObject("empty", "Không có dữ liệu tìm kiếm");
        return modelAndView;
    }
    @PostMapping("/search")
    public ModelAndView search(Principal principal, @RequestParam(value = "userName", required = false) String userName) {
        ModelAndView modelAndView = new ModelAndView("friend/searchFriend");
        /*Principal spring trả cho server khi user đăng nhập thành công*/
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
        /*Tìm thông tin user từ principal*/
        User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

        UserDTO userDTO = modelMapper.map(userInfo, UserDTO.class);
        List<User> listUserFindByUserName = userService.findUserByUserName(userName);
        List<SearchFriendDTO> searchFriendDTOS = listUserFindByUserName
                .stream()
                .map(user -> modelMapper.map(user, SearchFriendDTO.class))
                .collect(Collectors.toList());
        for (SearchFriendDTO searchFriendDTO : searchFriendDTOS) {
            Friend friend = friendService.findFriendByFriendSendIdAndFriendReceiverIdOrOpposite(userDTO.userId, searchFriendDTO.userId);
            if (friend != null) {
                 if (friend.getFriendStatus() == 0) {
                    searchFriendDTO.setFriendStatus(AppUtils.RequestFriend);
                } else if (friend.getFriendStatus() == 1) {
                    searchFriendDTO.setFriendStatus(AppUtils.AcceptFriend);
                }
            }else if (userDTO.userId == searchFriendDTO.userId) {
                searchFriendDTO.setFriendStatus(AppUtils.isYou);
            } else {
                searchFriendDTO.setFriendStatus(AppUtils.irrelevant);
            }
        }
        if (searchFriendDTOS.isEmpty()) {
            modelAndView.addObject("empty", "Không có dữ liệu tìm kiếm");
        }
        modelAndView.addObject("searchFriendDTOS", searchFriendDTOS);
        modelAndView.addObject("userDTO", userDTO);
        return modelAndView;
    }
    @PostMapping("/add/{friendReceiverId}")
    public ResponseEntity<?> addFriend(Principal principal, @PathVariable("friendReceiverId") Long friendReceiverId) {
        /*Principal spring trả cho server khi user đăng nhập thành công*/
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
        /*Tìm thông tin user từ principal*/
        User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

        Friend friend = new Friend();
        User friendReceiver = userService.findUserById(friendReceiverId);
        friend.setFriendSendId(userInfo);
        friend.setFriendReceiverId(friendReceiver);
        friend.setFriendStatus(AppUtils.RequestFriend);
        friendService.save(friend);
        return new ResponseEntity<>(null,
                HttpStatus.valueOf(201));

    }




    @GetMapping("/list-request")
    public ModelAndView listRequest(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("friend/listRequest");
        /*Principal spring trả cho server khi user đăng nhập thành công*/
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
        /*Tìm thông tin user từ principal*/
        User userInfo = userService.findUserByAccount(userPrincipal.getUsername());
        List<Friend> listFriendRequest = friendService.findFriendByFriendReceiverIdAndFriendStatus(userInfo.getUserId(), AppUtils.RequestFriend);
        modelAndView.addObject("listFriendRequest", listFriendRequest);
        if (listFriendRequest.isEmpty()) {
            modelAndView.addObject("empty", "Không có lời mời kết bạn");
        }
        return modelAndView;
    }




    @PostMapping("/accept/{friendSendId}")
    public ResponseEntity<?> accept(Principal principal, @PathVariable("friendSendId") Long friendSendId) {

        /*Principal spring trả cho server khi user đăng nhập thành công*/
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
        /*Tìm thông tin user từ principal*/
        User userInfo = userService.findUserByAccount(userPrincipal.getUsername());
        Friend findFriend = friendService.findFriendByFriendSendIdAndFriendReceiverId(friendSendId, userInfo.getUserId());
        findFriend.setFriendStatus(AppUtils.AcceptFriend);
        Friend friend = new Friend();
        friend.setFriendSendId(userInfo);
        friend.setFriendReceiverId(userService.findUserById(friendSendId));
        friend.setFriendStatus(AppUtils.AcceptFriend);
        friendService.save(friend);
        return new ResponseEntity<>(null,
                HttpStatus.valueOf(201));
    }
    @PostMapping("/reject/{friendSendId}")
    public ResponseEntity<?> reject(Principal principal, @PathVariable("friendSendId") Long friendSendId) {

        /*Principal spring trả cho server khi user đăng nhập thành công*/
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
        /*Tìm thông tin user từ principal*/
        User userInfo = userService.findUserByAccount(userPrincipal.getUsername());
        Friend findFriend = friendService.findFriendByFriendSendIdAndFriendReceiverId(friendSendId, userInfo.getUserId());
        friendService.delete(findFriend);
        return new ResponseEntity<>(null,
                HttpStatus.valueOf(201));
    }
}
