package com.codeforces.codeforces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problems, Integer> {
    List<Problems> findByCategory(String category);
    Optional<Problems> findByCategoryAndId(String category, int id);
}
