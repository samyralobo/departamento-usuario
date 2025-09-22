package com.usuario.departamento_usuario.repositories;

import com.usuario.departamento_usuario.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByDepartmentName(String departmentName);

    List<Department> findByUser(Department department);

}
