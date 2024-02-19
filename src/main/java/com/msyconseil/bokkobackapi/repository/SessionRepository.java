package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionModel, Integer> {

    public SessionModel save(SessionModel sessionModel);

}
