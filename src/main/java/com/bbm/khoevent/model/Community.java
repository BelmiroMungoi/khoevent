package com.bbm.khoevent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String website;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "community")
    private List<Event> events;
}
