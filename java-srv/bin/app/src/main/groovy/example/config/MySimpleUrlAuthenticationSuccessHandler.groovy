package example.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
          Authentication authentication) throws IOException {
      // run custom logics upon successful login

      if (request == null) {
        System.out.println("[ERROR] request is null");
        return;
      } else if (response == null) {
        System.out.println("[ERROR] response is null");
        return ;
      } else if (authentication == null) {
        System.out.println("[ERREOR] authentication is null");
        return ;
      }
      handle(request, response, authentication);
      clearAuthenticationAttributes(request);
  }

  private void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
      final String targetUrl = determineTargetUrl(authentication);


      RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

      if (response.isCommitted()) {
          System.out.println("[ERROR] response has already been committed. Unable to redirect to " + targetUrl);
          return;
      }
System.out.println("[INFO] redirect to " + targetUrl);
      redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  private String determineTargetUrl(final Authentication authentication) {
    if (authentication == null) {
      System.out.println("[ERROR] authentication is null");
      return "/";
    }
      Map<String, String> roleTargetUrlMap = new HashMap<>();
      for (example.model.ERole role : example.model.ERole.values()) {
        roleTargetUrlMap.put(role.formalLabel, role.url);  
      }

      final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
      for (final GrantedAuthority grantedAuthority : authorities) {

          String authorityName = grantedAuthority.getAuthority();
          if(roleTargetUrlMap.containsKey(authorityName)) {
            System.out.println("[INFO] role/redirect found");
              return roleTargetUrlMap.get(authorityName);
          }
      }
System.out.println("[ERROR] role not found");
      return "/";
  }

  /**
   * Removes temporary authentication-related data which may have been stored in the session
   * during the authentication process.
   */
  private final void clearAuthenticationAttributes(final HttpServletRequest request) {

      final HttpSession session = request.getSession(false);

      if (session == null) {
        System.out.println("[ERROR] session is null");
          return;
      }

      session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }
}
