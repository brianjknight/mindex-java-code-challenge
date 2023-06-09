package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.type.ReportingStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        // correction to logger below; Originally showed "Received employee CREATE request for id [{}]" instead of a READ or GET request.
        LOG.debug("Received employee read request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    /**
     * I extended the existing endpoint path so that a reporting structure can easily be found be simply adding "reporting-structure" to the url.
     *
     */
    @GetMapping("/employee/{id}/reporting-structure")
    public ReportingStructure getReportingStructure(@PathVariable String id) {
        LOG.debug("Received employee get request to find Reporting Structure for id [{}]", id);

        return employeeService.getReportingStructure(id);
    }

    /**
     * Endpoint to read the Compensation Repository to retrieve an existing Compensation object.
     * @param id - Id of the employee for whom you want to find their compensation.
     * @return Compensation object including the Employee, their salary, and the effective salary date.
     */
    @GetMapping("/employee/{id}/compensation")
    public Compensation readCompensation(@PathVariable String id) {
        LOG.debug("Received read request compensation for employee id [{}]", id);

        return compensationService.read(id);
    }

    /**
     * Endpoint to create a new compensation object for an employee.
     * @param id - Id of the employee to create the Compensation for.
     * @param salary amount of the employee's salary. The string salary is converted to a Double prior to persisting to the database.
     * @return The compensation persisted to the database.
     */
    @PostMapping("/employee/{id}/compensation/{salary}")
    public Compensation createCompensation(@PathVariable String id, @PathVariable String salary) {
        LOG.debug("Received request to create compensation for employee id [{}]", id);

        Double d = Double.valueOf(salary);

        return compensationService.create(id, d);
    }

}
