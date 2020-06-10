package app.ma.back;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

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
import app.ma.entities.Course;
import app.ma.entities.Problem;
import app.ma.entities.Role;
import app.ma.entities.Tag;
import app.ma.entities.User;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.ProblemRepository;
import app.ma.repositories.RoleRepository;
import app.ma.repositories.TagRepository;
import app.ma.repositories.UserRepository;

@Component
public class Runner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired private RoleRepository roleRepository;
	@Autowired private ArticleRepository articleRepository;
	@Autowired private ProblemRepository problemRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private CourseRepository courseRepository;
	@Autowired private TagRepository tagRepository;


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

		
		// Estudiantes y Profesores
		ArrayList<User> estudiantes = new ArrayList<User>();
		estudiantes.add(new User("GDAndres98", "Andrés", "Osorio", "img.png", "gd_andres98@hotmail.com", "12345"));
		estudiantes
				.add(new User("Alex_gal", "Marlon", "Estupiñán", "imgcfff.png", "maestupinan2@hotmail.com", "123123"));
		estudiantes.add(new User("nomovie2", "Ashoka", "Tano", "imgcfff.png", "nomovie2@hotmail.com", "123456"));
		createAllUsers(estudiantes, student);
		ArrayList<User> profesores = new ArrayList<User>();
		profesores.add(new User("profesor1", "Anakin", "Skywalker", "img.png", "skyani@hotmail.com", "12345"));
		profesores.add(new User("profesor2", "Kakashi", "Hatake", "imgcfff.png", "narutolore@hotmail.com", "123123"));
		createAllUsers(profesores, professor);

		ArrayList<Course> cursos = new ArrayList<Course>();
		cursos.add(new Course("Programación 1", "image1.png", profesores.get(0)));
		cursos.add(new Course("Programación 2", "image1.png", profesores.get(1)));
		createAllCourses(cursos);

		
		//Tags
		
		ArrayList<Tag> tags = new ArrayList<>();
		
		tags.add(new Tag("Grafos", 1));						// ID: 1
		tags.add(new Tag("Distancia mas corta", 2));		// ID: 2
		tags.add(new Tag("Programación Dinamica", 1));		// ID: 3
		tags.add(new Tag("Matematicas", 1));				// ID: 4
		tags.add(new Tag("Geometría", 1));					// ID: 5
		tags.add(new Tag("Aváros", 1));						// ID: 6
		tags.add(new Tag("Teoría de Juegos", 1));			// ID: 7
		tags.add(new Tag("Poligono Convexo", 2));			// ID: 8
		tags.add(new Tag("Arbol de Expación Minima", 2));	// ID: 9
		tags.add(new Tag("FFT", 2));						// ID: 10
		tags.add(new Tag("Flujo", 2));						// ID: 11
		tags.add(new Tag("Segment Tree", 2));				// ID: 12
		tags.add(new Tag("Sumas de Minkowsky", 2));			// ID: 13
		tags.add(new Tag("Redes y protocolos", 2));			// ID: 14

		createTags(tags);

		
		
		// Artículos
		ArrayList<Article> articulos = new ArrayList<Article>();
/*1*/	articulos.add(new Article("Servicios RESTful", "Interner Gomez", getMarkdown("articles/1.md"), new Date("2020/05/18")));
/*2*/	articulos.add(new Article("Algoritmos Avaros", "Don Cormen", getMarkdown("articles/2.md"), new Date("1998/03/12")));
/*3*/	articulos.add(new Article("Grafos para ruta mas corta", "Don Cormen", "latex.md", new Date("2009/12/31")));
/*4*/	articulos.add(new Article("Grafos en General", "Don Cormen", "latex.md", new Date("2011/03/12")));
/*5*/	articulos.add(new Article(
				"Este título es largo solo para ver cómo la pantalla al mostrarse pues... un titulo largo Este título es largo solo para ver cómo la pantalla al mostrarse pues... un titulo largo",
				"Don Cormen", "latex.md", new Date("1998/03/12")));
		
		createAllArticles(articulos);

		
		// Artículos-Tags
		addTagtoArticle(1l, 14l);
		addTagtoArticle(2l, 6l, 1l);
		addTagtoArticle(3l, 1l, 2l, 6l);
		addTagtoArticle(4l, 1l, 6l);
		addTagtoArticle(5l, 10l, 11l);
		
		
		// Problemas
		ArrayList<Problem> problemas = new ArrayList<Problem>();
/*1*/	problemas.add(new Problem("New Year Transportation", "CodeForces", getMarkdown("problems/1.md"), 2000l, 256l));
/*2*/	problemas.add(new Problem("Dijkstra?", "CodeForces", getMarkdown("problems/2.md"), 1000l, 64l));
/*3*/	problemas.add(new Problem("Favorite Time", "UVa", getMarkdown("problems/3.md"), 1000l, 64l));
/*4*/	problemas.add(new Problem("Game On Leaves", "CodeForces", getMarkdown("problems/1.md"), 2000l, 256l));
/*5*/	problemas.add(new Problem("Subsequence Hate", "CodeForces", getMarkdown("problems/2.md"), 1000l, 64l));
/*6*/	problemas.add(new Problem("Odd Selection", "UVa", getMarkdown("problems/3.md"), 1000l, 64l));
/*7*/	problemas.add(new Problem("Polygon", "CodeForces", getMarkdown("problems/1.md"), 2000l, 256l));
/*8*/	problemas.add(new Problem("Minimal Square", "CodeForces", getMarkdown("problems/2.md"), 1000l, 64l));
/*9*/	problemas.add(new Problem("Mixing Water", "UVa", getMarkdown("problems/3.md"), 1000l, 64l));
		createAllProblems(problemas);
		
		
		// Artículos-Tags
		addTagtoProblem(1l, 14l, 1l, 2l);
		addTagtoProblem(2l, 2l, 4l, 8l);
		addTagtoProblem(3l, 4l, 5l, 2l);
		addTagtoProblem(4l, 5l, 1l, 2l);
		addTagtoProblem(5l, 6l, 3l, 4l);
		addTagtoProblem(6l, 12l, 2l, 3l);
		addTagtoProblem(7l, 13l, 7l, 12l);
		addTagtoProblem(8l, 1l, 8l, 11l);
		
	}
	
	
	
	
	private void createTags(ArrayList<Tag> tags) {
		for(Tag tag: tags)
			tagRepository.save(tag);
	}




	public void addTagtoArticle
	( 	Long articleId, Long ...tagIds) {
		System.out.println("ARTICLE");
		Optional<Article> opArticle = articleRepository.findById(articleId);
		if (!opArticle.isPresent()) return;
		Article article = opArticle.get();
		for(Long tagid: tagIds) {
			Tag tag = tagRepository.findById(tagid).get();
			tag.addArticle(article);
			article.addTag(tag);						
			tagRepository.save(tag);
		}
	}
	
	public void addTagtoProblem
	( 	Long problemId, Long ...tagIds) {
		System.out.println("PROBLEM");

		Optional<Problem> opProblem = problemRepository.findById(problemId);
		if (!opProblem.isPresent()) return;
		Problem problem = opProblem.get();
		for(Long tagid: tagIds) {
			Tag tag = tagRepository.findById(tagid).get();
			tag.addProblem(problem);
			problem.addTag(tag);			
			tagRepository.save(tag);
		}
	}
	
	

	private String getMarkdown(String fileName) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(fileName)));
		return content;
	}

	private void createAllArticles(ArrayList<Article> articles) {
		for (Article e : articles)
			articleRepository.save(e);
	}

	private void createAllProblems(ArrayList<Problem> problems) {
		for (Problem e : problems)
			problemRepository.save(e);
	}

	private void createAllUsers(ArrayList<User> users, Role x) {
		for (User e : users) {
			x.addUser(e);
			e.addRole(x);
			userRepository.save(e);
		}
	}

	private void createAllCourses(ArrayList<Course> courses) {
		for (Course e : courses)
			courseRepository.save(e);
	}

}
