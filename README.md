# Atividades de Teste de Software — Semanas 10, 11 e 14

**Aluno:** Thiago Gimenes Santos  
**RA:** 24016862-2  
**Turma:** ESOFT-6S-A

Este repositório reúne atividades de desenvolvimento orientado a testes (TDD), testes de integração e teste de usabilidade.

## Estrutura do repositório

| Pasta | Conteúdo |
| --- | --- |
| `SEMANA10/` | Bubble Sort, conta bancária, estoque e carrinho de compras com TDD |
| `SEMANA11/` | Testes de integração com Spring Boot e exemplos complementares |
| `SEMANA 14/` | Relatório de teste de usabilidade no Mercado Livre com dados simulados |
| `evidencias/` | Logs de execução e relatórios dos testes Java |

## Executar as atividades Java

São necessários JDK 17 e Maven 3.9 ou superior, com a variável `JAVA_HOME` apontando para o JDK. A primeira execução exige acesso ao Maven Central para baixar as dependências.

Na raiz do repositório, onde está o `pom.xml` agregador, execute:

```bash
mvn clean test
```

O comando executa os sete módulos. Para executar um projeto isoladamente, entre na pasta correspondente e use `mvn test`.

### Resultado de validação registrado

O registro de validação de 10/11/2026 informa a execução com Microsoft OpenJDK 17.0.17 e Maven 3.9.9, com **71 testes aprovados**, zero falhas, zero erros e zero testes ignorados. As invocações dos testes parametrizados são contadas individualmente pelo JUnit.

| Projeto | Testes aprovados |
| --- | ---: |
| `SEMANA10/bubblesort-tdd` | 8 |
| `SEMANA10/carrinho-compras-tdd` | 13 |
| `SEMANA10/conta-bancaria-tdd-maven` | 11 |
| `SEMANA10/estoque-tdd` | 10 |
| `SEMANA11/complementares/tdd-estacionamento` | 6 |
| `SEMANA11/complementares/teste-integracao-maven` | 5 |
| `SEMANA11/exercicios_integracao_springboot` | 18 |
| **Total** | **71** |

A saída completa está registrada em `evidencias/02-suite-java.log`, e os resumos do Surefire estão em `evidencias/junit/`. Os arquivos compilados da pasta `target/` ficam fora da entrega.

## Semana 10 — TDD

### Implementação e decisões

| Projeto | Requisitos atendidos | Testes adicionais |
| --- | --- | --- |
| Bubble Sort | Ordem crescente, vetor já ordenado e valores repetidos; algoritmo implementado sem biblioteca de ordenação | Vetor vazio, unitário, valores negativos, ordem inversa, extremos inteiros e repetição da ordenação |
| Conta bancária | Depósito, acumulação, saque e recusa de saque superior ao saldo | Saldo inicial, saque exato e operações com valores não positivos ou não finitos |
| Estoque | Estoque inicial, entradas acumuladas, retirada e rejeição de retirada excessiva ou entrada não positiva | Retirada exata, retirada zero ou negativa e proteção contra overflow |
| Carrinho | Total vazio, adição, soma, remoção, rejeição de preço negativo e desconto de 10% a partir de 500 | Fronteiras de 499,99, 500 e 500,01; recálculo do desconto após remoção; remoção de produto ausente; produto nulo e preço zero |

Conta e estoque ignoram transações inválidas e preservam o estado, conforme os testes fornecidos. No carrinho, o construtor de `Produto` lança `IllegalArgumentException` quando recebe um preço negativo. Preço zero é permitido no carrinho. Na API da Semana 11, a regra exige preço estritamente positivo.

Foram mantidos os valores `double` do projeto-base, com tolerância nas comparações monetárias dos testes. O desconto é calculado sem arredondamento de apresentação. Por exemplo, `500,01 × 0,9 = 450,009`. A remoção usa a mesma instância do produto adicionado, pois o modelo original não define identificador ou igualdade de negócio.

### Ciclo TDD e evidências

O arquivo `evidencias/01-base-original.log` registra a execução anterior às implementações. Bubble Sort, conta e estoque apresentam falhas nos comportamentos ausentes. Nesse estágio, o carrinho ainda estava sem testes, e os cinco testes vazios da lista Spring passavam sem verificar as regras.

No carrinho, cada requisito foi acrescentado e executado antes da implementação correspondente, conforme os arquivos `evidencias/tdd-carrinho-R*-antes.log` e `evidencias/tdd-carrinho-R*-depois.log`.

| Requisito | Antes | Depois |
| --- | --- | --- |
| R1 — Total vazio | Falha com retorno provisório de `-1` | Total igual a zero |
| R2 — Adicionar produto | Adição sem efeito | Produto armazenado e incluído no total |
| R3 — Vários produtos | Já aprovado pela generalização de R2 | Soma de vários produtos aprovada |
| R4 — Remover produto | Remoção sem efeito | Produto removido |
| R5 — Preço negativo | Produto inválido aceito | Lançamento de `IllegalArgumentException` |
| R6 — Desconto | Subtotal de 600 sem desconto | Total de 540 após o desconto |

Em seguida, foram acrescentados os testes de limites e realizada uma refatoração de legibilidade, seguida de `mvn clean test`. Os logs documentam as execuções registradas. O histórico de commits e a participação dos integrantes devem ser consultados no próprio repositório.

## Semana 11 — Testes de integração

### Rastreabilidade dos critérios

| Caso | Método de teste | Verificação |
| --- | --- | --- |
| CT01 | `ProdutoServiceIntegrationTest.deveCadastrarERecuperarProduto` | ID gerado, nome Teclado e preço 150 recuperados do H2 |
| CT02 | `ProdutoServiceIntegrationTest.naoDevePersistirProdutoComNomeEmBranco` | `IllegalArgumentException` e quantidade de registros inalterada, inclusive com produto preexistente |
| CT03 — Desafio | `ProdutoServiceIntegrationTest.deveAtualizarPrecoPersistido` | Mouse cadastrado por 80 e recuperado com preço 95 e o mesmo ID, sem duplicação |
| CT04 | `ProdutoControllerIntegrationTest.deveCadastrarProdutoPelaApi` | POST com HTTP 200, JSON com ID numérico, nome e preço, além de confirmação no repositório |
| CT05 | `ProdutoControllerIntegrationTest.deveBuscarProdutoPelaApi` | ID extraído pelo Jackson, GET com HTTP 200 e JSON completo correspondente ao cadastro |

As classes ficam em `src/test/java/br/edu/exemplo/`, no módulo Spring Boot. Ambas usam `@SpringBootTest`, `@Autowired` e `@Transactional`. Os testes HTTP também usam `@AutoConfigureMockMvc`, com instâncias reais de `ProdutoService` e `ProdutoRepository`.

Os casos complementares verificam nomes nulos ou vazios, preços zero ou negativos, atualização inválida com preservação do preço, consulta de produto existente e inexistente, atualização de ID inexistente, remoção pelo repositório e JSON malformado. A remoção é verificada na camada de repositório.

### Persistência e isolamento

**Por que consultar `repository.findById`?**

A consulta ao repositório acrescenta evidências da integração com a persistência. Como o JPA pode devolver uma entidade do cache de primeiro nível na mesma transação, os testes usam `flush()` para enviar as alterações ao banco e `EntityManager.clear()` para limpar as entidades gerenciadas antes da leitura. Assim, a comparação usa o estado recuperado do H2. A verificação cobre escrita e leitura dentro da transação. Para verificar persistência após commit ou reinicialização, são necessários testes específicos.

**O que `@Transactional` faz ao término de cada teste?**

No contexto dos testes do Spring, a transação normalmente sofre rollback automático após cada método. As escritas enviadas ao H2 por `flush()` também participam desse rollback. Os testes usam `@BeforeTransaction` e `@AfterTransaction` para comparar a quantidade de registros antes da transação e depois da reversão.

Cada teste prepara seus próprios dados e pode ser executado independentemente da ordem. Os testes também independem de IDs sequenciais iniciados em 1, pois sequências podem avançar mesmo quando ocorre rollback.

**Quais componentes participam dos testes com MockMvc?**

O fluxo passa pelo `DispatcherServlet`, pelo roteamento Spring MVC, pela conversão JSON com Jackson, pelo controlador, pelo serviço real, pelo repositório Spring Data JPA, pelo Hibernate e pelo H2. São verificados o status HTTP, o tipo de conteúdo e os campos da resposta.

O MockMvc executa a infraestrutura web dentro do processo, usando objetos de requisição e resposta simulados. O serviço e o repositório continuam reais. A comunicação de rede e a configuração de um servidor publicado exigem outra abordagem de teste.

**Qual a diferença em relação a um teste unitário de controlador?**

Um teste unitário com dependências simuladas verifica o comportamento do controlador diante de respostas programadas. Esta suíte verifica a colaboração das camadas e a escrita e leitura no H2. Navegador, proxy e banco de produção ficam fora da cobertura descrita. A reversão transacional cobre o fluxo síncrono da aplicação; tarefas em outras threads ou transações independentes exigem uma estratégia própria de isolamento.

### Exemplos complementares

- **Estacionamento:** implementação e seis testes do exemplo, incluindo capacidade variável e lotação.
- **Integração entre `PedidoService` e `EstoqueService`:** testes originais e desafio de estoque exato. Com estoque 3 e pedido de 3 unidades, o retorno esperado é `true` e o saldo final é zero.

O projeto-base fornecido já continha a atualização `this.quantidade -= quantidade`, que foi preservada.

## Semana 14 — Teste de usabilidade

**Relatório:** [Teste de usabilidade no Mercado Livre](<SEMANA%2014/Semana%2014%20-%20Teste%20de%20usabilidade.pdf>)

A atividade avalia a facilidade de uso, a eficiência e a clareza da interface durante a busca, comparação e simulação de compra de um fone de ouvido Bluetooth no Mercado Livre.

O relatório foi elaborado com **dados simulados**, identificados no documento. Os resultados representam um exemplo de aplicação da atividade e de cálculo das métricas.

### Tarefas previstas

1. Acessar o Mercado Livre.
2. Pesquisar por “fone de ouvido Bluetooth”.
3. Aplicar o filtro de entrega Full.
4. Ordenar pelos melhores avaliados, conforme a disponibilidade do controle.
5. Comparar opções e abrir os detalhes de um produto.
6. Adicionar o produto ao carrinho.
7. Iniciar a compra e parar antes de confirmar o pedido ou efetuar o pagamento.
8. Retornar à página inicial.

### Resultados simulados

| Indicador | Resultado | Critério da atividade |
| --- | --- | --- |
| Adição ao carrinho | 9 de 10 participantes — 90% | Pelo menos 90% |
| Fluxo completo sem ajuda | 6 de 10 participantes — 60% | Indicador descritivo |
| Tempo médio entre os 8 participantes que concluíram o fluxo | Aproximadamente 3 min 38 s | Até 4 min |

As metas de adição ao carrinho e tempo médio foram atingidas no cenário simulado. A ficha detalhada de P01 registra 204 segundos e uma tarefa pendente porque a ordenação por avaliação estava indisponível no cenário. A disponibilidade real desse controle deve ser verificada durante a aplicação do teste com participantes.

## Integração contínua

O repositório contém os projetos das Semanas 10 e 11 e o relatório da Semana 14. O workflow incluído está configurado para executar `mvn -B clean test` no GitHub. O resultado de cada execução remota pode ser consultado na aba **Actions**.

As quantidades de testes e as informações de execução local apresentadas neste README correspondem aos registros de validação descritos acima.

**Fonte dos projetos-base:** repositório da disciplina e pacote fornecido pelo aluno.
