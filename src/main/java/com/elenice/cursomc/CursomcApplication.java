package com.elenice.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.elenice.cursomc.domain.Categoria;
import com.elenice.cursomc.domain.Cidade;
import com.elenice.cursomc.domain.Cliente;
import com.elenice.cursomc.domain.Endereco;
import com.elenice.cursomc.domain.Estado;
import com.elenice.cursomc.domain.Pagamento;
import com.elenice.cursomc.domain.PagamentoComBoleto;
import com.elenice.cursomc.domain.PagamentoComCartao;
import com.elenice.cursomc.domain.Pedido;
import com.elenice.cursomc.domain.Produto;
import com.elenice.cursomc.domain.enums.EstadoPagamento;
import com.elenice.cursomc.domain.enums.TipoCliente;
import com.elenice.cursomc.repositories.CategoriaRepository;
import com.elenice.cursomc.repositories.CidadeRepository;
import com.elenice.cursomc.repositories.ClienteRepository;
import com.elenice.cursomc.repositories.EnderecoRepository;
import com.elenice.cursomc.repositories.EstadoRepository;
import com.elenice.cursomc.repositories.PagamentoRepository;
import com.elenice.cursomc.repositories.PedidoRepository;
import com.elenice.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository; //dependencia
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3)); //associando categoria ao produto
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1)); //associando produto a categoria	
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Elenice", "elenice@gmail.com", "999.999.989-44", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("119516-72888", "112345-7890"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "2", "Apto 303", "Jardim", "34886600", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "45677889", cli1, c2);
		
		//cliente conhecendo seus 2 enderecos
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		//mascara de formatação pra instanciar uma data
		SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sfd.parse("30/02/2017 10:32"), cli1, e1);	
		Pedido ped2 = new Pedido(null, sfd.parse("10/10/2017 19:35"), cli1, e2);	
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,sfd.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
	}
	

}
