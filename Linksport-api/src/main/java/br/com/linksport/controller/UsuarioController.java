package br.com.linksport.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.linksport.model.Usuario;
import br.com.linksport.model.dto.UsuarioDTO;
import br.com.linksport.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioRepository repository;
	
	@PostMapping("/cadastrar")
	@ResponseBody
	public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody Usuario usuarioJSON) {
		Usuario u = new Usuario(usuarioJSON.getNomeCompleto(), usuarioJSON.getUsuario(), usuarioJSON.getEmail(), usuarioJSON.getSenha(), usuarioJSON.getDataNascimento(), usuarioJSON.getUf());
		u = repository.save(u);
		System.out.println(usuarioJSON);
		return new ResponseEntity<>(u.obterUsuarioDTO(), HttpStatus.CREATED); 
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable long id) {
		Optional<Usuario> u = repository.findById(id);
		
		 if (u.isPresent()) {
			 Usuario _u = u.get();
			 return new ResponseEntity<>(_u.obterUsuarioDTO(), HttpStatus.OK);
		 } else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	@GetMapping("/buscar/{email}")
	@ResponseBody
	public ResponseEntity<UsuarioDTO> buscarPorEmail(@PathVariable String email) {
		Usuario u = repository.findByEmail(email);
		if(u != null) {
			return ResponseEntity.status(HttpStatus.OK).body(u.obterUsuarioDTO());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@GetMapping("/buscar/user/{usuario}")
	@ResponseBody
	public ResponseEntity<UsuarioDTO> buscarPorUsuario(@PathVariable String usuario) {
		Usuario u = repository.findByUsuario(usuario);
		if(u != null) {
			return ResponseEntity.status(HttpStatus.OK).body(u.obterUsuarioDTO());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<UsuarioDTO> login(@RequestBody Usuario usuarioJSON) {
		Usuario u = repository.findByUsuarioSenha(usuarioJSON.getUsuario(), usuarioJSON.getSenha());
		if(u != null) {
			return ResponseEntity.status(HttpStatus.OK).body(u.obterUsuarioDTO());
			//return ResponseEntity.status(HttpStatus.OK).body(repository.findByIdUsuario(idUsuario))
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
		}
	}
	
	@DeleteMapping("apagar/{id}")
	@ResponseBody
	public ResponseEntity<Usuario> apagarUsuario(@PathVariable long id){
		try {
		      repository.deleteById(id);
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
    }
	
	@PutMapping("/atualizar/{id}")
	@ResponseBody
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuarioJSON, @PathVariable long id){
		String nomeNew = usuarioJSON.getNomeCompleto();
		String usuarioNew = usuarioJSON.getUsuario();
		String emailNew = usuarioJSON.getEmail();
		String senhaNew = usuarioJSON.getSenha();
		String dataNew = usuarioJSON.getDataNascimento();
		String ufNew = usuarioJSON.getUf();
		Optional<Usuario> u = repository.findById(id);
		
		 if (u.isPresent()) {
			 Usuario _u = u.get();
			 if(nomeNew != null) {
				 _u.setNomeCompleto(nomeNew); 								 
			 }
			 if(usuarioNew != null) {
				 _u.setUsuario(usuarioNew);
			 }
			 if(emailNew != null) {
				 _u.setEmail(emailNew);
			 }
			 if(senhaNew != null) {
				 _u.setSenha(senhaNew);
			 }
			 if(ufNew != null) {
				 _u.setUf(ufNew);
			 }
			 if(dataNew != null) {
				 _u.setDataNascimento(dataNew);
			 }
			 
			 return new ResponseEntity<>(repository.save(_u), HttpStatus.OK);
		 } else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
    }
}
