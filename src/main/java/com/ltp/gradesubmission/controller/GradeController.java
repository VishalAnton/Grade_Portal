package com.ltp.gradesubmission.controller;

import javax.validation.Valid;
import com.ltp.gradesubmission.connectpostgresqldb.GradeEntity;
import com.ltp.gradesubmission.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/")
    public String gradeForm(Model model, @RequestParam(required = false) String id) {
        GradeEntity grade = new GradeEntity();
        if (id != null) {
            grade = gradeService.getGradeById(id);
        }
        model.addAttribute("grade", grade);
        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submitForm(@ModelAttribute("grade") @Valid GradeEntity grade, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please correct the errors and try again.");
            return "redirect:/";
        }
        gradeService.submitGrade(grade);
        redirectAttributes.addFlashAttribute("success", "Grade submitted successfully.");
        return "redirect:/grades";
    }

    @GetMapping("/grades")
    public String getGrades(Model model) {
        model.addAttribute("grades", gradeService.getGrades());
        return "grades";
    }
}
