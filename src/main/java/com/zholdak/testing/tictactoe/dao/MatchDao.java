package com.zholdak.testing.tictactoe.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.zholdak.testing.tictactoe.model.Match;
import com.zholdak.testing.tictactoe.model.MatchStatus;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.springframework.util.StreamUtils.copyToString;

/**
 * IMHO: I think no need to extract an interface because only one implementation exist.
 *
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 17:46
 */
@Repository
public class MatchDao {

	private Logger logger = LoggerFactory.getLogger(MatchDao.class);

	private NamedParameterJdbcTemplate jdbcTemplate;

	private GameDao gameDao;
	private MoveDao moveDao;

	private String matchesInProgressSql;
	private String matchesFinishedSql;
	private String matchByIdSql;
	private String setMatchFinishedSql;
	private String matchStatusByIdSql;
	private String createMatchSql;

	@Autowired
	public MatchDao(
			GameDao gameDao, MoveDao moveDao,
			NamedParameterJdbcTemplate jdbcTemplate,
			@Value("classpath:sql/get_matches_in_progress.sql") Resource matchesInProgressSqlRes,
			@Value("classpath:sql/get_finished_matches.sql") Resource matchesFinishedSqlRes,
			@Value("classpath:sql/get_match_by_id.sql") Resource matchByIdSqlRes,
			@Value("classpath:sql/set_match_finished.sql") Resource setMatchFinishedSqlRes,
			@Value("classpath:sql/get_match_status_by_id.sql") Resource matchStatusByIdSqlRes,
			@Value("classpath:sql/create_match.sql") Resource createMatchSqlRes) throws IOException {

		this.gameDao = gameDao;
		this.moveDao = moveDao;

		this.jdbcTemplate = jdbcTemplate;

		this.matchesInProgressSql = copyToString(matchesInProgressSqlRes.getInputStream(), defaultCharset());
		this.matchesFinishedSql = copyToString(matchesFinishedSqlRes.getInputStream(), defaultCharset());
		this.matchByIdSql = copyToString(matchByIdSqlRes.getInputStream(), defaultCharset());
		this.setMatchFinishedSql = copyToString(setMatchFinishedSqlRes.getInputStream(), defaultCharset());
		this.matchStatusByIdSql = copyToString(matchStatusByIdSqlRes.getInputStream(), defaultCharset());
		this.createMatchSql = copyToString(createMatchSqlRes.getInputStream(), defaultCharset());
	}

	/**
	 *
	 */
	public List<Match> getInProgress() {
		List<Match> matches = jdbcTemplate.query(matchesInProgressSql, emptyMap(), new Mapper());
		matches.forEach(match -> {
			match.setMoves(moveDao.getByMatchId(match.getId()));
			match.setGame(gameDao.getById(match.getGameId()));
		});
		return matches;
	}

	/**
	 *
	 */
	public List<Match> getFinished() {
		List<Match> matches = jdbcTemplate.query(matchesFinishedSql, emptyMap(), new Mapper());
		matches.forEach(match -> {
			match.setMoves(moveDao.getByMatchId(match.getId()));
			match.setGame(gameDao.getById(match.getGameId()));
		});
		return matches;
	}

	/**
	 *
	 */
	public Match getById(int matchId) {
		try {
			Match match = jdbcTemplate.queryForObject(matchByIdSql, singletonMap("matchId", matchId), new Mapper());
			match.setGame(gameDao.getById(match.getGameId()));
			match.setMoves(moveDao.getByMatchId(match.getId()));
			return match;
		}catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	public MatchStatus getStatusById(int statusId) {
		try {
			return jdbcTemplate
					.queryForObject(matchStatusByIdSql, singletonMap("statusId", statusId), new StatusMapper());
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	/**
	 *
	 */
	public void setMatchFinished(Match match) {
		Map<String, Object> params = new HashMap<>();
		params.put("matchId", match.getId());
		params.put("statusId", match.getStatus().getId());
		jdbcTemplate.update(setMatchFinishedSql, params);
	}

	/**
	 *
	 */
	public int create(Match match) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("gameId", match.getGameId());
		namedParameters.addValue("title", match.getTitle());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(createMatchSql, namedParameters, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * Match mapper
	 */
	private static final class Mapper implements RowMapper<Match> {
		@Override
		public Match mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Match(rs.getInt("match_id"), rs.getString("match_title"), rs.getInt("game_id"), null,
					new MatchStatus(rs.getInt("status_id"), rs.getString("status_title"),
							rs.getBoolean("status_finalized")), emptyList());
		}
	}

	/**
	 * Status mapper
	 */
	private static final class StatusMapper implements RowMapper<MatchStatus> {
		@Override
		public MatchStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new MatchStatus(rs.getInt("id"), rs.getString("title"),
					rs.getBoolean("finalized"));
		}
	}
}
