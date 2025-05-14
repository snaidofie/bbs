package com.kh.bbs.domain.board.svc;


import com.kh.bbs.domain.board.dao.BoardDAO;
import com.kh.bbs.domain.endity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardSVCImpl implements BoardSVC{

  private final BoardDAO boardDAO;


  //등록
  @Override
  public List<Board> findAll() {
    return boardDAO.findAll();
  }

  //목록
  @Override
  public Long save(Board board) {
    return boardDAO.save(board);
  }

  //조회
  @Override
  public Optional<Board> findById(Long id) {
    return boardDAO.findById(id);
  }

  //삭제 단건
  @Override
  public int deleteById(Long id) {
    return boardDAO.deleteById(id);
  }

  //삭제 여러건
  @Override
  public int deleteByIds(List<Long> ids) {
    return boardDAO.deleteByIds(ids);
  }

  //수정
  @Override
  public int updateById(Long boardId, Board board) {return boardDAO.updateById(boardId, board);}
}
