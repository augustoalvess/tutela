/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.cadastros.modelos.negocios;

import java.sql.ResultSet;
import javax.swing.JOptionPane;
import tutela.modulos.principal.modelos.daos.UsuarioDAO;

/**
 *
 * @author augusto
 */
public class Usuario 
{
    private int idLogin;
    private Pessoa pessoa;
    private String login;
    private String senha;
    private boolean ehAdmin;
    
    public Usuario() 
    {
        popular(0, "", "");
    }

    public Usuario(int idLogin) 
    {
        popular(idLogin, "", "");
    }
    
    public Usuario(int idLogin, String login, String senha) 
    {
        popular(idLogin, login, senha);
    }

    public int getIdLogin()
    {
        return idLogin;
    }

    public void setIdLogin( int idLogin )
    {
        this.idLogin = idLogin;
    }

    public Pessoa getPessoa() 
    {
        return pessoa;
    }

    public void setPessoa( Pessoa pessoa )
    {
        this.pessoa = pessoa;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin( String login ) 
    {
        this.login = login;
    }

    public String getSenha() 
    {
        return senha;
    }

    public void setSenha( String senha ) 
    {
        this.senha = senha;
    }

    public boolean isEhAdmin() 
    {
        return ehAdmin;
    }

    public void setEhAdmin( boolean ehAdmin ) 
    {
        this.ehAdmin = ehAdmin;
    }
    
    private void popular(int idLogin, String login, String senha) 
    {
        try
        {
            UsuarioDAO usuarioDao = new UsuarioDAO();
            ResultSet resultSet;
            resultSet = usuarioDao.obterUsuario(idLogin, login, senha);
            resultSet.next();
            
            this.setIdLogin(resultSet.getInt("idlogin"));
            this.setLogin(resultSet.getString("login"));
            this.setSenha(resultSet.getString("senha"));
            this.setPessoa(new Pessoa(resultSet.getInt("idpessoa")));
            this.setEhAdmin(resultSet.getBoolean("eadmin"));
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
