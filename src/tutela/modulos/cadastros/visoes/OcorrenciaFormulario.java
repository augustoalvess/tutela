/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.cadastros.visoes;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import tutela.modulos.cadastros.modelos.daos.CriancaDAO;
import tutela.modulos.cadastros.modelos.daos.OcorrenciaDAO;
import tutela.modulos.cadastros.modelos.daos.PessoaDAO;
import tutela.modulos.cadastros.modelos.negocios.Crianca;
import tutela.modulos.cadastros.modelos.negocios.Ocorrencia;
import tutela.modulos.cadastros.modelos.negocios.Pessoa;
import tutela.modulos.principal.modelos.negocio.UsuarioLogado;

/**
 *
 * @author augusto
 */
public class OcorrenciaFormulario extends javax.swing.JDialog {

    public static OcorrenciaBusca ocorrenciaBusca;
    public int idOcorrencia = 0;
    public String situacaoValor = null;
    public List<Pessoa> pessoasIntimacao = new ArrayList<Pessoa>();
    public boolean removerPessoa = true;

    /**
     * Creates new form CriancaNovo
     */
    public OcorrenciaFormulario( OcorrenciaBusca parent, boolean modal ) {
        ocorrenciaBusca = parent;
        this.setModal( modal );

        initComponents();

        botaoSalvar.setIcon( new ImageIcon( OcorrenciaFormulario.this.getClass().getResource( "/tutela/publico/imagens/salvar.png" ) ) );
        botaoCancelar.setIcon( new ImageIcon( OcorrenciaFormulario.this.getClass().getResource( "/tutela/publico/imagens/cancelar.png" ) ) );

        try {
            ResultSet resultSetPessoa = new PessoaDAO().listar(0, null);
            while ( resultSetPessoa.next() ) {
                declaranteCombo.addItem( new Pessoa( resultSetPessoa.getInt( "idpessoa" ) ) );
                pessoaIntimacao.addItem( new Pessoa( resultSetPessoa.getInt( "idpessoa" ) ) );
            }
            
            ResultSet resultSet = new CriancaDAO().listar( 0, null );
            while ( resultSet.next() ) {
                criancaCombo.addItem( new Crianca( resultSet.getInt( "idpessoa" ) ) );
            }

            telefoneDeclarante.setFormatterFactory( new DefaultFormatterFactory( new MaskFormatter( "(##) - #########" ) ) );
            dataCadastro.setFormatterFactory( new DefaultFormatterFactory( new MaskFormatter( "##/##/####" ) ) );
            dataIntimacao.setFormatterFactory( new DefaultFormatterFactory( new MaskFormatter( "##/##/####" ) ) );
        } catch (Exception ex) {
            Logger.getLogger( OcorrenciaFormulario.class.getName() ).log( Level.SEVERE, null, ex );
        }

        conselheiro.setText( UsuarioLogado.getInstance().getUsuarioLogado().getPessoa().getNome() );
        dataCadastro.setText( new SimpleDateFormat( "dd/MM/yyyy" ).format( Calendar.getInstance().getTime() ) );
        dataIntimacaoProcessada.setText( new SimpleDateFormat( "dd/MM/yyyy" ).format( Calendar.getInstance().getTime() ) );

        //Bloqueia toda as abas, exceto a 1(0) e 2(1)
        tabbedPanel.setEnabledAt( 2, false );
        tabbedPanel.setEnabledAt( 3, false );
        tabbedPanel.setEnabledAt( 4, false );
        
        remover.setEnabled(false);
        
        // Evento ao selecionar um item da tabela.
        pessoasTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {  
            @Override  
            public void valueChanged(ListSelectionEvent evt) {  
               if (evt.getValueIsAdjusting())  
               {
                   return;  
               }
               
               if ( removerPessoa )
               {
                   remover.setEnabled(true);
               }
            }  
         });
    }

    /**
     * Popula os campos do formuário com os dados da ocorrencia selecionada.
     */
    public void populaForumlario( Ocorrencia ocorrencia ) {
        idOcorrencia = ocorrencia.getIdOcorrencia();
        situacaoValor = ocorrencia.getSituacao();

        tabbedPanel.setEnabledAt( 2, true );
        tabbedPanel.setEnabledAt( 3, true );

        //Popula aba 1(0) e 2(1)
        conselheiro.setText( ocorrencia.getPessoaConselheiro().getNome() );
        if ( ocorrencia.getCriancaAssociada() != null ) {
            criancaCombo.getModel().setSelectedItem( ocorrencia.getCriancaAssociada() );
        }
        dataCadastro.setText( ocorrencia.getDataOcorrencia() );
        declaranteCombo.getModel().setSelectedItem(ocorrencia.getNomeDeclarante());
        telefoneDeclarante.setText( ocorrencia.getTelefoneDeclarante() );
        estadoOcorreu.setSelectedItem( ocorrencia.getEstadoOcorreu() );
        cidadeOcorreu.setText( ocorrencia.getCidadeOcorreu() );
        bairroOcorreu.setText( ocorrencia.getBairroOcorreu() );
        ruaOcorreu.setText( ocorrencia.getRuaOcorreu() );
        numeroOcorreu.setText( ocorrencia.getRuaOcorreu() );
        complementoOcorreu.setText( ocorrencia.getComplementoOcorreu() );
        declaracao.setText( ocorrencia.getDeclaracao() );
        observacao.setText( ocorrencia.getObservacao() );

        if ( ocorrencia.getSituacao().equals( Ocorrencia.INTIMACAO_FEITA )
                || ocorrencia.getSituacao().equals( Ocorrencia.FECHADA )
                || ocorrencia.getSituacao().equals( Ocorrencia.ENCAMINHADA_PARA_INSTITUIÇÃO_TERCEIRA ) ) {
            pessoasIntimacao.clear();
            pessoasIntimacao.addAll( ocorrencia.getPessoasOcorrencia() );
            populaRegistrosNaTabela( pessoasTable, pessoasIntimacao );

            if ( ocorrencia.getSituacao().equals( Ocorrencia.FECHADA ) ) 
            {
                botaoSalvar.setVisible( false );
                jButton1.setEnabled(false);
                removerPessoa = false;
            }

            {
                //Aba 3 e 4
                dataIntimacaoProcessada.setText( ocorrencia.getDataIntimacaoProcessada() );
                dataIntimacao.setText( ocorrencia.getDataIntimacao() );
                estadoIntimacao.setSelectedItem( ocorrencia.getEstadoIntimacao() );
                cidadeIntimacao.setText( ocorrencia.getCidadeIntimacao() );
                bairroIntimacao.setText( ocorrencia.getBairroIntimacao() );
                ruaIntimacao.setText( ocorrencia.getRuaIntimacao() );
                numeroIntimacao.setText( ocorrencia.getNumeroIntimacao() );
                complementoIntimacao.setText( ocorrencia.getComplementoIntimacao() );
                intimacao.setText( ocorrencia.getIntimacao() );
            }

            //Quer dizer que, já foi feita a intimação, e agora, está sendo concluída!
            tabbedPanel.setEnabledAt( 4, true );

            //Aba 4, pessoas: Popular table
            //TODO
            //Aba 5
            if ( ocorrencia.getSituacao().equals( Ocorrencia.INTIMACAO_FEITA ) ) {
                ocorrencia.setSituacao( Ocorrencia.FECHADA );
            }
            situacao.getModel().setSelectedItem( ocorrencia.getSituacao() );
            if ( !ocorrencia.getProvidenciaTomada().equals( "null" ) ) {
                providenciaTomada.setText( ocorrencia.getProvidenciaTomada() );
            }
            if ( !ocorrencia.getParecerConclusivo().equals( "null" ) ) {
                parecerConclusivo.setText( ocorrencia.getParecerConclusivo() );
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        tabbedPanel = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        conselheiro = new javax.swing.JTextField();
        criancaCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        dataCadastro = new javax.swing.JFormattedTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        complementoOcorreu = new javax.swing.JTextField();
        estadoOcorreu = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cidadeOcorreu = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        bairroOcorreu = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        ruaOcorreu = new javax.swing.JTextField();
        numeroOcorreu = new javax.swing.JTextField();
        telefoneDeclarante = new javax.swing.JFormattedTextField();
        declaranteCombo = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        declaracao = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        observacao = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        dataIntimacaoProcessada = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        dataIntimacao = new javax.swing.JFormattedTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        cidadeIntimacao = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        bairroIntimacao = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        ruaIntimacao = new javax.swing.JTextField();
        numeroIntimacao = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        complementoIntimacao = new javax.swing.JTextField();
        estadoIntimacao = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        intimacao = new javax.swing.JTextArea();
        jLabel55 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel49 = new javax.swing.JLabel();
        pessoaIntimacao = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        pessoasTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        remover = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        providenciaTomada = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        parecerConclusivo = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        situacao = new javax.swing.JComboBox();
        jLabel37 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        botaoSalvar = new javax.swing.JButton();
        botaoCancelar = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(java.awt.SystemColor.controlLtHighlight);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setText("Conselheiro:");

        conselheiro.setEditable(false);
        conselheiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conselheiroActionPerformed(evt);
            }
        });

        criancaCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("Criança:");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setText("Data ocorrência:");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel8.setText("Pessoa declarante:");

        jLabel25.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel25.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel25.setForeground(java.awt.Color.red);
        jLabel25.setText("*");

        jLabel28.setForeground(java.awt.Color.gray);
        jLabel28.setText("Informação: Campo com (*), é obrigatório o seu preenchimento.");

        dataCadastro.setEditable(false);
        dataCadastro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));

        jLabel33.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel33.setText("Telefone declarante:");

        jLabel39.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel39.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel39.setForeground(java.awt.Color.red);
        jLabel39.setText("*");

        jLabel22.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel22.setText("Estado ocorrência:");

        estadoOcorreu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RS - Rio Grande do Sul", "SC - Santa Catarina", "SP - São Paulo", "RJ - Rio de Janeiro" }));
        estadoOcorreu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoOcorreuActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel23.setText("Rua/Número ocorrência:");

        jLabel40.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel40.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel40.setForeground(java.awt.Color.red);
        jLabel40.setText("*");

        jLabel24.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel24.setText("Complemento ocorrência:");

        cidadeOcorreu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cidadeOcorreuActionPerformed(evt);
            }
        });

        jLabel41.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel41.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel41.setForeground(java.awt.Color.red);
        jLabel41.setText("*");

        jLabel26.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel26.setText("Cidade ocorrência:");

        bairroOcorreu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bairroOcorreuActionPerformed(evt);
            }
        });

        jLabel42.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel42.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel42.setForeground(java.awt.Color.red);
        jLabel42.setText("*");

        jLabel43.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel43.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel43.setForeground(java.awt.Color.red);
        jLabel43.setText("*");

        jLabel27.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel27.setText("Bairro ocorrência:");

        declaranteCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(criancaCombo, 0, 466, Short.MAX_VALUE)
                            .addComponent(conselheiro, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dataCadastro, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(telefoneDeclarante)
                            .addComponent(declaranteCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bairroOcorreu, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cidadeOcorreu)
                            .addComponent(estadoOcorreu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(ruaOcorreu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numeroOcorreu, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(complementoOcorreu, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(conselheiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(criancaCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel39)
                    .addComponent(declaranteCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel33)
                    .addComponent(telefoneDeclarante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(estadoOcorreu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cidadeOcorreu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bairroOcorreu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ruaOcorreu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numeroOcorreu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(complementoOcorreu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addContainerGap())
        );

        tabbedPanel.addTab("Registro da Ocorrência", jPanel1);

        jPanel8.setBackground(java.awt.SystemColor.controlLtHighlight);

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel11.setText("Declaração:");

        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel12.setText("Observação:");

        jLabel34.setForeground(java.awt.Color.gray);
        jLabel34.setText("Informação: Campo com (*), é obrigatório o seu preenchimento.");

        declaracao.setColumns(20);
        declaracao.setRows(5);
        jScrollPane2.setViewportView(declaracao);

        observacao.setColumns(20);
        observacao.setRows(5);
        jScrollPane3.setViewportView(observacao);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 288, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabbedPanel.addTab("Descrição", jPanel5);

        jPanel10.setBackground(java.awt.SystemColor.controlLtHighlight);

        jLabel16.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel16.setText("Data de registro da intimação:");

        dataIntimacaoProcessada.setEditable(false);

        jLabel17.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel17.setText("Data comparecimento:");

        jLabel35.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel35.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel35.setForeground(java.awt.Color.red);
        jLabel35.setText("*");

        jLabel36.setForeground(java.awt.Color.gray);
        jLabel36.setText("Informação: Campo com (*), é obrigatório o seu preenchimento.");

        jLabel29.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel29.setText("Rua/Número comparecimento:");

        jLabel44.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel44.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel44.setForeground(java.awt.Color.red);
        jLabel44.setText("*");

        jLabel30.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel30.setText("Complemento:");

        cidadeIntimacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cidadeIntimacaoActionPerformed(evt);
            }
        });

        jLabel45.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel45.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel45.setForeground(java.awt.Color.red);
        jLabel45.setText("*");

        jLabel31.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel31.setText("Cidade comparecimento:");

        bairroIntimacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bairroIntimacaoActionPerformed(evt);
            }
        });

        jLabel46.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel46.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel46.setForeground(java.awt.Color.red);
        jLabel46.setText("*");

        jLabel47.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel47.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel47.setForeground(java.awt.Color.red);
        jLabel47.setText("*");

        jLabel32.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel32.setText("Bairro comparecimento:");

        jLabel48.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel48.setText("Estado comparecimento:");

        estadoIntimacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "RS - Rio Grande do Sul" }));

        jLabel13.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel13.setText("Intimação:");

        jLabel54.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel54.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel54.setForeground(java.awt.Color.red);
        jLabel54.setText("*");

        intimacao.setColumns(20);
        intimacao.setRows(5);
        jScrollPane6.setViewportView(intimacao);

        jLabel55.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel55.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel55.setForeground(java.awt.Color.red);
        jLabel55.setText("*");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dataIntimacaoProcessada)
                                    .addComponent(dataIntimacao))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bairroIntimacao, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cidadeIntimacao)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                        .addComponent(ruaIntimacao)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(numeroIntimacao, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(estadoIntimacao, javax.swing.GroupLayout.Alignment.TRAILING, 0, 472, Short.MAX_VALUE)
                                    .addComponent(complementoIntimacao))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel36))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(dataIntimacaoProcessada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(dataIntimacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(estadoIntimacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cidadeIntimacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bairroIntimacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ruaIntimacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numeroIntimacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(complementoIntimacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabbedPanel.addTab("Intimação", jPanel6);

        jPanel13.setBackground(java.awt.SystemColor.controlLtHighlight);

        jLabel53.setForeground(java.awt.Color.gray);
        jLabel53.setText("Informação: Campo com (*), é obrigatório o seu preenchimento.");

        jLabel49.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel49.setText("Selecionar Pessoa:");

        pessoaIntimacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        pessoaIntimacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pessoaIntimacaoActionPerformed(evt);
            }
        });

        pessoasTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Telefone Residencial", "Telefone Celular"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(pessoasTable);
        pessoasTable.getColumnModel().getColumn(0).setResizable(false);
        pessoasTable.getColumnModel().getColumn(1).setResizable(false);
        pessoasTable.getColumnModel().getColumn(2).setResizable(false);
        pessoasTable.getColumnModel().getColumn(3).setResizable(false);

        jButton1.setText("Adicionar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel52.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel52.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel52.setForeground(java.awt.Color.red);
        jLabel52.setText("*");

        remover.setText("Remover");
        remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator5)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel53)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(remover, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(pessoaIntimacao, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(pessoaIntimacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(remover))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabbedPanel.addTab("Pessoas da Intimação", jPanel7);

        jPanel12.setBackground(java.awt.SystemColor.controlLtHighlight);

        jLabel38.setForeground(java.awt.Color.gray);
        jLabel38.setText("Informação: Campo com (*), é obrigatório o seu preenchimento.");

        providenciaTomada.setColumns(20);
        providenciaTomada.setRows(5);
        jScrollPane4.setViewportView(providenciaTomada);

        jLabel14.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel14.setText("Providência tomada:");

        parecerConclusivo.setColumns(20);
        parecerConclusivo.setRows(5);
        jScrollPane5.setViewportView(parecerConclusivo);

        jLabel15.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel15.setText("Parecer conclusivo:");

        jLabel50.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel50.setText("Situação:");

        situacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fechada", "Encaminhada para Instituição Terceira" }));

        jLabel37.setBackground(java.awt.SystemColor.controlLtHighlight);
        jLabel37.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel37.setForeground(java.awt.Color.red);
        jLabel37.setText("*");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(situacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 288, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(situacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addContainerGap())
        );

        tabbedPanel.addTab("Parecer", jPanel12);

        jToolBar1.setRollover(true);

        botaoSalvar.setText("   Salvar   ");
        botaoSalvar.setFocusable(false);
        botaoSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoSalvar);

        botaoCancelar.setText("  Cancelar  ");
        botaoCancelar.setFocusable(false);
        botaoCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoCancelar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabbedPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabbedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void conselheiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conselheiroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_conselheiroActionPerformed

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setIdOcorrencia( idOcorrencia );
        
        ocorrencia.setPessoasOcorrencia( pessoasIntimacao );
        
        if ( idOcorrencia == 0 ) {
            ocorrencia.setSituacao( Ocorrencia.NOVA );
        }

        //Se a situação for 'ABERTA', quer dizer que agora a intimação foi feita
        if ( situacaoValor != null && situacaoValor.equals( Ocorrencia.ABERTA ) ) {
            ocorrencia.setSituacao( Ocorrencia.INTIMACAO_FEITA );
        }

        //Novo cadastro (aba 1 e 2)
        ocorrencia.setPessoaConselheiro( UsuarioLogado.getInstance().getUsuarioLogado().getPessoa() );
        if ( !criancaCombo.getModel().getSelectedItem().toString().trim().isEmpty() ) {
            ocorrencia.setCriancaAssociada( (Crianca) criancaCombo.getSelectedItem() );
        } else {
            ocorrencia.setCriancaAssociada( null );
        }
        ocorrencia.setDataOcorrencia( dataCadastro.getText() );
        
        
        if ( !declaranteCombo.getModel().getSelectedItem().toString().trim().isEmpty() ) 
        {
            ocorrencia.setNomeDeclarante(declaranteCombo.getSelectedItem().toString());
        }
        
        ocorrencia.setTelefoneDeclarante( telefoneDeclarante.getText() );
        ocorrencia.setEstadoOcorreu( estadoOcorreu.getSelectedItem().toString() );
        ocorrencia.setCidadeOcorreu( cidadeOcorreu.getText() );
        ocorrencia.setBairroOcorreu( bairroOcorreu.getText() );
        ocorrencia.setRuaOcorreu( ruaOcorreu.getText() );
        ocorrencia.setComplementoOcorreu( complementoOcorreu.getText() );
        ocorrencia.setNumeroOcorreu( numeroOcorreu.getText() );
        ocorrencia.setDeclaracao( declaracao.getText() );
        ocorrencia.setObservacao( observacao.getText() );

        //Aba 3 e 4
        if ( tabbedPanel.isEnabledAt( 2 ) || tabbedPanel.isEnabledAt( 3 ) || tabbedPanel.isEnabledAt( 4 ) ) {
            ocorrencia.setDataIntimacaoProcessada( dataIntimacaoProcessada.getText() );
            ocorrencia.setDataIntimacao( dataIntimacao.getText() );
            if ( estadoIntimacao.getSelectedIndex() > 0 ) {
                ocorrencia.setEstadoIntimacao( estadoIntimacao.getSelectedItem().toString() );
            }
            ocorrencia.setCidadeIntimacao( cidadeIntimacao.getText() );
            ocorrencia.setBairroIntimacao( bairroIntimacao.getText() );
            ocorrencia.setRuaIntimacao( ruaIntimacao.getText() );
            ocorrencia.setNumeroIntimacao( numeroIntimacao.getText() );
            ocorrencia.setComplementoIntimacao( complementoIntimacao.getText() );
            ocorrencia.setIntimacao( intimacao.getText() );
        }

        //Aba 5
        if ( tabbedPanel.isEnabledAt( 4 ) ) {
            ocorrencia.setSituacao( situacao.getSelectedItem().toString() );
            ocorrencia.setProvidenciaTomada( providenciaTomada.getText() );
            ocorrencia.setParecerConclusivo( parecerConclusivo.getText() );
        }

        OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();

        if ( ocorrencia.validaDadosObrigatorios() && ocorrenciaDAO.salvar( ocorrencia ) ) 
        {
            String mensagem = ( idOcorrencia > 0 ) ? "Registro atualizado com sucesso!" : "Registro efetuado com sucesso!";
            JOptionPane.showMessageDialog( null, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE );
            ocorrenciaBusca.atualizarTabela( ocorrenciaDAO.codigoSalvo );
            
            if ( !( this.idOcorrencia > 0 ) )
            {
                this.idOcorrencia = ocorrenciaDAO.codigoSalvo;
                this.situacaoValor = Ocorrencia.ABERTA;
                
                tabbedPanel.setEnabledAt( 2, true );
                tabbedPanel.setEnabledAt( 3, true );
            }
            else
            {
                this.situacaoValor = Ocorrencia.INTIMACAO_FEITA;
                tabbedPanel.setEnabledAt( 4, true );
            }
        }
    }//GEN-LAST:event_botaoSalvarActionPerformed

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarActionPerformed
        this.setVisible( false );
    }//GEN-LAST:event_botaoCancelarActionPerformed

    private void cidadeOcorreuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cidadeOcorreuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cidadeOcorreuActionPerformed

    private void bairroOcorreuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bairroOcorreuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bairroOcorreuActionPerformed

    private void cidadeIntimacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cidadeIntimacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cidadeIntimacaoActionPerformed

    private void bairroIntimacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bairroIntimacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bairroIntimacaoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if ( pessoaIntimacao.getModel().getSelectedItem() != null ) {
            Pessoa pessoa = (Pessoa) pessoaIntimacao.getModel().getSelectedItem();
            
            for (Pessoa p : pessoasIntimacao) 
            {
                if (p.getIdPessoa() == pessoa.getIdPessoa()) 
                {
                    JOptionPane.showMessageDialog( null, "Esta pessoa já está na lista de pessoas." );
                    return;
                }
            }
            
            pessoasIntimacao.add( pessoa );

            populaRegistrosNaTabela( pessoasTable, pessoasIntimacao );
        } else {
            JOptionPane.showMessageDialog( null, "Selecione uma pessoa." );
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void populaRegistrosNaTabela( JTable tabela, List<Pessoa> pessoas ) {
        try {
            this.limparTabela( tabela );
            int rowIndex = 0;

            for ( Iterator<Pessoa> it = pessoas.iterator(); it.hasNext(); ) {
                Pessoa pessoa = it.next();

                Object[] row = {
                    pessoa.getIdPessoa(),
                    pessoa.getNome(), //nome
                    pessoa.getTelefoneResidencial(),
                    pessoa.getTelefoneCelular()
                };

                ( (DefaultTableModel) tabela.getModel() ).insertRow( rowIndex, row );

                rowIndex++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( null, "Ocorreu um erro ao popular a tabela: " + e, "Erro", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Limpa os registros populados da tabela.
     *
     * @param tabela
     */
    public void limparTabela( JTable tabela ) {
        while ( tabela.getRowCount() > 0 ) {
            ( (DefaultTableModel) tabela.getModel() ).removeRow( 0 );
        }
    }

    private void estadoOcorreuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoOcorreuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estadoOcorreuActionPerformed

    private void removerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerActionPerformed
        int selected = pessoasTable.getSelectedRow();
        pessoasIntimacao.remove(selected);
        populaRegistrosNaTabela(pessoasTable, pessoasIntimacao);
    }//GEN-LAST:event_removerActionPerformed

    private void pessoaIntimacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pessoaIntimacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pessoaIntimacaoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main( String args[] ) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for ( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels() ) {
                if ( "Nimbus".equals( info.getName() ) ) {
                    javax.swing.UIManager.setLookAndFeel( info.getClassName() );
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger( OcorrenciaFormulario.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger( OcorrenciaFormulario.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger( OcorrenciaFormulario.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger( OcorrenciaFormulario.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater( new Runnable() {
            public void run() {
                OcorrenciaFormulario dialog = new OcorrenciaFormulario( ocorrenciaBusca, true );
                dialog.addWindowListener( new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing( java.awt.event.WindowEvent e ) {
                        System.exit( 0 );
                    }
                } );
                dialog.setVisible( true );
            }
        } );
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bairroIntimacao;
    private javax.swing.JTextField bairroOcorreu;
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cidadeIntimacao;
    private javax.swing.JTextField cidadeOcorreu;
    private javax.swing.JTextField complementoIntimacao;
    private javax.swing.JTextField complementoOcorreu;
    private javax.swing.JTextField conselheiro;
    private javax.swing.JComboBox criancaCombo;
    private javax.swing.JFormattedTextField dataCadastro;
    private javax.swing.JFormattedTextField dataIntimacao;
    private javax.swing.JTextField dataIntimacaoProcessada;
    private javax.swing.JTextArea declaracao;
    private javax.swing.JComboBox declaranteCombo;
    private javax.swing.JComboBox estadoIntimacao;
    private javax.swing.JComboBox estadoOcorreu;
    private javax.swing.JTextArea intimacao;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField numeroIntimacao;
    private javax.swing.JTextField numeroOcorreu;
    private javax.swing.JTextArea observacao;
    private javax.swing.JTextArea parecerConclusivo;
    private javax.swing.JComboBox pessoaIntimacao;
    private javax.swing.JTable pessoasTable;
    private javax.swing.JTextArea providenciaTomada;
    private javax.swing.JButton remover;
    private javax.swing.JTextField ruaIntimacao;
    private javax.swing.JTextField ruaOcorreu;
    private javax.swing.JComboBox situacao;
    private javax.swing.JTabbedPane tabbedPanel;
    private javax.swing.JFormattedTextField telefoneDeclarante;
    // End of variables declaration//GEN-END:variables
}
