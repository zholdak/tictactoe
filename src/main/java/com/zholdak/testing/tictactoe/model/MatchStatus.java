package com.zholdak.testing.tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 16:06
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MatchStatus {

	public static final int DRAW = -1;
	public static final int WON = 1;

	/** Status identifier */
	private int id;

	/** Status freiendly title */
	private String title;

	/** Is this final status of the match (is this match already finished) */
	private boolean finalized;
}
