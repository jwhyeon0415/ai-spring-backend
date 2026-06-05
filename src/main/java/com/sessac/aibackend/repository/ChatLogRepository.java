package com.sessac.aibackend.repository;

import com.sessac.aibackend.domain.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {

    List<ChatLog> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("""
          select c from ChatLog c
          join fetch c.user
          where c.user.id = :userId
          order by c.createdAt desc
          """)      // select문을 직접 만들어서 날림.  -> 한 번에 묶에서 select 날린다,,
    List<ChatLog> findByUserIdWithUser(Long userId);
}
