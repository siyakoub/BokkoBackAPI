package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageModel, Integer> {

    public MessageModel save(MessageModel messageModel);

}
