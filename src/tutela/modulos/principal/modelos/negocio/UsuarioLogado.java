/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutela.modulos.principal.modelos.negocio;

import tutela.modulos.cadastros.modelos.negocios.Usuario;

/**
 *
 * @author augusto
 */
public class UsuarioLogado {
    
    private static final UsuarioLogado instance = new UsuarioLogado();

    private Usuario usuarioLogado;
    
    private UsuarioLogado() {}
    
    public static UsuarioLogado getInstance() 
    {
        return instance;
    }
    
    public void setaUsuarioLogado( Usuario usuario ) 
    {
        this.usuarioLogado = usuario;
    }
    
    public Usuario getUsuarioLogado() 
    {
        return usuarioLogado;
    }
    
}
