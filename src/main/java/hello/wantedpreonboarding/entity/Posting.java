package hello.wantedpreonboarding.entity;

import hello.wantedpreonboarding.entity.enums.PositionType;
import hello.wantedpreonboarding.entity.enums.RegionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Builder
@AllArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private PositionType position;

    @Column(length = 255)
    private Integer incentive;

    private LocalDateTime deadLine;

    @Column(length = 255)
    private String stack;

    @Enumerated(EnumType.STRING)
    private RegionType region;

    @ManyToOne
    private Company company;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updatePosition(PositionType position) {
        this.position = position;
    }

    public void updateIncentive(Integer incentive) {
        this.incentive = incentive;
    }

    public void updateDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public void updateStack(String stack) {
        this.stack = stack;
    }

    public void updateRegion(RegionType region) {
        this.region = region;
    }
}
