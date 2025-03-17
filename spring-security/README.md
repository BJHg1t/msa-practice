# Spring Security + OAuth2

## 목차

#### 1. 주요 기능
#### 2. 구조
#### 3. 참고자료

------------------------
### 주요 기능
* 회원 가입
  * 회원 가입 시 기본적으로 __ROLE_USER__ 역할을 부여받는다.
  * 게시글 상세 조회에서 **인가 기능 확인**을 위해 관리자 가입도 임시로 가능하다.
* **JWT 토큰 방식**을 이용한 로그인 + __OAuth2__ 방식의 소셜 로그인(Google)
  * 비로그인 유저 또는 토큰의 기한이 만료된 유저는 로그인 페이지로 이동한다. 
  * Google 로그인했을 때 기본적으로 __ROLE_USER__ 역할을 부여받는다.
* 게시글 상세 조회
  * **관리자**만 게시글 목록 조회가 가능하다.
  * 페이징
    * 이전 페이지로 이동하는 버튼이 1페이지 일 때 비활성화
    * 다음 페이지로 이동하는 버튼이 마지막 페이지일 때 비활성화
* 게시글 작성, 수정, 삭제
  * 본인이 작성한 글이 아닐 경우 수정 및 삭제가 불가능하다.
* 파일 업로드, 다운로드
* 로그아웃 했을 때 

### 1. 회원가입

![회원가입 이미지](https://github.com/BJHg1t/msa-practice/blob/master/spring-security/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.png)

### 2. 로그인

![로그인 이미지](https://github.com/BJHg1t/msa-practice/blob/master/spring-security/%EB%A1%9C%EA%B7%B8%EC%9D%B8.png)

### 3. 게시글 조회 : 관리자 역할이 부여된 계정으로 로그인했을 때 조회 가능

![게시글 조회1 이미지](https://github.com/BJHg1t/msa-practice/blob/master/spring-security/%EC%9C%A0%EC%A0%80.png)
![게시글 조회2 이미지](https://github.com/BJHg1t/msa-practice/blob/master/spring-security/%EA%B4%80%EB%A6%AC%EC%9E%90.png)

### 4. 게시글 상세 페이지 : 본인 글이 아니면 수정, 삭제 불가능, 파일 다운로드 가능

![게시글 상세 페이지 이미지](https://github.com/BJHg1t/msa-practice/blob/master/spring-security/%EB%B3%B8%EC%9D%B8%EA%B8%80.png)

### 5.게시글 작성 : 파일 업로드 가능

![게시글 작성 이미지](https://github.com/BJHg1t/msa-practice/blob/master/spring-security/%EC%9E%91%EC%84%B1.png)

### 6.로그아웃 요청 시 토큰을 제거하고 로그인 페이지로 이동

![로그아웃 이미지](https://github.com/BJHg1t/msa-practice/blob/master/spring-security/%EB%A1%9C%EA%B7%B8%EC%95%84%EC%9B%83.png)
-------------------------
### 구조
#### 로그인

![로그인 구조 이미지](https://github.com/BJHg1t/msa-practice/blob/master/spring-security/%EA%B5%AC%EC%A1%B0.png)

#### 게시글

![게시글 구조 이미지](https://github.com/BJHg1t/msa-practice/blob/master/spring-security/%EA%B2%8C%EC%8B%9C%EA%B8%80%20%EA%B5%AC%EC%A1%B0.png)
-------------------------
### 참고 자료
#### Spring Security
* [Spring Security + JWT 토큰을 이용한 로그인 및 게시글 CRUD](https://github.com/BJHg1t/msa-practice/tree/master/spring-security)

#### OAuth2
* [Spring Security + OAuth2을 사용하여 구글 로그인 만들기](https://velog.io/@99mon/Spring-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-%EA%B5%AC%EA%B8%80-%EB%A1%9C%EA%B7%B8%EC%9D%B8)
* [OAuth2 동작 원리](https://velog.io/@yuureru/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-OAuth2.0-JWT%EB%A1%9C-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0#%EB%8F%99%EC%9E%91-%EC%9B%90%EB%A6%AC-%ED%94%84%EB%A1%A0%ED%8A%B8--%EB%B0%B1-%EC%B1%85%EC%9E%84-%EB%B6%84%EB%B0%B0)
