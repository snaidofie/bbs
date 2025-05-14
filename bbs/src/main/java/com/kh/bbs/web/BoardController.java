package com.kh.bbs.web;


import com.kh.bbs.domain.board.svc.BoardSVC;
import com.kh.bbs.domain.endity.Board;
import com.kh.bbs.web.form.DetailForm;
import com.kh.bbs.web.form.SaveForm;
import com.kh.bbs.web.form.UpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

  final private BoardSVC boardSVC;

  //목록화면
  @GetMapping
  public String findAll(Model model) {
    List<Board> list = boardSVC.findAll();
    model.addAttribute("list", list);
    return "board/all";
  }

  //등록화면
  @GetMapping("/add")
  public String addForm() {
    return "board/add";
  }

  //등록처리
  @PostMapping("/add")
  public String add(SaveForm saveForm, RedirectAttributes redirectAttributes) {
    log.info("title={},author={},content={}", saveForm.getTitle(), saveForm.getAuthor(), saveForm.getContent());

    Board board = new Board();
    board.setTitle(saveForm.getTitle());
    board.setAuthor(saveForm.getAuthor());
    board.setContent(saveForm.getContent());

    Long bid = boardSVC.save(board);
    redirectAttributes.addAttribute("id", bid);
    return "redirect:/boards";
  }

  //조회
  @GetMapping("/{id}")
  public String findById(@PathVariable("id") Long id, Model model) {
    Optional<Board> optionalBoard = boardSVC.findById(id);
    Board findedBoard = optionalBoard.orElseThrow();

    DetailForm detailForm = new DetailForm();
    detailForm.setBoardId(findedBoard.getBoardId());
    detailForm.setTitle(findedBoard.getTitle());
    detailForm.setAuthor(findedBoard.getAuthor());
    detailForm.setContent(findedBoard.getContent());
    detailForm.setCreatedDate(findedBoard.getCreatedDate());
    detailForm.setModifiedDate(findedBoard.getModifiedDate());

    model.addAttribute("detailForm", detailForm);
    return "board/detailForm";
  }


  //삭제 단건
  @GetMapping("/{id}/delete")
  public String deleteById(@PathVariable("id") Long boardId) {

    int rows = boardSVC.deleteById(boardId);
    return "redirect:/boards";
  }

  //삭제 여러건
  @PostMapping("/delete")
  public String deleteByIds(@RequestParam("boardIds") List<Long> boardIds) {
    int rows = boardSVC.deleteByIds(boardIds);
    return "redirect:/boards";
  }

  //상품수정화면
  @GetMapping("/{id}/edit")
  public String updateForm(@PathVariable("id") Long boardId, Model model) {
    //1)유효성체크
    //상품조회
    Optional<Board> optionalBoard = boardSVC.findById(boardId);
    Board findedBoard = optionalBoard.orElseThrow();
    UpdateForm updateForm = new UpdateForm();
    updateForm.setBoardId(findedBoard.getBoardId());
    updateForm.setTitle(findedBoard.getTitle());
    updateForm.setAuthor(findedBoard.getAuthor());
    updateForm.setContent(findedBoard.getContent());
    DetailForm detailForm = new DetailForm();
    detailForm.setCreatedDate(findedBoard.getCreatedDate());
    detailForm.setModifiedDate(findedBoard.getModifiedDate());
    model.addAttribute("detailForm",detailForm);
    model.addAttribute("updateForm", updateForm);
    return "board/updateForm";
  }

  //수정처리
  @PostMapping("/{id}/edit")
  public String updateById(
      @PathVariable("id") Long boardId,
      @Valid @ModelAttribute UpdateForm updateForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ){
    Board board = new Board();
    board.setBoardId(updateForm.getBoardId());
    board.setTitle(updateForm.getTitle());
    board.setAuthor(updateForm.getAuthor());
    board.setContent(updateForm.getContent());

    int rows = boardSVC.updateById(boardId, board);

    redirectAttributes.addAttribute("id",boardId);
    return "redirect:/boards";
  }


}
