package hello.wantedpreonboarding.entity;

import hello.wantedpreonboarding.enums.RegionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.plaf.synth.Region;

@NoArgsConstructor
@Entity
@Getter
@Builder
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 30)
    private RegionType region;

    @Column(nullable = false, length = 255)
    private String industry;

    @Column(nullable = false)
    private Integer tenure;
}
