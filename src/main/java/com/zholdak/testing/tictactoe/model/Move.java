package com.zholdak.testing.tictactoe.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 16:09
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Move {

	public Move(int matchId, char opponent, int position) {
		this.matchId = matchId;
		this.opponent = opponent;
		this.position = position;
	}

	/** Move identifier */
	private int id;

	/** Identifier of the match */
	private int matchId;

	/** Opponent. 'X' or 'O'. Will be refactored in reality. */
	private char opponent;

	/**
	 * Position of the move.<br/>
	 * Small note about: look at the picture, and you'll understand:
	 *<pre>
	 *  0 | 1 | 2
	 * ---+---+---
	 *  3 | 4 | 5
	 * ---+---+---
	 *  6 | 7 | 8
	 *</pre>
	 */
	private int position;

	/** Timestamp when this move was performed */
	private Instant performed;
}
