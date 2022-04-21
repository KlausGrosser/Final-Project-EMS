package com.finalproject.model.entity;


import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "userIdSeq", sequenceName = "users_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "first_login", nullable = false)
    private boolean firstLogin;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    @ToString.Exclude
    private List<Activity> activities;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "leave_id")
    )
    @ToString.Exclude
    private List<Leave> leaves;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @ToString.Exclude
    private List<ActivityRequest> activityRequests;

    @OneToMany
    @ToString.Exclude
    private List<Shift> shifts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "current_shift_id")
    private Shift currentShift;

    private String department;

    private String address;

    private int telephone;

    @OneToMany
    @ToString.Exclude
    private List<Absence> absences;

    private boolean supervisorRole;

    private String supervisorName;

    private String company;

    public User(Long id,
                String firstName,
                String lastName,
                String username,
                String password,
                boolean enabled,
                boolean firstLogin,
                Set<Authority> authorities,
                Department department,
                String address,
                int telephone,
                boolean supervisorRole,
                String supervisorName,
                Company company) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.firstLogin = firstLogin;
        this.authorities = authorities;
        this.department = department.name();
        this.address = address;
        this.telephone = telephone;
        this.supervisorRole = supervisorRole;
        this.supervisorName = supervisorName;
        this.fullName = firstName + " " + lastName;
        this.company = company.getName();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
