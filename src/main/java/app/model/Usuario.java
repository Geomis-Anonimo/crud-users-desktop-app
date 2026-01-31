package app.model;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private Telefone telefone;

    public Usuario() {}

    public Usuario(int id, String nome, String email, Telefone telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone.toString();
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public void setId(int anInt) {
        this.id = anInt;
    }
}
