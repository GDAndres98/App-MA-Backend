package app.ma.objects;

import java.util.ArrayList;

import app.ma.entities.Contest;
import app.ma.enums.Veredict;
import app.ma.repositories.SubmitRepository;

public class ContestStats {
	private Contest contest;
	private ArrayList<ProblemStats> stats;
	private SubmitRepository submitRepository;

	public ContestStats(Contest contest, SubmitRepository submitRepository) {
		this.contest = contest;
		this.submitRepository = submitRepository;
		stats = new ArrayList<>();
		for (ProblemInContest e : this.contest.getProblems()) {
			stats.add(new ProblemStats(e.getId()));
		}
	}

	public Contest getContest() {
		return contest;
	}

	public ArrayList<ProblemStats> getStats() {
		return stats;
	}

	public void setStats(ArrayList<ProblemStats> stats) {
		this.stats = stats;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	private class ProblemStats {
		public Long id;
		public Long accepted;
		public Long attempted;

		public ProblemStats(Long id) {
			this.id = id;
			this.accepted = submitRepository.countDistinctProblemContestUser_User_IdByProblemContestUser_ProblemContest_Contest_IdAndProblemContestUser_ProblemContest_Problem_IdAndVeredict(contest.getId(), id, Veredict.ACCEPTED);
			this.attempted = submitRepository.countDistinctProblemContestUser_User_IdByProblemContestUser_ProblemContest_Contest_IdAndProblemContestUser_ProblemContest_Problem_Id(contest.getId(), id);
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