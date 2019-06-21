package com.artcoversapp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@NoArgsConstructor

@Entity
@Table(name = "aroles")
public class AccountRole {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String role;

    public AccountRole(String role) {
        this.role = role;
    }
}
