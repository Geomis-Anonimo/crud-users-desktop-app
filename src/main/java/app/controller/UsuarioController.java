package app.controller;

import app.model.Telefone;
import app.model.Usuario;
import app.database.UsuarioDAO;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void criarUsuario(String nome, String email, String telefone) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }

        if (usuarioDAO.emailExiste(email, 0)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = new Usuario(0, nome, email, new Telefone(telefone));
        usuarioDAO.criar(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }

    public boolean atualizarUsuario(int id, String nome, String email, String telefone) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }

        if (usuarioDAO.emailExiste(email, id)) {
            throw new IllegalArgumentException("Email já cadastrado para outro usuário");
        }

        Usuario usuario = new Usuario(id, nome, email, new Telefone(telefone));
        return usuarioDAO.atualizar(usuario);
    }

    public boolean excluirUsuario(int id) {
        return usuarioDAO.excluir(id);
    }

    public Usuario buscarUsuarioPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    public List<Usuario> buscarUsuariosPorNome(String nome) {
        return usuarioDAO.buscarPorNome(nome);
    }
}