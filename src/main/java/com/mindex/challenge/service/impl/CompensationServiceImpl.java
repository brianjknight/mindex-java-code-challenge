package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    CompensationRepository compensationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Compensation create(String employeeId, Double salary) {
        LOG.debug("Creating compensation for employee id: [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if(employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        Compensation compensation = new Compensation();
        compensation.setEmployeeId(employeeId);
        compensation.setEmployee(employee);
        compensation.setSalary(salary);
        compensation.setEffectiveDate(LocalDateTime.now());


        return compensationRepository.insert(compensation);
    }

    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Reading compensation for employee id : [{}]", employeeId);

        Compensation compensation = compensationRepository.findByEmployeeId(employeeId);

        if(compensation == null) {
            throw new RuntimeException("Invalid employeeId:" + employeeId);
        }

        return compensation;
    }
}
