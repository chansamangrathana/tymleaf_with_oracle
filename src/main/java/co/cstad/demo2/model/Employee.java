package co.cstad.demo2.model;

import lombok.Data;

import java.util.Date;

@Data
public class Employee {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private Date hireDate;
        private String jobId;
        private Double salary;
        private Long managerId;
        private Double departmentId;
}
