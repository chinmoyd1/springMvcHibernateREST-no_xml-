package com.spring.hibernate.repository;

import com.spring.hibernate.model.Employee;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAO  {

    @Autowired
    private SessionFactory sessionFactory;


    public Employee get(int id) {
            return  sessionFactory.getCurrentSession().get(Employee.class, id);
    }

    public List<Employee> getAll() {
        List employees =  sessionFactory.getCurrentSession().createQuery("FROM Employee").list();
        return employees;
    }


    public long save(Employee employee) {
        sessionFactory.getCurrentSession().save(employee);
        return 0;
    }

    public void update(Employee employee) {
        sessionFactory.getCurrentSession().update(employee);
    }

    public void remove(Employee employee) {
        sessionFactory.getCurrentSession().delete(employee);
    }

}

