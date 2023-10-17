package com.generation.blogpessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Usuario;

import java.util.Optional;


public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByEmailUsuario(@Param("emailUsuario") String emailUsuario);
}