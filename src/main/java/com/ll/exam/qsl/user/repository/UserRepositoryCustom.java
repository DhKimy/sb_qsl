package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.user.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 jpa에서 기본으로 제공하는 메서드 이외에 추상메서드를 또 추가하고 싶을 때 사용하는 인터페이스
 */
public interface UserRepositoryCustom {
    SiteUser getQslUser(Long id);

    long getQslCount();

    SiteUser getQslUserOrderIdAscOne(long l);

    List<SiteUser> getQslUserOrderByIdAsc(long l);

    List<SiteUser> searchQsl(String user1);

    Page<SiteUser> searchQsl(String kw, Pageable pageable);
}


