package com.dwf.desafio_3.service.implement;

import com.dwf.desafio_3.entity.Usuario;
import com.dwf.desafio_3.repository.UserRepository;
import com.dwf.desafio_3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public List<Usuario> ListAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> GetCurrentUser(String nombre) { return repository.findByNombre(nombre); };

    @Override
    public Usuario createUser(Usuario usuario) {
        return repository.save(usuario);
    }

    public boolean existsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public boolean existsByNombre(String nombre) {
        return repository.findByNombre(nombre).isPresent();
    }

    @Override
    public void deleteUser(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User with id " + id + " does not exist.");
        }
    }

    @Override
    public Usuario getUserById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Usuario updateUser(Usuario userToUpdate) {
        return repository.save(userToUpdate);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.findById(id).isPresent();
    }


}
