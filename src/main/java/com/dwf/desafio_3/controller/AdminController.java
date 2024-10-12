package com.dwf.desafio_3.controller;

import com.dwf.desafio_3.entity.Usuario;
import com.dwf.desafio_3.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @ModelAttribute("currentUser")
    public Usuario currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Optional<Usuario> currentUser = userService.GetCurrentUser(userDetails.getUsername());
            return currentUser.orElse(null);
        }
        return null;
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public ModelAndView adminDashboardPage(@ModelAttribute("currentUser") Usuario currentUser) {
        ModelAndView mav = new ModelAndView("/admin/dashboard");
        mav.addObject("usuario", currentUser);
        return mav;
    }

    @GetMapping("/admin/usuarios")
    public ModelAndView adminUsersPage(@ModelAttribute("currentUser") Usuario currentUser) {
        List<Usuario> allUsers = userService.ListAllUsers();
        ModelAndView mav = new ModelAndView("/admin/usuarios");
        mav.addObject("allUsers", allUsers);
        mav.addObject("usuario", currentUser);
        return mav;
    }

    @GetMapping("/admin/create-user")
    public ModelAndView getCreateUser(@ModelAttribute("currentUser") Usuario currentUser) {
        Usuario newUser = new Usuario();
        ModelAndView mav = new ModelAndView("/admin/create_user");
        mav.addObject("usuario", currentUser);
        mav.addObject("newUser", newUser);
        return mav;
    }

    @PostMapping("/admin/usuarios/eliminar/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/admin/usuarios");

        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "Usuario eliminado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar el usuario.");
        }

        return mav;
    }


    @PostMapping("/admin/create-user")
    public ModelAndView createUser(@Valid @ModelAttribute("newUser") Usuario newUser,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Hay errores en el formulario, por favor verifica los campos.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newUser", result);
            redirectAttributes.addFlashAttribute("newUser", newUser);
            return new ModelAndView("redirect:/admin/create-user");
        }

        if (userService.existsByEmail(newUser.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Error: El email ya está en uso.");
            return new ModelAndView("redirect:/admin/create-user");
        }

        if (userService.existsByNombre(newUser.getNombre())) {
            redirectAttributes.addFlashAttribute("error", "Error: El nombre ya está en uso.");
            return new ModelAndView("redirect:/admin/create-user");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = newUser.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        newUser.setPassword(encodedPassword);

        try {
            userService.createUser(newUser);
            redirectAttributes.addFlashAttribute("message", "Usuario Creado Correctamente!");
            return new ModelAndView("redirect:/admin/usuarios");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Error: El nombre o email ya está en uso.");
            return new ModelAndView("redirect:/admin/create-user");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al crear el usuario.");
            return new ModelAndView("redirect:/admin/create-user");
        }
    }

    @GetMapping("/admin/edit-user/{id}")
    public ModelAndView goEditUser(@PathVariable Long id, @ModelAttribute("currentUser") Usuario currentUser) {
        boolean userExists = userService.existsById(id);

        if (userExists){
            Usuario userToEdit = userService.getUserById(id);
            ModelAndView mav = new ModelAndView("admin/edit-user");
            mav.addObject("userToEdit", userToEdit);
            mav.addObject("usuario", currentUser);
            return mav;
        }

        return new ModelAndView("redirect:/admin/usuarios").addObject("error", "Usuario no encontrado");
    }

    @PostMapping("/admin/edit-user/{id}")
    public ModelAndView updateUser(@PathVariable("id") Long id,
                                   @Valid @ModelAttribute("userToEdit") Usuario updatedUser,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes,
                                   @ModelAttribute("currentUser") Usuario currentUser) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Hay errores en el formulario, por favor verifica los campos.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userToEdit", result);
            redirectAttributes.addFlashAttribute("userToEdit", updatedUser);
            return new ModelAndView("redirect:/admin/edit-user/" + id);
        }

        try {
            // Recuperar el usuario original de la base de datos
            Optional<Usuario> existingUserOpt = Optional.ofNullable(userService.getUserById(id));
            if (existingUserOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
                return new ModelAndView("redirect:/admin/usuarios");
            }

            Usuario existingUser = existingUserOpt.get();

            // Asegurarse de que el ID no se cambie
            updatedUser.setId(existingUser.getId());

            // Actualizar los campos del usuario recuperado con los nuevos valores
            existingUser.setNombre(updatedUser.getNombre());
            existingUser.setEmail(updatedUser.getEmail());

            // Encriptar la contraseña solo si se proporciona una nueva
            if (!updatedUser.getPassword().isEmpty()) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
                existingUser.setPassword(encodedPassword);
            }

            // Guardar el usuario actualizado en la base de datos
            userService.updateUser(existingUser);

            redirectAttributes.addFlashAttribute("message", "Usuario actualizado correctamente!");
            return new ModelAndView("redirect:/admin/usuarios");

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error: El nombre o email ya está en uso.");
            return new ModelAndView("redirect:/admin/edit-user/" + id);

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al actualizar el usuario.");
            return new ModelAndView("redirect:/admin/edit-user/" + id);
        }
    }



}
