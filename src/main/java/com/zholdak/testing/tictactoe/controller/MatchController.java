package com.zholdak.testing.tictactoe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.zholdak.testing.tictactoe.model.Match;
import com.zholdak.testing.tictactoe.model.MatchRequest;
import com.zholdak.testing.tictactoe.model.Move;
import com.zholdak.testing.tictactoe.service.MatchService;

import static com.zholdak.testing.tictactoe.service.TicTacToeLogic.buildBoard;
import static com.zholdak.testing.tictactoe.service.TicTacToeLogic.getNextOpponent;
import static java.lang.String.format;

/**
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 22:01
 */
@Controller
@RequestMapping("match")
public class MatchController {

	private Logger logger = LoggerFactory.getLogger(DashboardController.class);

	private MatchService matchService;

	@Autowired
	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

	/**
	 * Board of the game
	 *
	 * @param matchId Math identifier
	 */
	@GetMapping("{matchId}/show")
	public ModelAndView board(@PathVariable("matchId") int matchId) {
		logger.info("Match with id {} show request received ...", matchId);

		Match match = matchService.getById(matchId);
		Move lastMove = match.getLastMove();

		ModelAndView modelAndView = new ModelAndView(format("%s/board", match.getGame().getNickname()));
		modelAndView.addObject("match", match);
		modelAndView.addObject("nextOpponent", getNextOpponent(lastMove == null ? null : lastMove.getOpponent()));
		modelAndView.addObject("board", buildBoard(match.getMoves()));
		modelAndView.addObject("finished", match.getStatus());

		return modelAndView;
	}

	/**
	 * Register new move and redirects to board of the game
	 *
	 * @param matchId Math identifier
	 * @param position Position to register
	 */
	@GetMapping("{matchId}/moveto/{position}")
	public RedirectView moveto(
			@PathVariable("matchId") int matchId,
			@PathVariable("position") int position) {

		logger.info("Move to position {} for match with id {} received ...", position, matchId);
		matchService.moveto(matchId, position);

		return new RedirectView(format("/match/%d/show", matchId));
	}

	/**
	 * Creates new match
	 *
	 * @param matchRequest Match request, contains game identifier and title of the new match
	 */
	@PostMapping("create")
	public RedirectView create(@ModelAttribute("matchRequest") MatchRequest matchRequest) {
		logger.info("New match create request received {} ...", matchRequest);

		return new RedirectView(format("/match/%d/show", matchService.create(matchRequest).getId()));
	}
}
