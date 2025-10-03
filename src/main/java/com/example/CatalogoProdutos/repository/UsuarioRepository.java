package com.example.CatalogoProdutos.repository;

import com.example.CatalogoProdutos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // O Spring Data JPA cria a consulta automaticamente a partir do nome do método
    // "Encontre um usuário pelo seu email"
    Optional<Usuario> findByEmail(String email);
}