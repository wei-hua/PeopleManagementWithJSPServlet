package personManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;



public class Dao {
//	private String jdbcUrl;
//	private String jdbcPassword;
//	private String jdbcUsername;
//	private Connection jdbcConn;
//	
//	public Dao(String jdbcUrl, String jdbcPassword, String jdbcUsername) {
//		super();
//		this.jdbcUrl = jdbcUrl;
//		this.jdbcPassword = jdbcPassword;
//		this.jdbcUsername = jdbcUsername;
//	}
//	
//	protected void connect()throws SQLException{
//		if(jdbcConn==null || jdbcConn.isClosed()){
//			try{
//				Class.forName("com.mysql.jdbc.Driver");
//			}catch(ClassNotFoundException e){
//				throw new SQLException(e);
//			}
//			jdbcConn=DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
//		}
//	}
	private Connection jdbcConn;
	private final String JNDINAME="java:comp/env/jndi/mysql";
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	protected void connect() throws NamingException, SQLException{
		Context ctx=new InitialContext();
		DataSource ds=(DataSource)ctx.lookup(JNDINAME);
		jdbcConn=ds.getConnection();
		logger.info(jdbcConn);
		
	}
	
	protected void disconnect() throws SQLException{
		if(jdbcConn != null & !jdbcConn.isClosed()){
			jdbcConn.close();
		}
	}
	
	public boolean addPerson(Person person)throws SQLException, NamingException{
		String sql="INSERT INTO person(id,name,age) VALUES (?,?,?)";
		connect();
		PreparedStatement statement=jdbcConn.prepareStatement(sql);
		
		statement.setInt(1, person.getId());
		statement.setString(2, person.getName());
		statement.setString(3, person.getAge());
		
		boolean inserted=statement.executeUpdate()>0;
		statement.close();
		disconnect();
		return inserted;
	}
	
	public boolean deletePerson(Person person) throws SQLException, NamingException{
		String sql="DELETE FROM person WHERE id=?";
		connect();
		PreparedStatement statement=jdbcConn.prepareStatement(sql);
		
		statement.setInt(1, person.getId());
		
		boolean deleted=statement.executeUpdate()>0;
		statement.close();
		disconnect();
		return deleted;
	}
	
	public boolean updatePerson(Person person) throws SQLException, NamingException{
		String sql="UPDATE person SET name=?,age=? where id=?";
		connect();
		PreparedStatement statement=jdbcConn.prepareStatement(sql);
		
		statement.setString(1, person.getName());
		statement.setString(2, person.getAge());
		statement.setInt(3, person.getId());
		
		boolean updated=statement.executeUpdate()>0;
		statement.close();
		disconnect();
		return updated;
	}
	
	public List<Person> listAllPerson() throws SQLException, NamingException{
		List<Person> listPerson=new ArrayList<>();
		
		String sql="select * from person";
		connect();
		logger.info("jdbcConn2"+jdbcConn);
		logger.error("connection wrong!");
			Statement statement = jdbcConn.createStatement();
			
			logger.error("wrong!");
			ResultSet resultSet=statement.executeQuery(sql);
			while(resultSet.next()){
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				String age=resultSet.getString("age");
				
				Person person=new Person(id,name,age);
				listPerson.add(person);
			}
			resultSet.close();
			statement.close();
			
			disconnect();
			
			return listPerson;
	}
	
	public Person getPersonById(int id) throws SQLException, NamingException{
		Person person=null;
		String sql="select * from person where id=?";
		connect();
		PreparedStatement statement=jdbcConn.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet=statement.executeQuery();
		
		if(resultSet.next()){
			String name=resultSet.getString("name");
			String age=resultSet.getString("age");
			
			person=new Person(id,name,age);
			
		}
		
		resultSet.close();
		statement.close();
		
		disconnect();
		
		return person;
	}
}
