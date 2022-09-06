package com.ll.exam.qsl;

import com.ll.exam.qsl.user.entity.SiteUser;
import com.ll.exam.qsl.user.entity.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("회원 생성")
	void test_1() {
		SiteUser user1 = SiteUser.builder()
				.username("user1")
				.password("{noop}1234")
				.email("user1@test.com")
				.build();

		// 빌더를 쓰는 경우 인자 순서가 달라도 상관 없다.
		SiteUser user2 = SiteUser.builder()
				.username("user2")
				.password("{noop}1234")
				.email("user2@test.com")
				.build();

		userRepository.saveAll(Arrays.asList(user1, user2));
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

}
