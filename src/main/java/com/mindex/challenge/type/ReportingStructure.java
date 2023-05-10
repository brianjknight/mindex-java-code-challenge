package com.mindex.challenge.type;

import com.mindex.challenge.data.Employee;

import java.util.Objects;

/**
 * This is a POJO for the ReportingStructure type. I created this under a new 'type' package since it will not be persisted data.
 */
public class ReportingStructure {
    private Employee employee;
    private Integer numberOfReports;

    public ReportingStructure() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(Integer numberOfReports) {
        this.numberOfReports = numberOfReports;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportingStructure that = (ReportingStructure) o;
        return Objects.equals(employee, that.employee) && Objects.equals(numberOfReports, that.numberOfReports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, numberOfReports);
    }

    @Override
    public String toString() {
        return "ReportingStructure{" +
                "employee=" + employee +
                ", numberOfReports=" + numberOfReports +
                '}';
    }
}