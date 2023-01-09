#DB생성
DROP DATABASE IF EXISTS Board;
CREATE DATABASE Board;
USE Board;

#게시물 테이블 생성
CREATE TABLE article(
id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
title CHAR(100) NOT NULL,
BODY TEXT NOT NULL
);

#게시물, 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
BODY = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
BODY = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
BODY = '내용3';

#회원 테이블 생성
CREATE TABLE MEMBER(
id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
loginId CHAR(20) NOT NULL,
loginPw CHAR(60) NOT NULL,
authLevel SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한레벨 (3=일반, 7=관리자)',
NAME CHAR(20) NOT NULL,
nickname CHAR(20) NOT NULL,
cellphoneNo CHAR(20) NOT NULL,
email CHAR(50) NOT NULL,
delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부(0=탈퇴전, 1=탈퇴)',
delDate DATETIME COMMENT '탈퇴날짜'
);

#회원, 테스트 데이터 생성(관리자 회원)
INSERT INTO MEMBER
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
authLevel = 7,
NAME = '관리자',
nickname = '관리자',
cellphoneNo = '01011111111',
email = 'moonkyus@hanmail.net';

#회원, 테스트 데이터 생성(일반 회원)
INSERT INTO MEMBER
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
NAME = 'user1',
nickname = 'user1',
cellphoneNo = '01022222222',
email = 'moonkyus@hanmail.net';

INSERT INTO MEMBER
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = 'user2',
NAME = 'user2',
nickname = 'user2',`article``member`
cellphoneNo = '01033334444',
email = 'moonkyus@hanmail.net';

#게시물 테이블에 회원정보 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

#기존 게시물에 작성자를 2번으로 지정
UPDATE article
SET memberId = 2
WHERE memberId = 0;