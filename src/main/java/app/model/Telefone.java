package app.model;

public class Telefone {
    private String numero;

    public Telefone(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser nulo ou vazio");
        }

        String numeroLimpo = numero.replaceAll("[^\\d]", "");

        if (numeroLimpo.length() < 10 || numeroLimpo.length() > 11) {
            throw new IllegalArgumentException(
                    "Telefone inválido. Deve ter 10 (fixo) ou 11 (celular) dígitos"
            );
        }

        this.numero = formatarNumero(numeroLimpo);
    }

    private String formatarNumero(String numeroLimpo) {
        String ddd = numeroLimpo.substring(0, 2);
        String resto = numeroLimpo.substring(2);

        if (numeroLimpo.length() == 10) {
            return String.format("(%s) %s-%s",
                    ddd,
                    resto.substring(0, 4),
                    resto.substring(4));
        } else {
            return String.format("(%s) %s-%s",
                    ddd,
                    resto.substring(0, 5),
                    resto.substring(5));
        }
    }

    public String getNumeroFormatado() {
        return numero;
    }

    @Override
    public String toString() {
        return numero;
    }
}