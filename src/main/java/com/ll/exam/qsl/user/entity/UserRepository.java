package com.ll.exam.qsl.user.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

public interface UserRepository extends JpaRepository<SiteUser, Long>, UserRepositoryCustom {

}
