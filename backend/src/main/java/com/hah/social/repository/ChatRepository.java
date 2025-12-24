package com.hah.social.repository;

import com.hah.social.model.entity.Chat;
import com.hah.social.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByUsersId(Long userId);

    @Query("SELECT c FROM Chat c WHERE :reqUser MEMBER OF c.users AND :reqUser2 MEMBER OF c.users")
    Chat findChatByUserId(@Param("reqUser") User reqUser, @Param("reqUser2") User user2);

}
