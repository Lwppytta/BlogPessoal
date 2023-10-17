package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuariosRepository;
import com.generation.blogpessoal.service.UsuariosService;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuariosService usuarioService;
	
	@Autowired
	private UsuariosRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		
		usuarioService.cadastrarUsuario(new Usuario(0L,"Root","root@root.com","root","-"));
	}
	
	@Test
	@DisplayName("Cadastrar um Usuario.")
	public void deveCriarUmUsuario() {
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario> (new Usuario(0L,"Paulo Antunes","paulo_a@email.com","12345678","-"));
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
	
		assertEquals(HttpStatus.CREATED,corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Não Deve Duplicar Usuario.")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Maria da Silva","marisilva@email.com","12345678","-"));
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario> (new Usuario(0L,
				"Maria da Silva","marisilva@email.com","12345678","-"));
	
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST,corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST,corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Atualizar Usuario.")
	public void deveAtualizarUsuario() {
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,
				"Amanda Leticia","amandalw@email.com","22222444","-"));
	
		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
				"Amanda Leticia Gomes","amandalw@email.com","22222444","-");
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.withBasicAuth("root@root.com","root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK,corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve Mostrar Todos Usuarios.")
	public void deveMostrarTodosUsuarios() {
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Maria da Silva","marisilva@email.com","12345678","-"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Amanda Leticia","amandalw@email.com","22222444","-"));
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root@root.com","root")
				.exchange("/usuarios/all", HttpMethod.GET,null,String.class);
		
		assertEquals(HttpStatus.OK,resposta.getStatusCode());
	}
}