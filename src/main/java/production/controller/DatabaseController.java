package production.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import production.model.Product;
import production.model.Components;
import production.model.Details;
import production.services.ComponentsService;
import production.services.DetailsService;
import production.services.ProductService;

import java.security.Principal;
import java.util.*;

@Controller
public class DatabaseController {

    private final ProductService productService;
    private final ComponentsService componentsService;
    private final DetailsService detailsService;
    private final ObjectMapper objectMapper;

    public DatabaseController(ProductService ps, ComponentsService cs, DetailsService ds, ObjectMapper om) {
        this.productService = ps;
        this.componentsService = cs;
        this.detailsService = ds;
        this.objectMapper = om;
    }

    @GetMapping("/")
    public String showWelcomePage() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }

    @PostMapping("/dashboard/execute")
    public String executeCommand(@RequestParam("query") String jsonInput, Model model, Principal principal) {
        
        model.addAttribute("lastCommand", jsonInput); 

        try {
            RequestController requestDto = objectMapper.readValue(jsonInput, RequestController.class);

            // Отримуємо права
            Authentication auth = (Authentication) principal;
            boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
            String action = requestDto.action;
            if (action == null) action = "";
            
            // Якщо не адмін
            if (!isAdmin && !action.equalsIgnoreCase("select")) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: Only SELECT is allowed for Users");
            }

            // Логіка виконання
            String entity = requestDto.entity;
            if (entity == null) entity = "";
            entity = entity.toLowerCase();
            action = action.toLowerCase();

            switch (action) {
                case "select" -> handleSelect(entity, model);
                case "insert" -> handleInsert(entity, requestDto.data, model);
                case "update" -> handleUpdate(entity, requestDto.data, model);
                case "delete" -> handleDelete(entity, requestDto.data, model);
                default -> throw new IllegalArgumentException("Невідома дія: " + action);
            }

        } catch (ResponseStatusException e) {
            throw e; // Помилка доступу далі до сторінки 403
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Помилка виконання: " + e.getMessage());
        }

        return "dashboard"; 
    }

    // Private Methods

    private void handleSelect(String entity, Model model) {
        switch (entity) {
            case "product", "products" -> model.addAttribute("productsList", productService.findAll());
            case "component", "components" -> model.addAttribute("componentsList", componentsService.findAll());
            case "detail", "details" -> model.addAttribute("detailsList", detailsService.findAll());
            default -> throw new IllegalArgumentException("Невідома сутність: " + entity);
        }
    }

    private void handleInsert(String entity, Map<String, Object> data, Model model) {
        switch (entity) {
            case "product", "products" -> {
                Product p = objectMapper.convertValue(data, Product.class);
                if (p.getId() == null) p = new Product(UUID.randomUUID(), p.getName(), p.getSerialNumber(), p.getCategory());
                p.setIsNew(true);
                productService.save(p);
                handleSelect("products", model);
            }
            case "component", "components" -> {
                Components c = objectMapper.convertValue(data, Components.class);
                componentsService.save(c);
                handleSelect("components", model);
            }
            case "detail", "details" -> {
                Details d = objectMapper.convertValue(data, Details.class);
                detailsService.save(d);
                handleSelect("details", model);
            }
        }
        model.addAttribute("message", "Успішно додано!");
    }

    private void handleUpdate(String entity, Map<String, Object> data, Model model) {
        switch (entity) {
            case "product", "products" -> {
                Product p = objectMapper.convertValue(data, Product.class);
                p.setIsNew(false);
                productService.save(p);
                handleSelect("products", model);
            }
            case "component", "components" -> {
                Components c = objectMapper.convertValue(data, Components.class);
                componentsService.save(c);
                handleSelect("components", model);
            }
            case "detail", "details" -> {
                Details d = objectMapper.convertValue(data, Details.class);
                detailsService.save(d);
                handleSelect("details", model);
            }
        }
        model.addAttribute("message", "Успішно оновлено!");
    }

    private void handleDelete(String entity, Map<String, Object> data, Model model) {
        Object idVal = data.get("id");
        if (idVal == null) throw new IllegalArgumentException("ID не вказано");

        switch (entity) {
            case "product", "products" -> {
                productService.deleteById(UUID.fromString(idVal.toString()));
                handleSelect("products", model);
            }
            case "component", "components" -> {
                componentsService.deleteById(Integer.parseInt(idVal.toString()));
                handleSelect("components", model);
            }
            case "detail", "details" -> {
                detailsService.deleteById(Integer.parseInt(idVal.toString()));
                handleSelect("details", model);
            }
        }
        model.addAttribute("message", "Успішно видалено!");
    }

    public static class RequestDto {
        public String action;
        public String entity;
        public Map<String, Object> data;
    }
}