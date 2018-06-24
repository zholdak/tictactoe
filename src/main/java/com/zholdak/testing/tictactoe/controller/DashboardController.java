package com.zholdak.testing.tictactoe.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.zholdak.testing.tictactoe.dao.GameDao;
import com.zholdak.testing.tictactoe.dao.MatchDao;
import com.zholdak.testing.tictactoe.model.Game;
import com.zholdak.testing.tictactoe.model.Match;
import com.zholdak.testing.tictactoe.model.MatchRequest;

import static java.time.Instant.now;

/**
 * @author Aleksey Zholdak (aleksey@zholdak.com) 2018-06-23 12:49
 */
@Controller
public class DashboardController {

	private Logger logger = LoggerFactory.getLogger(DashboardController.class);

	private GameDao gameDao;
	private MatchDao matchDao;

	@Autowired
	public DashboardController(GameDao gameDao, MatchDao matchDao) {
		this.gameDao = gameDao;
		this.matchDao = matchDao;
	}

	/**
	 * "Root" endpoint, redirects to dashboard endpoint
	 */
	@GetMapping("/")
	public RedirectView redirect() {
		return new RedirectView("/dashboard");
	}

	/**
	 * Main endpoint of the application
	 */
	@GetMapping("dashboard")
	public ModelAndView dashboard() {
		logger.info("Dashboard request received ...");

		ModelAndView modelAndView = new ModelAndView("dashboard");

		List<Game> games = gameDao.getAll();
		logger.trace("{} games fetched: {}", games.size(), games);
		modelAndView.addObject("games", games);

		List<Match> matchesInProgress = matchDao.getInProgress();
		logger.trace("{} matches in progress fetched: {}", matchesInProgress.size(), matchesInProgress);
		modelAndView.addObject("matchesInProgress", matchesInProgress);

		List<Match> finishedMatches = matchDao.getFinished();
		logger.trace("{} finished matches fetched: {}", finishedMatches.size(), finishedMatches);
		modelAndView.addObject("finishedMatches", finishedMatches);

		modelAndView.addObject("matchRequest", new MatchRequest(games.get(0).getId(), "New match " + now().toString()));

		return modelAndView;
	}

}
