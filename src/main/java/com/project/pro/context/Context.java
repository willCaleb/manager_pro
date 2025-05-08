package com.project.pro.context;

import com.project.pro.model.entity.Cliente;
import com.project.pro.model.entity.Profissional;

public class Context {

    public static final ThreadLocal<Cliente> CURRENT_CLIENTE = new ThreadLocal<>();
    public static final ThreadLocal<Profissional> CURRENT_PROFISSIONAL = new ThreadLocal<>();
    public static final ThreadLocal<String> TOKEN = new ThreadLocal<>();

    public static void setCurrentCliente(Cliente cliente) {
        CURRENT_CLIENTE.set(cliente);
    }

    public static void setCurrentProfissional(Profissional profissional){CURRENT_PROFISSIONAL.set(profissional);}

    public static void setToken(String token) {
        TOKEN.set(token);
    }

    public static void clearContext() {
        CURRENT_CLIENTE.remove();
    }
}
