--아이디, 제목, 내용, 작성자, 작성날짜, 수정날짜

--시퀀스삭제
DROP SEQUENCE board_id_seq;

--테이블 삭제
DROP TABLE board;
-------
--코드
-------
CREATE TABLE Board (
board_id  NUMBER(10) PRIMARY KEY,    --번호
title varchar2(30) NOT null,         --제목
content clob NOT null, 		         --내용
author varchar2(30) NOT NULL,		 --작성자
created_date timestamp default systimestamp,    --작성날짜
modified_date timestamp default systimestamp	--수정날짜
);

--시퀀스 생생
create sequence board_id_seq;

--샘플데이터 of board
INSERT INTO board (board_id, title, content, author) VALUES (board_id_seq.nextval, '가나아', '어느어느하느하느날아에', '나아가');
INSERT INTO board (board_id, title, content, author) VALUES (board_id_seq.nextval, '가나아', '어느어느하느하느날아에', '나아가');

SELECT * FROM board;

COMMIT;