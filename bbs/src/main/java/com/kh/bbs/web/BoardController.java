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
  public String addForm(Model model) {
    model.addAttribute("saveForm", new SaveForm());
    return "board/addForm";
  }

  //등록처리
  @PostMapping("/add")
  public String add(
      @Valid @ModelAttribute SaveForm saveForm,
      RedirectAttributes redirectAttributes,
      BindingResult bindingResult,
      Model model
  ){
    log.info("title={},author={},content={}", saveForm.getTitle(), saveForm.getAuthor(), saveForm.getContent());

    //유효성 체크
    if (bindingResult.hasErrors()){
      return "board/addForm";
    }

    Board board = new Board();
    board.setTitle(saveForm.getTitle());
    board.setAuthor(saveForm.getAuthor());
    board.setContent(saveForm.getContent());

    Long bid = boardSVC.save(board);
    redirectAttributes.addAttribute("id", bid);
    return "redirect:/boards/{id}";
  }

  //게시글 상세 조회
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


  //단건 삭제
  @GetMapping("/{id}/del")
  public String deleteById(@PathVariable("id") Long boardId) {

    int rows = boardSVC.deleteById(boardId);
    return "redirect:/boards";
  }

  //여러건 삭제
  @PostMapping("/del")
  public String deleteByIds(@RequestParam("boardIds") List<Long> boardIds) {
    int rows = boardSVC.deleteByIds(boardIds);
    return "redirect:/boards";
  }

  //게시글 수정 화면
  @GetMapping("/{id}/edit")
  public String updateForm(@PathVariable("id") Long boardId, Model model) {
    //유효성체크
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

  //수정 처리
  @PostMapping("/{id}/edit")
  public String updateById(
      @PathVariable("id") Long boardId,
      @Valid @ModelAttribute UpdateForm updateForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ){
    if (bindingResult.hasErrors()) {
      return "board/updateForm";
    }

    Board board = new Board();
    board.setBoardId(updateForm.getBoardId());
    board.setTitle(updateForm.getTitle());
    board.setAuthor(updateForm.getAuthor());
    board.setContent(updateForm.getContent());

    int rows = boardSVC.updateById(boardId, board);

    redirectAttributes.addAttribute("id",boardId);
    return "redirect:/boards/{id}";
  }


}
