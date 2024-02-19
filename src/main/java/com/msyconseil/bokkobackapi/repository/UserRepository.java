package com.msyconseil.bokkobackapi.repository;

import com.msyconseil.bokkobackapi.model.UserModel;
import com.msyconseil.bokkobackapi.service.sqlstoredprocedureanswer.SqlStoredProcedureAnswer;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Integer>{

    public UserModel save(UserModel userModel);



}
