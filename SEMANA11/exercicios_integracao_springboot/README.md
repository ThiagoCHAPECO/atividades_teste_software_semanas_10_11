# Integração com Spring Boot, JUnit 5 e H2

**Colaborador:** Thiago Gimenes. Preencher RA e turma antes da entrega.

## Executar

Requisitos: JDK 17 e Maven 3.9+. Nesta pasta, execute `mvn test`. A primeira execução baixa as dependências Maven.

Resultado real: **18 testes aprovados, zero falhas, zero erros e zero ignorados**, em 10/11/2026. Mantida a aplicação do projeto-base; completadas as classes de teste sem mocks do serviço ou repositório.

## Casos exigidos

| Caso | Classe/método | Verificação |
|---|---|---|
| CT01 | ProdutoServiceIntegrationTest.deveCadastrarERecuperarProduto | Teclado/150 recebe ID e é relido do H2 |
| CT02 | ProdutoServiceIntegrationTest.naoDevePersistirProdutoComNomeEmBranco | Nome em branco lança IllegalArgumentException e preserva a contagem |
| CT03 - desafio | ProdutoServiceIntegrationTest.deveAtualizarPrecoPersistido | Mouse/80 é atualizado para 95 e relido, com mesmo ID e sem duplicação |
| CT04 | ProdutoControllerIntegrationTest.deveCadastrarProdutoPelaApi | POST retorna 200, ID numérico, Mouse/80 e registro real no H2 |
| CT05 | ProdutoControllerIntegrationTest.deveBuscarProdutoPelaApi | Extrai ID pelo Jackson e confirma que o GET retorna o mesmo JSON do POST |

Foram acrescentadas verificações de nomes nulos/vazios, preços inválidos, atualização inválida sem alterar o preço, busca existente/inexistente, atualização inexistente, remoção no repositório e JSON malformado. Cada invocação parametrizada é contada como um teste.

## Discussões

Consultar o repositório acrescenta evidência da integração com a persistência; conferir apenas o objeto retornado não comprova a escrita no banco. Para não depender do cache de primeiro nível do JPA, os testes chamam `repository.flush()` e `entityManager.clear()` antes da releitura. A consulta seguinte recupera o estado do H2 dentro da mesma transação. Isso não equivale a comprovar commit ou persistência depois de reiniciar a aplicação.

O Spring normalmente executa rollback após cada teste anotado com `@Transactional`. Há métodos `@BeforeTransaction` e `@AfterTransaction` nas duas classes para conferir que a contagem volta ao valor anterior. `flush()` não faz commit e não impede rollback. Os IDs são extraídos do cadastro e não assumidos como sequenciais ou iguais a 1. Cada cenário prepara seus dados e não depende de outro teste.

MockMvc exercita roteamento/DispatcherServlet, conversão JSON/Jackson, controlador, serviço, repositório Spring Data, Hibernate e H2 reais. Não abre uma porta HTTP: usa objetos de requisição e resposta simulados dentro do processo. O serviço e o repositório não foram substituídos por mocks.

Um teste unitário com dependências simuladas confere uma unidade contra respostas programadas; esta suíte verifica a colaboração real das camadas. Não testa rede TCP, proxy, navegador ou banco de produção. O rollback vale para o fluxo síncrono destes testes; outra thread ou transação independente exigiria estratégia própria.

## Evidências e enunciado

- [Log completo da suíte](../../evidencias/02-suite-java.log)
- [Relatórios Surefire deste projeto](../../evidencias/junit/exercicios_integracao_springboot)
- [README geral e rastreabilidade](../../README.md)
- [Enunciado original](ENUNCIADO.md)

As classes estão em `src/test/java/br/edu/exemplo`. A configuração usa H2 em memória e `create-drop`, sem dependência de um banco externo. Não foram criados endpoints além dos que já existiam no projeto-base.
