package org.softuni.mbnm.repository;

import org.softuni.mbnm.domain.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {

    Optional<Log> findLogByUsername(String username);

}
