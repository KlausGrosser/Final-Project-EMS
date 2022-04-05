package finalproject.services;

import finalproject.models.Employee;
import finalproject.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthUtil {

    private final EmployeeRepository employeeRepository;

    //get logged in user from security context
    public Employee getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication == null || !(authentication).isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        UserDetails loggedInUser;
        try {
            loggedInUser = (UserDetails) principal;
        }
        catch (Exception e){
            return null;
        }

        Optional<Employee> optionalUser = employeeRepository.findByEmail(loggedInUser.getUsername());
        return optionalUser.orElse(null);
    }
}