package com.example.projectappchat.service.friend;


import com.example.projectappchat.entity.Friend;
import com.example.projectappchat.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendRepository friendRepository;

    @Override
    public List<Friend> findFriendByUserIdAndFriendStatus(Long friendSendId, byte friendStatus) {
        return friendRepository.findFriendByUserIdAndFriendStatus(friendSendId, friendStatus);
    }

    @Override
    public void save(Friend friend) {
        friendRepository.save(friend);
    }

    @Override
    public List<Friend> findFriendByFriendReceiverIdAndFriendStatus(Long userId, byte friendStatus) {
        return friendRepository.findFriendByFriendReceiverIdAndFriendStatus(userId, friendStatus);
    }

    @Override
    public Friend findFriendByFriendSendIdAndFriendReceiverId(Long friendSendId, Long friendReceiverId) {
        return friendRepository.findFriendByFriendSendIdAndFriendReceiverId(friendSendId, friendReceiverId);
    }

    @Override
    public Friend findFriendByFriendSendIdAndFriendReceiverIdOrOpposite(Long friendSendId, Long friendReceiverId) {
        return friendRepository.findFriendByFriendSendIdAndFriendReceiverIdOrOpposite(friendSendId,friendReceiverId);
    }

    @Override
    public void delete(Friend friend) {
        friendRepository.delete(friend);
    }

    @Override
    public List<Friend> findFriendOnline(Long userId) {
        return friendRepository.findFriendOnline(userId);
    }

    @Override
    public List<Friend> findFriendNotInGroupByUserIdAndGroupChatId(Long userId, Long groupChatId) {
        return friendRepository.findFriendNotInGroupByUserIdAndGroupChatId(userId,groupChatId);
    }
}
