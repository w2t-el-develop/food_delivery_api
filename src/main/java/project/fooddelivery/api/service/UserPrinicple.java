package project.fooddelivery.api.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import project.fooddelivery.api.customer.entity.User;

public class UserPrinicple implements UserDetails {
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrinicple(User user) {
        this.user = user;
        // e.g. CUSTOMER -> ROLE_CUSTOMER, RESTAURANT -> ROLE_RESTAURANT, so hasRole("CUSTOMER") works
        String role = "ROLE_" + user.getUserType().getUserTypeName().toUpperCase(Locale.ROOT);
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return authorities;
    }

    @Override
    public String getPassword() {
       return user.getUserPassword();
    }

    @Override
    public String getUsername() {
       return user.getPhoneNumber();
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
