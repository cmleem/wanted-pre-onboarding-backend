package hello.wantedpreonboarding.entity;

import hello.wantedpreonboarding.enums.CareerType;
import hello.wantedpreonboarding.enums.PositionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private PositionType category;

    @Enumerated(EnumType.STRING)
    private CareerType career;

    @Column(nullable = false)
    private Boolean exposed;
}
