package com.example.projectappchat.controller;


import com.example.projectappchat.dto.UserDTO;
import com.example.projectappchat.entity.Friend;
import com.example.projectappchat.repository.UserRepository;
import com.example.projectappchat.service.friend.FriendService;
import com.example.projectappchat.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Value("${uploadDir}")
    private String uploadFolder;


    private static final Logger log = LoggerFactory.getLogger("UserController.class");

    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    @Autowired
    UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/profile")
    public ModelAndView profile(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("user/userProfile");
        /*User spring trả cho server khi đăng nhập thành công*/
        User userPrincipal = (User) ((Authentication) principal).getPrincipal();

        /*Tìm thông tin user từ principal*/
        com.example.projectappchat.entity.User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

        modelAndView.addObject("userInfo", userInfo);

        return modelAndView;
    }

    @GetMapping("/edit-profile")
    public ModelAndView editProfile(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("user/editUserProfile");
        /*User spring trả cho server khi đăng nhập thành công*/
        User userPrincipal = (User) ((Authentication) principal).getPrincipal();

        /*Tìm thông tin user từ principal*/
        com.example.projectappchat.entity.User userInfo = userService.findUserByAccount(userPrincipal.getUsername());
        /*System.out.println("userlogo: " + userInfo.getUserLogo());*/

        modelAndView.addObject("userInfo", userInfo);
        return modelAndView;
    }

    @PostMapping("/edit-profile/save")
    public ModelAndView saveEditProfile(Principal principal,
                                        @RequestParam(value = "userName", required = false) String userName,
                                        @RequestParam(value = "userEmail", required = false) String userEmail,
                                        @RequestParam(value = "userContactNumber", required = false) String userContactNumber,
                                        @RequestParam(value = "facebook", required = false) String facebook) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/profile");
        /*User spring trả cho server khi đăng nhập thành công*/
        User userPrincipal = (User) ((Authentication) principal).getPrincipal();

        /*Tìm thông tin user từ principal*/
        com.example.projectappchat.entity.User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

        userInfo.setUserName(userName);
        userInfo.setUserEmail(userEmail);
        userInfo.setUserContactNumber(userContactNumber);
        userInfo.setFacebook(facebook);
        userService.save(userInfo);

        return modelAndView;
    }

    //update avatar user
    @PostMapping("/edit-profile/update-avatar")
    public ModelAndView updateAvatar (Principal principal,
                                      final @RequestParam(value = "file", required = false) MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/profile");
        /*User spring trả cho server khi đăng nhập thành công*/
        User userPrincipal = (User) ((Authentication) principal).getPrincipal();

        /*Tìm thông tin user từ principal*/
        com.example.projectappchat.entity.User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

       /* System.out.println("file: " + file);*/
        byte[] imageData = new byte[0];
        try {
            imageData = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }


        userInfo.setUserLogo(imageData);
        userService.save(userInfo);
        return modelAndView;
    }


    @GetMapping("/profile/{userId}")
    public ModelAndView viewProfileAnotherUser(Principal principal, @PathVariable("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView("user/anotherUserProfile");

        /*User spring trả cho server khi đăng nhập thành công*/
        User userPrincipal = (User) ((Authentication) principal).getPrincipal();

        /*Tìm thông tin user từ principal*/
        com.example.projectappchat.entity.User userInfo = userService.findUserByAccount(userPrincipal.getUsername());

        com.example.projectappchat.entity.User friendInfo = userService.findUserById(userId);
        UserDTO friendInfoDTO = modelMapper.map(friendInfo, UserDTO.class);

       /* System.out.println(userDTO.toString());*/

        Friend friend = friendService.findFriendByFriendSendIdAndFriendReceiverIdOrOpposite(userInfo.getUserId(), userId);

        modelAndView.addObject("userInfo", friendInfoDTO);

        if (friend !=null) {
            modelAndView.addObject("friendStatus", friend.getFriendStatus());
        }


        return modelAndView;
    }


}
