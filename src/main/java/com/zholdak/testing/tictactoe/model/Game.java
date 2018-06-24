package com.zholdak.testing.tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 12:32
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Game {

	/** Game identifier. It should be specified when new game creates */
	private int id;

	/** Nockname of the game. Used for endpoints, views etc. naming */
	private String nickname;

	/** Title of the game */
	private String title;

	/** Is this game has 'active' status or not. (maybe it's logic was not realized yet) */
	private boolean active;
}
