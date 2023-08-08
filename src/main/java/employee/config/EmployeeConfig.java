package employee.config;

import employee.repo.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories (basePackages = "employee.repo")
public class EmployeeConfig {

}
