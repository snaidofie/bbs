package com.kh.bbs.domain.board.dao;

import com.kh.bbs.domain.endity.Board;
import org.assertj.core.api.Assertions;
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

  @Test
  @DisplayName("삭제")
  void deleteById(){
    Long id = 34L;
    int rows = boardDAO.deleteById(id);
    log.info("rows={}",rows);
    Assertions.assertThat(rows).isEqualTo(1);
  }

  @Test
  @DisplayName("상품삭제(여러건)")
  void deleteByIds() {
    List<Long> ids = List.of(35L,45L);
    int rows = boardDAO.deleteByIds(ids);
    Assertions.assertThat(rows).isEqualTo(2);
  }

  @Test
  @DisplayName("상품수정")
  void updateById() {
    Long id = 61L;
    Board product = new Board();
    product.setTitle("코딩을 잘하는 방법");
    product.setAuthor("코딩박사");
    product.setContent("코딩은 이렇게 하는 겁니다 참 쉽죠?");

    int rows = boardDAO.updateById(id, product);

    Optional<Board> optProduct = boardDAO.findById(id);
    // optProduct.orElseThrow() : optional 컨테이너 객체에 product객체가 존재하면 반환 존재하지 않으면 예외발생
    Board modifiedProduct = optProduct.orElseThrow();

    Assertions.assertThat(modifiedProduct.getTitle()).isEqualTo("코딩을 잘하는 방법");
    Assertions.assertThat(modifiedProduct.getAuthor()).isEqualTo("코딩박사");
    Assertions.assertThat(modifiedProduct.getContent()).isEqualTo("코딩은 이렇게 하는 겁니다 참 쉽죠?");
  }
}