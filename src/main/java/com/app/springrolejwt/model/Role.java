package com.akhianand.springrolejwt.model;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Setter(value = AccessLevel.NONE)
    private long id;

    @Column
    private String name;

    @Column
    private String description;
}
