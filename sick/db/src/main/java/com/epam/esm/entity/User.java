package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", unique = true, length = 30)
    private String username;
    @Column(name = "password", length = 60)
    private String password;
    @Column(name = "role", updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;
    @Column(name = "create_date", updatable = false)
    private LocalDate createDate;
    @Column(name = "update_date")
    private LocalDate updateDate;
    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    public User() {
    }

    @PrePersist
    private void onCreate() {
        createDate = LocalDate.now();
        updateDate = LocalDate.now();
        role = Role.ROLE_USER;
    }

    @PreUpdate
    private void onUpdate() {
        updateDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                getRole() == user.getRole() &&
                Objects.equals(getCreateDate(), user.getCreateDate()) &&
                Objects.equals(getUpdateDate(), user.getUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(getId(), getUsername(), getPassword(), getRole(), getCreateDate(), getUpdateDate());
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
