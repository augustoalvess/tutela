/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.principal.modelos.daos;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import tutela.modulos.principal.modelos.daos.ConexaoBD;

/**
 *
 * @author augusto
 */
public class UsuarioDAO extends ConexaoBD 
{
    /**
     * Lista todos os registros de login, obtidos
     * da base de dados.
     * 
     * @param filtro
     * @return ResultSet
     */
    public ResultSet obterUsuario(int idLogin, String login, String senha)
    {        
        try
        {
            String sql = "SELECT idlogin," +
                                "login," +
                                "senha," +
                                "idpessoa," +
                                "eadmin " +
                           "FROM login " +
                          "WHERE idlogin = " + idLogin + " " +
                             "OR (login = '" + login + "' AND senha = MD5('" + senha + "')) " +
                       "ORDER BY idlogin";
            
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
}
