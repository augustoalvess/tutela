/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.cadastros.modelos.daos;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import tutela.modulos.cadastros.modelos.negocios.Pessoa;
import tutela.modulos.principal.modelos.daos.ConexaoBD;

/**
 *
 * @author augusto
 */
public class PessoaDAO extends ConexaoBD 
{
    public int codigoSalvo;
    
    /**
     * Registra ou atualiza uma nova criança na base de dados.
     * 
     * @param pessoa
     * @return boolean
     */
    public boolean salvar(Pessoa pessoa) 
    {
        if ( pessoa.getIdPessoa() > 0 )
        {
            return this.atualizar(pessoa);
        }
        else
        {
            return this.inserir(pessoa);
        }
    }
    
    /**
     * Registra uma nova criança na base de dados.
     * 
     * @param pessoa
     * @return boolean
     */
    private boolean inserir(Pessoa pessoa)
    {
        try 
        {
            String sql = "INSERT INTO pessoa" +
                                     "(nome," +
                                      "estadoCivil," +
                                      "dataNascimento," +
                                      "sexo," +
                                      "origemEtnica," +
                                      "estado," +
                                      "cidade," +
                                      "bairro," +
                                      "rua," +
                                      "numero," +
                                      "complemento," +
                                      "rg," +
                                      "cpf," +
                                      "telefoneResidencial," +
                                      "telefoneCelular," +
                                      "econselheiro," +
                                      "email)" +
                              "VALUES ('" + pessoa.getNome() + "'," +
                                      "'" + pessoa.getEstadoCivil() + "'," +
                                      "'" + pessoa.getDataNascimento() + "'," +
                                      "'" + pessoa.getSexo() + "'," +
                                      "'" + pessoa.getOrigemEtnica() + "'," +
                                      "'" + pessoa.getEstado() + "'," +
                                      "'" + pessoa.getCidade() + "'," +
                                      "'" + pessoa.getBairro() + "'," +
                                      "'" + pessoa.getRua() + "'," +
                                      "'" + pessoa.getNumero() + "'," +
                                      "'" + pessoa.getComplemento() + "'," +
                                      "'" + pessoa.getRg() + "'," +
                                      "'" + pessoa.getCpf() + "'," +
                                      "'" + pessoa.getTelefoneResidencial() + "'," +
                                      "'" + pessoa.getTelefoneCelular() + "'," +
                                      "'" + pessoa.iseConselheiro() + "'," +
                                      "'" + pessoa.getEmail() + "') " +
                           "RETURNING idpessoa";

            Statement st = super.getConnection().createStatement();
            ResultSet resultSet;
            
            resultSet = st.executeQuery(sql);
            resultSet.next();
            
            codigoSalvo = resultSet.getInt(1);
            
            super.registraLogDeRegistro(sql);

            return true;
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao salvar registro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Atualiza o registro de uma criança na base de dados.
     * 
     * @param pessoa
     * @return boolean
     */
    private boolean atualizar(Pessoa pessoa)
    {
        try 
        {
            String sql = "UPDATE pessoa " +
                            "SET nome = '" + pessoa.getNome() + "'," +
                                "estadoCivil = '" + pessoa.getEstadoCivil() + "'," +
                                "dataNascimento = '" + pessoa.getDataNascimento() + "'," +
                                "sexo = '" + pessoa.getSexo() + "'," +
                                "origemEtnica = '" + pessoa.getOrigemEtnica() + "'," +
                                "estado = '" + pessoa.getEstado() + "'," +
                                "cidade = '" + pessoa.getCidade() + "'," +
                                "bairro = '" + pessoa.getBairro() + "'," +
                                "rua = '" + pessoa.getRua() + "'," +
                                "numero = '" + pessoa.getNumero() + "'," +
                                "complemento = '" + pessoa.getComplemento() + "'," +
                                "rg = '" + pessoa.getRg() + "'," +
                                "cpf = '" + pessoa.getCpf() + "'," +
                                "telefoneResidencial = '" + pessoa.getTelefoneResidencial() + "'," +
                                "telefoneCelular = '" + pessoa.getTelefoneCelular() + "'," +
                                "econselheiro = '" + pessoa.iseConselheiro() + "'," +
                                "email = '" + pessoa.getEmail() + "' " +
                          "WHERE idPessoa = " + pessoa.getIdPessoa();
            
            Statement st = super.getConnection().createStatement();
            st.executeUpdate(sql);
            
            codigoSalvo = pessoa.getIdPessoa();
            
            super.registraLogDeRegistro(sql);

            return true;
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar registro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Lista todos os registros de pessoa, obtidos
     * da base de dados.
     * 
     * @param filtro
     * @return ResultSet
     */
    public ResultSet listar(int idPessoa, String busca)
    {        
        try
        {
            String sql = "SELECT idPessoa," +
                                "nome," +
                                "estadoCivil," +
                                "TO_CHAR(dataNascimento, 'dd/mm/yyyy') AS dataNascimento," +
                                "(CASE sexo WHEN 'M' THEN 'Masculino' ELSE 'Feminino' END) AS sexo," +
                                "origemEtnica," +
                                "cidade," +
                                "telefoneCelular," +
                                "(CASE econselheiro WHEN TRUE THEN 'Sim' ELSE 'Não' END) AS econselheiro," +
                                "estado," +
                                "bairro," +
                                "rua," +
                                "numero," +
                                "complemento," +
                                "rg," +
                                "cpf," +
                                "telefoneResidencial," +
                                "email " +
                           "FROM pessoa " +
                          "WHERE (CASE " + idPessoa + " WHEN 0 THEN TRUE ELSE (idPessoa = " + idPessoa + ") END) " +
                            "AND (CASE WHEN '" + busca + "' = 'null' THEN TRUE ELSE " +
                                      "((idpessoa::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(nome::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(estadoCivil::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(dataNascimento::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(sexo::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(origemEtnica::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(cidade::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(telefoneCelular::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(estado::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(bairro::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(rua::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(numero::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(econselheiro::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(complemento::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(rg::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(cpf::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(telefoneResidencial::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(email::TEXT ILIKE '%" + busca + "%')) " +
                                 "END) " +
                       "ORDER BY idPessoa, nome";
            
            Statement st = super.getConnection().createStatement();
            ResultSet resultSet = st.executeQuery(sql);
            
            return resultSet;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Erro ao listar registros: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Exclui registro de uma criança da base de dados.
     * 
     * @param idPessoa
     * @return boolean
     */
    public boolean excluir(int idPessoa)
    {
        try
        {
            String sql = "DELETE FROM pessoa " +
                               "WHERE idpessoa = " + idPessoa;
            
            Statement st = super.getConnection().createStatement();
            st.execute(sql);
            super.registraLogDeRegistro(sql);
            
            return true;
        }
        catch ( Exception e )
        {
            JOptionPane.showMessageDialog(null, "Erro ao excluir registro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}

