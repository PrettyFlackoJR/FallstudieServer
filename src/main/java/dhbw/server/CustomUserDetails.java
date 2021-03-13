package dhbw.server;

import dhbw.server.entities.Nutzer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private Nutzer nutzer;

    public CustomUserDetails(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return nutzer.getNut_passwort();
    }

    @Override
    public String getUsername() {
        return nutzer.getNut_email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        return nutzer.getNut_vorname() + " " + nutzer.getNut_nachname();
    }
}
