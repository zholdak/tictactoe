package com.zholdak.testing.tictactoe.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.zholdak.testing.tictactoe.model.Move;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.Collections.singletonMap;
import static org.springframework.util.StreamUtils.copyToString;

/**
 * IMHO: I think no need to extract an interface because only one implementation exist.
 *
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 18:35
 */
@Repository
public class MoveDao {

	private Logger logger = LoggerFactory.getLogger(MatchDao.class);

	private NamedParameterJdbcTemplate jdbcTemplate;

	private String moveSql;
	private String allMatchMovesSql;
	private String insertMoveSql;

	public MoveDao(
			NamedParameterJdbcTemplate jdbcTemplate,
			@Value("classpath:sql/get_move_by_id.sql") Resource moveSqlRes,
			@Value("classpath:sql/get_all_match_moves.sql") Resource allMatchMovesSqlRes,
			@Value("classpath:sql/insert_move.sql") Resource insertMoveSqlRes) throws IOException {

		this.jdbcTemplate = jdbcTemplate;
		this.moveSql = copyToString(moveSqlRes.getInputStream(), defaultCharset());
		this.allMatchMovesSql = copyToString(allMatchMovesSqlRes.getInputStream(), defaultCharset());
		this.insertMoveSql = copyToString(insertMoveSqlRes.getInputStream(), defaultCharset());
	}

	/**
	 * Get move by it's identifier
	 */
	public Move getById(int moveId) {
		try {
			return jdbcTemplate.queryForObject(moveSql, singletonMap("moveId", moveId), new Mapper());
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	/**
	 * Get all moves list of the match
	 */
	public List<Move> getByMatchId(int matchId) {
		return jdbcTemplate.query(allMatchMovesSql, singletonMap("matchId", matchId), new Mapper());
	}

	/**
	 * Insert new move and return it's identifier
	 *
	 * @return Identifier of the newly inserted move
	 */
	public int insertMove(Move move) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("matchId", move.getMatchId());
		namedParameters.addValue("opponent", move.getOpponent());
		namedParameters.addValue("position", move.getPosition());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(insertMoveSql, namedParameters, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * Move mapper
	 */
	private static final class Mapper implements RowMapper<Move> {
		@Override
		public Move mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Move(rs.getInt("move_id"), rs.getInt("match_id"), rs.getString("move_opponent").charAt(0),
					rs.getInt("move_position"), rs.getTimestamp("move_performed").toInstant());
		}
	}
}
