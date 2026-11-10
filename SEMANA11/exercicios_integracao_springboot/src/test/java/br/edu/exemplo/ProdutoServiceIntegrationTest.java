package br.edu.exemplo;
import br.edu.exemplo.repository.ProdutoRepository;
import br.edu.exemplo.service.ProdutoService;
import br.edu.exemplo.model.Produto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.AfterTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest @Transactional
class ProdutoServiceIntegrationTest {
 @Autowired ProdutoService service;
 @Autowired ProdutoRepository repository;
 @Autowired EntityManager entityManager;
 private long quantidadeAntesDaTransacao;
 @BeforeTransaction void registrarQuantidadeInicial() {
  quantidadeAntesDaTransacao = repository.count();
 }
 @AfterTransaction void confirmarRollback() {
  assertEquals(quantidadeAntesDaTransacao, repository.count());
 }
 private Produto consultarBanco(Long id) {
  repository.flush();
  entityManager.clear();
  return repository.findById(id).orElseThrow();
 }
 @Test void deveCadastrarERecuperarProduto(){
  Produto salvo = service.cadastrar("Teclado", 150.0);
  assertNotNull(salvo.getId());
  Produto encontrado = consultarBanco(salvo.getId());
  assertEquals(salvo.getId(), encontrado.getId());
  assertEquals("Teclado", encontrado.getNome());
  assertEquals(150.0, encontrado.getPreco(), 0.001);
 }
 @Test void naoDevePersistirProdutoComNomeEmBranco(){
  service.cadastrar("Produto existente", 25);
  long antes = repository.count();
  assertThrows(IllegalArgumentException.class, () -> service.cadastrar("   ", 80.0));
  repository.flush();
  entityManager.clear();
  assertEquals(antes, repository.count());
 }
 @Test void deveAtualizarPrecoPersistido(){
  Produto salvo = service.cadastrar("Mouse", 80.0);
  Long id = salvo.getId();
  consultarBanco(id);
  service.atualizarPreco(id, 95.0);
  Produto encontrado = consultarBanco(id);
  assertEquals(id, encontrado.getId());
  assertEquals("Mouse", encontrado.getNome());
  assertEquals(95.0, encontrado.getPreco(), 0.001);
  assertEquals(1, repository.count());
 }
 @ParameterizedTest @NullAndEmptySource @ValueSource(strings = {"\t", "\n"})
 void deveRejeitarOutrosNomesInvalidos(String nome) {
  long antes = repository.count();
  assertThrows(IllegalArgumentException.class, () -> service.cadastrar(nome, 80));
  assertEquals(antes, repository.count());
 }
 @ParameterizedTest @ValueSource(doubles = {0, -1})
 void deveRejeitarCadastroComPrecoNaoPositivo(double preco) {
  long antes = repository.count();
  assertThrows(IllegalArgumentException.class, () -> service.cadastrar("Mouse", preco));
  assertEquals(antes, repository.count());
 }
 @ParameterizedTest @ValueSource(doubles = {0, -1})
 void devePreservarPrecoAposAtualizacaoInvalida(double preco) {
  Produto produto = service.cadastrar("Mouse", 80);
  assertThrows(IllegalArgumentException.class, () -> service.atualizarPreco(produto.getId(), preco));
  assertEquals(80, consultarBanco(produto.getId()).getPreco(), 0.001);
 }
 @Test void deveBuscarProdutoExistentePeloServico() {
  Produto produto = service.cadastrar("Monitor", 600);
  repository.flush(); entityManager.clear();
  Produto encontrado = service.buscar(produto.getId());
  assertEquals("Monitor", encontrado.getNome());
  assertEquals(600, encontrado.getPreco(), 0.001);
 }
 @Test void deveRejeitarBuscaDeProdutoInexistente() {
  assertThrows(IllegalArgumentException.class, () -> service.buscar(-1L));
 }
 @Test void deveRejeitarAtualizacaoDeProdutoInexistente() {
  assertThrows(IllegalArgumentException.class, () -> service.atualizarPreco(-1L, 95));
  assertEquals(0, repository.count());
 }
 @Test void deveRemoverProdutoDoRepositorio() {
  Produto produto = service.cadastrar("Mouse", 80);
  repository.deleteById(produto.getId());
  repository.flush(); entityManager.clear();
  assertTrue(repository.findById(produto.getId()).isEmpty());
 }
}
