# Atividades de Teste de Software - Semanas 10 e 11

**Colaborador:** Thiago Gimenes.

**Identificação acadêmica:** NOME: Thiago Gimenes Santos. RA:24016862-2. TURMA: ESOFT-6S-A.

## Executar as atividades Java

Requisitos: JDK 17 e Maven 3.9 ou superior, com `JAVA_HOME` apontando para o JDK. A primeira execução precisa de acesso ao Maven Central para baixar as dependências.

```bash
mvn clean test
```

Execute na pasta deste README, onde está o `pom.xml` agregador. O comando executa os sete módulos. Para rodar um projeto isolado, entre na respectiva pasta e execute `mvn test`.

Validação realizada em 10/11/2026, com Microsoft OpenJDK 17.0.17 e Maven 3.9.9: **71 testes aprovados, zero falhas, zero erros e zero ignorados**. As invocações dos testes parametrizados são contadas individualmente pelo JUnit.

| Projeto | Testes aprovados |
|---|---:|
| [SEMANA10/bubblesort-tdd](SEMANA10/bubblesort-tdd) | 8 |
| [SEMANA10/carrinho-compras-tdd](SEMANA10/carrinho-compras-tdd) | 13 |
| [SEMANA10/conta-bancaria-tdd-maven](SEMANA10/conta-bancaria-tdd-maven) | 11 |
| [SEMANA10/estoque-tdd](SEMANA10/estoque-tdd) | 10 |
| [SEMANA11/complementares/tdd-estacionamento](SEMANA11/complementares/tdd-estacionamento) | 6 |
| [SEMANA11/complementares/teste-integracao-maven](SEMANA11/complementares/teste-integracao-maven) | 5 |
| [SEMANA11/exercicios_integracao_springboot](SEMANA11/exercicios_integracao_springboot) | 18 |

Saída completa em [evidencias/02-suite-java.log](evidencias/02-suite-java.log). Resumos do Surefire em [evidencias/junit](evidencias/junit). Os arquivos compilados de `target/` não fazem parte da entrega.

## Semana 10 - implementação e decisões

| Projeto | Requisitos atendidos | Testes adicionais |
|---|---|---|
| Bubble Sort | Ordem crescente, vetor já ordenado e repetidos; algoritmo implementado sem biblioteca de ordenação | Vazio, unitário, negativos, ordem inversa, extremos inteiros e repetição da ordenação |
| Conta bancária | Depósito, acumulação, saque e recusa de saque maior que o saldo | Saldo inicial, saque exato e operações não positivas ou não finitas |
| Estoque | Estoque inicial, entradas acumuladas, retirada e rejeição de retirada excessiva ou entrada não positiva | Retirada exata, retirada zero/negativa e proteção contra overflow |
| Carrinho | Total vazio, adição, soma, remoção, preço negativo e desconto de 10% a partir de 500 | Fronteiras 499,99/500/500,01, recalcular desconto após remoção, remoção ausente, produto nulo e preço zero |

Conta e estoque ignoram operações inválidas e mantêm o estado, em conformidade com os testes fornecidos. Para o preço negativo do carrinho, o requisito admite uma escolha: adotou-se `IllegalArgumentException` no construtor de `Produto`, impedindo a existência de um produto inválido. Preço zero é permitido no carrinho; na API da Semana 11, a regra original exige preço estritamente positivo.

Mantiveram-se os valores `double` do projeto-base; os testes monetários usam tolerância. O desconto é matemático, sem arredondamento de apresentação: 500,01 × 0,9 = 450,009. Remover utiliza a instância do produto adicionada, pois o modelo original não tem identificador ou igualdade de negócio.

## TDD e evidências

O log [01-base-original.log](evidencias/01-base-original.log) registra a execução antes das implementações: Bubble Sort, conta e estoque falham nos comportamentos ausentes; o carrinho ainda não tem testes; os cinco testes vazios da lista Spring passam, sem comprovar as regras.

No carrinho, cada requisito foi acrescentado e executado antes da evolução correspondente, conforme os arquivos `evidencias/tdd-carrinho-R*-antes.log` e `*-depois.log`.

| Requisito do carrinho | Antes | Depois |
|---|---|---|
| R1 - total vazio | Falha: retorno provisório -1 | Aprovado: total zero |
| R2 - adicionar | Falha: adição ainda sem efeito | Aprovado: armazena e soma produtos |
| R3 - vários produtos | Já aprovado com a generalização de R2 | Aprovado, sem forçar falha artificial |
| R4 - remover | Falha: remoção ainda sem efeito | Aprovado: remove o produto |
| R5 - preço negativo | Falha: produto inválido era aceito | Aprovado: lança exceção |
| R6 - desconto | Falha: subtotal 600 sem desconto | Aprovado: total 540 |

Depois foram acrescentados os limites e feita a refatoração de legibilidade, seguida de `clean test`. Esses logs registram execuções reais; não representam um histórico de commits ou uma simulação de participação dos integrantes.

## Semana 11 - rastreabilidade dos critérios

| Caso | Método na classe de teste | Evidência verificada |
|---|---|---|
| CT01 | `ProdutoServiceIntegrationTest.deveCadastrarERecuperarProduto` | ID gerado, nome Teclado e preço 150 recuperados do H2 |
| CT02 | `ProdutoServiceIntegrationTest.naoDevePersistirProdutoComNomeEmBranco` | `IllegalArgumentException` e contagem inalterada, inclusive com produto preexistente |
| CT03 - desafio | `ProdutoServiceIntegrationTest.deveAtualizarPrecoPersistido` | Mouse cadastrado por 80 e relido com preço 95 e mesmo ID, sem duplicar registro |
| CT04 | `ProdutoControllerIntegrationTest.deveCadastrarProdutoPelaApi` | POST com HTTP 200, JSON com ID numérico/nome/preço e confirmação no repositório |
| CT05 | `ProdutoControllerIntegrationTest.deveBuscarProdutoPelaApi` | ID extraído pelo Jackson, GET com HTTP 200 e JSON completo igual ao cadastro |

As duas classes estão em [src/test/java/br/edu/exemplo](SEMANA11/exercicios_integracao_springboot/src/test/java/br/edu/exemplo). Ambas usam `@SpringBootTest`, `@Autowired` e `@Transactional`; os testes HTTP usam ainda `@AutoConfigureMockMvc`. Não há mocks de `ProdutoService` ou `ProdutoRepository`.

Casos complementares verificam nomes nulos/vazios, preços zero/negativos, atualização inválida sem alterar preço, consulta existente/inexistente, atualização de ID inexistente, remoção pelo repositório e JSON malformado. A remoção é verificada na camada de repositório: não foi inventado um endpoint DELETE.

## Respostas às discussões e isolamento

**Por que consultar `repository.findById`?** O objeto retornado pelo serviço pode estar correto apenas na memória. Consultar o repositório acrescenta evidência da integração com a persistência. Ainda há uma sutileza: na mesma transação, o JPA pode devolver uma entidade de seu cache de primeiro nível. Por isso, os testes executam `flush()` para enviar alterações ao banco e `EntityManager.clear()` para limpar as entidades gerenciadas antes de reler. A comparação passa a usar o estado recuperado do H2. Isso comprova a escrita/leitura dentro da transação; não comprova um commit definitivo ou persistência após reiniciar a aplicação.

**O que `@Transactional` faz no término de cada teste?** No contexto de testes do Spring, a transação é normalmente revertida automaticamente após cada método. `flush()` não é `commit`: as escritas enviadas ao H2 também participam do rollback. Foram usados `@BeforeTransaction` e `@AfterTransaction` para comparar a quantidade de registros antes da transação e depois do rollback nas duas classes. Não há dependência de ordem, de outro teste criar dados ou de IDs sequenciais a partir de 1. Sequências de IDs não precisam voltar ao valor anterior após rollback.

**Quais componentes o MockMvc exercita?** O fluxo inclui o `DispatcherServlet`, o roteamento Spring MVC, a conversão JSON pelo Jackson, o controlador, o serviço real, o repositório Spring Data JPA, o Hibernate e o H2. Também são verificados status, tipo de conteúdo e campos da resposta.

**Por que não é necessário iniciar um servidor HTTP?** O MockMvc executa a infraestrutura web do Spring com objetos de requisição e resposta simulados, dentro do processo. Não há porta de rede, conexão TCP ou servidor externo. O termo “Mock” do nome não significa que o serviço e o repositório foram simulados.

**Diferença para um teste unitário de controlador:** um teste com dependências simuladas verifica principalmente o comportamento do controlador diante de respostas programadas. Esta suíte verifica a colaboração real das camadas e a escrita/leitura no H2. Ela não testa a configuração de um servidor publicado, navegador, proxy ou banco de produção. A garantia de rollback se aplica ao fluxo síncrono desta aplicação; trabalho em outra thread ou transação independente exigiria estratégia própria.

## Exemplos complementares da Semana 11

O estacionamento mantém a implementação e os seis testes do exemplo, incluindo capacidade variável e lotação. A integração `PedidoService`/`EstoqueService` inclui os testes originais e o desafio do estoque exato: estoque 3, pedido 3, retorno `true` e saldo 0. O exemplo fornecido no ZIP já continha a correção `this.quantidade -= quantidade`; ela foi preservada, sem alegar que um defeito já corrigido ainda existia.

## Entrega

Este pacote corresponde às semanas 10 e 11. Publique seu conteúdo em um repositório próprio, revise a identificação acadêmica e consulte [COMO_ENTREGAR.md](COMO_ENTREGAR.md). Os desafios de jogos são entregues em pacotes independentes e exigem um repositório para cada desafio.

O workflow incluído executa `mvn -B clean test` no GitHub após a publicação. A execução local já foi verificada; não houve publicação ou execução remota automática. Os testes verificam os cenários descritos, e a nota é atribuída pelo professor.

Fonte dos projetos-base: [repositório da disciplina](https://github.com/JoaoChoma/teste_software_2026), a partir do ZIP fornecido pelo aluno.
