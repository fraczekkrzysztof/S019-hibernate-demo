package com.luv2code.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Employee;
import com.luv2code.hibernate.demo.entity.Student;

public class EmployeeDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Employee.class)
				.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();
		// rozpoczêcie transakcji
		session.beginTransaction();

		try {
			// create Employee
			Employee emp1 = new Employee("Jan", "Kowalski", "Firma1");
			Employee emp2 = new Employee("Stefan", "Kowalewicz", "Firma2");
			Employee emp3 = new Employee("Andrzej", "Szczelba", "Firma2");

			session.save(emp1);
			session.save(emp2);
			session.save(emp3);

			// commit
			session.getTransaction().commit();

			// pobranie obiektu o id 1
			session = factory.getCurrentSession();
			session.beginTransaction();
			Employee tempEmpl = session.get(Employee.class, 1);
			System.out.println("Pobra³em pracownika : " + tempEmpl);
			session.getTransaction().commit();

			// pobranie pracowników z formy "Firma2"
			session = factory.getCurrentSession();
			session.beginTransaction();
			List<Employee> employees = session.createQuery("from Employee e where e.company = 'Firma2'")
					.getResultList();
			System.out.println("Lista pracowników z Firma2");
			for (Employee empl : employees) {
				System.out.println(empl);
			}
			session.getTransaction().commit();
			
			//usuniecie pracownika o id = 3
			session = factory.getCurrentSession();
			session.beginTransaction();
			Employee tempEmp2 = session.get(Employee.class, 3);
			System.out.println("Usuniêcie pracownika o id=3");
			session.delete(tempEmp2);
			session.getTransaction().commit();

		} finally {
			factory.close();
		}

	}

}
