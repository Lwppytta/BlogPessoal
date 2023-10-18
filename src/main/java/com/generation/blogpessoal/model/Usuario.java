package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Nome de Usuario é obrigatório.")
	private String nomeUsuario;

	@Schema(example="email@email.com.br")
	@NotNull(message = "Email de Usuario é obrigatório.")
	@Email(message = "Email de Usuario de Usuario é obrigatório.")
	private String emailUsuario;

	@NotBlank(message = "Senha é obrigatório.")
	@Size(min = 8, message = "Senha deve ter no minimo 8 caracteres.")
	private String senha;

	@Size(max=5000,message = "O link da foto não pode ser maior do que 5000 caracteres.")
	private String fotoUsuario;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Postagem> postagem;

	public Usuario(Long id, String nomeUsuario, String emailUsuario, 
			String senha,String fotoUsuario) {
		this.id = id;
		this.nomeUsuario = nomeUsuario;
		this.emailUsuario = emailUsuario;
		this.senha = senha;
		this.fotoUsuario = fotoUsuario;
	}
	
	public Usuario() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFotoUsuario() {
		return fotoUsuario;
	}

	public void setFotoUsuario(String fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
}