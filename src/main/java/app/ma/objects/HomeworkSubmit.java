package app.ma.objects;

import app.ma.entities.Submit;

public class HomeworkSubmit {
	Submit submit;
	Double grade;
	
	
	
	public HomeworkSubmit() {
	}
	public HomeworkSubmit(Submit s, Double g) {
		this.submit = s;
		this.grade  = g;
	}
	
	
	public Submit getSubmit() {
		return submit;
	}
	public void setSubmit(Submit submit) {
		this.submit = submit;
	}
	public Double getGrade() {
		return grade;
	}
	public void setGrade(Double grade) {
		this.grade = grade;
	}
}
