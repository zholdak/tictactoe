package com.zholdak.testing.tictactoe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zholdak.testing.tictactoe.controller.DashboardController;
import com.zholdak.testing.tictactoe.dao.MatchDao;
import com.zholdak.testing.tictactoe.dao.MoveDao;
import com.zholdak.testing.tictactoe.model.Match;
import com.zholdak.testing.tictactoe.model.MatchRequest;
import com.zholdak.testing.tictactoe.model.MatchStatus;
import com.zholdak.testing.tictactoe.model.Move;

import static com.zholdak.testing.tictactoe.model.MatchStatus.DRAW;
import static com.zholdak.testing.tictactoe.model.MatchStatus.WON;
import static com.zholdak.testing.tictactoe.service.TicTacToeLogic.buildBoard;
import static com.zholdak.testing.tictactoe.service.TicTacToeLogic.getNextOpponent;
import static com.zholdak.testing.tictactoe.service.TicTacToeLogic.getXfromPosition;
import static com.zholdak.testing.tictactoe.service.TicTacToeLogic.getYfromPosition;
import static com.zholdak.testing.tictactoe.service.TicTacToeLogic.isWinner;

/**
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-24 20:32
 */
@Service
public class MatchService {

	private Logger logger = LoggerFactory.getLogger(DashboardController.class);

	private MatchDao matchDao;
	private MoveDao moveDao;

	@Autowired
	public MatchService(MatchDao matchDao, MoveDao moveDao) {
		this.matchDao = matchDao;
		this.moveDao = moveDao;
	}

	/**
	 * Retrieve match by it's identifier
	 */
	public Match getById(int matchId) {
		logger.trace("Fetching match with id {} from ...", matchId);
		Match match = matchDao.getById(matchId);
		logger.trace("Fetched {}", match);
		return match;
	}

	/**
	 * Insert move and updates it's id after operation
	 */
	public Move insertMove(Move move) {
		logger.trace("Inserting move into match with id {} ...", move.getMatchId());
		int moveId = moveDao.insertMove(move);
		logger.trace("Move with id {} inserted", moveId);
		return moveDao.getById(moveId);
	}

	/**
	 * Creates new match
	 *
	 * @param matchRequest Match request, contains game identifier and title of the new match
	 */
	public Match create(MatchRequest matchRequest) {
		logger.trace("Creating new match for game with id {} ...", matchRequest.getGameId());
		int matchId = matchDao.create(new Match(matchRequest.getTitle(), matchRequest.getGameId()));
		logger.trace("New match with id {} created", matchId);
		return matchDao.getById(matchId);
	}

	/**
	 * Register new move
	 *
	 * @param matchId Math identifier
	 * @param position Position to register
	 */
	public void moveto(int matchId, int position) {
		logger.trace("Executing new move of match with id {} to position {} ...", matchId, position);

		Match match = getById(matchId);
		Move lastMove = match.getLastMove();
		char nextOpponent = getNextOpponent(lastMove == null ? null : lastMove.getOpponent());

		Move move = insertMove(new Move(matchId, nextOpponent, position));

		match.getMoves().add(move);
		if (isWinner(buildBoard(match.getMoves()), getXfromPosition(position), getYfromPosition(position))) {
			MatchStatus matchStatus = matchDao.getStatusById(WON);
			match.setStatus(matchStatus);
			matchDao.setMatchFinished(match);
		} else if (match.getMoves().size() == 9) {
			MatchStatus matchStatus = matchDao.getStatusById(DRAW);
			match.setStatus(matchStatus);
			matchDao.setMatchFinished(match);
		}
	}
}
