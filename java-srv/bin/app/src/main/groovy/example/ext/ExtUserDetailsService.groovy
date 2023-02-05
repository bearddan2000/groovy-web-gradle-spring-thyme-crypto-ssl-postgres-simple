package example.ext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.*;

/**
 * This class exists because Spring default InMemoryUserDetailsManager doesn't return
 * instances of types it's given. It only returns instances of UserDetails which isn't
 * good if you want to work with your own User class.
 *
 * @see org.springframework.security.provisioning.InMemoryUserDetailsManager
 */
public class ExtUserDetailsService implements UserDetailsService {

  @Autowired
  example.repository.UserRepository userRepository;

  private final Map<String, ExtUserDetails> users = new HashMap<>();

  public void addUserList(List<example.model.User> userDetailsList){
    for (example.model.User u : userDetailsList) {
      createUser(u);
    }
  }

  //@Override
  private void createUser(example.model.User user) {
      ExtUserDetails extUserDetails = new ExtUserDetails(user);
      Assert.isTrue(!userExists(user.getUsername()));

      users.put(user.getUsername().toLowerCase(), extUserDetails);
  }

  private boolean userExists(String username) {
      return users.containsKey(username.toLowerCase());
  }

  public UserDetails loadUserByUsername(String username)
              throws UsernameNotFoundException {
      example.model.User user = userRepository.findByUsername(username);

      if (user == null) {
          throw new UsernameNotFoundException("Could not find user");
      }

      return new ExtUserDetails(user);
  }
}
