package finalproject.services;

import finalproject.models.Role;
import finalproject.models.User;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private EmployeeService employeeService;
    //private ManagerService managerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = employeeService.findByEmail(email);
//            if(user == null) {
//                user = managerService.findByEmail(email);
//            }

            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;


            List<GrantedAuthority> authorities = new ArrayList<>();
            for(Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }

            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    authorities);

        } catch (Exception e) {
            throw new UsernameNotFoundException(
                    String.format("No person found with email: %s found", email));
        }
    }
}
