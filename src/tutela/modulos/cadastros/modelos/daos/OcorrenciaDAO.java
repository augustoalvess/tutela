/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.cadastros.modelos.daos;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import tutela.modulos.cadastros.modelos.negocios.Ocorrencia;
import tutela.modulos.principal.modelos.daos.ConexaoBD;
import tutela.modulos.principal.modelos.negocio.UsuarioLogado;

/**
 *
 * @author augusto
 */
public class OcorrenciaDAO  extends ConexaoBD
{    
    public int codigoSalvo;
    
    /**
     * Registra ou atualiza uma nova ocorrencia na base de dados.
     * 
     * @param ocorrencia
     * @return boolean
     */
    public boolean salvar(Ocorrencia ocorrencia) 
    {
        if ( ocorrencia.getIdOcorrencia() > 0 )
        {
            new OcorrenciaPessoaDAO().salvar(ocorrencia, ocorrencia.getPessoasOcorrencia());
            return this.atualizar(ocorrencia);
        }
        else
        {
            new OcorrenciaPessoaDAO().salvar(ocorrencia, ocorrencia.getPessoasOcorrencia());
            return this.inserir(ocorrencia);
        }
    }
    
    /**
     * Registra uma nova ocorrencia na base de dados.
     * 
     * @param ocorrencia
     * @return boolean
     */
    private boolean inserir(Ocorrencia ocorrencia)
    {
        try 
        {
            //Depois de inserir, já fica como aberta.
            ocorrencia.setSituacao(Ocorrencia.ABERTA);
            
            int criancaAssociadaId = 0;
            if ( ocorrencia.getCriancaAssociada() != null ) 
            {
                criancaAssociadaId = ocorrencia.getCriancaAssociada().getIdPessoa();
            }
            
            String sql = "INSERT INTO ocorrencia" +
                                     "(pessoaid," +
                                      "dataocorrencia," +
                                      "nomedeclarante," +
                                      "telefonedeclarante," +
                                      "criancaid," +
                                      "estadoocorreu," +
                                      "cidadeocorreu," +
                                      "bairroocorreu," +
                                      "ruaocorreu," +
                                      "numeroocorreu," +
                                      "complementoocorreu," +
                                      "declaracao," +
                                      "observacao," +
                                      "dataintimacaoprocessada," +
                                      "intimacao," +
                                      "dataintimacao," +
                                      "estadointimacao," +
                                      "cidadeintimacao," +
                                      "bairrointimacao," +
                                      "ruaintimacao," +
                                      "numerointimacao," +
                                      "complementointimacao," +
                                      "providenciatomada," +
                                      "parecerconclusivo," +
                                      "situacao)" +
                              "VALUES (" + ocorrencia.getPessoaConselheiro().getIdPessoa() + "," +
                                      "'" + ocorrencia.getDataOcorrencia() + "'," +
                                      "'" + ocorrencia.getNomeDeclarante() + "'," +
                                      "'" + ocorrencia.getTelefoneDeclarante() + "'," +
                                      "" + criancaAssociadaId + "," +
                                      "'" + ocorrencia.getEstadoOcorreu()+ "'," +
                                      "'" + ocorrencia.getCidadeOcorreu() + "'," +
                                      "'" + ocorrencia.getBairroOcorreu() + "'," +
                                      "'" + ocorrencia.getRuaOcorreu() + "'," +
                                      "'" + ocorrencia.getNumeroOcorreu() + "'," +
                                      "'" + ocorrencia.getComplementoOcorreu() + "'," +
                                      "'" + ocorrencia.getDeclaracao() + "'," +
                                      "'" + ocorrencia.getObservacao() + "'," +
                                      "'" + ocorrencia.getDataIntimacaoProcessada() + "'," +
                                      "'" + ocorrencia.getIntimacao() + "'," +
                                      "'" + ocorrencia.getDataIntimacao() + "'," +
                                      "'" + ocorrencia.getEstadoIntimacao() + "'," +
                                      "'" + ocorrencia.getCidadeIntimacao() + "'," +
                                      "'" + ocorrencia.getBairroIntimacao() + "'," +
                                      "'" + ocorrencia.getRuaIntimacao() + "'," +
                                      "'" + ocorrencia.getNumeroIntimacao() + "'," +
                                      "'" + ocorrencia.getComplementoIntimacao() + "'," +
                                      "'" + ocorrencia.getProvidenciaTomada()+ "'," +
                                      "'" + ocorrencia.getParecerConclusivo() + "'," +
                                      "'" + ocorrencia.getSituacao() + "') " +
                           "RETURNING idocorrencia";

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
     * Atualiza o registro de uma ocorrencia na base de dados.
     * 
     * @param ocorrencia
     * @return boolean
     */
    private boolean atualizar(Ocorrencia ocorrencia)
    {
        try 
        {
            int criancaAssociadaId = 0;
            if ( ocorrencia.getCriancaAssociada() != null ) 
            {
                criancaAssociadaId = ocorrencia.getCriancaAssociada().getIdPessoa();
            }
            
            String sql = "UPDATE ocorrencia " +
                            "SET pessoaid = " + ocorrencia.getPessoaConselheiro().getIdPessoa() + "," +
                                "dataocorrencia = '" + ocorrencia.getDataOcorrencia() + "'," +
                                "nomedeclarante = '" + ocorrencia.getNomeDeclarante() + "'," +
                                "telefonedeclarante = '" + ocorrencia.getTelefoneDeclarante() + "'," +
                                "criancaid = " + criancaAssociadaId + "," +
                                "estadoocorreu = '" + ocorrencia.getEstadoOcorreu() + "'," +
                                "cidadeocorreu = '" + ocorrencia.getCidadeOcorreu() + "'," +
                                "bairroocorreu = '" + ocorrencia.getBairroOcorreu() + "'," +
                                "ruaocorreu = '" + ocorrencia.getRuaOcorreu() + "'," +
                                "numeroocorreu = '" + ocorrencia.getNumeroOcorreu() + "'," +
                                "complementoocorreu = '" + ocorrencia.getComplementoOcorreu() + "'," +
                                "declaracao = '" + ocorrencia.getDeclaracao() + "'," +
                                "observacao = '" + ocorrencia.getObservacao() + "'," +
                                "dataintimacaoprocessada = '" + ocorrencia.getDataIntimacaoProcessada() + "'," +
                                "intimacao = '" + ocorrencia.getIntimacao() + "'," +
                                "dataintimacao = '" + ocorrencia.getDataIntimacao() + "'," +
                                "estadointimacao = '" + ocorrencia.getEstadoIntimacao() + "'," +
                                "cidadeintimacao = '" + ocorrencia.getCidadeIntimacao() + "'," +
                                "bairrointimacao = '" + ocorrencia.getBairroIntimacao() + "'," +
                                "ruaintimacao = '" + ocorrencia.getRuaIntimacao() + "'," +
                                "numerointimacao = '" + ocorrencia.getNumeroIntimacao() + "'," +
                                "complementointimacao = '" + ocorrencia.getComplementoIntimacao() + "'," +
                                "providenciatomada = '" + ocorrencia.getProvidenciaTomada() + "'," +
                                "parecerconclusivo = '" + ocorrencia.getParecerConclusivo() + "'," +
                                "situacao = '" + ocorrencia.getSituacao() + "' " +
                          "WHERE idocorrencia = " + ocorrencia.getIdOcorrencia();
            
            Statement st = super.getConnection().createStatement();
            st.executeUpdate(sql);
            
            codigoSalvo = ocorrencia.getIdOcorrencia();
            
            super.registraLogDeRegistro(sql);

            return true;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar registro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Lista todos os registros de ocorrencias, obtidos
     * da base de dados.
     * 
     * @param filtro
     * @return ResultSet
     */
    public ResultSet listar(int idOcorrencia, String busca)
    {        
        try
        {
            String sql = "SELECT idocorrencia," +
                                "pessoaid," +
                                "dataocorrencia," +
                                "nomedeclarante," +
                                "telefonedeclarante," +
                                "criancaid," +
                                "estadoocorreu," +
                                "cidadeocorreu," +
                                "bairroocorreu ," +
                                "ruaocorreu ," +
                                "numeroocorreu  ," +
                                "complementoocorreu ," +
                                "declaracao ," +
                                "observacao ," +
                                "dataintimacaoprocessada ," +
                                "intimacao ," +
                                "dataintimacao ," +
                                "estadointimacao ," +
                                "cidadeintimacao ," +
                                "bairrointimacao ," +
                                "ruaintimacao ," +
                                "numerointimacao ," +
                                "complementointimacao ," +
                                "providenciatomada ," +
                                "parecerconclusivo  ," +
                                "situacao " +
                           "FROM ocorrencia " +
                          "WHERE (CASE " + idOcorrencia + " WHEN 0 THEN TRUE ELSE (idocorrencia = " + idOcorrencia + ") END) " +
                            "AND (CASE WHEN '" + busca + "' = 'null' THEN TRUE ELSE " +
                                      "((idocorrencia::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(dataocorrencia ::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(situacao::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(nomedeclarante::TEXT ILIKE '%" + busca + "%') OR " +
                                       "(telefonedeclarante::TEXT ILIKE '%" + busca + "%')) " +
                                 "END) " +
                       "ORDER BY idOcorrencia, nomeDeclarante";
         
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
     * Exclui registro de uma ocorrencia da base de dados.
     * 
     * @param idOcorrencia
     * @return boolean
     */
    public boolean excluir(int idOcorrencia)
    {
        try
        {
            String sql = "DELETE FROM ocorrencia " +
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
