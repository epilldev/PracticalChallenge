package br.com.practical.Service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import br.com.practical.Model.Cliente;

@Service
public class ClienteService {

	private final JdbcTemplate jdbcTemplate;

	public ClienteService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Serviço para lidar com a query utilizando o JDBCTemplate para a inserção.
	public Cliente save(Cliente cliente) {
		String sql = "INSERT INTO clientes (nome_completo, email, telefone, data_nascimento, cpf, endereco, data_registro, status_cliente) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, cliente.getNomeCompleto(), cliente.getEmail(), cliente.getTelefone(),
				cliente.getDataNascimento(), cliente.getCpf(), cliente.getEndereco(), cliente.getDataRegistro(),
				cliente.getStatusCliente());
		return cliente;
	}

	// Método para deletar um cliente pelo ID utilizando o JDBCTemplate
	public void excluir(String id) {
		String sql = "DELETE FROM clientes WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

}
