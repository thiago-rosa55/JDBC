package application;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;
import entities.Aluno;
import jbbc.AlunoJDBC;

public class Programa {

	public static void main(String[] args) {

		try {

			int opcao = 0;
			Scanner console = new Scanner(System.in);

			do {
				System.out.print("######## Menu ########" + "\n1- Cadastrar" + "\n2- Listar" + "\n3- Alterar"
						+ "\n4- Excluir" + "\n5- Sair");
				System.out.print("\n\tOpção: ");
				opcao = Integer.parseInt(console.nextLine());
				System.out.println("\n\n\n\n");

				if (opcao == 1) {

					Aluno a = new Aluno();
					AlunoJDBC acao = new AlunoJDBC();

					System.out.print("\n*** Cadastrar Aluno ***\n\r");
					System.out.print("Nome: ");
					a.setNome(console.nextLine());
					System.out.print("Sexo: ");
					a.setSexo(console.nextLine());
					System.out.print("Data de nascimento: ");
					a.setDt_nasc(new Date(console.nextLine()));
					System.out.println("Digite o id: ");
					a.setId(console.nextInt());

					acao.salvar(a);
					console.nextLine();
					System.out.println("\n\n\n\n");
				}
				if (opcao == 2) {

					AlunoJDBC acao = new AlunoJDBC();

					for (Aluno a : acao.listar()) {
						if (a == null) {
							System.out.println("Nenhum Aluno(a) cadastrado!");
						} else {
							System.out.println("Nome:" + a.getNome() + "\nSexo:" + a.getSexo() + "\nData Nascimento:"
									+ a.getDt_nasc() + "\nid:" + a.getId());
							System.out.println();

						}
					}

					console.nextLine();
					System.out.println("\n\n");
				}
				if (opcao == 3) {
					int i=0;
					Aluno al = new Aluno();
					Aluno a = new Aluno();
					
					AlunoJDBC acao = new AlunoJDBC();

					System.out.print("\n*** Alterar Aluno(a) ***\n\r");
					System.out.print("Alterar nome: ");
					al.setNome(console.nextLine());
					System.out.print("Alterar sexo: ");
					al.setSexo(console.nextLine());
					System.out.print("Alterar data de nascimento: ");
					al.setDt_nasc(new Date(console.nextLine()));
					System.out.println("Digite o id: ");
					i = (console.nextInt());
					a = acao.buscaAluno(i);
					try {
						
						acao.alterar(al, a.getId());

					} catch (SQLException e) {

					}
					System.out.println("\n*** Aluno(a) Alterado com sucesso! ***\n\r");

					console.nextLine();
					System.out.println("\n\n");

				}
				if (opcao == 4) {

					AlunoJDBC acao = new AlunoJDBC();
					int i = 0;
					System.out.print("\n*** Excluir Aluno ***\n\r");
					System.out.print("Digite id do Aluno(a): ");
					i = (console.nextInt());
					acao.apagar(i);

					console.nextLine();
					System.out.println("\n\n\n\n");
				}
			} while (opcao != 5);
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}
}
