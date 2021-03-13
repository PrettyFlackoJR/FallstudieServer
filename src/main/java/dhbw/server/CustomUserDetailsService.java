package dhbw.server;

import dhbw.server.entities.Nutzer;
import dhbw.server.repositories.NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NutzerRepository nutzerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Nutzer nutzer = nutzerRepository.findByEmail(email);
        if (nutzer == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(nutzer);
    }
}
