package dhbw.server;

import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private Nutzer nutzer;

    public CustomUserDetails(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = nutzer.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole_name()));
        }

        return authorities;
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
}
