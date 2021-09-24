package com.app.springrolejwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(	name = "health",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "uuid")
        })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String status;

    private ZonedDateTime date;

    private Float latitute;

    private Float longitude;

    private Integer cardiacSteps;
}

