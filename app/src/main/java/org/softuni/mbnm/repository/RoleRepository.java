package org.softuni.mbnm.repository;

import org.softuni.mbnm.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByAndAuthority(String authority);
}
