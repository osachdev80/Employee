import com.fasterxml.jackson.databind.ObjectMapper;
import employee.controller.EmployeeController;
import employee.exception.NotFoundEmployeeException;
import employee.model.Employee;
import employee.repo.EmployeeRepository;
import employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@ComponentScan(basePackages = "employee.controller")
@ContextConfiguration(classes = EmployeeService.class)
public class EmployeeControllerTest {


    @MockBean
    private EmployeeRepository employeeRepo ;

    @MockBean
    private EmployeeService employeeService ;

    @Autowired
    private MockMvc mockMvc ;

    @Test
    public void testAddEmployee () throws Exception {
        Employee employee = Employee.builder()
                .firstName("John")
                .surname("Smith")
                .niNumber("WE123222A")
                .build();
        when(employeeRepo.save(employee)).thenReturn(employee);
        mockMvc.perform(MockMvcRequestBuilders.post("/addEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employee)))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).addEmployee(employee);

    }

    @Test
    public void test_getAllEmployees() throws Exception {
        Employee employeeOne = Employee.builder()
                .firstName("John")
                .surname("Smith")
                .niNumber("WE123222A")
                .build();
        Employee employeeTwo = Employee.builder()
                .firstName("John")
                .surname("Smith")
                .niNumber("WE123222A")
                .build();

        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(employeeOne, employeeTwo));

        when(employeeRepo.findAll()).thenReturn(Arrays.asList(employeeOne, employeeTwo));
        mockMvc.perform(MockMvcRequestBuilders.get("/getAllEmployees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Smith"))
                .andExpect(jsonPath("$[0].niNumber").value("WE123222A"));
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    public void test_getEmployeesByNI() throws Exception {
        Employee employeeOne = Employee.builder()
                .firstName("John")
                .surname("Smith")
                .niNumber("WE123222AD")
                .build();
        Employee employeeTwo = Employee.builder()
                .firstName("John")
                .surname("Smith")
                .niNumber("WE123222A")
                .build();

        when(employeeService.findByNiNumber("WE123222AD")).thenReturn(employeeOne);

        when(employeeRepo.findByNINumber("WE123222AD")).thenReturn(Optional.of(employeeOne));
        mockMvc.perform(MockMvcRequestBuilders.get("/getByNiNumber/{niNumber}", "WE123222AD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.surname").value("Smith"))
                .andExpect(jsonPath("$.niNumber").value("WE123222AD"));
        verify(employeeService, times(1)).findByNiNumber("WE123222AD");
    }

    @Test
    public void test_getEmployeesByNI_EmployeeNotFound() throws Exception {
        Employee employeeOne = Employee.builder()
                .firstName("John")
                .surname("Smith")
                .niNumber("WE123222AD")
                .build();
        when(employeeService.findByNiNumber("WE123222AD")).thenThrow(NotFoundEmployeeException.class);

        when(employeeRepo.findByNINumber("WE123222AD")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/getByNiNumber/{niNumber}", "WE123222AD"))
                .andExpect(status().isNotFound());
        verify(employeeService, times(1)).findByNiNumber("WE123222AD");
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
