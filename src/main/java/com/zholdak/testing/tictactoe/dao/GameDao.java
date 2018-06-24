package com.zholdak.testing.tictactoe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zholdak.testing.tictactoe.model.Game;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

/**
 * IMHO: I think no need to extract an interface because only one implementation exist.
 *
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 12:26
 */
@Repository
public class GameDao {

	private final String SELECT_ALL = "select * from game;";
	private final String SELECT_ONE = "select * from game where id=:gameId;";

	private Logger logger = LoggerFactory.getLogger(GameDao.class);
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public GameDao(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Get all games list
	 */
	public List<Game> getAll() {
		return jdbcTemplate.query(SELECT_ALL, emptyMap(), new Mapper());
	}

	/**
	 * Get game by it's identifier
	 */
	public Game getById(int gameId) {
		return jdbcTemplate.queryForObject(SELECT_ONE, singletonMap("gameId", gameId), new Mapper());
	}

	/**
	 * Game mapper
	 */
	private static final class Mapper implements RowMapper<Game> {
		@Override
		public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Game(rs.getInt("id"), rs.getString("nickname"), rs.getString("title"), rs.getBoolean("active"));
		}
	}
}
