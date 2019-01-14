package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbutil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		//create our student db util... and pass in the conn pool/ datasource
		
		try {
			studentDbutil = new StudentDbUtil(dataSource);
			
		}catch(Exception exc ) {
			throw new ServletException(exc);
		}
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//read the "command" parameter
			String theCommand = request.getParameter("comand");
			
			//if the command is missing, then default to listing students
			
			if(theCommand == null) {
				theCommand = "LIST";
			}
			
		    //route to the appropriate method
			
			switch (theCommand) {
			case "LIST":
				listStudents(request, response);
				break;
			case "ADD":
				addStudent(request, response);
				break;
			case "LOAD":
				loadStudent(request, response);
				break;

			default:
				listStudents(request, response);				
			}
			
			//list the students...in MVC fashion
			listStudents(request, response);			
		} catch(Exception exc) {
			throw new ServletException(exc);
		}
			}


	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read student id from form data
		
		String theStudentId = request.getParameter("studentId");
		
		
		//get student from database(db util)
		
		Student student = studentDbutil.getStudents(theStudentId);
		//place student in the request attribute
		
		
		//send to jsp page: update-student-form.jsp
		
		
		
	}


	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read student info from form data
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		
		//create a new student object
		
		Student theStudent = new Student(firstName, lastName, email); 
		
		//add the student to the database
		
		studentDbutil.addStudent(theStudent);
		
		//send back to main page (the student list)
		
		listStudents(request, response);
		
	}


	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//get students from db util
		
		List<Student> students = studentDbutil.getStudents();
		
		// add students to the request
		
		request.setAttribute("STUDENT_LIST", students);
		
		//send to JSP page (view)
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_students.jsp");
		dispatcher.forward(request, response);
		
	}

}
