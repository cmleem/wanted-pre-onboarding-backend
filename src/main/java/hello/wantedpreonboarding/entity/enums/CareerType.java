package hello.wantedpreonboarding.entity.enums;

import ch.qos.logback.core.boolex.EvaluationException;
import lombok.Getter;

@Getter
public enum CareerType {
    EntryLevel("0"), OneToThreeYears("1~3"), ThreeToFiveYears("3~5"), FiveToSevenYears("5~7"), SevenToNineYears("7~9"), TenPlusYears("10+");

    private final String value;

    CareerType(String value) {
        this.value = value;
    }
}
