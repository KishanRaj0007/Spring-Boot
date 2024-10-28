package com.codeforces.codeforces;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProblemController {
    @Autowired
    private ProblemRepository problemRepository;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        List<String> categories = List.of("B", "C", "D");
        // Retrieve counts from repository
        mav.addObject("categories", categories);
        return mav;
    }

    @GetMapping("/problems/{category}")
    public ModelAndView viewProblems(@PathVariable String category) {
        ModelAndView mav = new ModelAndView("problems");
        List<Problems> problems = problemRepository.findByCategory(category);
        long solvedCount = problems.stream().filter(Problems::isSolved).count();
        long totalCount = problems.size(); // Assuming you want total problems count
        mav.addObject("problems", problems);
        mav.addObject("category", category);
        mav.addObject("solvedCount", solvedCount);
        mav.addObject("totalCount", totalCount);
        return mav;
    }

    @PostMapping("/solve/{category}/{id}")
    public String solveProblem(@PathVariable String category, @PathVariable int id) {
        problemRepository.findByCategoryAndId(category, id).ifPresent(problem -> {
            problem.setSolved(true);
            problemRepository.save(problem);
        });
        return "redirect:/problems/" + category;
    }

    @PostMapping("/review/{category}/{id}")
    public String reviewProblem(@PathVariable String category, @PathVariable int id, @RequestParam boolean reviewed) {
    problemRepository.findByCategoryAndId(category, id).ifPresent(problem -> {
        problem.setReviewed(reviewed); // Set the reviewed status
        problemRepository.save(problem);
    });
    return "redirect:/problems/" + category;
}
}
