package me.afua.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    CourseRepository courseRepository;

    @RequestMapping("/")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "courseform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Course course, BindingResult result) {
        if (result.hasErrors()) {
            return "courseform";
        }
        courseRepository.save(course);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("course", courseRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("course", courseRepository.findById(id));
        return "courseform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        courseRepository.deleteById(id);
        return "redirect:/";
    }


    @GetMapping("/search")
    public String getSearch()
    {
        return "coursesearchform";
    }

    @PostMapping("/search")
    public String showSearchResults(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search",searchString);
        model.addAttribute("courses",courseRepository.findAllByTitleContainingIgnoreCase(searchString));
        return "list";
    }

    @PostMapping("/searchbetween")
    public String showSearchBetweenResults(HttpServletRequest request, Model model){
        int min = Integer.parseInt(request.getParameter("min"));
        int max = Integer.parseInt(request.getParameter("max"));
        String searchString = "Credits between " + min + " and " + max;
        model.addAttribute("search",searchString);
        model.addAttribute("min",min);
        model.addAttribute("max",max);
        model.addAttribute("courses",courseRepository.findAllByCreditsBetween(min,max));
        return "list";
    }
}
