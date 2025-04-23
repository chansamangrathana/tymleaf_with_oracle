package co.cstad.demo2.controller;

import co.cstad.demo2.model.Employee;
import co.cstad.demo2.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public String viewEmployee(
            @RequestParam(value = "email", required = false, defaultValue = "") String email,
            @RequestParam(value = "jobId", required = false, defaultValue = "") String jobId,
            Model model) {
        List<Employee> employees = employeeService.getEmployeesByEmailAndJobId(email, jobId);
        model.addAttribute("employee", employees);
        model.addAttribute("selectedEmail", email);
        model.addAttribute("selectedJobId", jobId);
        return "employee";
    }
}
