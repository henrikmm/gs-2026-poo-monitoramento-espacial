/*
 * Conceito de POO demonstrado: ENCAPSULAMENTO
 *
 * Todos os atributos sao PRIVADOS. Dados sensiveis (coordenadas) so sao
 * lidos/alterados com o codigo de acesso correto. Todas as alteracoes passam
 * por validacao (combustivel 0-100, tripulantes >= 0, etc.), impedindo que o
 * objeto entre em um estado invalido.
 * ============================================================================
 */

/** Guarda e protege os dados sensiveis da missao. */
public class DadosMissao {

    // Todos PRIVADOS: ninguem acessa diretamente, apenas via getters/setters.
    private String coordenadas;        // dado sensivel - protegido por senha
    private final String codigoAcesso; // senha de acesso (imutavel apos criacao)
    private double nivelCombustivel;   // 0 a 100 (%)
    private String trajetoria;
    private int numeroTripulantes;

    public DadosMissao(String codigoAcesso, String coordenadas, String trajetoria,
                       double nivelCombustivel, int numeroTripulantes) {
        this.codigoAcesso = codigoAcesso;
        this.coordenadas = coordenadas;
        this.trajetoria = trajetoria;
        // Usa os setters para garantir validacao tambem na criacao.
        setNivelCombustivel(nivelCombustivel);
        setNumeroTripulantes(numeroTripulantes);
    }

    // --------------------- Dados sensiveis: COORDENADAS (com senha) -----------

    /** Le as coordenadas apenas se o codigo de acesso estiver correto. */
    public String getCoordenadas(String codigo) {
        if (autenticar(codigo)) {
            return coordenadas;
        }
        return "ACESSO NEGADO - codigo de acesso incorreto.";
    }

    /** Altera as coordenadas apenas com o codigo de acesso correto. */
    public boolean setCoordenadas(String codigo, String novasCoordenadas) {
        if (!autenticar(codigo)) {
            System.out.println("!! ACESSO NEGADO: codigo incorreto. Coordenadas nao alteradas.");
            return false;
        }
        if (novasCoordenadas == null || novasCoordenadas.trim().isEmpty()) {
            System.out.println("!! Coordenadas invalidas (vazias).");
            return false;
        }
        this.coordenadas = novasCoordenadas;
        System.out.println(">> Coordenadas atualizadas com sucesso.");
        return true;
    }

    /** Confere a senha sem nunca expor o codigo armazenado. */
    private boolean autenticar(String codigo) {
        return codigoAcesso.equals(codigo);
    }

    // --------------------- Combustivel (com validacao) ------------------------

    public double getNivelCombustivel() {
        return nivelCombustivel;
    }

    public void setNivelCombustivel(double nivel) {
        if (nivel < 0 || nivel > 100) {
            System.out.println("!! Nivel de combustivel invalido (" + nivel + "). Use 0 a 100.");
            return;
        }
        this.nivelCombustivel = nivel;
    }

    /** Alerta automatico: combustivel critico quando abaixo de 20%. */
    public boolean combustivelBaixo() {
        return nivelCombustivel < 20.0;
    }

    // --------------------- Trajetoria -----------------------------------------

    public String getTrajetoria() {
        return trajetoria;
    }

    public void setTrajetoria(String trajetoria) {
        if (trajetoria == null || trajetoria.trim().isEmpty()) {
            System.out.println("!! Trajetoria invalida (vazia).");
            return;
        }
        this.trajetoria = trajetoria;
    }

    // --------------------- Tripulantes (com validacao) ------------------------

    public int getNumeroTripulantes() {
        return numeroTripulantes;
    }

    public void setNumeroTripulantes(int numeroTripulantes) {
        if (numeroTripulantes < 0) {
            System.out.println("!! Numero de tripulantes nao pode ser negativo.");
            return;
        }
        this.numeroTripulantes = numeroTripulantes;
    }
}
