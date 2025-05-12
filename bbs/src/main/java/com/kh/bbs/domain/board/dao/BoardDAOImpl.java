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

  private final NamedParameterJdbcTemplate template;

  private RowMapper<Board> boardRowMapper() {
    return (rs, rowNum) -> {
      Board board = new Board();
      board.setBoardId(rs.getLong("board_id"));
      board.setTitle(rs.getString("title"));
      board.setContent(rs.getString("content"));
      board.setAuthor(rs.getString("author"));
      board.setCreateDate(rs.getTimestamp("created_date"));    // ✅ Timestamp 사용
      board.setModifiedDate(rs.getTimestamp("modified_date")); // ✅ Timestamp 사용
      return board;
    };
  }

  /**
   * 목록
   * @return 목록
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
   * @param board
   * @return 등록
   */
  @Override
  public Long save(Board board) {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO board (board_id, TITLE, CONTENT, AUTHOR ) ");
    sql.append("     VALUES (board_id_seq.nextval, :title , :content , :author ) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(board);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    long rows = template.update(sql.toString(), param, keyHolder, new String[]{"board_id"});
    log.info("rows={}",rows);

    Number pidNumber = (Number)keyHolder.getKeys().get("board_id");
    long bid = pidNumber.longValue();
    return bid;
  }

  /**
   * 조회
   * @param id
   * @return
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
   * @param id 상품번호
   * @return 삭제건수
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
   * @param ids
   * @return
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


}
