# Pedido e estoque - integração entre objetos

**Colaborador:** Thiago Gimenes. Preencher RA e turma antes da entrega.

## Implementação

Exemplo de dois componentes reais: PedidoService consulta o EstoqueService e só retira unidades quando há saldo. O ZIP original já contém a retirada corrigida com this.quantidade; esse comportamento foi preservado. O escopo do exemplo utiliza quantidades positivas.

## Casos de teste

EstoqueServiceTest verifica consulta e retirada. PedidoServiceIntegrationTest verifica aceitação com estoque 10/pedido 3/saldo 7, recusa com estoque 2/pedido 3/saldo 2 e o desafio de estoque exato 3/pedido 3/retorno true/saldo 0.

## Executar

Requisitos: JDK 17 e Maven 3.9+. Nesta pasta:

```bash
mvn test
```

Resultado verificado em 10/11/2026: **5 testes aprovados, zero falhas, zero erros e zero ignorados**.

Os testes estão em `src/test/java` e a implementação em `src/main/java`. Cada teste cria seus próprios objetos; não existe estado compartilhado entre métodos nem dependência de ordem.

## Evidências e contexto

Consulte o [README geral](../../../README.md), o [log da suíte Java](../../../evidencias/02-suite-java.log) e os [relatórios Surefire](../../../evidencias/junit/teste-integracao-maven). O enunciado original foi preservado nos arquivos de requisitos, quando fornecidos.
