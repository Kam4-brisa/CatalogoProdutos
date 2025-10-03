package com.example.CatalogoProdutos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String exibirTelaDeLogin() {
        return "login";
    }

    @GetMapping("/index")
    public String paginaHome() {
        return "index";
    }
}