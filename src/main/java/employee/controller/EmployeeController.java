package employee.controller;

import employee.exception.NotFoundEmployeeException;
import employee.model.Employee;
//import employee.repo.EmployeeRepo;
import employee.repo.EmployeeRepository;
import employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class EmployeeController {


    private EmployeeService employeeService;

    @Autowired
    public EmployeeController( EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping(path = "/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return  ResponseEntity.ok().body(employee);
    }

    @GetMapping(path = "/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return  ResponseEntity.ok().body(employeeService.getAllEmployees());

    }

    @GetMapping(path = "/getByNiNumber/{niNumber}")
    public ResponseEntity<Employee> getEmployeeByNINumber(@PathVariable String niNumber) {
        try {
            return  ResponseEntity.ok().body(employeeService.findByNiNumber(niNumber));
        } catch (NotFoundEmployeeException e) {
            log.error("Employee with NI Number {} not found", niNumber);
            return ResponseEntity.notFound().build();
        }
    }
}
