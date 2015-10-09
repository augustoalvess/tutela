package tutela.modulos.principal.modelos.daos;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fabricio.pretto
 */
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.swing.JOptionPane;
import tutela.modulos.principal.modelos.negocio.UsuarioLogado;

public class ConexaoBD {

    private static ConexaoBD instancia = null;
    private Connection conexao = null;

    public ConexaoBD() {
        try {
            // Carrega informações do arquivo de propriedades
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/tutela/configuracoes/db/db.properties"));
            String dbdriver = prop.getProperty("db.driver");
            String dburl = prop.getProperty("db.url");
            String dbuser = prop.getProperty("db.user");
            String dbsenha = prop.getProperty("db.senha");

            // Carrega Driver do Banco de Dados
            Class.forName(dbdriver);

            if (dbuser.length() != 0) // conexão COM usuário e senha
            {
                conexao = DriverManager.getConnection(dburl, dbuser, dbsenha);
            } else // conexão SEM usuário e senha
            {
                conexao = DriverManager.getConnection(dburl);
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Retorna instância
    public static ConexaoBD getInstance() {
        if (instancia == null) {
            instancia = new ConexaoBD();
        }
        return instancia;
    }

    // Retorna conexão
    public Connection getConnection() {
        if (conexao == null) {
            throw new RuntimeException("conexao==null");
        }
        return conexao;
    }

    // Efetua fechamento da conexão
    public void shutDown() {
        try {
            conexao.close();
            instancia = null;
            conexao = null;
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    /**
     * Para registro de logs.
     * 
     * @param String sql 
     */
    public void registraLogDeRegistro(String sql)
    {
        try
        {
            String usuario = UsuarioLogado.getInstance().getUsuarioLogado().getPessoa().getNome();
            String sqlreplace = sql.replace("'", "''");
            
            String query = "INSERT INTO registrolog" +
                                       "(nomeusuario," +
                                        "sqllog)" +
                                "VALUES ('" + usuario + "'," +
                                        "'" + sqlreplace + "')";

            Statement st = this.getConnection().createStatement();
            st.execute(query);
        }
        catch ( Exception err )
        {
            JOptionPane.showMessageDialog(null, "Erro ao registrar log: " + err, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

