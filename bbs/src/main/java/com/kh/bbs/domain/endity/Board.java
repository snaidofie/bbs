package com.kh.bbs.domain.endity;

import lombok.*;
import java.sql.Timestamp;

@Data
public class Board {
  private Long boardId;
  private String title;
  private String content;
  private String author;
  private Timestamp createDate;
  private Timestamp modifiedDate; // ✅ camelCase로 수정
}
