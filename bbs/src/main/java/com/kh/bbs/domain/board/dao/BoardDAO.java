package com.kh.bbs.domain.board.dao;

import com.kh.bbs.domain.endity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardDAO {
  //목록
  List<Board> findAll();

  //등록
  Long save(Board board);

  //조회
  Optional<Board> findById(Long id);

  //삭제 (단건)
  int deleteById(Long id);

  //삭제 (여러건)
  int deleteByIds(List<Long> ids);

  //수정
  int updateById(Long boardId, Board board);
}
