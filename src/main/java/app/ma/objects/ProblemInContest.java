package app.ma.objects;

public class ProblemInContest {
	private Long id;
	private String letter;
	private String title;

	public ProblemInContest(Long id,  String title, String letter) {
		this.id = id;
		this.title = title;
		this.letter = letter;
	}

	public Long getId() {
		return id;
	}

	public String getLetter() {
		return letter;
	}

	public String getTitle() {
		return title;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public void setTitle(String name) {
		this.title = name;
	}

}