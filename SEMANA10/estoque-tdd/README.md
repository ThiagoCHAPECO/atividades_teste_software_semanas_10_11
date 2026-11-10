# Estoque de produtos - TDD

**Colaborador:** Thiago Gimenes. Preencher RA e turma antes da entrega.

## Implementação

O estoque inicia em zero, acumula entradas positivas e permite retiradas positivas que não excedam o disponível. Entradas não positivas e retiradas inválidas são ignoradas. Uma entrada que excederia o limite inteiro também é ignorada. Os seis testes originais foram mantidos.

## Casos de teste

Casos originais: estoque inicial, entrada, acumulação, retirada, retirada excessiva e entrada negativa. Casos adicionais: entrada zero, retirada zero/negativa, retirada exata e proteção contra overflow.

## Executar

Requisitos: JDK 17 e Maven 3.9+. Nesta pasta:

```bash
mvn test
```

Resultado verificado em 10/11/2026: **10 testes aprovados, zero falhas, zero erros e zero ignorados**.

Os testes estão em `src/test/java` e a implementação em `src/main/java`. Cada teste cria seus próprios objetos; não existe estado compartilhado entre métodos nem dependência de ordem.

## Evidências e contexto

Consulte o [README geral](../../README.md), o [log da suíte Java](../../evidencias/02-suite-java.log) e os [relatórios Surefire](../../evidencias/junit/estoque-tdd). O enunciado original foi preservado nos arquivos de requisitos, quando fornecidos.
