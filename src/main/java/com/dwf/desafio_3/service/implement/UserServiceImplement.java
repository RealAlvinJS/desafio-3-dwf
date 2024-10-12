package com.dwf.desafio_3.service.implement;

import com.dwf.desafio_3.entity.Usuario;
import com.dwf.desafio_3.repository.UserRepository;
import com.dwf.desafio_3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplement implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public List<Usuario> ListAllUsers() {
        return repository.findAll();
    }
}
