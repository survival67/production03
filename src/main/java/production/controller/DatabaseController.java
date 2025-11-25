package production.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller; // Змінився імпорт
import org.springframework.ui.Model;            // Для передачі даних в HTML
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller 
@RequestMapping("/sql")
public class DatabaseController {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Показати форму (GET запит)
    @GetMapping
    public String showForm() {
        return "query-form"; // Повертає файл templates/sql-form.html
    }

    // 2. Обробити команду (POST запит)
    @PostMapping
    public String executeSql(@RequestParam("query") String sql, Model model) {
        String cleanSql = sql.trim();
        model.addAttribute("query", cleanSql);

        try {
            String upper = cleanSql.toUpperCase();

            // -------------------------- SELECT --------------------------
            if (upper.startsWith("SELECT")) {
                List<Map<String, Object>> result = jdbcTemplate.queryForList(cleanSql);

                if (result.isEmpty()) {
                    model.addAttribute("message", "Запит не повернув даних.");
                } else {
                    model.addAttribute("resultList", result);
                    model.addAttribute("headers", result.get(0).keySet());
                }

                return "query-result";
            }

            // -------------------------- INSERT / UPDATE / DELETE --------------------------
            int rows = jdbcTemplate.update(cleanSql);
            model.addAttribute("message", "Успішно! Змінено рядків: " + rows);

            // Визначаємо таблицю
            String table = extractTableName(cleanSql);
            if (table != null) {
                String selectAll = "SELECT * FROM " + table;
                List<Map<String, Object>> full = jdbcTemplate.queryForList(selectAll);

                if (!full.isEmpty()) {
                    model.addAttribute("resultList", full);
                    model.addAttribute("headers", full.get(0).keySet());
                }
            }

        } catch (Exception e) {
            model.addAttribute("error", "Помилка виконання SQL: " + e.getMessage());
        }

        return "query-result";
    }

    private String extractTableName(String sql) {
        String lower = sql.toLowerCase();

        if (lower.startsWith("insert into")) {
            return lower.split("\\s+")[2];
        }
        if (lower.startsWith("update")) {
            return lower.split("\\s+")[1];
        }
        if (lower.startsWith("delete from")) {
            return lower.split("\\s+")[2];
        }
        return null;
    }
}