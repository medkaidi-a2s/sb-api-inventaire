package dz.a2s.a2sinventaire.repositories;

import dz.a2s.a2sinventaire.entities.DiMessages;
import dz.a2s.a2sinventaire.entities.keys.DiMessagesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiMessagesRepository extends JpaRepository<DiMessages, DiMessagesId> {

    @Query(value = "SELECT MSG_DESC_LOC FROM DI_MESSAGES WHERE MSG_SYS_ID = 1 AND MSG_ID = :msgId", nativeQuery = true)
    String getMsgDescLocById(@Param("msgId") Integer msgId);

}
