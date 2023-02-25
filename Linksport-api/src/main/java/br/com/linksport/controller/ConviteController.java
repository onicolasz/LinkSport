package br.com.linksport.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.linksport.model.Card;
import br.com.linksport.model.Convite;
import br.com.linksport.model.Usuario;
import br.com.linksport.repository.CardRepository;
import br.com.linksport.repository.ConviteRepository;
import br.com.linksport.repository.UsuarioRepository;

@Controller
@RequestMapping("/convite")
public class ConviteController {
	@Autowired
	private ConviteRepository repository;
	@Autowired
	private UsuarioRepository ur;
	@Autowired
	private CardRepository cr;
	
	@PostMapping("/cadastrar")
	@ResponseBody
	public ResponseEntity<Convite> cadastrar(@RequestParam long idUsuario, long idCard) {
		Optional<Usuario> u = ur.findById(idUsuario);
		Optional<Card> c = cr.findById(idCard);
		
		if (u.isPresent() || c.isPresent()) {
			 Usuario _u = u.get();
			 Card _c = c.get();
			 Convite cv = new Convite(_u,_c, "Esperando");
			 cv = repository.save(cv);
			 return new ResponseEntity<>(cv, HttpStatus.OK); 
		 } else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	@GetMapping("/{idUsuario}")
	@ResponseBody
	public ResponseEntity<List<Convite>> listarConvitesPorUsuario(@PathVariable long idUsuario){
		String resposta = "Esperando";
        return ResponseEntity.status(HttpStatus.OK).body(repository.findByIdUsuario(idUsuario, resposta));
    }
	
	@PutMapping("/atualizar/{idConvite}")
	@ResponseBody
	public ResponseEntity<Convite> atualizarConvite(@RequestBody Convite conviteJSON, @PathVariable long idConvite){
		String respostaNew = conviteJSON.getResposta();
		Optional<Convite> cv = repository.findById(idConvite);
		
		 if (cv.isPresent()) {
			 Convite _cv = cv.get();
			 _cv.setResposta(respostaNew);
			 return new ResponseEntity<>(repository.save(_cv), HttpStatus.OK);
		 } else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
    }
}
