# Carrinho de compras - TDD

**Colaborador:** Thiago Gimenes. Preencher RA e turma antes da entrega.

## Implementação

Os seis requisitos de REQUISITOS.md foram implementados: vazio, adicionar, somar, remover, rejeitar preço negativo e desconto de 10% para subtotal igual ou superior a 500. Produto com preço inválido lança IllegalArgumentException no construtor. Remoção usa a mesma instância adicionada; produto nulo é rejeitado.

## Casos de teste

CarrinhoTest cobre os seis requisitos. CarrinhoLimitesTest acrescenta os três valores de fronteira 499,99/500/500,01, remoção que elimina o direito ao desconto, produto ausente, produto nulo e preço zero. Valores double são comparados com tolerância; o desconto não faz arredondamento de apresentação.

## Executar

Requisitos: JDK 17 e Maven 3.9+. Nesta pasta:

```bash
mvn test
```

Resultado verificado em 10/11/2026: **13 testes aprovados, zero falhas, zero erros e zero ignorados**.

Os testes estão em `src/test/java` e a implementação em `src/main/java`. Cada teste cria seus próprios objetos; não existe estado compartilhado entre métodos nem dependência de ordem.

## Evidências de TDD

Os logs por requisito estão na pasta de evidências da raiz. R1, R2, R4, R5 e R6 falharam antes da implementação correspondente e passaram depois. R3 já passou com a generalização da soma feita em R2, sem criar uma falha artificial. A formatação foi refatorada no final e a suíte foi reexecutada.

## Evidências e contexto

Consulte o [README geral](../../README.md), o [log da suíte Java](../../evidencias/02-suite-java.log) e os [relatórios Surefire](../../evidencias/junit/carrinho-compras-tdd). O enunciado original foi preservado nos arquivos de requisitos, quando fornecidos.
