package com.app.springrolejwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import javax.persistence.*;

@Entity(name = "refreshtoken")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(	name = "refreshtoken",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "token")})
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(unique = true)
    private String token;

    private Long refreshCount;

    @Column
    private Instant expiryDate;

    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }

}
