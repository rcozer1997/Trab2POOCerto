package foodApp.Usuarios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import foodApp.App;
import foodApp.Exceptions.CodigoNaoEncontradoException;
import foodApp.Lanchonetes.ComparaLanchonetePontos;
import foodApp.Lanchonetes.ComparaLanchonetePrecoMedio;
import foodApp.Lanchonetes.ComparaLanchoneteVendas;
import foodApp.Lanchonetes.Lanche;
import foodApp.Lanchonetes.Lanchonete;

public class Cliente extends Usuario {
	Scanner s = new Scanner (System.in);
	public Cliente(Sistema sistema) {
		super(sistema);
		this.ident = 3;
	}

	public Cliente(ArrayList<String> list) {
		super(list);
	}

	public void menu() {
		int opcao;
		
		do {
		System.out.println("------------------------------------------------");
		System.out.println("              MENU CLIENTE                      ");
		System.out.println("------------------------------------------------");
		System.out.println("1) Listar todas as lanchonetes por pontuação");
		System.out.println("2) Listar lanchonetes de uma categoriapor pontuação");
		System.out.println("3) Listar lanchonetes por preco");
		System.out.println("4) Listar lanchonetes pelo numero de vendas");
		System.out.println("5) Ver lanches de uma lanchonete");
		System.out.println("6) Buscar lanche");
		System.out.println("7) Fazer pedido");
		System.out.println("8) Remover cadastro");
		System.out.println("9) Avaliar Lanchonete");
		System.out.println("0) Sair");

		opcao = s.nextInt();
		switch(opcao) {
		case 1:
			listarLanchonetesPontuacao();
			break;
		case 2:
			listarPontuacaoCategoria();
			break;
		case 3:
			listarLanchonetesPrecoMedio();
			break;
		case 4:
			listarLanchonetesVendas();
			break;
		case 5:
			try {
			verLanchesLanchonete();
			}catch(CodigoNaoEncontradoException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 6:
			buscarLanche();
			break;
		case 7:
			try {
			fazerPedidoLanchonete();
			}catch(CodigoNaoEncontradoException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 8:
			removerCadastro(this.getEmail(), sistema.getListaUsuarios());
			App.main(null);
			break;
		case 9:
			try {
			avaliaLanchonete();
			}catch(CodigoNaoEncontradoException e) {
				System.out.println(e.getMessage());
			}			
			break;
		default:
			if(opcao == 0) {
				System.out.println("");
			}
			else System.out.println("Opcao invalida! Tente novamente.");
			break;
		}	
		}while(opcao!=0);
	}

	public void exibirLanchonetes() {	
		System.out.println("---------------------------------------------------------");
		System.out.printf("%15s%20s%15s%15s%15s", "CODIGO", "NOME", "ENDERECO", "CATEGORIA", "PONTOS");
		System.out.println();
		System.out.println("---------------------------------------------------------");
		for(Lanchonete l : sistema.getTodasLanchonetes()) {
			System.out.println("---------------------------------------------------------");
			System.out.format("%15s%20s%15s%15s%15s",l.codigo, l.nome, l.endereco, l.categoria, l.pontos);
			System.out.println();
			System.out.println("---------------------------------------------------------");
		}
			
	}
	
	public Lanchonete buscarLanchonete(int codigo) {
		for(Lanchonete l : sistema.getTodasLanchonetes())
			if(l.codigo == codigo)
				return l;
		return null;
	}
		
	public void listarLanchonetes() {
		System.out.println("---------------------------------------------------------");
		System.out.printf("%15s%20s%15s%15s%15s", "CODIGO", "NOME", "ENDERECO", "CATEGORIA", "PONTOS");
		System.out.println();
		System.out.println("---------------------------------------------------------");
		for(Lanchonete l : sistema.getTodasLanchonetes()) {
			System.out.println("---------------------------------------------------------");
			System.out.format("%15s%20s%15s%15s%15s",l.codigo, l.nome, l.endereco, l.categoria, l.pontos);
			System.out.println();
			System.out.println("---------------------------------------------------------");
			
		}
	}
	
	public void verLanchesLanchonete() throws CodigoNaoEncontradoException{
		listarLanchonetes();
		System.out.println("Qual lanchonete gostaria de visualizar?");
		int codigo = s.nextInt();
		Lanchonete l = buscarLanchonete(codigo);
		if(l == null) {
			throw new CodigoNaoEncontradoException("Codigo invalido!");
		}
		else {
			l.listarLanches();
		}
	}
	
	public void listarLanchonetesPontuacao() {
		ComparaLanchonetePontos c = new ComparaLanchonetePontos();
		Collections.sort(sistema.getTodasLanchonetes(), c);
		System.out.println(sistema.getTodasLanchonetes());
	}
	
	public void listarPontuacaoCategoria() {
		Scanner s = new Scanner (System.in);
		System.out.print("Categoria das lanchonetes a serem exibidas:");
		String categoria = s.nextLine();
		ArrayList<Lanchonete> lanchonete = new ArrayList<>();
		for(Lanchonete l : sistema.getTodasLanchonetes()) {
			if(l.categoria.equals(categoria))
				lanchonete.add(l);
		}
		ComparaLanchonetePontos c = new ComparaLanchonetePontos();
		Collections.sort(lanchonete, c);
		System.out.println(lanchonete);
	}
	
	public void listarLanchonetesPrecoMedio() {
		ComparaLanchonetePrecoMedio c = new ComparaLanchonetePrecoMedio();
		Collections.sort(sistema.getTodasLanchonetes(), c);
		exibirLanchonetes();
	}
	
	public void listarLanchonetesVendas() {
		ComparaLanchoneteVendas c = new ComparaLanchoneteVendas();
		Collections.sort(sistema.getTodasLanchonetes(), c);
		exibirLanchonetes();
	}
	
	public void buscarLanche() {
		System.out.println("Digite o lanche que deseja buscar: ");
		String lancheBuscado = s.nextLine();
		System.out.println("---------------------------------------------------------");
		System.out.printf("%20s%20s%20s%15s", "NOME", "LANCHONETE", "COD. LANCHONETE", "COD. PRODUTO");
		System.out.println();
		System.out.println("---------------------------------------------------------");
		for(Lanchonete l : sistema.getTodasLanchonetes()) {		
			for(Lanche lanche : l.getListaLanches()) {
				if(lanche.getNome().contains(lancheBuscado)) {
					System.out.println("---------------------------------------------------------");
					System.out.format("%20s%20s%15s%15s",lanche.getNome(), lanche.getNomeLanchonete(), l.getCodigo(), lanche.getCodigo());
					System.out.println();
					System.out.println("---------------------------------------------------------");
				}
			}
		}
	}
	
	public void fazerPedidoLanchonete() throws CodigoNaoEncontradoException{
		exibirLanchonetes();
		System.out.println("Codigo da lanchonete a se fazer pedido: ");
		int codigo = s.nextInt();
		Lanchonete l = buscarLanchonete(codigo);
		if(l == null) {
			throw new CodigoNaoEncontradoException("Codigo invalido!");
		}
		else {
			l.fazerPedido(this.getNome());
		}
	}
	
	public void avaliaLanchonete() throws CodigoNaoEncontradoException{
		exibirLanchonetes();
		System.out.println("Qual lanchonete deseja avaliar?");
		int codigo = s.nextInt();
		Lanchonete l = buscarLanchonete(codigo);
		if(l == null) {
			throw new CodigoNaoEncontradoException("Codigo invalido!");
		}
		else {
			System.out.println("Insira uma nota de 0 a 5: ");
			float nota = s.nextFloat();
			if(nota < 0 || nota > 5) {
				System.out.println("Nota avaliativa invalida! Insira uma nota de 0 a 5!");
			}
			else {
				l.atualizaPontos(nota);
				arq.salvaLanchoneteArq(sistema.getTodasLanchonetes());
			}
			
		}
	}
}
