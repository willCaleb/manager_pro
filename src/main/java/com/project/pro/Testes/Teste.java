package com.project.pro.Testes;

import com.project.pro.utils.NumericUtils;

import java.text.DecimalFormat;
import java.util.*;

public class Teste {



    private static void gerarTabelas() {

    }

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
