package br.com.linksport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.linksport.model.Card;


public interface CardRepository extends JpaRepository<Card, Long>{
	@Query(value = "SELECT * FROM card WHERE usuarioid = :param", nativeQuery = true)
	List<Card> findByIdUsuario(@Param("param") long idUsuario);
	
	@Query(value = "SELECT * FROM card WHERE id = :param", nativeQuery = true)
	Card findByIdCard(@Param("param") long idCard);
}
