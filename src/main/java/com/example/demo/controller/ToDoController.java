package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.ToDo;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.ToDoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOpt.get();
        model.addAttribute("todos", toDoService.getTodosByUser(user));
        model.addAttribute("newTodo", new ToDo());

        // Tambahkan username untuk ditampilkan di halaman home
        model.addAttribute("name", user.getUsername());

        return "home";
    }

    @PostMapping("/add")
public String addTodo(@Valid @ModelAttribute("newTodo") ToDo todo, BindingResult bindingResult, Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    Optional<User> userOpt = userRepository.findByUsername(username);

    if (bindingResult.hasErrors()) {
        if (userOpt.isPresent()) {
            model.addAttribute("todos", toDoService.getTodosByUser(userOpt.get()));
            model.addAttribute("name", userOpt.get().getUsername());
        }
        return "home"; // render ulang dengan data lengkap
    }

    if (userOpt.isPresent()) {
        todo.setUser(userOpt.get());
        toDoService.saveTodo(todo);
    }

    return "redirect:/"; // redirect supaya tidak re-submit form
}


    @PostMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        toDoService.deleteTodo(id);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateTodo(@PathVariable Long id) {
        Optional<ToDo> todo = toDoService.getTodoById(id);
        todo.ifPresent((ToDo t) -> {
            t.setCompleted(!t.isCompleted());
            toDoService.saveTodo(t);
        });
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ToDo todo = toDoService.getTodoById(id).orElseThrow();
        model.addAttribute("todo", todo);
        return "edit";
    }

    @PostMapping("/edit")
    public String updateTodoSubmit(@ModelAttribute ToDo todo) {
        ToDo existing = toDoService.getTodoById(todo.getId()).orElseThrow();
        existing.setTask(todo.getTask());
        toDoService.saveTodo(existing);
        return "redirect:/";
    }
}
