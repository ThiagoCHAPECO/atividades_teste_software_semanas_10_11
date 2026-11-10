package br.edu.exemplo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ContaBancariaLimitesTest {
    @Test void deveIniciarComSaldoZero() {
        assertEquals(0, new ContaBancaria().getSaldo());
    }
    @Test void devePermitirSaqueDoSaldoExato() {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.sacar(100);
        assertEquals(0, conta.getSaldo());
    }
    @ParameterizedTest
    @ValueSource(doubles = {0, -1, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void deveIgnorarOperacoesInvalidas(double valor) {
        ContaBancaria conta = new ContaBancaria();
        conta.depositar(100);
        conta.depositar(valor);
        assertEquals(100, conta.getSaldo());
        conta.sacar(valor);
        assertEquals(100, conta.getSaldo());
    }
}
