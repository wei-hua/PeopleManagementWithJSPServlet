package personManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Dao personDao;
	
	//private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public void init(){
//		String jdbcUrl=getServletContext().getInitParameter("jdbcUrl");
//		String jdbcUsername=getServletContext().getInitParameter("jdbcUsername");
//		String jdbcPassword=getServletContext().getInitParameter("jdbcPassword");
		
		personDao=new Dao();
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
				try {
					insertPerson(request,response);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
				case "/delete":
				try {
					deletePerson(request,response);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
				case "/update":
				try {
					updatePerson(request,response);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
				case "/edit":
				try {
					showEditForm(request,response);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
				default:
				try {
					listPerson(request,response);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	
	private void insertPerson(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException, NamingException{
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		Person person=new Person(id,name,age);
		personDao.addPerson(person);
		response.sendRedirect("list");
	}
	
	private void deletePerson(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException, NamingException{
		int id=Integer.parseInt(request.getParameter("id"));
		Person person=new Person(id);
		personDao.deletePerson(person);
		response.sendRedirect("list");
	}
	
	private void updatePerson(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException,NamingException{
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		Person person=new Person(id,name,age);
		personDao.updatePerson(person);
		response.sendRedirect("list");
	}
	private void showEditForm(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException,NamingException{
		int id=Integer.parseInt(request.getParameter("id"));
		Person existingPerson=personDao.getPersonById(id);
		RequestDispatcher dispatcher=request.getRequestDispatcher("PersonForm.jsp");
		request.setAttribute("person", existingPerson);
		dispatcher.forward(request, response);
	}
	private void listPerson(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException,NamingException{
		List<Person> listPerson=personDao.listAllPerson();
		RequestDispatcher dispatcher=request.getRequestDispatcher("PersonList.jsp");
		request.setAttribute("listPerson", listPerson);
		dispatcher.forward(request, response);
		
	}
}
