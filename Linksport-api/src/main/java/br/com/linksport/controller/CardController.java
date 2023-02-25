package br.com.linksport.controller;

import java.util.List;
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

import br.com.linksport.model.Card;
import br.com.linksport.model.Usuario;
import br.com.linksport.repository.CardRepository;
import br.com.linksport.repository.UsuarioRepository;


@Controller
@RequestMapping("/card")
public class CardController {
	@Autowired
	private CardRepository repository;
	@Autowired
	private UsuarioRepository ur;
	
	@PostMapping("/{idUsuario}")
	@ResponseBody
	public ResponseEntity<Card> cadastrar(@RequestBody Card cardJSON, @PathVariable long idUsuario) {
		Optional<Usuario> u = ur.findById(idUsuario);
		
		if (u.isPresent()) {
			 Usuario _u = u.get();
			 Card c = new Card(cardJSON.getTitulo(), cardJSON.getData(), cardJSON.getStatus(), cardJSON.getEvento(), cardJSON.getHora(), cardJSON.getLocalizacao(), _u);
			 c = repository.save(c);
			 System.out.println(cardJSON);
			 return new ResponseEntity<>(c, HttpStatus.OK); 
		 } else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	@GetMapping("/{idUsuario}")
	@ResponseBody
	public ResponseEntity<List<Card>> listarCardsPorUsuario(@PathVariable long idUsuario){
        return ResponseEntity.status(HttpStatus.OK).body(repository.findByIdUsuario(idUsuario));
    }
	
	@PutMapping("/{idCard}")
	@ResponseBody
	public ResponseEntity<Card> atualizarCard(@RequestBody Card cardJSON, @PathVariable long idCard){
		String statusNew = cardJSON.getStatus();
		Optional<Card> c = repository.findById(idCard);
		
		 if (c.isPresent()) {
			 Card _c = c.get();
			 _c.setStatus(statusNew);
			 return new ResponseEntity<>(repository.save(_c), HttpStatus.OK);
		 } else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
    }
	
	@DeleteMapping("/{idCard}")
	@ResponseBody
	public ResponseEntity<Card> apagarCard(@PathVariable long idCard){
		try {
		      repository.deleteById(idCard);
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
    }
}


