package finalproject.models;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public abstract class User {
  //Class SimpleGrantedAuthority stores a String representation of an authority granted to the Authentication object.
  public abstract Collection<? extends GrantedAuthority> getAuthorities();

}
