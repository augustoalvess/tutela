/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.cadastros.modelos.daos;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import tutela.modulos.cadastros.modelos.negocios.Ocorrencia;
import tutela.modulos.cadastros.modelos.negocios.Pessoa;
import tutela.modulos.principal.modelos.daos.ConexaoBD;

/**
 *
 * @author augusto
 */
public class OcorrenciaPessoaDAO extends ConexaoBD {
    
    /**
     * Registra ou atualiza as pessoas da ocorrencia
     * 
     * @param ocorrencia
     * @param pessoas
     * @return boolean
     */
    public boolean salvar(Ocorrencia ocorrencia, List<Pessoa> pessoas) 
    {
        try 
        {
            excluir( ocorrencia.getIdOcorrencia() );
            
            for ( Pessoa pessoa : pessoas ) {
                inserir( ocorrencia.getIdOcorrencia(), pessoa.getIdPessoa() );
            }
            
            return true;
        } 
        catch ( Exception e ) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao salvar registro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Registra a lista de pessoas da ocorrencia
     * 
     * @param idOcorrencia
     * @param idPessoa
     * @return boolean
     */
    private boolean inserir(int idOcorrencia, int idPessoa)
    {
        try 
        {
            String sql = "INSERT INTO ocorrenciaPessoa" +
                                     "(idocorrencia," +
                                      "idpessoa)" +
                              "VALUES (" + idOcorrencia + "," +
                                      "" + idPessoa + ") ";

            Statement st = super.getConnection().createStatement();
            st.executeUpdate(sql);
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
     * Lista todos as pessoas da ocorrencia, já retorna lista de pessoas
     * 
     * @param idOcorrencia
     * @return ResultSet
     */
    public List<Pessoa> listar(int idOcorrencia)
    {        
        try
        {
            String sql = "SELECT * " +
                           "FROM ocorrenciapessoa " +
                          "WHERE idocorrencia = " + idOcorrencia;
            
            Statement st = super.getConnection().createStatement();
            ResultSet resultSet = st.executeQuery(sql);
            
            List<Pessoa> pessoas = new ArrayList<Pessoa>();

            while(resultSet.next()) {
                pessoas.add(new Pessoa(resultSet.getInt("idpessoa")));
            }
            
            return pessoas;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Erro ao listar registros: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
            return new ArrayList();
        }
    }
    
    /**
     * Exclui registros de pessoas vinculadas a ocorrencia
     * 
     * @param idOcorrencia
     * @return boolean
     */
    public boolean excluir(int idOcorrencia)
    {
        try
        {
            String sql = "DELETE FROM ocorrenciapessoa " +
                               "WHERE idocorrencia = " + idOcorrencia;
            
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
