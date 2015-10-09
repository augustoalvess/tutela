/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.cadastros.modelos.daos;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import tutela.modulos.principal.modelos.daos.ConexaoBD;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import tutela.modulos.cadastros.modelos.negocios.Crianca;

/**
 *
 * @author augusto
 */
public class CriancaDAO extends ConexaoBD
{    
    public int codigoSalvo;
    
    /**
     * Registra ou atualiza uma nova criança na base de dados.
     * 
     * @param crianca
     * @return boolean
     */
    public boolean salvar(Crianca crianca) 
    {
        if ( crianca.getIdPessoa() > 0 )
        {
            return this.atualizar(crianca);
        }
        else
        {
            return this.inserir(crianca);
        }
    }
    
    /**
     * Registra uma nova criança na base de dados.
     * 
     * @param crianca
     * @return boolean
     */
    private boolean inserir(Crianca crianca)
    {
        try 
        {
            String sql = "INSERT INTO crianca" +
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
                                      "email," +
                                      "possuiNecessidadeEspecial," +
                                      "necessidadeEspecial," +
                                      "nomeMae," +
                                      "nomePai," +
                                      "outroResponsavel," +
                                      "certidaoNascimento)" +
                              "VALUES ('" + crianca.getNome() + "'," +
                                      "'" + crianca.getEstadoCivil() + "'," +
                                      "'" + crianca.getDataNascimento() + "'," +
                                      "'" + crianca.getSexo() + "'," +
                                      "'" + crianca.getOrigemEtnica() + "'," +
                                      "'" + crianca.getEstado() + "'," +
                                      "'" + crianca.getCidade() + "'," +
                                      "'" + crianca.getBairro() + "'," +
                                      "'" + crianca.getRua() + "'," +
                                      "'" + crianca.getNumero() + "'," +
                                      "'" + crianca.getComplemento() + "'," +
                                      "'" + crianca.getRg() + "'," +
                                      "'" + crianca.getCpf() + "'," +
                                      "'" + crianca.getTelefoneResidencial() + "'," +
                                      "'" + crianca.getTelefoneCelular() + "'," +
                                      "'" + crianca.getEmail() + "'," +
                                      "'" + crianca.isPossuiNecessidadeEspecial() + "'," +
                                      "'" + crianca.getNecessidadeEspecial() + "'," +
                                      "'" + crianca.getNomeMae() + "'," +
                                      "'" + crianca.getNomePai() + "'," +
                                      "'" + crianca.getOutroResponsavel() + "'," +
                                      "'" + crianca.getCertidaoNascimento() + "') " +
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
     * @param crianca
     * @return boolean
     */
    private boolean atualizar(Crianca crianca)
    {
        try 
        {
            String sql = "UPDATE crianca " +
                            "SET nome = '" + crianca.getNome() + "'," +
                                "estadoCivil = '" + crianca.getEstadoCivil() + "'," +
                                "dataNascimento = '" + crianca.getDataNascimento() + "'," +
                                "sexo = '" + crianca.getSexo() + "'," +
                                "origemEtnica = '" + crianca.getOrigemEtnica() + "'," +
                                "estado = '" + crianca.getEstado() + "'," +
                                "cidade = '" + crianca.getCidade() + "'," +
                                "bairro = '" + crianca.getBairro() + "'," +
                                "rua = '" + crianca.getRua() + "'," +
                                "numero = '" + crianca.getNumero() + "'," +
                                "complemento = '" + crianca.getComplemento() + "'," +
                                "rg = '" + crianca.getRg() + "'," +
                                "cpf = '" + crianca.getCpf() + "'," +
                                "telefoneResidencial = '" + crianca.getTelefoneResidencial() + "'," +
                                "telefoneCelular = '" + crianca.getTelefoneCelular() + "'," +
                                "email = '" + crianca.getEmail() + "'," +
                                "possuiNecessidadeEspecial = '" + crianca.isPossuiNecessidadeEspecial() + "'," +
                                "necessidadeEspecial = '" + crianca.getNecessidadeEspecial() + "'," +
                                "nomeMae = '" + crianca.getNomeMae() + "'," +
                                "nomePai = '" + crianca.getNomePai() + "'," +
                                "outroResponsavel = '" + crianca.getOutroResponsavel() + "'," +
                                "certidaoNascimento = '" + crianca.getCertidaoNascimento() + "' " +
                          "WHERE idPessoa = " + crianca.getIdPessoa();
            
            Statement st = super.getConnection().createStatement();
            st.executeUpdate(sql);
            
            codigoSalvo = crianca.getIdPessoa();
            
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
     * Lista todos os registros de crianças, obtidos
     * da base de dados.
     * 
     * @param filtro
     * @return ResultSet
     */
    public ResultSet listar(int idPessoa, String busca)
    {        
        try
        {
            String sql = "SELECT idpessoa," +
                                "nome," +
                                "estadoCivil," +
                                "TO_CHAR(dataNascimento, 'dd/mm/yyyy') AS dataNascimento," +
                                "(CASE sexo WHEN 'M' THEN 'Masculino' ELSE 'Feminino' END) AS sexo," +
                                "origemEtnica," +
                                "cidade," +
                                "telefoneCelular," +
                                "(CASE possuiNecessidadeEspecial WHEN TRUE THEN 'Sim' ELSE 'Não' END) AS possuiNecessidadeEspecial," +
                                "nomeMae," +
                                "nomePai," +
                                "outroResponsavel," +
                                "estado," +
                                "bairro," +
                                "rua," +
                                "numero," +
                                "complemento," +
                                "rg," +
                                "cpf," +
                                "telefoneResidencial," +
                                "email," +
                                "necessidadeEspecial," +
                                "certidaoNascimento " +
                           "FROM crianca " +
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
                                       "(possuiNecessidadeEspecial::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(nomeMae::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(nomePai::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(outroResponsavel::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(estado::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(bairro::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(rua::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(numero::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(complemento::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(rg::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(cpf::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(telefoneResidencial::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(email::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(necessidadeEspecial::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(certidaoNascimento::TEXT ILIKE '%" + busca + "%')) " +
                                 "END) " +
                       "ORDER BY idpessoa, nome";
            
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
     * @param idCrianca
     * @return boolean
     */
    public boolean excluir(int idPessoa)
    {
        try
        {
            String sql = "DELETE FROM crianca " +
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
