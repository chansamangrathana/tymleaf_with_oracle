package co.cstad.demo2.repository;

import co.cstad.demo2.model.Employee;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall filterEmployeeCall;

    @PostConstruct
    private void init() {
        this.filterEmployeeCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GETALLEMPLOYEES")
                .withSchemaName("C##ORA1")
                .declareParameters(
                        new SqlParameter("p_job_id", Types.VARCHAR),
                        new SqlOutParameter("emp_cursor", OracleTypes.CURSOR, new EmployeeRowMapper())
                );
    }

    public List<Employee> findByEmailAndJobId(String email, String jobId) {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_job_id", jobId);

        Map<String, Object> result = filterEmployeeCall.execute(inParams);
        List<Employee> allEmployees = (List<Employee>) result.get("emp_cursor");

        if (email == null || email.trim().isEmpty()) {
            return allEmployees;
        }

        return allEmployees.stream()
                .filter(emp -> email.equalsIgnoreCase(emp.getEmail()))
                .collect(Collectors.toList());
    }
    public void save(Employee employee) {
        if (employee.getId() == null) {
            String sql = "INSERT INTO EMPLOYEE_LIST (first_name, last_name, email, hire_date, job_id, salary, manager_id, department_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getHireDate(), employee.getJobId(), employee.getSalary(), employee.getManagerId(), employee.getDepartmentId());
        } else {
            String sql = "UPDATE EMPLOYEE_LIST SET first_name = ?, last_name = ?, email = ?, hire_date = ?, job_id = ?, salary = ?, manager_id = ?, department_id = ? WHERE employee_id = ?";
            jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getHireDate(), employee.getJobId(), employee.getSalary(), employee.getManagerId(), employee.getDepartmentId(), employee.getId());
        }
    }
    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee c = new Employee();
            c.setId(rs.getLong("EMPLOYEE_ID"));
            c.setFirstName(rs.getString("FIRST_NAME"));
            c.setLastName(rs.getString("LAST_NAME"));
            c.setEmail(rs.getString("EMAIL"));
//          c.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            c.setHireDate(rs.getDate("HIRE_DATE"));
            c.setJobId(rs.getString("JOB_ID"));
            c.setSalary(rs.getDouble("SALARY"));
            c.setManagerId(rs.getLong("MANAGER_ID"));
            c.setDepartmentId(rs.getDouble("DEPARTMENT_ID"));
            return c;
        }
    }
}
