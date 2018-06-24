package com.zholdak.testing.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 16:07
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Match {

	/** Match identifier. Will be set automatically by DB on creation. */
	private int id;

	/** The title of the match */
	private String title;

	/** Game identifier */
	private int gameId;

	/** Game object. For usability. */
	private Game game;

	/** Status of the match */
	private MatchStatus status;

	/** List of all moves of this match */
	private List<Move> moves = new ArrayList<>();

	public Match(String title, int gameId) {
		this.title = title;
		this.gameId = gameId;
	}

	/**
	 * Gets first move of the match
	 *
	 * @return {@code null} if no moves yet
	 */
	public Move getFirstMove() {
		return moves.size() > 0 ? moves.get(0) : null;
	}

	/**
	 * Gets last move of the match
	 *
	 * @return {@code null} if no moves yet
	 */
	public Move getLastMove() {
		return moves.size() > 0 ? moves.get(moves.size()-1) : null;
	}
}
