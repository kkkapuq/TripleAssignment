# TripleAssignment
트리플 백엔드 과제 실행방법

## 1. 테스트 환경 설정

### 🛠️ 개발환경

- java 8
- Springboot 2.7.1
- Maven
- Mysql 5.7
- Mybatis 2.1.4
- Docker
- InteliJ

### 1. Maven 패키징

```bash
# mvn clean package -DskipTests
```

### 2. Docker 설정

```bash
-- 로컬에서 돌리기 위한 네트워크 생성
# docker network create springboot-mysql-net
-- 생성 확인
# docker network ls

-- mysql 이미지 가져오기
# docker pull mysql:5.7

-- mysql 백그라운드 컨테이너 실행
# docker run --name tempmysql -p 3306:3306 --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=triple1234 -e MYSQL_DATABASE=mydb -e MYSQL_USER=user -e MYSQL_PASSWORD=user -d mysql:5.7
```

### 3. DB 설정

```sql
-- DB 생성
CREATE DATABASE triple default CHARACTER SET UTF8;
```

이후는 편의상 DBeaver로 연동하여 작업했습니다. DBeaver 연동 방법은 이전에 블로그에 포스팅한 글로 대체하도록 하겠습니다.

[[DBeaver] 설치하기 및 DB연결](https://kkkapuq.tistory.com/77)

**ERD**

![Untitled](https://user-images.githubusercontent.com/44130863/177029898-0d96f1cb-9427-4dd4-bc1c-80a5ea891dfc.png)

**테이블 생성**

```sql
CREATE TABLE `TUSERPOINTHIS` (
	`USER_ID`	VARCHAR(40)	NOT NULL	COMMENT '유저ID',
	`POINT_SEQ`	VARCHAR(8)	NOT NULL,
	`POINT`	INT(10)	NOT NULL	DEFAULT 0	COMMENT '포인트',
	`REVIEW_ID`	VARCHAR(40)	NOT NULL	COMMENT '리뷰ID',
	`REVIEW_TYPE`	VARCHAR(2)	NOT NULL	COMMENT '10 : 리뷰 작성 포인트 지급
20 : 리뷰 사진 작성 포인트 지급
30 : 보너스 포인트 지급
40 : 포인트 감소',
	`INSERT_ID`	VARCHAR(10)	NOT NULL	COMMENT '등록자ID',
	`INSERT_DATE`	DATETIME	NOT NULL	COMMENT '등록일시',
	`MODIFY_ID`	VARCHAR(10)	NOT NULL	COMMENT '수정자ID',
	`MODIFY_DATE`	DATETIME	NOT NULL	COMMENT '수정일시'
);

ALTER TABLE `TUSERPOINTHIS` ADD CONSTRAINT `PK_TUSERPOINTHIS` PRIMARY KEY (
	`USER_ID`,
	`POINT_SEQ`
);

CREATE TABLE `TPLACEREVIEW` (
	`USER_ID`	VARCHAR(40)	NOT NULL	COMMENT '유저ID',
	`PLACE_ID`	VARCHAR(40)	NOT NULL	COMMENT '장소ID',
	`REVIEW_ID`	VARCHAR(40)	NOT NULL	COMMENT '리뷰ID',
	`CONTENT`	TEXT(1000)	NOT NULL	COMMENT '리뷰내용',
	`DISPLAY_YN`	VARCHAR(1)	NOT NULL	DEFAULT 0	COMMENT '1 : 전시상태
0 : 미전시상태(삭제 등 사유)',
	`INSERT_ID`	VARCHAR(10)	NOT NULL	COMMENT '등록자ID',
	`INSERT_DATE`	DATETIME	NOT NULL	COMMENT '등록일시',
	`MODIFY_ID`	VARCHAR(10)	NOT NULL	COMMENT '수정자ID',
	`MODIFY_DATE`	DATETIME	NOT NULL	COMMENT '수정일시'
);

ALTER TABLE `TPLACEREVIEW` ADD CONSTRAINT `PK_TPLACEREVIEW` PRIMARY KEY (
	`USER_ID`,
	`PLACE_ID`,
	`REVIEW_ID`
);

CREATE TABLE `TPLACEREVIEWPHOTO` (
	`USER_ID`	VARCHAR(40)	NOT NULL	COMMENT '유저ID',
	`PLACE_ID`	VARCHAR(40)	NOT NULL	COMMENT '장소ID',
	`PHOTO_ID`	VARCHAR(40)	NOT NULL	COMMENT '사진ID',
	`USE_YN`	VARCHAR(1)	NOT NULL	DEFAULT 0	COMMENT '1 : 사용
0 : 미사용',
	`INSERT_ID`	VARCHAR(10)	NOT NULL	COMMENT '등록자ID',
	`INSERT_DATE`	DATETIME	NOT NULL	COMMENT '등록일시',
	`MODIFY_ID`	VARCHAR(10)	NOT NULL	COMMENT '수정자ID',
	`MODIFY_DATE`	DATETIME	NOT NULL	COMMENT '수정일시'
);

ALTER TABLE `TPLACEREVIEWPHOTO` ADD CONSTRAINT `PK_TPLACEREVIEWPHOTO` PRIMARY KEY (
	`USER_ID`,
	`PLACE_ID`,
	`PHOTO_ID`
);

-- 인덱스 생성
CREATE INDEX USER_ID_IDX ON TUSERPOINTHIS(USER_ID);
CREATE INDEX USER_ID_IDX ON TPLACEREVIEW(USER_ID);
CREATE INDEX USER_ID_IDX ON TPLACEREVIEWPHOTO(USER_ID);
```

## 2. 테스트 실행방법

**프로젝트 구조**

![Untitled (1)](https://user-images.githubusercontent.com/44130863/177029892-e9fb7a09-7025-4c79-b2a4-10ceee1c2ceb.png)

**로컬에서 실행**

![Untitled (2)](https://user-images.githubusercontent.com/44130863/177029894-e983dd23-a082-4f70-b9ec-b32742130bf0.png)

**TEST URL**

포인트 조회

```sql
http://localhost:8080/mypage/point-list?userId=3ede0ef2-bbbb-4817-a5f3-0c575361f745
```

![Untitled (3)](https://user-images.githubusercontent.com/44130863/177029896-724649ec-0de0-4a26-b28f-a73b82aef5ad.png)

리뷰 이벤트

```sql
http://localhost:8080/mypage/events
```

![Untitled (4)](https://user-images.githubusercontent.com/44130863/177029899-93a671a0-358a-439d-9f21-da1826723763.png)

이후 테스트 진행은 첨부된 테스트 케이스 엑셀파일 참고 부탁드립니다.
