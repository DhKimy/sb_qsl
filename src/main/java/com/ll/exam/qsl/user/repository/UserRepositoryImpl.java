package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.user.entity.SiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.ll.exam.qsl.user.entity.QSiteUser.siteUser;

/**
UserRepositoryCustom 인터페이스의 실제 구현.
Jpa가 커스텀 인터페이스의 추상 메서드를 자동으로 구현해주지 않기 때문에 필요하다.
*/
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    //쿼리 팩토리 생성
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public SiteUser getQslUser(Long id) {
    /* 이런 쿼리를 작성할 것이다.
        SELECT *
        FROM site_user
        WHERE id = 1
    */
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.id.eq(1L))
                .fetchOne();

    }

    @Override
    public long getQslCount() {
        return jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .fetchOne();
    }
}
