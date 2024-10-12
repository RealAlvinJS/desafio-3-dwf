package com.dwf.desafio_3.service;

import com.dwf.desafio_3.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<Usuario> ListAllUsers();

    public Optional<Usuario> GetCurrentUser(String nombre);

    public Usuario createUser(Usuario usuario);

    boolean existsByEmail(String email);

    boolean existsByNombre(String nombre);

    boolean existsById(Long id);

    public void deleteUser(Long id);

    public Usuario getUserById(Long id);

    public Usuario updateUser(Usuario userToUpdate);
}
