package com.mycena;

import com.mycena.domain.User;
import com.mycena.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Test
	public void contextLoads() {

		User user = userRepository.findByUserName("admin").get();

		System.out.println(user);

	}

}
