package com.app.springrolejwt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String username;

    private Integer heartBeat;

    private String status;

    private ZonedDateTime date;

    private Float latitude;

    private Float longitude;

    private Integer cardiacSteps;

    private Boolean hasData = false;

    private ZonedDateTime updated_at;
}

