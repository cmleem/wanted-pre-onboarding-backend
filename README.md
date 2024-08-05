# wanted-pre-onboarding-backend
프리온보딩 백엔드 인턴십 선발과제

<br>

## 개요
- 채용 공고 서비스 API 서버 구현

## 요구 사항
- 채용 공고를 등록, 조회, 수정, 삭제합니다.
- 채용 공고 목록을 가져옵니다.
- 채용 공고를 검색할 수 있습니다.
- 채용 상세 페이지에는 해당 회사가 올린 다른 채용 공고가 추가적으로 포함됩니다.
- 사용자는 채용 공고에 지원합니다. (사용자는 1회만 지원 가능합니다.)

## 개발 환경
- Java SE 17
- Spring Boot 3.3.2
- MySQL 8.3.0

## 주요 기능
- 채용 공고 상세 조회 시 해당 회사의 채용 공고 리스트 조회 기능
- 키워드로 채용 공고 및 회사 정보 검색 기능
  - 채용 공고 제목, 내용, 직무, 기술 스택, 지역과 회사명, 회사 소개글 등의 키워드로 정보 검색 가능
- 채용 공고 지원 기능
  - 사용자 1명 당 채용 공고에 1회 지원 가능
- 채용 공고 등록, 조회, 수정, 삭제 기능
- 지원 상세 조회 및 지원 목록 조회 기능

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
