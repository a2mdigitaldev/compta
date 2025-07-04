package com.comptamaroc.salaries.model;

import com.comptamaroc.core.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @NotBlank(message = "Employee number is required")
    @Size(max = 20, message = "Employee number must not exceed 20 characters")
    @Column(name = "employee_number", nullable = false, unique = true, length = 20)
    private String employeeNumber;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    @Column(name = "address", length = 200)
    private String address;

    @NotNull(message = "Hire date is required")
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @NotNull(message = "Base salary is required")
    @DecimalMin(value = "0.0", message = "Base salary must be greater than or equal to 0")
    @Column(name = "base_salary", nullable = false, precision = 15, scale = 2)
    private BigDecimal baseSalary;

    @Size(max = 50, message = "Position must not exceed 50 characters")
    @Column(name = "position", length = 50)
    private String position;

    @Size(max = 50, message = "Department must not exceed 50 characters")
    @Column(name = "department", length = 50)
    private String department;

    @NotNull(message = "Employment status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status", nullable = false)
    private EmploymentStatus employmentStatus;

    // Moroccan specific fields
    @Size(max = 20, message = "CIN must not exceed 20 characters")
    @Column(name = "cin", length = 20)
    private String cin; // Carte d'Identité Nationale

    @Size(max = 20, message = "CNSS number must not exceed 20 characters")
    @Column(name = "cnss_number", length = 20)
    private String cnssNumber; // Caisse Nationale de Sécurité Sociale

    @Size(max = 20, message = "Bank account must not exceed 20 characters")
    @Column(name = "bank_account", length = 20)
    private String bankAccount;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Constructors
    public Employee() {}

    public Employee(String employeeNumber, String firstName, String lastName, LocalDate hireDate, 
                   BigDecimal baseSalary, EmploymentStatus employmentStatus) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.baseSalary = baseSalary;
        this.employmentStatus = employmentStatus;
        this.isActive = true;
    }

    // Getters and setters
    public String getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public LocalDate getTerminationDate() { return terminationDate; }
    public void setTerminationDate(LocalDate terminationDate) { this.terminationDate = terminationDate; }

    public BigDecimal getBaseSalary() { return baseSalary; }
    public void setBaseSalary(BigDecimal baseSalary) { this.baseSalary = baseSalary; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public EmploymentStatus getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(EmploymentStatus employmentStatus) { this.employmentStatus = employmentStatus; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getCnssNumber() { return cnssNumber; }
    public void setCnssNumber(String cnssNumber) { this.cnssNumber = cnssNumber; }

    public String getBankAccount() { return bankAccount; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    // Helper methods
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isCurrentlyEmployed() {
        return isActive && terminationDate == null;
    }

    // Enums
    public enum EmploymentStatus {
        FULL_TIME,     // Temps plein
        PART_TIME,     // Temps partiel
        CONTRACT,      // Contrat
        INTERN,        // Stagiaire
        CONSULTANT,    // Consultant
        TERMINATED     // Terminé
    }
}
