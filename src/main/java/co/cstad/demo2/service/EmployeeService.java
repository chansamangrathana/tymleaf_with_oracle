package co.cstad.demo2.service;

import co.cstad.demo2.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeesByEmailAndJobId(String email, String jobId);
    void saveEmployee(Employee employee);

}