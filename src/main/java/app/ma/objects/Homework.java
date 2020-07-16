package app.ma.objects;

import java.util.Date;

import app.ma.entities.Problem;

public class Homework {
	public Problem problem;
	public Date limitTime;
	public Date currentTime;
	public Long testCases;
	
	public Homework() {		
	}

	public Homework(Problem p, Date l, Long t) {
		setProblem(p);
		setLimitTime(l);
		setTestCases(t);
		setCurrentTime(new Date());
	}
	
	
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public Date getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}
	public Long getTestCases() {
		return testCases;
	}
	public void setTestCases(Long testCases) {
		this.testCases = testCases;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
	
	
}
