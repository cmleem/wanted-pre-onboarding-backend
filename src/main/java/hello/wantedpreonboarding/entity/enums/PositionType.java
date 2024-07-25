package hello.wantedpreonboarding.entity.enums;

import lombok.Getter;

@Getter
public enum PositionType {
    DEVELOPMENT("Development"), MANAGEMENT("Management"), DESIGN("Design"), SALES("Sales"), MARKETING("Marketing"), MEDIA("Media"), ENGINEERING("Engineering"), FINANCE("Finance"), PRODUCTION("Production"), MEDICAL("Medical"), OTHERS("Others");

    private final String value;

    PositionType(String value) {
        this.value = value;
    }
}
