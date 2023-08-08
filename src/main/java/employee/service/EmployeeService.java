package employee.service;

import employee.exception.NotFoundEmployeeException;
import employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees() ;

    Employee findByNiNumber(String niNumber) throws NotFoundEmployeeException;
}
