package com.zholdak.testing.tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO object for match creating request
 *
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 22:42
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MatchRequest {

	/** Game identifier */
	private int gameId;

	/** Desired match title */
	private String title;
}
