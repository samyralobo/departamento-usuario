package com.usuario.departamento_usuario.repositories;

import com.usuario.departamento_usuario.models.Department;
import com.usuario.departamento_usuario.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    void deleteAllByDepartment(Department department);


    boolean existsByEmail(String email);
}
