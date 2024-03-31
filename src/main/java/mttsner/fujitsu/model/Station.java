package mttsner.fujitsu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String wmocode;

    @Column(nullable = false)
    private String phenomenon;

    @Column(nullable = false)
    private float airtemperature;

    @Column(nullable = false)
    private float windspeed;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant observedAt;
}
