package com.ll.exam.qsl.user.entity;

/**
 jpa에서 기본으로 제공하는 메서드 이외에 추상메서드를 또 추가하고 싶을 때 사용하는 인터페이스
 */
public interface UserRepositoryCustom {
    SiteUser getQslUser(Long id);
}


