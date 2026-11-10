# Estacionamento - exemplo complementar

**Colaborador:** Thiago Gimenes. Preencher RA e turma antes da entrega.

## Implementação

Exemplo fornecido preservado: capacidade definida no construtor, ocupação inicial zero, entrada de veículos e exceção IllegalStateException ao tentar ultrapassar a capacidade.

## Casos de teste

Os seis testes fornecidos verificam ocupação inicial, capacidade 10, capacidade 25, entrada simples, entradas acumuladas e lotação com preservação da ocupação.

## Executar

Requisitos: JDK 17 e Maven 3.9+. Nesta pasta:

```bash
mvn test
```

Resultado verificado em 10/11/2026: **6 testes aprovados, zero falhas, zero erros e zero ignorados**.

Os testes estão em `src/test/java` e a implementação em `src/main/java`. Cada teste cria seus próprios objetos; não existe estado compartilhado entre métodos nem dependência de ordem.

## Evidências e contexto

Consulte o [README geral](../../../README.md), o [log da suíte Java](../../../evidencias/02-suite-java.log) e os [relatórios Surefire](../../../evidencias/junit/tdd-estacionamento). O enunciado original foi preservado nos arquivos de requisitos, quando fornecidos.
