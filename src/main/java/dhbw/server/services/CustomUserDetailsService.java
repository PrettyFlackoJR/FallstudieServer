package dhbw.server.services;

import dhbw.server.CustomUserDetails;
import dhbw.server.entities.Nutzer;
import dhbw.server.repositories.NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NutzerRepository nutzerRepository;

    /**
     * Wird für Login mit Spring Security benötigt.
     * Hier wird überprüft, ob der angegebene User existiert.
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Nutzer nutzer = nutzerRepository.findByEmail(email);
        if (nutzer == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(nutzer);
    }
}
