package com.ll.exam.qsl;

import com.ll.exam.qsl.user.entity.SiteUser;
import com.ll.exam.qsl.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test") // 테스트 모드 활성화 하는 어노테이션
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("회원 생성")
	void test_1() {
		SiteUser user3 = SiteUser.builder()
				.username("user3")
				.password("{noop}1234")
				.email("user3@test.com")
				.build();

		// 빌더를 쓰는 경우 인자 순서가 달라도 상관 없다.
		SiteUser user4 = SiteUser.builder()
				.username("user4")
				.password("{noop}1234")
				.email("user4@test.com")
				.build();

		userRepository.saveAll(Arrays.asList(user3, user4));
	}

	@Test
	@DisplayName("1번 회원을 QSL로 가져오기")
	void test_2() {
		SiteUser u1 = userRepository.getQslUser(1L);

		assertThat(u1.getId()).isEqualTo(1L);
		assertThat(u1.getUsername()).isEqualTo("user1");
		assertThat(u1.getEmail()).isEqualTo("user1@test.com");
		assertThat(u1.getPassword()).isEqualTo("{noop}1234");
	}

	@Test
	@DisplayName("2번 회원을 Qsl로 가져오기")
	void test_3() {
		SiteUser u2 = userRepository.getQslUser(2L);
		assertThat(u2.getId()).isEqualTo(2L);
		assertThat(u2.getUsername()).isEqualTo("user2");
		assertThat(u2.getEmail()).isEqualTo("user2@test.com");
		assertThat(u2.getPassword()).isEqualTo("{noop}1234");
	}

	@Test
	@DisplayName("모든 회원 수")
	void test_4() {
		long count = userRepository.getQslCount();

		assertThat(count).isGreaterThan(0);
	}

	@Test
	@DisplayName("가장 오래된 회원 1명")
	void test_5() {
		SiteUser user1 = userRepository.getQslUserOrderIdAscOne(1L);

		assertThat(user1.getId()).isEqualTo(1L);
		assertThat(user1.getUsername()).isEqualTo("user1");
		assertThat(user1.getEmail()).isEqualTo("user1@test.com");
		assertThat(user1.getPassword()).isEqualTo("{noop}1234");

	}

}
