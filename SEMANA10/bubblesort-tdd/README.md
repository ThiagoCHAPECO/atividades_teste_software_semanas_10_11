# Bubble Sort - TDD

**Colaborador:** Thiago Gimenes. Preencher RA e turma antes da entrega.

## Implementação

Ordenação crescente com Bubble Sort, parada antecipada quando nenhuma troca é necessária e preservação de todas as ocorrências. Os três testes originais foram mantidos integralmente.

## Casos de teste

Os testes originais verificam vetor desordenado, já ordenado e repetidos. Os adicionais verificam vetor vazio, um elemento, negativos e zero, ordem inversa e extremos inteiros. Cada teste adicional repete a ordenação para confirmar a idempotência.

## Executar

Requisitos: JDK 17 e Maven 3.9+. Nesta pasta:

```bash
mvn test
```

Resultado verificado em 10/11/2026: **8 testes aprovados, zero falhas, zero erros e zero ignorados**.

Os testes estão em `src/test/java` e a implementação em `src/main/java`. Cada teste cria seus próprios objetos; não existe estado compartilhado entre métodos nem dependência de ordem.

## Evidências e contexto

Consulte o [README geral](../../README.md), o [log da suíte Java](../../evidencias/02-suite-java.log) e os [relatórios Surefire](../../evidencias/junit/bubblesort-tdd). O enunciado original foi preservado nos arquivos de requisitos, quando fornecidos.
