package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.user.entity.SiteUser;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

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

    @Override
    public SiteUser getQslUserOrderIdAscOne(long l) {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .limit(1) // 정렬 후 첫번째 값만 가져옴.
                .fetchOne(); // limit과 fetchOne을 합쳐서 fetchFirst로도 쓸 수 있다.
    }

    @Override
    public List<SiteUser> getQslUserOrderByIdAsc(long l) {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .limit(1) // 정렬 후 첫번째 값만 가져옴.
                .fetch(); // 리턴 값이 list일 경우 fetch만 쓰면 된다.
    }

    @Override
    public List<SiteUser> searchQsl(String kw) {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                )
                .orderBy(siteUser.id.desc())
                .fetch();

    }

    public Page<SiteUser> searchQsl(String kw, Pageable pageable) {
        JPAQuery<SiteUser> usersQuery = jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                )
                .offset(pageable.getOffset()) // 몇개를 건너 띄어야 하는지 LIMIT {1}, ?
                .limit(pageable.getPageSize()); // 한페이지에 몇개가 보여야 하는지 LIMIT ?, {1}

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(siteUser.getType(), siteUser.getMetadata());
            usersQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<SiteUser> users = usersQuery.fetch();

        JPAQuery<Long> usersCountQuery = jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                );

        return PageableExecutionUtils.getPage(users, pageable, usersCountQuery::fetchOne);
    }
}
