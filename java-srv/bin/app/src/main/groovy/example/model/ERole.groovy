package example.model;

public enum ERole {
  USER("USER", "ROLE_USER", "/user")
  , ADMIN("ADMIN", "ROLE_ADMIN", "/admin")
  , SUPER("SUPER", "ROLE_SUPER", "/super");

  public final String label;
  public final String formalLabel;
  public final String url;

  ERole(String...args){
    label = args[0];
    formalLabel = args[1];
    url = args[2];
  }

  @Override
  public String toString(){
    return String.format("\n\t[SECURITY ROLE] label=%s, formalLabel=%s, url=%s", label, formalLabel, url);
  }
}
