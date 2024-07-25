package hello.wantedpreonboarding.enums;

import lombok.Getter;

@Getter
public enum RegionType {
    SEOUL("Seoul"), BUSAN("Busan"), DAEGU("Daegu"), INCHEON("Incheon"), GWANGJU("Gwangju"), DAEJEON("Daejeon"), ULSAN("Ulsan"), SEJONG("Sejong"), GYEONGI("Gyeongi"), GANGWON("Gangwon"), CHOONGBUK("Choong-Buk"), CHOONGNAM("Choong-Nam"), JEONBUK("Jeon-Buk"), JEONNAM("Jeon-Nam"), GYEONGBUK("Gyeong-Buk"), GYEONGNAM("Gyeong-Nam"), JEJU("Jeju");

    private final String value;

    RegionType(String value) {
        this.value = value;
    }
}
