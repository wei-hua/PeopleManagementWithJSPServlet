package personManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Dao personDao;
	
	public void init(){
		String jdbcUrl=getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername=getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword=getServletContext().getInitParameter("jdbcPassword");
		
		personDao=new Dao(jdbcUrl,jdbcUsername,jdbcPassword);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String action=request.getServletPath(); //
		try{
			switch(action){
				case "/new":
					showNewForm(request,response);
					break;
				case "/insert":
					insertPerson(request,response);
					break;
				case "/delete":
					deletePerson(request,response);
					break;
				case "/update":
					updatePerson(request,response);
					break;
				case "/edit":
					showEditForm(request,response);
					break;
				default:
					listPerson(request,response);
					break;
			}
		}catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	private void showNewForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher=request.getRequestDispatcher("PersonForm.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertPerson(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException{
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		Person person=new Person(id,name,age);
		personDao.addPerson(person);
		response.sendRedirect("list");
	}
	
	private void deletePerson(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException{
		int id=Integer.parseInt(request.getParameter("id"));
		Person person=new Person(id);
		personDao.deletePerson(person);
		response.sendRedirect("list");
	}
	
	private void updatePerson(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException{
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		Person person=new Person(id,name,age);
		personDao.updatePerson(person);
		response.sendRedirect("list");
	}
	private void showEditForm(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException{
		int id=Integer.parseInt(request.getParameter("id"));
		Person existingPerson=personDao.getPersonById(id);
		RequestDispatcher dispatcher=request.getRequestDispatcher("PersonForm.jsp");
		request.setAttribute("person", existingPerson);
		dispatcher.forward(request, response);
	}
	private void listPerson(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException{
		List<Person> listPerson=personDao.listAllPerson();
		RequestDispatcher dispatcher=request.getRequestDispatcher("PersonList.jsp");
		request.setAttribute("listPerson", listPerson);
		dispatcher.forward(request, response);
		
	}
}