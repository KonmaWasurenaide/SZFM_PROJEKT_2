package com.ggames.GGames.Data.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entitás az alkalmazás felhasználóinak reprezentálására.
 *
 * <p>Implementálja a Spring Security {@code UserDetails} interfészét, ezzel biztosítva
 * a hitelesítési és jogosultságkezelési funkciókat.</p>
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A felhasználó bejelentkezési neve (kötelező, egyedi).
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * A felhasználó email címe (kötelező, egyedi).
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * A felhasználó jelszava (hash-elve tárolva, kötelező).
     */
    @Column(nullable = false)
    private String password;

    /**
     * A felhasználó jogosultsági szerepköre (alapértelmezett: "USER").
     */
    @Column(name = "user_role")
    private String userRole = "USER";


    /**
     * Visszaadja a felhasználó jogosultságait.
     * @return A szerepkört tartalmazó {@code GrantedAuthority} lista, "ROLE_" prefixszel ellátva.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.userRole));
    }

    /**
     * Visszaadja a felhasználó bejelentkezési nevét.
     * @return A felhasználónév.
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Visszaadja a felhasználó jelszavát.
     * @return A jelszó.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Jelzi, hogy a felhasználói fiók nem járt-e le.
     * @return Mindig {@code true}.
     */
    @Override
    public boolean isAccountNonExpired() { return true; }

    /**
     * Jelzi, hogy a felhasználói fiók nincs-e zárolva.
     * @return Mindig {@code true}.
     */
    @Override
    public boolean isAccountNonLocked() { return true; }

    /**
     * Jelzi, hogy a felhasználó hitelesítő adatai nem jártak-e le.
     * @return Mindig {@code true}.
     */
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    /**
     * Jelzi, hogy a felhasználó engedélyezve van-e.
     * @return Mindig {@code true}.
     */
    @Override
    public boolean isEnabled() { return true; }

    /**
     * A felhasználó által birtokolt játékok listája (könyvtár).
     * Kapcsolat: Egy felhasználó több letöltéssel rendelkezhet (One-to-Many).
     */
    @OneToMany(mappedBy = "user")
    private List<DownloadEntity> downloadedEntitiys = new ArrayList<>();
}