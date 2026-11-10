# Conta bancária - TDD

**Colaborador:** Thiago Gimenes. Preencher RA e turma antes da entrega.

## Implementação

Depósitos positivos aumentam o saldo; saques positivos até o saldo disponível diminuem o saldo. Operações inválidas não alteram o estado, mantendo o contrato dos quatro testes originais. Valores não finitos e depósitos que causariam overflow também são ignorados.

## Casos de teste

Além dos quatro testes fornecidos, verificam-se saldo inicial, saque exato e cinco entradas inválidas parametrizadas: zero, negativo, NaN e infinitos positivo/negativo.

## Executar

Requisitos: JDK 17 e Maven 3.9+. Nesta pasta:

```bash
mvn test
```

Resultado verificado em 10/11/2026: **11 testes aprovados, zero falhas, zero erros e zero ignorados**.

Os testes estão em `src/test/java` e a implementação em `src/main/java`. Cada teste cria seus próprios objetos; não existe estado compartilhado entre métodos nem dependência de ordem.

## Evidências e contexto

Consulte o [README geral](../../README.md), o [log da suíte Java](../../evidencias/02-suite-java.log) e os [relatórios Surefire](../../evidencias/junit/conta-bancaria-tdd-maven). O enunciado original foi preservado nos arquivos de requisitos, quando fornecidos.
