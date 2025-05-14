--아이디, 제목, 내용, 작성자, 작성날짜, 수정날짜
DROP SEQUENCE board_id_seq;
DROP TABLE board;

CREATE TABLE Board (
board_id  NUMBER(10) PRIMARY KEY, --아이디
title varchar2(30) NOT null,			 --제목
content clob NOT null, 					--내용
author varchar2(50) NOT NULL,		 --작성자
created_date timestamp default systimestamp, 		--작성날짜
modified_date timestamp default systimestamp	 	--수정날짜
);

create sequence board_id_seq;

INSERT INTO board (board_id, title, content, author) VALUES (board_id_seq.nextval, '가나아', '어느어느하느하느날아에', '나아가');
INSERT INTO board (board_id, title, content, author) VALUES (board_id_seq.nextval, '가나아', '어느어느하느하느날아에', '나아가');

SELECT * FROM board;

COMMIT;

SELECT board_id, title, content, author, created_date, modified_date
FROM board
ORDER BY board_id desc;

SELECT board_id, title, content, author, created_date, modified_date
FROM board
WHERE board_id = 1;

DELETE FROM board
WHERE board_id = 6;

UPDATE board
SET title = '나다아', content = '가늘가늘머리머리대머리', author = '다기아', modified_date = systimestamp
WHERE board_id = 9;

