package app.ma.objects;

import java.util.ArrayList;

import app.ma.entities.Contest;
import app.ma.entities.ProblemContest;

public class ContestScoreboard {
	private Contest contest;
	private ArrayList<Long> problemIds;
	private ArrayList<UserStats> stats;

	public ContestScoreboard(Contest contest) {
		this.contest = contest;

		this.problemIds = new ArrayList<Long>();
		this.stats = new ArrayList<UserStats>();

		for (ProblemInContest e : this.contest.getProblems()) {
			problemIds.add(e.getId());
		}

	}

	private class UserStats {
		public Long id;
		public Long accepted;
		public Long attempted;

		public UserStats(Long id) {
			this.id = id;
		}

		public Long getId() {
			return id;
		}

		public Long getAccepted() {
			return accepted;
		}

		public Long getAttempted() {
			return attempted;
		}

	}
}
