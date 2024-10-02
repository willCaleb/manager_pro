package com.project.pro.controller;

import com.project.pro.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
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
