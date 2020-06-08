package app.ma.back;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.ma.entities.Article;
import app.ma.entities.Course;
import app.ma.entities.Role;
import app.ma.entities.User;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.RoleRepository;
import app.ma.repositories.UserRepository;

@Component
public class Runner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CourseRepository courseRepository;

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

		ArrayList<Article> articulos = new ArrayList<Article>();
		articulos.add(new Article("Servicios RESTful", "Interner Gomez", getArticleMD(0), new Date("2020/05/18")));
		articulos.add(new Article("Algoritmos Avaros", "Don Cormen", getArticleMD(1), new Date("1998/03/12")));
		articulos.add(new Article("Grafos para ruta mas corta", "Don Cormen", "latex.md", new Date("2009/12/31")));
		articulos.add(new Article("Grafos en General", "Don Cormen", "latex.md", new Date("2011/03/12")));
		articulos.add(
				new Article("Este título es largo solo para ver cómo la pantalla al mostrarse pues... un titulo largo",
						"Don Cormen", "latex.md", new Date("1998/03/12")));
		createAllArticles(articulos);

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

	}

	private void createAllArticles(ArrayList<Article> articles) {
		for (Article e : articles)
			articleRepository.save(e);
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

	private String getArticleMD(int x) {
		String articles[] = new String[4];
		articles[0] = "Actualmente los servicios Web RESTful son ampliamente utilizados. Parte del éxito radica en su simplicidad. Algunas compañías importantes brindan este tipo de servicios, algunos ejemplos son: Facebook, Twitter, Amazon, eBay, Google, entre otras. Pero antes hablar de RESTful es necesario abordar dos conceptos clave: servicios Web y REST.\r\n" + 
				"\r\n" + 
				"## ¿Qué es un servicio web?\r\n" + 
				"Como primera aproximación se puede decir que es cualquier tipo de servicio provisto por la Web incluyendo las páginas Web.\r\n" + 
				"\r\n" + 
				"Lo anterior no es del todo cierto, por ello la definición que se puede considerar es la provista por la W3C:\r\n" + 
				"\r\n" + 
				">Un servicio Web es un sistema de software diseñado para admitir la interacción interoperable de máquina a máquina a través de una red. Tiene una interfaz descrita en un formato de proceso de máquina (específicamente WSDL). Otros sistemas interactúan con el servicio web en una forma prescrita por su descripción utilizando mensajes SOAP, normalmente transmitidos utilizando HTTP con una serialización XML junto con otros estándares relacionados con la Web.\r\n" + 
				"\r\n" + 
				"Sin embargo, sigue sin ser general. Es específica ya que solo habla de servicios web basados en SOAP y WSDL.\r\n" + 
				"\r\n" + 
				"Por ello, en el año 2004 el grupo de trabajo del W3C en una nota afirmó lo siguiente:\r\n" + 
				"\r\n" + 
				">Podemos identificar dos clases principales de servicios Web:\r\n" + 
				">- Servicios Web que cumplen con **REST**, en los cuales el propósito principal del servicio es manipular representaciones XML de recursos web utilizando un conjunto uniforme de operaciones «sin estado»\r\n" + 
				">- Servicios Web arbitrarios, en los que el servicio puede exponer un conjunto arbitrario de operaciones»\r\n" + 
				"\r\n" + 
				"Entonces, se podría considerar como definición:\r\n" + 
				"\r\n" + 
				"> Un servicio Web es un sistema de software diseñado para admitir la interacción interoperable de máquina a máquina a través de una red\r\n" + 
				"\r\n" + 
				"## ¿Qué es REST?\r\n" + 
				"El objetivo principal de cualquier sistema distribuido es facilitar el acceso a recursos remotos. REST se diseñó pensando en ser simple, con ello se lograría una rápida adopción del usuario y un desarrollo rápido.\r\n" + 
				"\r\n" + 
				">REST es un estilo arquitectónico diseñado para y sobre un sistema distribuido particular, la Web\r\n" + 
				"### Servicios Web RESTful\r\n" + 
				"Los dos conceptos clave son necesarios ya que un servicio Web RESTful es aquél servicio web que está basado en la arquitectura REST. Los servicios Web RESTful se basan en recursos. Un recurso es una entidad, la cual se almacena principalmente en un servidor y el cliente solicita el recurso utilizando servicios Web RESTful.\r\n" + 
				"\r\n" + 
				"#####  Características principales de un servicio Web RESTful\r\n" + 
				"- Tiene cinco operaciones típicas: listar, crear, leer, actualizar y borrar\r\n" + 
				"- Cada operación requiere de dos cosas: El método URI y HTTP\r\n" + 
				"- El URI es un sustantivo que contiene el nombre del recurso\r\n" + 
				"- El método HTTP es un verbo $A$\r\n" + 
				"\r\n" + 
				"### Servicios Web RESTful\r\n" + 
				"- GET\r\n" + 
				"\r\n" + 
				"![Imagen Get][get]\r\n" + 
				"- POST\r\n" + 
				"\r\n" + 
				"![Imagen Post][post]\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"[get]: http://blog.bi-geek.com/wp-content/uploads/2017/12/00_RESTFUL_WEBSERVICES_GET.png\r\n" + 
				"[post]: http://blog.bi-geek.com/wp-content/uploads/2017/12/01_RESTFUL_WEBSERVICES_POST-1.png";
		articles [1] ="En ciencias de la computación, un **algoritmo voraz** (también conocido como goloso, ávido, devorador o greedy) es una estrategia de búsqueda por la cual se sigue una heurística consistente en elegir la opción óptima en cada paso local con la esperanza de llegar a una solución general óptima. Este esquema algorítmico es el que menos dificultades plantea a la hora de diseñar y comprobar su funcionamiento. Normalmente se aplica a los problemas de *optimización*.\r\n" + 
				"\r\n" + 
				"## Esquema\r\n" + 
				"Dado un conjunto finito de entradas $C,$ un algoritmo voraz devuelve un conjunto $S,$ (seleccionados) tal que $S simboloraro C$ y que además cumple con las restricciones del problema inicial. A cada conjunto $S,$ que satisfaga las restricciones se le suele denominar prometedor, y si este además logra que la función objetivo se minimice o maximice (según corresponda) diremos que $S,$ es una solución óptima.\r\n" + 
				"\r\n" + 
				"### Elementos de los que consta la técnica\r\n" + 
				"El conjunto $C,$ de candidatos, entradas del problema.\r\n" + 
				"- Función **solución**. Comprueba, en cada paso, si el subconjunto actual de candidatos elegidos forma una solución (no importa si es óptima o no lo es).\r\n" + 
				"- Función de **selección**. Informa cuál es el elemento más prometedor para completar la solución. Éste no puede haber sido escogido con anterioridad. Cada elemento es considerado una sola vez. Luego, puede ser rechazado o aceptado y pertenecerá a $C/S$.\r\n" + 
				"- Función de **factibilidad**. Informa si a partir de un conjunto se puede llegar a una solución. Lo aplicaremos al conjunto de seleccionados unido con el elemento más prometedor.\r\n" + 
				"- Función **objetivo**. Es aquella que queremos maximizar o minimizar, el núcleo del problema.\r\n" + 
				"\r\n" + 
				"### Funcionamiento\r\n" + 
				"El algoritmo escoge en cada paso al mejor elemento $c simboloraro C$ posible, conocido como el **elemento más prometedor**. Se elimina ese elemento del conjunto de candidatos ($Varias formulas$) y, acto seguido, comprueba si la inclusión de este elemento en el conjunto de elementos seleccionados $Varias formulas$ produce una solución factible.\r\n" + 
				"\r\n" + 
				"En caso de que así sea, se incluye ese elemento en $S.$ Si la inclusión no fuera factible, se descarta el elemento. Iteramos el bucle, comprobando si el conjunto de seleccionados es una solución y, si no es así, pasando al siguiente elemento del conjunto de candidatos.";

		return articles[x];
	}

}
