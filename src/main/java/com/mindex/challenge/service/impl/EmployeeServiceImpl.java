package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.type.ReportingStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        // Original message was "Creating employee with id [{}]" instead of GETTING or READING
        LOG.debug("Reading employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    public ReportingStructure getReportingStructure(String id) {
        // First read the database to see if the Employee exists.
        Employee employee = this.read(id);

        //Uses a private helper method to calculate the number of reports.
        Integer numberOfReports = this.getNumberOfReports(employee);
        System.out.println("numberOfReports = " + numberOfReports);

        //Instantiate a new ReportingStructure object:
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(numberOfReports);

        System.out.println("ReportingStructure from Service " + reportingStructure);

        return reportingStructure;
    }

    /**
     * Recursive helper method to calculate the number of successive direct reports falling under a given employee.
     * @param employee the employee for which to find the number of reports.
     * @return Integer representing the number of reports.
     */
    private Integer getNumberOfReports(Employee employee) {
        int numberOfReports = 0;

        // If statement includes the base case for an employee that has null List<Employee> directReports.
        // Otherwise, the recursive call is made.
        if (employee.getDirectReports() != null) {
            numberOfReports += employee.getDirectReports().size();

            for (Employee e : employee.getDirectReports()) {
                Employee employee1 = this.read(e.getEmployeeId());
                numberOfReports += getNumberOfReports(employee1);
            }
        }

        return numberOfReports;
    }
}
