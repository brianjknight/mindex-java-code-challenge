package com.mindex.challenge;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.impl.CompensationServiceImpl;
import com.mindex.challenge.service.impl.EmployeeServiceImpl;
import com.mindex.challenge.type.ReportingStructure;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChallengeApplicationTests {

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	@InjectMocks
	private CompensationServiceImpl compensationServiceImpl;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private CompensationRepository compensationRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testEmployeeServiceImpl_getReportingStructure() {
		// GIVEN
		Employee donkey = new Employee();
		donkey.setEmployeeId("ID2");
		donkey.setFirstName("Donkey");
		donkey.setLastName("Kong");
		donkey.setPosition("Associate");
		donkey.setDepartment("Software");

		List<Employee> list = new ArrayList<>();
		list.add(donkey);
		Employee brian = new Employee();
		brian.setEmployeeId("ID1");
		brian.setFirstName("Brian");
		brian.setLastName("Knight");
		brian.setPosition("Manager");
		brian.setDepartment("Software");
		brian.setDirectReports(list);

		when(employeeRepository.findByEmployeeId("ID1")).thenReturn(brian);
		when(employeeRepository.findByEmployeeId("ID2")).thenReturn(donkey);

		// WHEN
		ReportingStructure brianReportingStructure = employeeServiceImpl.getReportingStructure("ID1");
		// THEN
		Assertions.assertEquals(1, brianReportingStructure.getNumberOfReports(), "Expected employeeId for Brian to have 1 reports.");

		// WHEN
		ReportingStructure donkeyReportingStructure = employeeServiceImpl.getReportingStructure("ID2");
		// THEN
		Assertions.assertEquals(0, donkeyReportingStructure.getNumberOfReports(), "Expected employeeId for Donkey to have 0 reports.");

		// WHEN & THEN
		Assertions.assertThrows(RuntimeException.class, () -> employeeServiceImpl.getReportingStructure("non-existing id"));

	}

	@Test
	public void testCompensationServiceImpl_create() {
		// GIVEN
		String id = "ID2";
		Double salary = 100.00;

		Employee donkey = new Employee();
		donkey.setEmployeeId(id);
		donkey.setFirstName("Donkey");
		donkey.setLastName("Kong");
		donkey.setPosition("Associate");
		donkey.setDepartment("Software");

		Compensation donkeyCompensation = new Compensation();
		donkeyCompensation.setEmployeeId(donkey.getEmployeeId());
		donkeyCompensation.setEmployee(donkey);
		donkeyCompensation.setSalary(salary);
		donkeyCompensation.setEffectiveDate(LocalDateTime.now());

		when(employeeRepository.findByEmployeeId(id)).thenReturn(donkey);
		when(compensationRepository.insert(any(Compensation.class))).thenReturn(donkeyCompensation);

		// WHEN
		Compensation result = compensationServiceImpl.create(id, salary);
		// THEN
		Assertions.assertEquals(donkeyCompensation.getEmployeeId(), result.getEmployeeId());
		Assertions.assertEquals(donkeyCompensation.getEmployee(), result.getEmployee());
		Assertions.assertEquals(donkeyCompensation.getSalary(), result.getSalary());

		// WHEN & THEN
		Assertions.assertThrows(RuntimeException.class, () -> compensationServiceImpl.create("not an id", salary));

	}

	@Test
	public void testCompensationServiceImpl_read() {
		// GIVEN
		String id = "ID2";
		Double salary = 100.00;

		Employee donkey = new Employee();
		donkey.setEmployeeId(id);
		donkey.setFirstName("Donkey");
		donkey.setLastName("Kong");
		donkey.setPosition("Associate");
		donkey.setDepartment("Software");

		Compensation donkeyCompensation = new Compensation();
		donkeyCompensation.setEmployeeId(donkey.getEmployeeId());
		donkeyCompensation.setEmployee(donkey);
		donkeyCompensation.setSalary(salary);
		donkeyCompensation.setEffectiveDate(LocalDateTime.now());

		when(compensationRepository.findByEmployeeId(id)).thenReturn(donkeyCompensation);

		// WHEN
		Compensation result = compensationServiceImpl.read(id);
		// THEN
		Assertions.assertEquals(donkeyCompensation.getEmployeeId(), result.getEmployeeId());
		Assertions.assertEquals(donkeyCompensation.getEmployee(), result.getEmployee());
		Assertions.assertEquals(donkeyCompensation.getSalary(), result.getSalary());

		// WHEN & THEN
		Assertions.assertThrows(RuntimeException.class, () -> compensationServiceImpl.read("not an id"));
	}

}
