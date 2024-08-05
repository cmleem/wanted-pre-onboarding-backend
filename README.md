# wanted-pre-onboarding-backend
프리온보딩 백엔드 인턴십 선발과제

## 서비스 개요
- 본 서비스는 기업의 채용을 위한 웹 서비스 입니다.
- 회사는 채용공고를 생성하고, 이에 사용자는 지원합니다.

## 요구 사항
- 채용 공고를 등록, 조회, 수정, 삭제합니다.
- 채용 공고 목록을 가져옵니다.
- 채용공고를 검색할 수 있습니다.
- 채용 상세 페이지에는 해당 회사가 올린 다른 채용공고 가 추가적으로 포함됩니다.
- 사용자는 채용공고에 지원합니다. (사용자는 1회만 지원 가능합니다.)

## 개발 환경
- Java SE 17
- Spring Boot 3.3.2
- MySQL 8.3.0

## 디렉토리 구조
```
├─.github
├─.gradle
├─gradle
│  └─wrapper
└─src
    ├─main
    │  ├─java
    │  │  └─hello
    │  │      └─wantedpreonboarding
    │  │          ├─controller
    │  │          ├─dto
    │  │          │  ├─request
    │  │          │  └─response
    │  │          ├─entity
    │  │          │  └─enums
    │  │          ├─mapper
    │  │          ├─repository
    │  │          └─service
    │  └─resources
    │      ├─static
    │      └─templates
    └─test
        ├─java
        │  └─hello
        │      └─wantedpreonboarding
        │          ├─controller
        │          ├─repository
        │          └─service
        └─resources
```
