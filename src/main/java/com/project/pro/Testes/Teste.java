package com.project.pro.Testes;

import com.project.pro.model.entity.Role;
import com.project.pro.model.entity.Usuario;
import com.project.pro.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Teste {

    private final RoleRepository roleRepository;

    private static void listarClassesNomeTabelaErrado() {

        String packageName = Usuario.class.getPackage().getName();

        packageName = packageName.replace(".", "/");

        Reflections reflections = new Reflections(packageName, Scanners.SubTypes);

        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        classes.forEach(classe -> System.out.println(classe.getSimpleName()));

        List<Class<?>> semPrefixo = classes.stream()
                .filter(classe -> classe.isAnnotationPresent(Table.class))
                .filter(classe -> !classe.getAnnotation(Table.class).name().startsWith("pro_"))
                .collect(Collectors.toList());
        semPrefixo.forEach(System.out::println);


    }

//    public void gerarRoles() {
//
//        List<Role> roles = new ArrayList<>();
//
//        Role roleUser = new Role();
//        Role rolePro = new Role();
//        Role roleAnounymous = new Role();
//        Role roleRoot = new Role();
//
//        roleUser.setEnumRole(EnumRole.USER);
//        rolePro.setEnumRole(EnumRole.PROFESSIONAL);
//        roleAnounymous.setEnumRole(EnumRole.ANONYMOUS);
//        roleRoot.setEnumRole(EnumRole.ROOT);
//
//        roles.add(roleUser);
//        roles.add(rolePro);
//        roles.add(roleAnounymous);
//        roles.add(roleRoot);
//
//        roleRepository.saveAll(roles);
//
//    }



//    public static void main(String[] args) {
//        listarClassesNomeTabelaErrado();
//    }

//    public static void main(String[] args) {
//        String packageName = Usuario.class.getPackage().getName();
//        String path = packageName.replace('.', '/');
//
//        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
//        if (resource == null) {
//            System.out.println("Pacote não encontrado!");
//            return;
//        }
//
//        File directory = new File(resource.getFile());
//        if (!directory.exists()) {
//            System.out.println("Diretório não encontrado: " + directory.getAbsolutePath());
//            return;
//        }
//
//        for (String file : Objects.requireNonNull(directory.list())) {
//            System.out.println(file);
//        }
//    }

//    public static void main(String[] args) {
//        List<Class<?>> classes = getClassesInSamePackage(Usuario.class);
//
//        if (classes.isEmpty()) {
//            System.out.println("Nenhuma classe encontrada.");
//        } else {
//             classes.stream()
//                    .filter(classe -> classe.isAnnotationPresent(Table.class))
//                    .filter(classe -> !classe.getAnnotation(Table.class).name().startsWith("pro_"))
//                    .forEach(System.out::println);
//
//        }
//    }
//
//    public static List<Class<?>> getClassesInSamePackage(Class<?> baseClass) {
//        Reflections reflections = new Reflections(
//                baseClass.getPackage().getName(), Scanners.SubTypes
//        );
//
//        Set<Class<? extends AbstractEntity>> classSet = reflections.getSubTypesOf(AbstractEntity.class);
//
//        return classSet.stream().collect(Collectors.toList());
//    }
//
//    private static void gerarTabelas() {
//
//    }

//    public static void desenharQuadrado(Integer width) {
//
//        System.out.print(Character.getName(0x80));
//
//        for (int i = 0; i < width; i++) {
//            for (int k = 0; k <= width; k++) {
//                if (NumericUtils.isEquals(i, 0)) {
//                    if (k == width - 1) {
//                        System.out.print("___");
//                    }
//                } else if (NumericUtils.isEquals(k, width - 1)) {
//                    System.out.print("‾‾‾ ");
//
//                } else {
//                    System.out.print("‾");
//                }
//            }else if (NumericUtils.isEquals(0, k) || NumericUtils.isEquals(k, width - 2)) {
//                System.out.print("|");
//            } else {
//                System.out.print("   ");
//            }
//        }
//    }}
//
//
//}

    public static void desenharTrianguloDuplo() {
        int inicio = 0;
        int fim = 13;

        for (int i = 0; i <= 13; i++) {
            for (int k = 0; k < i; k++) {
                for (int x = 0; x < k; x++) {
                    System.out.print(" ");
                }
                System.out.println("*");
            }

            System.out.println();
        }
    }

    public static void desenharTrianguloInvertido() {
        int inicio = 0;
        int fim = 13;

        for (int i = 0; i <= 7; i++) {
            for (int k = 0; k <= 13; k++) {
                if (i == 0) {
                    System.out.print("_");
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    if (inicio >= 7) {
                        break;
                    }
                    System.out.println();
                    for (int j = 0; j < inicio; j++) {
                        System.out.print(" ");
                    }
                    System.out.print("\\");
                    inicio++;

                    for (int x = 0; x < fim - inicio; x++) {
                        System.out.print(" ");
                    }
                    System.out.print("/");
                    fim--;
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }
    }

    public static void desenharCirculo() {

    }

    public static void desenharFibonacci(int size) {
        for (Integer v : getFibonacciList(size)) {
            for (int i = 0; i < v; i++) {
                System.out.print(" ");
            }
            System.out.println(v);
        }


    }

    public static List<Integer> getFibonacciList(int size) {
        List<Integer> sequencia = new LinkedList<>();

        for (int i = 0; i <= size; i++) {
            sequencia.add(fibonacci(i));
        }
        return sequencia;
    }

    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}
