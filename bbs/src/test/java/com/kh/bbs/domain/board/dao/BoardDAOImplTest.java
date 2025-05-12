package com.kh.bbs.domain.board.dao;

import com.kh.bbs.domain.endity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class BoardDAOImplTest {

  @Autowired
  BoardDAO boardDAO;

  @Test
  @DisplayName("등록")
  void save() {
    Board board = new Board();
    board.setTitle("테스트제목");
    board.setAuthor("작가");
    board.setContent("내용");

    Long bid = boardDAO.save(board);
    log.info("번호={}", bid);
  }

  @Test
  @DisplayName("목록")
  void findAll() {
    List<Board> list = boardDAO.findAll();
    for (Board board : list) {
      log.info("board={}", board);
    }
  }

  @Test
  @DisplayName("조회")
  void findById() {
    Long boardId = 1L;
    Optional<Board> optionalBoard = boardDAO.findById(boardId);
    Board findedBoard = optionalBoard.orElseThrow();
    log.info("findedBoard={}", findedBoard);
  }
}