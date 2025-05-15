package com.kh.bbs.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateForm {
  private Long boardId;

  /**
   * 게시글 제목
   * - 비어 있지 않아야 하며, 1자 이상 30자 이하로 입력해야 합니다.
   */
  @NotBlank(message = "제목은 필수 입니다.") // 제목은 필수 입력
  @Size(min=1,max = 10,message = "타이틀은 10자를 초과할 수 없습니다.") // 제목 길이 제한
  private String title;

  /**
   * 게시글 내용
   * - 비어 있지 않아야 하며, 1자 이상 4000자 이하로 입력해야 합니다.
   */
  @NotBlank(message = "내용은 필수 입니다.") // 내용은 필수 입력
  @Size(min=1,max = 4000,message = "내용은 4000자를 초과할 수 없습니다.")  // 내용 길이 제한
  private String content;

  /**
   * 게시글 작성자
   * - 비어 있지 않아야 하며, 1자 이상 50자 이하로 입력해야 합니다.
   */
  @NotBlank(message = "작성자명은 필수 입니다.") // 작성자는 필수 입력
  @Size(min=1,max = 10,message = "작성자명은 10자를 초과할 수 없습니다.") // 작성자 이름 길이 제한
  private String author;
}
