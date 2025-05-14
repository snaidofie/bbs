package com.kh.bbs.web.form;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateForm {
  private Long boardId;
  private String title;
  private String content;
  private String author;
}
