package br.com.practical.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.practical.Model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	// Query nativa para excluir o usuário
	@Modifying
	@Query(value = "DELETE FROM cliente WHERE id = ?1", nativeQuery = true)
	void deleteClienteById(Long id);

}
