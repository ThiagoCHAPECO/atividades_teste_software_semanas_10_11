package br.edu.exemplo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
import br.edu.exemplo.model.Produto;
import br.edu.exemplo.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.AfterTransaction;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest @AutoConfigureMockMvc @Transactional
class ProdutoControllerIntegrationTest {
 @Autowired MockMvc mockMvc;
 @Autowired ObjectMapper objectMapper;
 @Autowired ProdutoRepository repository;
 @Autowired EntityManager entityManager;
 private long quantidadeAntesDaTransacao;
 @BeforeTransaction void registrarQuantidadeInicial() {
  quantidadeAntesDaTransacao = repository.count();
 }
 @AfterTransaction void confirmarRollback() {
  assertEquals(quantidadeAntesDaTransacao, repository.count());
 }
 @Test void deveCadastrarProdutoPelaApi() throws Exception {
  String resposta = mockMvc.perform(post("/produtos")
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
      {"nome":"Mouse","preco":80.0}
      """))
   .andExpect(status().isOk())
   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
   .andExpect(jsonPath("$.id").isNumber())
   .andExpect(jsonPath("$.nome").value("Mouse"))
   .andExpect(jsonPath("$.preco").value(80.0))
   .andReturn().getResponse().getContentAsString();
  long id = objectMapper.readTree(resposta).get("id").asLong();
  repository.flush(); entityManager.clear();
  Produto salvo = repository.findById(id).orElseThrow();
  assertEquals("Mouse", salvo.getNome());
  assertEquals(80, salvo.getPreco(), 0.001);
 }
 @Test void deveBuscarProdutoPelaApi() throws Exception {
  String resposta = mockMvc.perform(post("/produtos")
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
      {"nome":"Mouse","preco":80.0}
      """))
   .andExpect(status().isOk())
   .andExpect(jsonPath("$.id").isNumber())
   .andReturn().getResponse().getContentAsString();
  JsonNode cadastrado = objectMapper.readTree(resposta);
  long id = cadastrado.get("id").asLong();
  repository.flush(); entityManager.clear();
  String consulta = mockMvc.perform(get("/produtos/{id}", id))
   .andExpect(status().isOk())
   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
   .andExpect(jsonPath("$.id").value(id))
   .andExpect(jsonPath("$.nome").value("Mouse"))
   .andExpect(jsonPath("$.preco").value(80.0))
   .andReturn().getResponse().getContentAsString();
  assertEquals(cadastrado, objectMapper.readTree(consulta));
 }
 @Test void deveRecusarJsonMalformadoSemPersistir() throws Exception {
  long antes = repository.count();
  mockMvc.perform(post("/produtos").contentType(MediaType.APPLICATION_JSON).content("{"))
   .andExpect(status().isBadRequest());
  assertEquals(antes, repository.count());
 }
}
