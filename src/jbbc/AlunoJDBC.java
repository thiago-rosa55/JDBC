package jbbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import entities.Aluno;

public class AlunoJDBC {

	Connection con;
	String sql;
	PreparedStatement pst;

	public void salvar(Aluno a) throws IOException {

		try {
			Connection con = db.getConexao();
			System.out.println("Conexão realizada com sucesso !");

			sql = "INSERT INTO aluno (nome, sexo, dt_nasc, id) VALUES ( ?,  ?, ?, ?)";

			pst = con.prepareStatement(sql);
			pst.setString(1, a.getNome());
			pst.setString(2, a.getSexo());

			Date dataSql = new Date(a.getDt_nasc().getTime());
			pst.setDate(3, dataSql);
			pst.setInt(4, a.getId());
			pst.executeUpdate();
			System.out.println("\nCadastro do aluno realizado com sucesso!");

			db.fechaConexao();
			System.out.println("Conexão fechada com sucesso !");
		} catch (SQLException e) {

			System.out.println(e);
		}

	}

	public List<Aluno> listar() throws IOException, SQLException {
		Connection con = db.getConexao();

		try {

			List<Aluno> alunos = new ArrayList<Aluno>();
			PreparedStatement stmt = con.prepareStatement("select * from aluno");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				Aluno aluno = new Aluno();
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setSexo(rs.getString("sexo"));
				aluno.setDt_nasc(rs.getDate("dt_nasc"));

				alunos.add(aluno);
			}
			rs.close();
			stmt.close();
			return alunos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void apagar(int id) throws IOException, SQLException {
		Connection connection = db.getConexao();
		try {

			PreparedStatement stmt = connection.prepareStatement("DELETE FROM aluno WHERE id=?");
			stmt.setInt(1, id);
			stmt.execute();
			System.out.println("Aluno(a) Apagado com sucesso!");
			stmt.close();
		} catch (SQLException e) {
			
		}

	}

	public void alterar(Aluno a, int id) throws IOException, SQLException {
		Connection connection = db.getConexao();

		String sqlBusca = "select * from aluno where id = ?";

		PreparedStatement statementBusca = connection.prepareStatement(sqlBusca);
		statementBusca.setInt(1, id);
		
		ResultSet resultSet = statementBusca.executeQuery();
		if (resultSet.next()) {
			String sqlAtualizacao = "UPDATE aluno set nome=?, sexo=?, dt_nasc=?";
			try {
				PreparedStatement stmt = connection.prepareStatement(sqlAtualizacao);
				stmt.setString(1, a.getNome());
				stmt.setString(2, a.getSexo());
				stmt.setDate(3, new Date(a.getDt_nasc().getTime()));
				stmt.executeUpdate();
				System.out.println("Aluno(a) Alterado com sucesso!");
				stmt.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			

		}
	}
	public Aluno buscaAluno(int id) throws SQLException, IOException {
		Connection connection = db.getConexao();
		
		PreparedStatement stmt = connection.prepareStatement("select * from aluno where id = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		try {

			Aluno aluno = new Aluno();

			if (rs.next()) {
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setSexo(rs.getString("sexo"));
				aluno.setDt_nasc(rs.getDate("dt_nasc"));
			}

			rs.close();
			stmt.close();
			return aluno;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
