/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.cadastros.modelos.negocios;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import tutela.modulos.cadastros.modelos.daos.PessoaDAO;
import tutela.modulos.principal.modelos.negocio.Validacao;

/**
 *
 * @author augusto
 */
public class Pessoa 
{
    public static final String sexoM = "M";
    public static final String sexoF = "F";
    
    private int idPessoa;
    private String nome;
    private String estadoCivil;
    private String dataNascimento;
    private String sexo;
    private String origemEtnica;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String complemento;
    private String rg;
    private String cpf;
    private String telefoneResidencial;
    private String telefoneCelular;
    private String email;
    private boolean eConselheiro;

    public Pessoa() {}

    public Pessoa(int idPessoa) {
        popular(idPessoa);
    }
    
    public int getIdPessoa() 
    {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) 
    {
        this.idPessoa = idPessoa;
    }
    
    public String getNome() 
    {
        return nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }

    public String getEstadoCivil() 
    {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) 
    {
        this.estadoCivil = estadoCivil;
    }

    public String getDataNascimento() 
    {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) 
    {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() 
    {
        return sexo;
    }

    public void setSexo(String sexo) 
    {
        this.sexo = sexo;
    }

    public String getOrigemEtnica() 
    {
        return origemEtnica;
    }

    public void setOrigemEtnica(String origemEtnica) 
    {
        this.origemEtnica = origemEtnica;
    }

    public String getEstado() 
    {
        return estado;
    }

    public void setEstado(String estado) 
    {
        this.estado = estado;
    }

    public String getCidade() 
    {
        return cidade;
    }

    public void setCidade(String cidade) 
    {
        this.cidade = cidade;
    }

    public String getBairro() 
    {
        return bairro;
    }

    public void setBairro(String bairro) 
    {
        this.bairro = bairro;
    }

    public String getRua() 
    {
        return rua;
    }

    public void setRua(String rua) 
    {
        this.rua = rua;
    }

    public String getNumero() 
    {
        return numero;
    }

    public void setNumero(String numero) 
    {
        this.numero = numero;
    }

    public String getComplemento() 
    {
        return complemento;
    }

    public void setComplemento(String complemento) 
    {
        this.complemento = complemento;
    }

    public String getRg() 
    {
        return rg;
    }

    public void setRg(String rg) 
    {
        this.rg = rg;
    }

    public String getCpf() 
    {
        return cpf;
    }

    public void setCpf(String cpf) 
    {
        this.cpf = cpf;
    }

    public String getTelefoneResidencial() 
    {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(String telefoneResidencial) 
    {
        this.telefoneResidencial = telefoneResidencial;
    }

    public String getTelefoneCelular() 
    {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) 
    {
        this.telefoneCelular = telefoneCelular;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public boolean iseConselheiro() {
        return eConselheiro;
    }

    public void seteConselheiro(boolean eConselheiro) {
        this.eConselheiro = eConselheiro;
    }

    @Override
    public String toString() 
    {
        return getNome();
    }

    /**
     * Popula os campos do formulário com os dados
     * do modelo pessoa.
     * 
     * @param int idPessoa
     */
    public void popular(int idPessoa)
    {
        try
        {
            PessoaDAO pessoaDao = new PessoaDAO();
            ResultSet resultSet;
            resultSet = pessoaDao.listar(idPessoa, null);
            resultSet.next();
            
            this.setIdPessoa(idPessoa);
            this.setNome(resultSet.getString("nome"));
            this.setEstadoCivil(resultSet.getString("estadocivil"));
            this.setDataNascimento(resultSet.getString("datanascimento"));
            this.setSexo(resultSet.getString("sexo"));
            this.setOrigemEtnica(resultSet.getString("origemetnica"));
            this.setEstado(resultSet.getString("estado"));
            this.setCidade(resultSet.getString("cidade"));
            this.setBairro(resultSet.getString("bairro"));
            this.setRua(resultSet.getString("rua"));
            this.setNumero(resultSet.getString("numero"));
            this.setComplemento(resultSet.getString("complemento"));
            this.setRg(resultSet.getString("rg"));
            this.setCpf(resultSet.getString("cpf"));
            this.setTelefoneResidencial(resultSet.getString("telefoneresidencial"));
            this.setTelefoneCelular(resultSet.getString("telefonecelular"));
            this.setEmail(resultSet.getString("email"));
            this.seteConselheiro((resultSet.getString("econselheiro").equals("Sim")) ? true : false);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao popular registro da pessoa: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean validaDadosObrigatorios() 
    {
        boolean camposValidos = true;
        String erro = "";
        
        if ( this.getNome().length() == 0 )
        {
            erro += "\nCampo 'Nome' é requerido!";
        }
        
        if ( this.getEstadoCivil().length() == 0 || this.getEstadoCivil().equals(" "))
        {
            erro += "\nCampo 'Estado civil' é requerido!";
        }        
        
        if ( this.getDataNascimento().length() == 0 )
        {
            erro += "\nCampo 'Data de nascimento' é requerido!";
        }        
        
        if ( this.getSexo().length() == 0 )
        {
            erro += "\nCampo 'Estado civil' é requerido!";
        }        
        
        if ( this.getOrigemEtnica().length() == 0 || this.getOrigemEtnica().equals(" ") )
        {
            erro += "\nCampo 'Origem étnica' é requerido!";
        }        
        
        if ( this.getEstado().length() == 0 )
        {
            erro += "\nCampo 'Estado' é requerido!";
        }        
        
        if ( this.getCidade().length() == 0 )
        {
            erro += "\nCampo 'Cidade' é requerido!";
        }        
        
        if ( this.getBairro().length() == 0 )
        {
            erro += "\nCampo 'Bairro' é requerido!";
        }        
        
        if ( this.getRua().length() == 0 )
        {
            erro += "\nCampo 'Rua' é requerido!";
        }        
        
        if ( this.getNumero().length() == 0 )
        {
            erro += "\nCampo 'Numero' é requerido!";
        }        
        
        if ( this.getTelefoneCelular().length() == 0 )
        {
            erro += "\nCampo 'Telefone celular' é requerido!";
        }
        
        if ( this.getCpf().replace(".", "").replace("-", "").length() > 0 )
        {
            if ( !Validacao.validarCPF(this.getCpf()) )
            {
                //erro += "\nRegistro de 'CPF' é inválido!";
            }
        }
        
        if ( erro.length() > 0 )
        {
            JOptionPane.showMessageDialog(null, "Preencha corretamente os campos!" + erro, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return !(erro.length() > 0);
    }
    
}
