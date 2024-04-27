package de.michelblank.songtagebuch.database.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"identityProvider", "idpId"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String identityProvider;
    @Column(nullable = false)
    private String idpId;
    @Column(nullable = false)
    private String name;
}
