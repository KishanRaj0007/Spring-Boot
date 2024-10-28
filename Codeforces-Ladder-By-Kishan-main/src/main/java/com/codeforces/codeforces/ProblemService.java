package com.codeforces.codeforces;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    // Method to get all problems by category
    public List<Problems> getAllProblemsByCategory(String category) {
        return problemRepository.findByCategory(category);
    }

    // Method to mark a problem as solved
    public void markProblemAsSolved(String category, int id) {
        problemRepository.findByCategoryAndId(category, id).ifPresent(problem -> {
            problem.setSolved(true);
            problemRepository.save(problem);
        });
    }

    // Method to mark a problem for review
    public void markProblemForReview(String category, int id) {
        problemRepository.findByCategoryAndId(category, id).ifPresent(problem -> {
            problem.setReviewed(!problem.isReviewed()); //toggle the review status
            problemRepository.save(problem);
        });
    }
}
