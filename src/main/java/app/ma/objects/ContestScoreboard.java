package app.ma.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import app.ma.entities.Contest;
import app.ma.entities.ProblemContestUser;
import app.ma.repositories.ProblemContestUserRepository;

public class ContestScoreboard {
	private Contest contest;
	private ArrayList<UserStats> stats;
	private Iterable<ProblemContestUser> problemContestUsers;
	private HashMap<Long, UserStats> statMap;

	public ContestScoreboard(Contest contest, ProblemContestUserRepository problemContestUserRepository) {
		this.contest = contest;
		this.problemContestUsers = problemContestUserRepository.findByProblemContest_Contest_Id(contest.getId());

		statMap = new HashMap<>();
		stats = new ArrayList<>();

		for (ProblemContestUser e : problemContestUsers) {
			UserStats uS = statMap.get(e.getUser().getId());
			ProblemScore pS = new ProblemScore(e);
			if (uS == null) {
				uS = new UserStats(e.getUser().getId());
				statMap.put(uS.id, uS);
				stats.add(uS);
				uS.setUsername(e.getUser().getUsername());
				uS.setName(e.getUser().getFirstName());
			}
			uS.addProblem(pS);
		}

		Collections.sort(stats);
		for (int i = 0; i < stats.size(); i++)
			stats.get(i).setPosition(i + 1l);

	}

	public ArrayList<UserStats> getStats() {
		return stats;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public void setStats(ArrayList<UserStats> stats) {
		this.stats = stats;
	}

	public void setProblemContestUsers(Iterable<ProblemContestUser> problemContestUsers) {
		this.problemContestUsers = problemContestUsers;
	}

	private class UserStats implements Comparable<UserStats> {
		private Long id;
		private Long position;
		private String username;
		private String name;
		private Long generalPenalty;
		private Long problemsSolved;

		public ArrayList<ProblemScore> problemsScore;

		public UserStats(Long id) {
			this.position = Long.MAX_VALUE;
			this.id = id;
			this.generalPenalty = 0l;
			this.problemsSolved = 0l;
			this.problemsScore = new ArrayList<>();
		}

		public void addProblem(ProblemScore pS) {
			problemsScore.add(pS);
			generalPenalty += pS.getPenalty();
			problemsSolved += pS.getSolved() ? 1 : 0;
		}

		public Long getId() {
			return id;
		}

		public Long getPosition() {
			return position;
		}

		public void setPosition(Long position) {
			this.position = position;
		}

		public String getUsername() {
			return username;
		}

		public String getName() {
			return name;
		}

		public Long getGeneralPenalty() {
			return generalPenalty;
		}

		public Long getProblemsSolved() {
			return problemsSolved;
		}

		public ArrayList<ProblemScore> getProblemsScore() {
			return problemsScore;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public int compareTo(UserStats o) {
			if (this.problemsSolved == o.problemsSolved) {
				return Long.compare(this.generalPenalty, o.getGeneralPenalty());
			}
			return Long.compare(o.getProblemsSolved(), this.problemsSolved);
		}

	}

	public class ProblemScore {

		private Long id;
		private Long penalty;
		private Long tries;
		private Boolean solved;

		public ProblemScore(ProblemContestUser e) {
			this.id = e.getProblemContest().getProblem().getId();
			this.tries = e.getTries();
			if (e.getSolDate() == null) {
				this.penalty = 0l;
				this.solved = false;
			} else {
				this.penalty = this.tries * 1200000;
				solved = true;
				this.penalty += e.solDate.getTime() - contest.getStartTime().getTime();
			}
		}

		public Long getId() {
			return id;
		}

		public Long getPenalty() {
			return penalty;
		}

		public Boolean getSolved() {
			return solved;
		}

		public Long getTries() {
			return tries;
		}

	}
}
