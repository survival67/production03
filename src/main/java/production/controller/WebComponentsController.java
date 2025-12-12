package production.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import production.model.Components;
import production.services.ComponentsService;
import production.services.ProductService;

@Controller
public class WebComponentsController {

    private final ComponentsService componentsService;
    private final ProductService productService;

    public WebComponentsController(ComponentsService componentsService, ProductService productService) {
        this.componentsService = componentsService;
        this.productService = productService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/components")
    public String showComponents(Model model) {
        model.addAttribute("components", componentsService.findAll());
        return "components";
    }

    @GetMapping("/admin/components/form")
    @PreAuthorize("hasRole('ADMIN')")
    public String componentForm(@RequestParam(required = false) Integer id, Model model) {
        // Завантажуємо список виробів для випадаючого списку
        model.addAttribute("productsList", productService.findAll());

        if (id != null) {
            // Шукаємо існуючий компонент
            Components component = componentsService.findById(id);
            if (component == null) {
                component = new Components(null, "", "", null);
            }
            model.addAttribute("component", component);
        } else {
            model.addAttribute("component", new Components(null, "", "", null));
        }
        return "component-form";
    }

    @PostMapping("/admin/components/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveComponent(@ModelAttribute Components component) {
        componentsService.save(component);
        return "redirect:/components";
    }

    @PostMapping("/admin/components/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteComponent(@PathVariable Integer id) {
        componentsService.deleteById(id);
        return "redirect:/components";
    }
}