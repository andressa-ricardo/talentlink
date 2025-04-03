package com.talentlink.talentlink.utils;

public class CPFValidator {

    public static boolean verifyCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int[] numeros = cpf.chars().map(Character::getNumericValue).toArray();

        return (calcularDigito(numeros, 9) == numeros[9]) && (calcularDigito(numeros, 10) == numeros[10]);
    }

    private static int calcularDigito(int[] numeros, int posicao) {
        int soma = 0;
        for (int i = 0; i < posicao; i++) {
            soma += numeros[i] * ((posicao + 2) - i);
        }
        int digito = (soma * 10) % 11;
        return (digito == 10) ? 0 : digito;
    }
}
