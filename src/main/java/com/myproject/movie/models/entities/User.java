package com.myproject.movie.models.entities;

import com.myproject.movie.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String phone;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime createdAt = LocalDateTime.now();
//    private LocalDateTime lastLogin;
    private boolean isActive = true;

//    @OneToMany(mappedBy = "user")
//    private List<Booking> bookings;
//
//    @OneToMany(mappedBy = "user")
//    private List<Review> reviews;
//
//    @OneToMany(mappedBy = "user")
//    private List<Payment> payments;
//
//    @OneToMany(mappedBy = "user")
//    private List<UserPoint> userPoints;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public String getUsername() {
        return email;
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
