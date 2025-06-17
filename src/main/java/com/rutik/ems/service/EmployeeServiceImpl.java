package com.rutik.ems.service;

        import com.rutik.ems.model.Employee;
        import com.rutik.ems.repository.EmployeeRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Override
    public List<Employee> getAll() {
        return repo.findAll();
    }

    @Override
    public Employee getById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Employee create(Employee emp) {
        return repo.save(emp);
    }

    @Override
    public Employee update(int id, Employee emp) {
        Employee existing = repo.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(emp.getName());
            existing.setDepartment(emp.getDepartment());
            existing.setEmail(emp.getEmail());
            existing.setSalary(emp.getSalary());
            return repo.save(existing);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }
}
