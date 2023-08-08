package employee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Builder
@ToString
@EqualsAndHashCode (of = "niNumber")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    private String niNumber;
    private String firstName;
    private String surname;
}
