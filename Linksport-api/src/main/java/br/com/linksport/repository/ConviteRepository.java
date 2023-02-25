package br.com.linksport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.linksport.model.Convite;


public interface ConviteRepository extends JpaRepository<Convite, Long>{
	
	@Query(value = "SELECT * FROM convite WHERE usuario_id = :param AND resposta = :param2", nativeQuery = true)
	List<Convite> findByIdUsuario(@Param("param") long idUsuario, @Param("param2") String resposta);

}
