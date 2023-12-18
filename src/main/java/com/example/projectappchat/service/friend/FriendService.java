package com.example.projectappchat.service.friend;

import com.example.projectappchat.entity.Friend;

import java.util.List;

public interface FriendService {
    List<Friend> findFriendByUserIdAndFriendStatus(Long userId, byte friendStatus);

    void save(Friend friend);

    List<Friend> findFriendByFriendReceiverIdAndFriendStatus(Long userId, byte friendStatus);

    Friend findFriendByFriendSendIdAndFriendReceiverId(Long friendSendId, Long friendReceiverId);

    Friend findFriendByFriendSendIdAndFriendReceiverIdOrOpposite(Long friendSendId, Long friendReceiverId);

    void delete(Friend friend);

    List<Friend> findFriendOnline(Long userId);

    List<Friend> findFriendNotInGroupByUserIdAndGroupChatId(Long userId, Long groupChatId);

}
