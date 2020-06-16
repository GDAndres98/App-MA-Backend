package app.ma.back;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import app.ma.compositeKey.UserCourseKey;
import app.ma.entities.Article;
import app.ma.entities.Course;
import app.ma.entities.Problem;
import app.ma.entities.Role;
import app.ma.entities.Section;
import app.ma.entities.Tag;
import app.ma.entities.User;
import app.ma.entities.UserCourse;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.ProblemRepository;
import app.ma.repositories.RoleRepository;
import app.ma.repositories.SectionRepository;
import app.ma.repositories.TagRepository;
import app.ma.repositories.UserCourseRepository;
import app.ma.repositories.UserRepository;

@Component
public class Runner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired private RoleRepository roleRepository;
	@Autowired private ArticleRepository articleRepository;
	@Autowired private ProblemRepository problemRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private CourseRepository courseRepository;
	@Autowired private SectionRepository sectionRepository;
	@Autowired private TagRepository tagRepository;	
	@Autowired private UserCourseRepository userCourseRepository;


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
		cursos.add(new Course("Programación 1", "code", profesores.get(0)));
		cursos.add(new Course("Paradigmas de Programación", "boxes", profesores.get(1)));
		cursos.add(new Course("Estructura de Datos", "dice-d20", profesores.get(1)));
		cursos.add(new Course("Automatas y algoritmos para la vida eterna por la gloria del imperio galactico ameno dorime", "empire", profesores.get(0)));
		createAllCourses(cursos);
		
		// Registro de estudiantes a clases
		
		addStudentToCourse(1l, 1l);
		addStudentToCourse(1l, 2l);
		addStudentToCourse(2l, 1l);
		addStudentToCourse(2l, 2l);
		addStudentToCourse(2l, 3l);
		addStudentToCourse(2l, 4l);

		
		
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
		tags.add(new Tag("Fuerza Bruta", 2));				// ID: 15
		tags.add(new Tag("TODOS", 3));						// ID: 16

		createTags(tags);

		
		
		// Artículos
		ArrayList<Article> articulos = new ArrayList<Article>();
/*1*/	articulos.add(new Article("Servicios RESTful", "Interner Gomez", getMarkdown("articles/1.md"), new Date("2020/05/18")));
/*2*/	articulos.add(new Article("Algoritmos Avaros", "Don Cormen", getMarkdown("articles/2.md"), new Date("1998/03/12")));
/*3*/	articulos.add(new Article("Grafos para ruta mas corta", "Don Cormen", getMarkdown("articles/1.md"), new Date("2009/12/31")));
/*4*/	articulos.add(new Article("Grafos en General", "Don Cormen", getMarkdown("articles/1.md"), new Date("2011/03/12")));
/*5*/	articulos.add(new Article("Geometria", "Don Cormen", getMarkdown("articles/1.md"), new Date("2011/03/12")));
/*6*/	articulos.add(new Article("Arbol de expansión minima", "Dimitrius Papadopoulos", getMarkdown("articles/1.md"), new Date("2011/03/12")));
/*7*/	articulos.add(new Article("El famoso algoritmo de Dijkstra para buscar el camino de dos nodos de un grafo", "El Mr Dijkstra", getMarkdown("articles/1.md"), new Date("1960/03/12")));
/*8*/	articulos.add(new Article("Algoritmos para contar números", "No tengo ni idea", getMarkdown("articles/1.md"), new Date("1001/03/12")));
/*9*/	articulos.add(new Article("Counting Sort", "Dimitrius Papadopoulos", getMarkdown("articles/1.md"), new Date("1998/03/12")));
/*10*/	articulos.add(new Article(
				"Este título es largo solo para ver cómo la pantalla al mostrarse pues... un titulo largo Este título es largo solo para ver cómo la pantalla al mostrarse pues... un titulo largo",
				"Don Cormen", getMarkdown("articles/1.md"), new Date("1998/03/12")));
		
		createAllArticles(articulos);

		
		// Artículos-Tags
		addTagtoArticle(1l, 16l, 14l);
		addTagtoArticle(2l, 16l, 6l, 1l);
		addTagtoArticle(3l, 16l, 1l, 2l, 6l);
		addTagtoArticle(4l, 16l, 1l, 6l);
		addTagtoArticle(5l, 16l, 10l, 11l, 7l);
		addTagtoArticle(6l, 16l, 10l, 11l, 7l, 13l);
		addTagtoArticle(7l, 16l, 1l, 2l, 2l, 1l);
		addTagtoArticle(8l, 16l, 10l, 11l);
		addTagtoArticle(9l, 16l, 15l, 3l, 4l);
		addTagtoArticle(10l, 16l, 15l, 1l, 2l, 3l, 4l, 5l, 6l);
		
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
		addTagtoProblem(9l, 2l, 3l, 4l);

		
		
		
		//Section
		// id = 1
		createSection("For loops", "En esta sección se va a enterder el concepto de 'for loop' ademas de sus posibles aplicaciones", 1l);
		createSection("Funciones Recursivas", "En esta sección se va enseñar el uso de las funciones recursivas como sus posibles implementaciones.", 1l);
		createSection("Programación dinamica", "En esta sección se va a dar un pequeño vistazo en el paradigma de la programación dinamica así como diferentes tipos de estas.", 1l);
		createSection("Distancia minima en grafos", "En esta sección se busca presentar el problema de distancia minima en grafos mostrando todos los tipos y los algoritmos que los solucionan.", 1l);
		
		// id = 2
		createSection("Programación dinamica", "En esta sección se va a dar un pequeño vistazo en el paradigma de la programación dinamica así como diferentes tipos de estas.", 2l);
		createSection("Distancia minima en grafos", "En esta sección se busca presentar el problema de distancia minima en grafos mostrando todos los tipos y los algoritmos que los solucionan.", 2l);
		
		// id = 3
		createSection("Funciones Recursivas", "En esta sección se va enseñar el uso de las funciones recursivas como sus posibles implementaciones.", 3l);
		createSection("Programación dinamica", "En esta sección se va a dar un pequeño vistazo en el paradigma de la programación dinamica así como diferentes tipos de estas.", 3l);
		createSection("Distancia minima en grafos", "En esta sección se busca presentar el problema de distancia minima en grafos mostrando todos los tipos y los algoritmos que los solucionan.", 3l);
		
		//Section-Article
		addArticleToSection(1l, 1l);
		addArticleToSection(2l, 1l);
		addArticleToSection(3l, 1l);
		addArticleToSection(4l, 1l);
		
		addArticleToSection(1l, 2l);
		addArticleToSection(4l, 2l);
		addArticleToSection(5l, 2l);
		addArticleToSection(6l, 2l);
		
		addArticleToSection(10l, 3l);
		addArticleToSection(8l, 3l);
		addArticleToSection(9l, 3l);
		addArticleToSection(7l, 3l);
		addArticleToSection(2l, 3l);
		
	}
	
	
	public void createSection 
	(
			String 	title	, 
			String 	description, 
			Long 	courseId) {
		Optional<Course> opCourse = courseRepository.findById(courseId);
		Course course = opCourse.get();
		
		Section section = new Section();
		section.setTitle(title);
		section.setDescription(description);
		section.setPostedAt(course);
		
		sectionRepository.save(section);
	}
	
	public void addArticleToSection
	(
			Long 		articleId,  
			Long 		sectionId) {
		Optional<Article> opArticle = articleRepository.findById(articleId);
		Optional<Section> opSection = sectionRepository.findById(sectionId);
		Section section = opSection.get();
		Article article = opArticle.get();

		section.addArticle(article);
		article.addSection(section);
		
		sectionRepository.save(section);
	}
	

	public void addStudentToCourse
	(
			Long 	studentId, 
			Long 	courseId) {
		
		Course course = courseRepository.findById(courseId).get();
		User student  = userRepository.findById(studentId).get();
		
		UserCourseKey key = new UserCourseKey(course.getId(), student.getId());
		UserCourse userCourse = new UserCourse();
		userCourse.setId(key);
		userCourse.setCourse(course);
		userCourse.setStudent(student);
		userCourseRepository.save(userCourse);
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