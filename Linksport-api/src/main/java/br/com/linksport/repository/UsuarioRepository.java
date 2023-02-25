package br.com.linksport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.linksport.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	//Usuario findById(long id);
	
	@Query(value="SELECT U from Usuario as U where U.usuario = ?1 and U.senha = ?2")
	Usuario findByUsuarioSenha(String usuario, String senha);
	
	@Query(value="SELECT U from Usuario as U where U.email = ?1")
	Usuario findByEmail(String email);
	
	@Query(value="SELECT U from Usuario as U where U.usuario = ?1")
	Usuario findByUsuario(String usuario);
	
}
