package example.controller;

import example.model.*;
import example.repository.*;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Controller
public class DatabaseSeedController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PostConstruct
  public void init() {

    for ( User user : createUsers()) {
      seedUsers(user);
    }
  }

  private List<User> createUsers() {
    final String psw = passwordEncoder.encode("pass");

    List<User> list = new ArrayList<>();

    list.add(new User("admin", psw, example.model.ERole.ADMIN));
    list.add(new User("user", psw, example.model.ERole.USER));
    list.add(new User("super", psw, example.model.ERole.SUPER));

    return list;
  }

  private void seedUsers(User user) {
    System.out.println("[INFO] Seeding user: " + user.toString());
    userRepository.save(user);
  }
}
