package example.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
  example.model.User findByUsername(String username);
}
