package com.spring.crud.model;

import com.spring.crud.dto.CrudDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "crud_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crud
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roll number")
    private Integer rollNo;

    @Column(name = "first name")
    @Pattern(regexp = "^[A-Z]{1,}[a-z]{2,}$", message = "First Letter Should Be Capital")
    private String firstName;

    @Column(name = "last name")
    @Pattern(regexp = "^[A-Z]{1,}[a-z]{2,}$", message = "First Letter Should Be Capital")
    private String lastName;

    public Crud(CrudDTO crudDTO) {
        this.firstName = crudDTO.getFirstName();
        this.lastName = crudDTO.getLastName();
    }
}