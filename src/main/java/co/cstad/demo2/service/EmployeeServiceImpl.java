package co.cstad.demo2.service;

import co.cstad.demo2.model.Employee;
import co.cstad.demo2.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployeesByEmailAndJobId(String email, String jobId) {
        return employeeRepository.findByEmailAndJobId(email, jobId);
    }
}
