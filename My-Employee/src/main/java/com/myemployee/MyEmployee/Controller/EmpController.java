package com.myemployee.MyEmployee.Controller;

import com.myemployee.MyEmployee.Exceptions.EmployeeNotFoundException;
import com.myemployee.MyEmployee.Model.Employee;
import com.myemployee.MyEmployee.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmpController {
    @Autowired
    private EmployeeRepository employeeRepository;
    //Get All Details from Database
    @GetMapping("/employees")
    List<Employee> getEmployeesDetails(){
        return employeeRepository.findAll();
    }

    //Add Employee details
    @PostMapping("/saveEmployee")
    Employee newEmployee(@RequestBody Employee newEmployee){
        return employeeRepository.save(newEmployee);
    }

    //update Employee Details
    @PutMapping("/update/{id}")
    Employee updateEmployee(@RequestBody Employee newEmployee,@PathVariable Long id){
        return employeeRepository.findById(id).map(employee -> {
                    employee.setFirstName(newEmployee.getFirstName());
                    employee.setLastName(newEmployee.getLastName());
                    employee.setFatherName(newEmployee.getFatherName());
                    employee.setPlace(newEmployee.getPlace());
                    employee.setAge(newEmployee.getAge());
                    employee.setGender(newEmployee.getGender());
                    employee.setDateOfBirth(newEmployee.getDateOfBirth());
                    employee.setEducation(newEmployee.getEducation());
                    employee.setDepartment(newEmployee.getDepartment());
                    return employeeRepository.save(employee);
                }
        ).orElseThrow(()-> new EmployeeNotFoundException(id));
    }

    @DeleteMapping("/delete/{id}")
    String deleteEmployee(@PathVariable Long id){
        if(!employeeRepository.existsById(id)){
            throw new EmployeeNotFoundException(id);
        }else if (employeeRepository.existsById(id))
            employeeRepository.deleteById(id);
        return "Employee with id "+id+" has been deleted successfully..!";
    }

    //Get Employee Details Based on Id
    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable Long id){

        return employeeRepository.findById(id).orElseThrow(()-> new EmployeeNotFoundException(id));
    }
}
