package example.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="user_id")
  Long id;

  String username;
  String password;

  ERole securityRole;

  boolean enabled;
  Date lastLogin;

  public User() {}

  public User(String username, String password, ERole role) {

      this.username = username;
      this.password = password;
      this.securityRole = role;
      this.enabled = true;
      this.lastLogin = new Date();
  }

  public void setId(Long id) {
      this.id = id;
  }

  public Long getId() {
      return id;
  }

  public void setUsername(String username) {
      this.username = username;
  }

  public String getUsername() {
      return username;
  }

  public void setPassword(String password) {
      this.password = password;
  }

  public String getPassword() {
      return password;
  }

  public void setEnabled(boolean value) {
      this.enabled = value;
  }

  public boolean getEnabled() {
      return enabled;
  }

  public void setLastLogin(Date date) {
      this.lastLogin = date;
  }

  public Date getLastLogin() {
      return lastLogin;
  }

  public void setSecurityRole(ERole role) {
      this.securityRole = role;
  }

  public ERole getSecurityRole() {
      return securityRole;
  }

  @Override
  public String toString(){
    return String.format("username=%s, %s", username, securityRole.toString());
  }
}
