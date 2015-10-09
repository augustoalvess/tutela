/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.cadastros.visoes;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import tutela.modulos.cadastros.modelos.daos.OcorrenciaDAO;
import tutela.modulos.cadastros.modelos.negocios.Ocorrencia;
import tutela.modulos.principal.modelos.daos.ConexaoBD;

/**
 *
 * @author augusto
 */
public class OcorrenciaBusca extends javax.swing.JInternalFrame {

    public static OcorrenciaFormulario ocorrenciaFormulario;
    
    /**
     * Creates new form OcorrenciaBusca
     */
    public OcorrenciaBusca() {
        initComponents();

        setTitle("Ocorrência");
        
        botaoNovo.setIcon(new ImageIcon(OcorrenciaBusca.this.getClass().getResource("/tutela/publico/imagens/novo.png")));
        botaoEditar.setIcon(new ImageIcon(OcorrenciaBusca.this.getClass().getResource("/tutela/publico/imagens/editar.png")));
        botaoExcluir.setIcon(new ImageIcon(OcorrenciaBusca.this.getClass().getResource("/tutela/publico/imagens/excluir.png")));
        botaoBuscar.setIcon(new ImageIcon(OcorrenciaBusca.this.getClass().getResource("/tutela/publico/imagens/buscar.png")));
        botaoAtualizar.setIcon(new ImageIcon(OcorrenciaBusca.this.getClass().getResource("/tutela/publico/imagens/atualizar.png")));
        botaoImprimir.setIcon(new ImageIcon(OcorrenciaBusca.this.getClass().getResource("/tutela/publico/imagens/imprimir.png")));
        botaoEmitirOficio.setIcon(new ImageIcon(OcorrenciaBusca.this.getClass().getResource("/tutela/publico/imagens/oficio.png")));
        botaoEmitirOficio.setEnabled(false);
        botaoEmitirIntimacoes.setIcon(new ImageIcon(OcorrenciaBusca.this.getClass().getResource("/tutela/publico/imagens/intimacao.png")));
        botaoEmitirIntimacoes.setEnabled(false);
        
        this.desabilitaAcoesDeEdicaoEExclusao();
    
        tabelaOcorrencia.setRowHeight(20);
        tabelaOcorrencia.setPreferredSize(new Dimension(10000, 500));
        
        // Evento ao selecionar um item da tabela.
        tabelaOcorrencia.getSelectionModel().addListSelectionListener(new ListSelectionListener() {  
            @Override  
            public void valueChanged(ListSelectionEvent evt) {  
               if (evt.getValueIsAdjusting())  
               {
                   return;  
               }
               
               int selected = tabelaOcorrencia.getSelectedRow();
               
               if ( selected != -1 )
               {
                    abilitaAcoesDeEdicaoEExclusao();
                    
                    Object registro = tabelaOcorrencia.getValueAt(selected, 2); // Pega a situação da ocorrência.
                    
                    // Habilita emissão do documento de oficio, se a situação estiver como "Ocorrencia.ENCAMINHADA_PARA_INSTITUIÇÃO_TERCEIRA".
                    if ( registro.toString().equals(Ocorrencia.ENCAMINHADA_PARA_INSTITUIÇÃO_TERCEIRA) )
                    {
                        botaoEmitirOficio.setEnabled(true);
                    }
                    else
                    {
                        botaoEmitirOficio.setEnabled(false);
                    }
                    
                    if ( !registro.toString().equals(Ocorrencia.NOVA) && !registro.toString().equals(Ocorrencia.ABERTA) )
                    {
                        botaoEmitirIntimacoes.setEnabled(true);
                    }
                    else
                    {
                        botaoEmitirIntimacoes.setEnabled(false);
                    }
               }
               else
               {
                   desabilitaAcoesDeEdicaoEExclusao();
                   botaoEmitirOficio.setEnabled(false);
                   botaoEmitirIntimacoes.setEnabled(false);
               }
            }  
         });
        
        this.atualizarTabela(0);
    }
    
    private void abilitaAcoesDeEdicaoEExclusao()
    {
        botaoEditar.setEnabled(true);
        botaoExcluir.setEnabled(true);
    }

    private void desabilitaAcoesDeEdicaoEExclusao()
    {
        botaoEditar.setEnabled(false);
        botaoExcluir.setEnabled(false);
    }
    
    public final void atualizarTabela(int codigoRegistroASelecionar) {
	Ocorrencia ocorrencia = new Ocorrencia();
        OcorrenciaDAO criancaDao = new OcorrenciaDAO();
        ResultSet resultSet;
        resultSet = criancaDao.listar(0, null);
        ocorrencia.populaRegistrosNaTabela(tabelaOcorrencia, resultSet, codigoRegistroASelecionar);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        botaoNovo = new javax.swing.JButton();
        botaoEditar = new javax.swing.JButton();
        botaoExcluir = new javax.swing.JButton();
        botaoAtualizar = new javax.swing.JButton();
        botaoImprimir = new javax.swing.JButton();
        botaoEmitirOficio = new javax.swing.JButton();
        botaoEmitirIntimacoes = new javax.swing.JButton();
        painelConteudo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaOcorrencia = new javax.swing.JTable();
        busca = new javax.swing.JTextField();
        botaoBuscar = new javax.swing.JButton();

        setBorder(null);
        setClosable(true);
        setTitle("Ocorrências");

        jToolBar1.setRollover(true);

        botaoNovo.setText("    Novo    ");
        botaoNovo.setFocusable(false);
        botaoNovo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoNovo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNovoActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoNovo);

        botaoEditar.setText("   Editar   ");
        botaoEditar.setFocusable(false);
        botaoEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoEditar);

        botaoExcluir.setText("   Excluir  ");
        botaoExcluir.setFocusable(false);
        botaoExcluir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoExcluir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoExcluirActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoExcluir);

        botaoAtualizar.setText(" Atualizar ");
        botaoAtualizar.setFocusable(false);
        botaoAtualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoAtualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAtualizarActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoAtualizar);

        botaoImprimir.setText("  Imprimir  ");
        botaoImprimir.setFocusable(false);
        botaoImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoImprimirActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoImprimir);

        botaoEmitirOficio.setText("  Ofício  ");
        botaoEmitirOficio.setFocusable(false);
        botaoEmitirOficio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoEmitirOficio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoEmitirOficio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEmitirOficioActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoEmitirOficio);

        botaoEmitirIntimacoes.setText("  Intimações  ");
        botaoEmitirIntimacoes.setFocusable(false);
        botaoEmitirIntimacoes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoEmitirIntimacoes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoEmitirIntimacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEmitirIntimacoesActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoEmitirIntimacoes);

        painelConteudo.setBackground(java.awt.SystemColor.controlLtHighlight);

        tabelaOcorrencia.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        tabelaOcorrencia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Data", "Situação", "Declarante", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaOcorrencia);

        busca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscaActionPerformed(evt);
            }
        });
        busca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buscaKeyPressed(evt);
            }
        });

        botaoBuscar.setText("Procurar");
        botaoBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoBuscarActionPerformed(evt);
            }
        });
        botaoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                botaoBuscarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout painelConteudoLayout = new javax.swing.GroupLayout(painelConteudo);
        painelConteudo.setLayout(painelConteudoLayout);
        painelConteudoLayout.setHorizontalGroup(
            painelConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelConteudoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1170, Short.MAX_VALUE)
                    .addGroup(painelConteudoLayout.createSequentialGroup()
                        .addComponent(busca, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        painelConteudoLayout.setVerticalGroup(
            painelConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelConteudoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botaoBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(busca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(painelConteudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelConteudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoActionPerformed
        ocorrenciaFormulario = new OcorrenciaFormulario(this, true);
        ocorrenciaFormulario.setLocationRelativeTo(null);  // centraliza a tela
        ocorrenciaFormulario.setVisible(true);
    }//GEN-LAST:event_botaoNovoActionPerformed

    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        int selected = tabelaOcorrencia.getSelectedRow();
        
        if ( selected != -1 )
        {
            Object registro = tabelaOcorrencia.getValueAt(selected, 0);
            int codOcorrencia = Integer.parseInt(registro.toString());
            
            Ocorrencia ocorrencia = new Ocorrencia(codOcorrencia);
            
            ocorrenciaFormulario = new OcorrenciaFormulario(this, true);
            ocorrenciaFormulario.populaForumlario(ocorrencia);
            ocorrenciaFormulario.setLocationRelativeTo(null);
            ocorrenciaFormulario.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Selecione um registro!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_botaoEditarActionPerformed

    private void botaoExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExcluirActionPerformed
        int selected = tabelaOcorrencia.getSelectedRow();
        
        if ( selected != -1 )
        {
            int reply = JOptionPane.showConfirmDialog(null, "Você têm certeza que deseja excluir este registro?", "Atenção!", JOptionPane.YES_NO_OPTION);
            
            if ( reply == JOptionPane.YES_OPTION ) 
            {
                Object registro = tabelaOcorrencia.getValueAt(selected, 0);
                int idOcorrencia = Integer.parseInt(registro.toString());
                
                OcorrenciaDAO ocorrenciaDao = new OcorrenciaDAO();
                
                if ( ocorrenciaDao.excluir(idOcorrencia) )
                {
                    this.atualizarTabela(0);
                    JOptionPane.showMessageDialog(null, "Registro excluído com sucesso!");
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Selecione um registro!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_botaoExcluirActionPerformed

    private void botaoAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAtualizarActionPerformed
        this.atualizarTabela(0);
    }//GEN-LAST:event_botaoAtualizarActionPerformed

    private void buscaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscaKeyPressed
        if ( evt.getKeyCode() == KeyEvent.VK_ENTER ) 
        {
            this.botaoBuscarActionPerformed(null);
        }
    }//GEN-LAST:event_buscaKeyPressed

    private void botaoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBuscarActionPerformed
        String buscar = this.busca.getText();
        Ocorrencia ocorrencia = new Ocorrencia();
        OcorrenciaDAO ocorrenciaDao = new OcorrenciaDAO();
        ResultSet resultSet;
        resultSet = ocorrenciaDao.listar(0, buscar);
        ocorrencia.populaRegistrosNaTabela(tabelaOcorrencia, resultSet, 0);
    }//GEN-LAST:event_botaoBuscarActionPerformed

    private void buscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscaActionPerformed

    private void botaoEmitirOficioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEmitirOficioActionPerformed
        try 
        {
            int selected = tabelaOcorrencia.getSelectedRow();
            Object registro = tabelaOcorrencia.getValueAt(selected, 0);
            
            // Compila o relatorio
            JasperReport relatorio = JasperCompileManager.compileReport(getClass().getResourceAsStream("/tutela/modulos/relatorios/relatorios/oficio.jrxml"));

            // Mapeia campos de parametros para o relatorio, mesmo que nao existam
            Map parametros = new HashMap();
            parametros.put("idocorrencia", registro.toString());

            // Executa relatoio
            JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, ConexaoBD.getInstance().getConnection());
            JasperExportManager.exportReportToPdfFile(impressao, "/tmp/oficio.pdf");
            
            // Exibe resultado em video
            JasperViewer.viewReport(impressao, false);
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao gerar ofício: " + e);
        }
    }//GEN-LAST:event_botaoEmitirOficioActionPerformed

    private void botaoEmitirIntimacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEmitirIntimacoesActionPerformed
        try 
        {
            int selected = tabelaOcorrencia.getSelectedRow();
            Object registro = tabelaOcorrencia.getValueAt(selected, 0);
            
            // Compila o relatorio
            JasperReport relatorio = JasperCompileManager.compileReport(getClass().getResourceAsStream("/tutela/modulos/relatorios/relatorios/intimacao.jrxml"));

            // Mapeia campos de parametros para o relatorio, mesmo que nao existam
            Map parametros = new HashMap();
            parametros.put("idocorrencia", registro.toString());

            // Executa relatoio
            JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, ConexaoBD.getInstance().getConnection());
            JasperExportManager.exportReportToPdfFile(impressao, "/tmp/intimacao.pdf");
            
            // Exibe resultado em video
            JasperViewer.viewReport(impressao, false);
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao gerar intimações: " + e);
        }
    }//GEN-LAST:event_botaoEmitirIntimacoesActionPerformed

    private void botaoImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoImprimirActionPerformed
        try 
        {            
            // Compila o relatorio
            JasperReport relatorio = JasperCompileManager.compileReport(getClass().getResourceAsStream("/tutela/modulos/relatorios/relatorios/ocorrencias.jrxml"));

            // Mapeia campos de parametros para o relatorio, mesmo que nao existam
            Map parametros = new HashMap();

            // Executa relatoio
            JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, ConexaoBD.getInstance().getConnection());
            JasperExportManager.exportReportToPdfFile(impressao, "/tmp/ocorrencias.pdf");
            
            // Exibe resultado em video
            JasperViewer.viewReport(impressao, false);
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao gerar imprimir lista de ocorrências: " + e);
        }
    }//GEN-LAST:event_botaoImprimirActionPerformed

    private void botaoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_botaoBuscarKeyPressed
        if ( evt.getKeyCode() == KeyEvent.VK_ENTER ) 
        {
            this.botaoBuscarActionPerformed(null);
        }
    }//GEN-LAST:event_botaoBuscarKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAtualizar;
    private javax.swing.JButton botaoBuscar;
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaoEmitirIntimacoes;
    private javax.swing.JButton botaoEmitirOficio;
    private javax.swing.JButton botaoExcluir;
    private javax.swing.JButton botaoImprimir;
    private javax.swing.JButton botaoNovo;
    private javax.swing.JTextField busca;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JTable tabelaOcorrencia;
    // End of variables declaration//GEN-END:variables
}
