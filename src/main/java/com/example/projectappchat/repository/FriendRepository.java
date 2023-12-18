package com.example.projectappchat.repository;


import com.example.projectappchat.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface FriendRepository extends JpaRepository<Friend, Long> {
    /*Tìm danh sách bạn bè của user*/
    @Query(value = "select fr from Friend fr " +
            "where fr.friendSendId.userId = :userId and fr.friendStatus = :friendStatus " +
            "order by fr.friendReceiverId.online desc")
    List<Friend> findFriendByUserIdAndFriendStatus(@Param("userId") Long userId,
                                                   @Param("friendStatus") byte friendStatus);

    /*Tim friend gui loi moi yeu cau*/
    @Query(value = "select fr from Friend fr where fr.friendReceiverId.userId = :userId " +
            "and fr.friendStatus = :friendStatus")
    List<Friend> findFriendByFriendReceiverIdAndFriendStatus(@Param("userId") Long userId,
                                                             @Param("friendStatus") byte friendStatus);

    /*Tim friend de xac dinh moi quan he cua 2 user*/
    @Query(value = "select fr from Friend fr where fr.friendSendId.userId = :friendSendId " +
            "and fr.friendReceiverId.userId = :friendReceiverId")
    Friend findFriendByFriendSendIdAndFriendReceiverId(@Param("friendSendId") Long friendSendId,
                                                       @Param("friendReceiverId") Long friendReceiverId);

    /*Tim friend de xac dinh moi quan he cua 2 user*/
    @Query(value = "select * from Friend fr where fr.friend_send_id = :friendSendId " +
            "and fr.friend_receiver_id = :friendReceiverId " +
            "or fr.friend_send_id =:friendReceiverId and fr.friend_receiver_id = :friendSendId limit 1", nativeQuery = true)
    Friend findFriendByFriendSendIdAndFriendReceiverIdOrOpposite(@Param("friendSendId") Long friendSendId,
                                                                 @Param("friendReceiverId") Long friendReceiverId);

    /*Tim danh sach ban be cua user dang online*/
    @Query(value = "select fr from Friend fr where fr.friendSendId.userId =:userId " +
            "and fr.friendStatus=1 order by fr.friendReceiverId.online desc ")
    List<Friend> findFriendOnline(@Param("userId") Long userId);


    /*Tim danh sach ban be cua user chua duoc them vao group chat*/
    @Query(value = "select * from Friend fr where fr.friend_send_id = :userId " +
            "and fr.friend_status = 1 " +
            "and fr.friend_receiver_id not in " +
            "(select user_id from group_chat_detail gcd where gcd.group_chat_id = :groupChatId)", nativeQuery = true)
    List<Friend> findFriendNotInGroupByUserIdAndGroupChatId (@Param("userId") Long userId,
                                                                 @Param("groupChatId") Long groupChatId);
}
