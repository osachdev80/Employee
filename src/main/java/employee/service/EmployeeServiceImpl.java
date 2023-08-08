package employee.service;

import employee.exception.NotFoundEmployeeException;
import employee.model.Employee;
import employee.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public Employee addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findByNiNumber(String niNumber) throws NotFoundEmployeeException {
        return employeeRepository.findByNINumber(niNumber).orElseThrow(()
                -> new NotFoundEmployeeException("Employee Not found"));

    }
}
