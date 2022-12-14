/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import com.example.redesocial.client.UsuarioSessionBean;
import com.example.redesocial.comunidade.Comunidade;
import com.example.redesocial.comunidade.ComunidadeServiceLocal;
import com.example.redesocial.dtos.PostagemDTO;
import com.example.redesocial.postagem.Postagem;
import com.example.redesocial.postagem.PostagemServiceLocal;
import com.example.redesocial.usuario.Usuario;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * @author Tygsv
 */
@Named
@Transactional
@RequestScoped
public class PostagemController {
    
    @Inject PostagemServiceLocal postagemService;
    
    @Inject UsuarioSessionBean usuarioSession;    
    
    @Inject
    ComunidadeServiceLocal comunidadeService;
    
    private String conteudo;
    
    public void postar(){
        Postagem post = new Postagem();
        post.setConteudo(conteudo);
        post.setUsuario(usuarioSession.getUsuario());
        postagemService.salvar(post);
    }

    public List<PostagemDTO> getFeed() {
        Usuario usuario = usuarioSession.getUsuario();
        if (usuario == null)
            return postagemService.getPostagemFeed();
        else
            return postagemService.getPostagemFeed(usuario);
    }
    
    public List<PostagemDTO> getFeedPerfil(){
        Usuario usuario = usuarioSession.getUsuario();
        return getFeedFromUser(usuario);
    }

    public List<PostagemDTO> getFeedFromUser(Usuario usuario) {
        return postagemService.getPostagemPerfil(usuario);
    }

    public String getConteudo(){
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    
    public void atualizarCurtidas(PostagemDTO p){
        postagemService.atualizarCurtidas(p.getPostagem().getId(), usuarioSession.getUsuario());
    }
    
    
    public List<PostagemDTO> getFeedComunidade(String nomeComunidade){
            Comunidade comunidade = comunidadeService.localizarPorNome(nomeComunidade);
            return postagemService.getPostagemComunidade(comunidade);   
    }
    
    public void responder(Long id){
        Postagem post = new Postagem();
        Postagem pai = postagemService.localizarPorId(id);
        post.setConteudo(conteudo);
        post.setUsuario(usuarioSession.getUsuario());
        post.setPostagemPai(pai);
        postagemService.salvar(post);
    }
}
