package production.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import production.model.Details;
import production.services.ComponentsService;
import production.services.DetailsService;

@Controller
public class WebDetailsController {

    private final DetailsService detailsService;
    private final ComponentsService componentsService;

    public WebDetailsController(DetailsService detailsService, ComponentsService componentsService) {
        this.detailsService = detailsService;
        this.componentsService = componentsService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/details")
    public String showDetails(Model model) {
        model.addAttribute("details", detailsService.findAll());
        return "details";
    }

    @GetMapping("/admin/details/form")
    @PreAuthorize("hasRole('ADMIN')")
    public String detailForm(@RequestParam(required = false) Integer id, Model model) {
        // Завантажуємо список компонентів для вибору батьківського вузла
        model.addAttribute("componentsList", componentsService.findAll());

        if (id != null) {
            // Шукаємо існуючу деталь
            Details detail = detailsService.findById(id);
            // Якщо не знайдено, створюємо нову
            if (detail == null) {
                detail = new Details(null, "", "", 0, null);
            }
            model.addAttribute("detail", detail);
        } else {
            model.addAttribute("detail", new Details(null, "", "", 0, null));
        }
        return "detail-form";
    }

    @PostMapping("/admin/details/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveDetail(@ModelAttribute Details detail) {
        detailsService.save(detail);
        return "redirect:/details";
    }

    @PostMapping("/admin/details/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteDetail(@PathVariable Integer id) {
        detailsService.deleteById(id);
        return "redirect:/details";
    }
}