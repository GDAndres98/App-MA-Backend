package app.ma.back;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.ma.entities.Article;
import app.ma.entities.Role;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.RoleRepository;
import app.ma.services.ArticleController;

@Component
public class Runner implements CommandLineRunner {
	
    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public void run(String... args) throws Exception {
		Role student = new Role();
		student.setName("student");
		student.setStudent(true);
		
		roleRepository.save(student);
		
		Role professor = new Role();
		professor.setName("professor");
		professor.setProfessor(true);
	
		roleRepository.save(professor);
		
		createArticle("Servicios RESTful", "Interner Gomez", "latex.md", new Date("2020/05/18"));
		createArticle("Algoritmos Avaros", "Don Cormen", "latex.md", new Date("1998/03/12"));
		createArticle("Grafos para ruta mas corta", "Don Cormen", "latex.md", new Date("2009/12/31"));
		createArticle("Grafos en General", "Don Cormen", "latex.md", new Date("2011/03/12"));
		createArticle("Este título es largo solo para ver cómo la pantalla al mostrarse pues... un titulo largo",
				"Don Cormen", "latex.md", new Date("1998/03/12"));
	}
	
	
	public void createArticle 
	(
			String 	title		, 
			String 	author		, 
			String 	markdownURL	, 
			Date	dateWritten) {
		
		Article article = new Article(title, author, markdownURL, dateWritten);
		articleRepository.save(article);
		
	}

}
