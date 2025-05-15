package com.kh.bbs.domain.endity;

import lombok.*;
import java.sql.Timestamp;

@Data
public class Board {
  private Long boardId; //게시물번호
  private String title; //제목
  private String content; //내용
  private String author; //작성자
  private Timestamp createdDate; //작성일
  private Timestamp modifiedDate; //수정일
}
