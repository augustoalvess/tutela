/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.cadastros.modelos.negocios;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import tutela.modulos.cadastros.modelos.daos.OcorrenciaDAO;
import tutela.modulos.cadastros.modelos.daos.OcorrenciaPessoaDAO;

/**
 *
 * @author augusto
 */
public class Ocorrencia {
    
    public static String NOVA = "Nova";
    public static String ABERTA = "Aberta";
    public static String INTIMACAO_FEITA = "Intimações feitas";
    public static String FECHADA = "Fechada";
    public static String ENCAMINHADA_PARA_INSTITUIÇÃO_TERCEIRA = "Encaminhada para Instituição Terceira";
    
    private int idOcorrencia;
    
    //1ª Aba
    private Pessoa pessoaConselheiro;
    private String dataOcorrencia;
    private String nomeDeclarante;
    private String telefoneDeclarante;
    private Crianca criancaAssociada; //Não obrigatório
    private String estadoOcorreu;
    private String cidadeOcorreu;
    private String bairroOcorreu;
    private String ruaOcorreu;
    private String numeroOcorreu;
    private String complementoOcorreu;
    private String declaracao;
    private String observacao;
    
    //2ª Aba
    private String dataIntimacaoProcessada; //hoje
    private String intimacao;
    private List<Pessoa> pessoasOcorrencia;
    private String dataIntimacao; //quando vai ocorrer
    private String estadoIntimacao;
    private String cidadeIntimacao;
    private String bairroIntimacao;
    private String ruaIntimacao;
    private String numeroIntimacao;
    private String complementoIntimacao;
    
    //3ª Aba
    private String providenciaTomada;
    private String parecerConclusivo;
    private String situacao;

    public Ocorrencia() {}

    public Ocorrencia(int idOcorrencia) 
    {
        this.popular(idOcorrencia);
    }

    public int getIdOcorrencia() 
    {
        return idOcorrencia;
    }

    public void setIdOcorrencia( int idOcorrencia ) 
    {
        this.idOcorrencia = idOcorrencia;
    }
    
    public Pessoa getPessoaConselheiro()
    {
        return pessoaConselheiro;
    }

    public void setPessoaConselheiro( Pessoa pessoaConselheiro )
    {
        this.pessoaConselheiro = pessoaConselheiro;
    }

    public String getDataOcorrencia()
    {
        return dataOcorrencia;
    }

    public void setDataOcorrencia( String dataOcorrencia )
    {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getNomeDeclarante() 
    {
        return nomeDeclarante;
    }

    public void setNomeDeclarante( String nomeDeclarante ) 
    {
        this.nomeDeclarante = nomeDeclarante;
    }

    public String getTelefoneDeclarante() 
    {
        return telefoneDeclarante;
    }

    public void setTelefoneDeclarante( String telefoneDeclarante )
    {
        this.telefoneDeclarante = telefoneDeclarante;
    }

    public Crianca getCriancaAssociada()
    {
        return criancaAssociada;
    }

    public void setCriancaAssociada( Crianca criancaAssociada ) 
    {
        this.criancaAssociada = criancaAssociada;
    }

    public String getEstadoOcorreu()
    {
        return estadoOcorreu;
    }

    public void setEstadoOcorreu( String estadoOcorreu )
    {
        this.estadoOcorreu = estadoOcorreu;
    }

    public String getCidadeOcorreu()
    {
        return cidadeOcorreu;
    }

    public void setCidadeOcorreu( String cidadeOcorreu )
    {
        this.cidadeOcorreu = cidadeOcorreu;
    }

    public String getBairroOcorreu()
    {
        return bairroOcorreu;
    }

    public void setBairroOcorreu( String bairroOcorreu ) 
    {
        this.bairroOcorreu = bairroOcorreu;
    }

    public String getRuaOcorreu() 
    {
        return ruaOcorreu;
    }

    public void setRuaOcorreu( String ruaOcorreu )
    {
        this.ruaOcorreu = ruaOcorreu;
    }

    public String getNumeroOcorreu()
    {
        return numeroOcorreu;
    }

    public void setNumeroOcorreu( String numeroOcorreu )
    {
        this.numeroOcorreu = numeroOcorreu;
    }

    public String getComplementoOcorreu() 
    {
        return complementoOcorreu;
    }

    public void setComplementoOcorreu( String complementoOcorreu ) 
    {
        this.complementoOcorreu = complementoOcorreu;
    }

    public String getDeclaracao() 
    {
        return declaracao;
    }

    public void setDeclaracao( String declaracao ) 
    {
        this.declaracao = declaracao;
    }

    public String getObservacao() 
    {
        return observacao;
    }

    public void setObservacao( String observacao )
    {
        this.observacao = observacao;
    }

    public String getDataIntimacaoProcessada() 
    {
        return dataIntimacaoProcessada;
    }

    public void setDataIntimacaoProcessada( String dataIntimacaoProcessada )
    {
        this.dataIntimacaoProcessada = dataIntimacaoProcessada;
    }

    public String getIntimacao()
    {
        return intimacao;
    }

    public void setIntimacao( String intimacao ) 
    {
        this.intimacao = intimacao;
    }

    public List<Pessoa> getPessoasOcorrencia()
    {
        return pessoasOcorrencia;
    }

    public void setPessoasOcorrencia( List<Pessoa> pessoasOcorrencia )
    {
        this.pessoasOcorrencia = pessoasOcorrencia;
    }

    public String getDataIntimacao()
    {
        return dataIntimacao;
    }

    public void setDataIntimacao( String dataIntimacao )
    {
        this.dataIntimacao = dataIntimacao;
    }

    public String getEstadoIntimacao()
    {
        return estadoIntimacao;
    }

    public void setEstadoIntimacao( String estadoIntimacao ) 
    {
        this.estadoIntimacao = estadoIntimacao;
    }

    public String getCidadeIntimacao()
    {
        return cidadeIntimacao;
    }

    public void setCidadeIntimacao( String cidadeIntimacao )
    {
        this.cidadeIntimacao = cidadeIntimacao;
    }

    public String getBairroIntimacao()
    {
        return bairroIntimacao;
    }

    public void setBairroIntimacao( String bairroIntimacao )
    {
        this.bairroIntimacao = bairroIntimacao;
    }

    public String getRuaIntimacao() 
    {
        return ruaIntimacao;
    }

    public void setRuaIntimacao( String ruaIntimacao ) 
    {
        this.ruaIntimacao = ruaIntimacao;
    }

    public String getNumeroIntimacao()
    {
        return numeroIntimacao;
    }

    public void setNumeroIntimacao( String numeroIntimacao )
    {
        this.numeroIntimacao = numeroIntimacao;
    }

    public String getComplementoIntimacao()
    {
        return complementoIntimacao;
    }

    public void setComplementoIntimacao( String complementoIntimacao ) 
    {
        this.complementoIntimacao = complementoIntimacao;
    }

    public String getProvidenciaTomada() {
        return providenciaTomada;
    }

    public void setProvidenciaTomada( String providenciaTomada )
    {
        this.providenciaTomada = providenciaTomada;
    }

    public String getParecerConclusivo() 
    {
        return parecerConclusivo;
    }

    public void setParecerConclusivo( String parecerConclusivo )
    {
        this.parecerConclusivo = parecerConclusivo;
    }

    public String getSituacao() 
    {
        return situacao;
    }

    public void setSituacao( String situacao ) 
    {
        this.situacao = situacao;
    }
    
    /**
     * Verifica se os dados obrigatórios para um
     * registro de criança, foram corretamente
     * preenchidos.
     * 
     * @return boolean
     */
    public boolean validaDadosObrigatorios() 
    {
        boolean camposValidos = true;
        String erro = "";

        if ( getNomeDeclarante() == null )
        {
            erro += "\nCampo 'O declarante da ocorrência' é requerido!";                
        }

        if ( getTelefoneDeclarante().equals( "(  ) -          " ) ) 
        {
            erro += "\nCampo 'Telefone do delcarante' é requerido!";                
        }

        if ( getEstadoOcorreu().length() == 0 ) 
        {
            erro += "\nCampo 'Estado' é requerido!";                
        }

        if ( getCidadeOcorreu().length() == 0 ) 
        {
            erro += "\nCampo 'Cidade' é requerido!";                
        }

        if ( getBairroOcorreu().length() == 0 ) 
        {
            erro += "\nCampo 'Bairro' é requerido!";                
        }

        if ( getRuaOcorreu().length() == 0 ) 
        {
            erro += "\nCampo 'Rua' é requerido!";                
        }

        if ( getNumeroOcorreu().length() == 0 ) 
        {
            erro += "\nCampo 'Número' é requerido!";                
        }

        if ( getSituacao().equalsIgnoreCase( INTIMACAO_FEITA ) ) 
        {
            erro += this.validacaoIntimacoes(erro);
        } 
        else if ( getSituacao().equalsIgnoreCase( INTIMACAO_FEITA ) || getSituacao().equalsIgnoreCase( FECHADA ) || getSituacao().equalsIgnoreCase( ENCAMINHADA_PARA_INSTITUIÇÃO_TERCEIRA ) ) 
        {
            erro += this.validacaoIntimacoes(erro);
            
            //Encerrando
            if ( getProvidenciaTomada().length() == 0 ) 
            {
                erro += "\nCampo 'Providência Tomada' é requerido!";                
            }
        } 
        
        if ( erro.length() > 0 )
        {
            JOptionPane.showMessageDialog(null, "Preencha corretamente os campos!" + erro, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return !(erro.length() > 0);
    }
    
    /**
     * Efetua a validação das intimações.
     * 
     * @param erro
     * @return 
     */
    public String validacaoIntimacoes(String erro)
    {
        //Fazendo intimação
        if ( getDataIntimacao().equals( "  /  /    " ) ) 
        {
            erro += "\nCampo 'Data da intimação' é requerido!";                
        }

        if ( getEstadoIntimacao() == null || getEstadoIntimacao().length() == 0 ) 
        {
            erro += "\nCampo 'Estado da intimação' é requerido!";                
        }

        if ( getCidadeIntimacao().length() == 0 )
        {
            erro += "\nCampo 'Cidade da intimação' é requerido!";                
        }

        if ( getBairroIntimacao().length() == 0 )
        {
            erro += "\nCampo 'Bairro da intimação' é requerido!";        
        }

        if ( getRuaIntimacao().length() == 0 )
        {
            erro += "\nCampo 'Rua da intimação' é requerido!";                
        }

        if ( getNumeroIntimacao() == null || getNumeroIntimacao().length() == 0 )
        {
            erro += "\nCampo 'Número da intimação' é requerido!";                
        }

        if ( getIntimacao().length() == 0 )
        {
            erro += "\nCampo 'Intimação' é requerido!";                
        }

        if ( getPessoasOcorrencia() == null || getPessoasOcorrencia().isEmpty() )
        {
            erro += "\nCampo 'Pessoas' deve ao menos contér uma pessoa!";                
        }
        
        return erro;
    }
    
    /**
     * Popula os registros obtidos na tabela.
     * Conforme colunas que a tabela aceite.
     * 
     * @param tabel
     * @param resultSet 
     */
    public void populaRegistrosNaTabela(JTable tabela, ResultSet resultSet, int codigoRegistroASelecionar)
    {
        try
        {
            this.limparTabela(tabela);
            
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            boolean selecionarLinha = false;
            boolean linhaSelecionada = false;
            int linhaASelecionar = 0;

            while ( resultSet.next() )
            {  
                Object[] row = {
                    resultSet.getObject("idocorrencia"),
                    resultSet.getObject("dataocorrencia"), //data
                    resultSet.getObject("situacao"), //situacao
                    resultSet.getObject("nomedeclarante"), //declarante
                    resultSet.getObject("telefonedeclarante") //telefoneDeclarante
                };

                for ( int i = 1; i <= columns; i++ )
                {  
                    /**
                     * Se for a primeira, coluna, e o registro dessa coluna for igual ao registro a ser selecionado, 
                     * a linha deste registro deve ficar selecionada.
                     */
                    
                    if ( ( i -1 ) == 0 && ( resultSet.getObject(i).toString().equals(String.valueOf(codigoRegistroASelecionar)) ) && !linhaSelecionada )
                    {
                        selecionarLinha = true;
                    }
                }

                ((DefaultTableModel) tabela.getModel()).insertRow(resultSet.getRow() -1, row);
                
                
                if ( selecionarLinha )
                {
                    tabela.addRowSelectionInterval(linhaASelecionar, linhaASelecionar);
                    linhaSelecionada = true;
                    selecionarLinha = false;
                }
                
                linhaASelecionar ++;
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao popular a tabela: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Limpa os registros populados da tabela.
     * 
     * @param tabela 
     */
    public void limparTabela(JTable tabela)
    {
        while ( tabela.getRowCount() > 0 )
        {
            ((DefaultTableModel) tabela.getModel()).removeRow(0);
        }
    }
    
    public void popular(int idOcorrencia)
    {
        try
        {
            OcorrenciaDAO ocorrenciaDao = new OcorrenciaDAO();
            ResultSet resultSet;
            resultSet = ocorrenciaDao.listar(idOcorrencia, null);
            resultSet.next();
        
            Crianca crianca = null;
            if (resultSet.getInt("criancaid") > 0) {
                crianca = new Crianca(resultSet.getInt("criancaid"));
            }
            
            setIdOcorrencia(idOcorrencia);
            setPessoaConselheiro(new Pessoa(resultSet.getInt("pessoaid")));
            setDataOcorrencia(resultSet.getString("dataocorrencia"));
            setNomeDeclarante(resultSet.getString("nomedeclarante"));
            setTelefoneDeclarante(resultSet.getString("telefonedeclarante"));
            setCriancaAssociada(crianca);
            setEstadoOcorreu(resultSet.getString("estadoocorreu"));
            setCidadeOcorreu(resultSet.getString("cidadeocorreu"));
            setBairroOcorreu(resultSet.getString("bairroocorreu"));
            setRuaOcorreu(resultSet.getString("ruaocorreu"));
            setNumeroOcorreu(resultSet.getString("numeroocorreu"));
            setComplementoOcorreu(resultSet.getString("complementoocorreu"));
            setDeclaracao(resultSet.getString("declaracao"));
            setObservacao(resultSet.getString("observacao"));
            setDataIntimacaoProcessada(resultSet.getString("dataintimacaoprocessada"));
            setIntimacao(resultSet.getString("intimacao"));
            setDataIntimacao(resultSet.getString("dataintimacao"));
            setEstadoIntimacao(resultSet.getString("estadointimacao"));
            setCidadeIntimacao(resultSet.getString("cidadeintimacao"));
            setBairroIntimacao(resultSet.getString("bairrointimacao"));
            setRuaIntimacao(resultSet.getString("ruaintimacao"));
            setNumeroIntimacao(resultSet.getString("numerointimacao"));
            setComplementoIntimacao(resultSet.getString("complementointimacao"));
            setProvidenciaTomada(resultSet.getString("providenciatomada"));
            setParecerConclusivo(resultSet.getString("parecerconclusivo"));
            setSituacao(resultSet.getString("situacao"));
            
            //setar todas pessoas da Ocorrencia
            setPessoasOcorrencia(new OcorrenciaPessoaDAO().listar(idOcorrencia));
            
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao popular registro da criança: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
