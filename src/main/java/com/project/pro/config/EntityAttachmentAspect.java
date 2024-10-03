//package com.project.pro.config;
//
//import com.project.pro.config.security.JwtTokenProvider;
//import com.project.pro.model.entity.Cliente;
//import com.project.pro.model.entity.Profissional;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.ParameterizedType;
//import java.nio.file.AccessDeniedException;
//
//@Aspect
////@Component
//public class RoleCheckAspect {
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Pointcut("execution(* com.exemplo.controller.AbstractController.listar(..))")
//    public void listarItensPointcut() {}
//
//    @Around("listarItensPointcut()")
//    public Object verificarRole(ProceedingJoinPoint joinPoint) throws Throwable {
//        HttpServletRequest request = (RequestContextHolder.getRequestAttributes()).getRequest();
//        String token = request.getHeader("Authorization").substring(7);
//
//        String role = jwtTokenProvider.getAudienceFromToken(token);
//
//        // Obter o tipo da entidade a partir do método genérico
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        ParameterizedType returnType = (ParameterizedType) signature.getMethod().getGenericReturnType();
//        Class<?> entityClass = (Class<?>) returnType.getActualTypeArguments()[0];
//
//        // Verificar se a entidade pertence ao papel correto
//        if (isEntityPermittedForRole(entityClass, role)) {
//            return joinPoint.proceed();
//        } else {
//            throw new AccessDeniedException("Acesso negado: Usuário não autorizado.");
//        }
//    }
//
//    private boolean isEntityPermittedForRole(Class<?> entityClass, String role) {
//        if (role.equals("CLIENTE")) {
//            return Cliente.class.isAssignableFrom(entityClass);
//        } else if (role.equals("PROFISSIONAL")) {
//            return Profissional.class.isAssignableFrom(entityClass);
//        }
//        return false;
//    }
//}
//
