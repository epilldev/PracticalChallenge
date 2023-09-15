package br.com.practical.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.practical.Model.Cliente;
import br.com.practical.Repository.ClienteRepository;
import br.com.practical.Service.ClienteService;

@RestController
@RequestMapping("cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private ClienteService service;

	// Método para consultar
	@GetMapping("/consulta/{id}")
	public ResponseEntity<Cliente> consultarCliente(@PathVariable Long id) {
		try {
			Optional<Cliente> optionalCliente = repository.findById(id);

			if (optionalCliente.isPresent()) {
				Cliente cliente = optionalCliente.get();
				return ResponseEntity.ok(cliente);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Método para deletar
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<String> deletarCliente(@PathVariable Long id) {
		try {
			Optional<Cliente> optionalCliente = repository.findById(id);

			if (optionalCliente.isPresent()) {
				Cliente cliente = optionalCliente.get();
				service.excluir(cliente.getId().toString());
				return ResponseEntity.ok("Cliente com ID " + id + " excluído com sucesso.");
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao excluir o cliente com ID " + id + ": " + e.getMessage());
		}
	}

	// Método para criar
	@PostMapping("/criar")
	public ResponseEntity<String> criarCliente(@RequestBody Cliente cliente) {
		try {
			Cliente savedCliente = repository.save(cliente);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Cliente criado com sucesso. ID: " + savedCliente.getId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao criar o cliente: " + e.getMessage());
		}
	}

	// Método para alterar
	@PutMapping("/alterar/{id}")
	public ResponseEntity<String> alterarCliente(@PathVariable Long id, @RequestBody Cliente clienteNovo) {
		try {
			Optional<Cliente> optionalCliente = repository.findById(id);

			if (optionalCliente.isPresent()) {
				Cliente clienteAlterado = optionalCliente.get();

				clienteAlterado.setNomeCompleto(clienteNovo.getNomeCompleto());
				clienteAlterado.setEmail(clienteNovo.getEmail());
				clienteAlterado.setTelefone(clienteNovo.getTelefone());
				clienteAlterado.setDataNascimento(clienteNovo.getDataNascimento());
				clienteAlterado.setCpf(clienteNovo.getCpf());
				clienteAlterado.setEndereco(clienteNovo.getEndereco());
				clienteAlterado.setDataRegistro(clienteNovo.getDataRegistro());
				clienteAlterado.setStatusCliente(clienteNovo.getStatusCliente());

				repository.save(clienteAlterado);

				return ResponseEntity.ok("Cliente com ID " + id + " alterado com sucesso.");
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao alterar o cliente com ID " + id + ": " + e.getMessage());
		}
	}

}
