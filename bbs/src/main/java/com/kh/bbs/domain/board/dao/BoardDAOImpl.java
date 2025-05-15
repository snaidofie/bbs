package com.kh.bbs.domain.board.dao;

import com.kh.bbs.domain.endity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
class BoardDAOImpl implements BoardDAO {

   final NamedParameterJdbcTemplate template;

   RowMapper<Board> boardRowMapper() {
    return (rs, rowNum) -> {
      Board board = new Board();
      board.setBoardId(rs.getLong("board_id"));
      board.setTitle(rs.getString("title"));
      board.setContent(rs.getString("content"));
      board.setAuthor(rs.getString("author"));
      board.setCreatedDate(rs.getTimestamp("created_date"));    // ✅ Timestamp 사용
      board.setModifiedDate(rs.getTimestamp("modified_date")); // ✅ Timestamp 사용
      return board;
    };
  }

  /**
   * 게시글 목록
   * @return 게시글 목록
   */
  @Override
  public List<Board> findAll() {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT board_id, title, content, author, created_date, modified_date ");
    sql.append("FROM board ");
    sql.append("ORDER BY board_id DESC");

    List<Board> list = template.query(sql.toString(), boardRowMapper());
    return list;
  }

  /**
   * 등록
   * @param board 등록할 게시글 정보
   * @return 생성된 게시글의 ID
   */
  @Override
  public Long save(Board board) {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO board (board_id, TITLE, CONTENT, AUTHOR ) ");
    sql.append("     VALUES (board_id_seq.nextval, :title , :content , :author ) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(board);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    long rows = template.update(sql.toString(), param, keyHolder, new String[]{"board_id"});

    Number pidNumber = (Number)keyHolder.getKeys().get("board_id");
    long bid = pidNumber.longValue();
    return bid;
  }

  /**
   * 조회
   * @param id 조회할 게시글 ID
   * @return 조회된 게시글
   */
  @Override
  public Optional<Board> findById(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT board_id, title, content, author, created_date, modified_date ");
    sql.append("  FROM board ");
    sql.append(" WHERE board_id = :id ");

    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

    Board board = null;
    try{
      board = template.queryForObject(sql.toString(), param, BeanPropertyRowMapper.newInstance(Board.class));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }

    return Optional.of(board);
  }

  /**
   * 삭제 단건
   * @param id 삭제할 게시글 ID
   * @return 삭제된 건수
   */
  @Override
  public int deleteById(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM board ");
    sql.append("      WHERE board_id = :id ");
    Map<String, Long> param = Map.of("id", id);
    int rows = template.update(sql.toString(), param);
    return rows;
  }

  /**
   * 삭제 여러건
   * @param ids 삭제할 게시글 ID 리스트
   * @return 삭제된 건수
   */
  @Override
  public int deleteByIds(List<Long> ids) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM board ");
    sql.append("      WHERE board_id in ( :ids ) ");
    Map<String, List<Long>> param = Map.of("ids", ids);
    int rows = template.update(sql.toString(), param);
    return rows;
  }

  /**
   * 수정
   * @param boardId 수정할 게시글 ID
   * @param board 수정된 게시글 정보
   * @return 수정된 건수
   */
  @Override
  public int updateById(Long boardId, Board board) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE board ");
    sql.append("SET title = :title, content = :content, author = :author, modified_date = systimestamp ");
    sql.append("WHERE board_id = :boardId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("title", board.getTitle())
        .addValue("content", board.getContent())
        .addValue("author", board.getAuthor())
        .addValue("modified_date", board.getModifiedDate())
        .addValue("boardId", boardId);

    int rows = template.update(sql.toString(), param);
    return rows;
  }


}
