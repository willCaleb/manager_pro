package com.project.pro.controller;

import com.project.pro.model.dto.UsuarioDTO;
import com.project.pro.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UserDetailsServiceImpl userDetailsService;

//    @PostMapping("/cadastrar")
//    public UserDetails cadastrar(@RequestBody UsuarioDTO usuarioDTO) {
//        return userDetailsService.incluir(usuarioDTO.toEntity());
//    }
    
}
