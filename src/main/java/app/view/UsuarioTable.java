package app.view;

import javax.swing.table.AbstractTableModel;
import app.model.Usuario;
import java.util.List;
import java.util.ArrayList;

public class UsuarioTable extends AbstractTableModel {
    private List<Usuario> usuarios;
    private String[] colunas = {"ID", "Nome", "Email", "Telefone"};

    public UsuarioTable() {
        this.usuarios = new ArrayList<>();
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        fireTableDataChanged(); // Notifica a tabela que os dados mudaram
    }

    public Usuario getUsuario(int linha) {
        if (linha >= 0 && linha < usuarios.size()) {
            return usuarios.get(linha);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = usuarios.get(rowIndex);
        switch (columnIndex) {
            case 0: return usuario.getId();
            case 1: return usuario.getNome();
            case 2: return usuario.getEmail();
            case 3: return usuario.getTelefone();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        // Torna as células não editáveis diretamente na tabela
//        return false;
//    }
}

