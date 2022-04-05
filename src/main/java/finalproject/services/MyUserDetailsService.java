package finalproject.services;

import finalproject.models.Employee;
import finalproject.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    //private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        try {
//            AppUser appUser = employeeService.findByEmail(email).orElseThrow();
//
//            boolean enabled = true;
//            boolean accountNonExpired = true;
//            boolean credentialsNonExpired = true;
//            boolean accountNonLocked = true;
//
//
//            List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority(appUser.getRole().getName()));
//
//            return new User(
//                    appUser.getEmail(),
//                    appUser.getPassword(),
//                    enabled,
//                    accountNonExpired,
//                    credentialsNonExpired,
//                    accountNonLocked,
//                    authorities);
//
//        } catch (Exception e) {
//            throw new UsernameNotFoundException(
//                    String.format("No person found with email: %s found", email));
//        }
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //we use email since it ius unique
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("No user with email: " + email));
        return new UserDetailsImpl(employee);
    }
}


