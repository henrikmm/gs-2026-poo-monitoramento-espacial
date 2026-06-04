/*
 * Conceito de POO demonstrado: HERANCA (classe abstrata base)
 *
 * Define atributos e comportamentos comuns a QUALQUER motor (potencia, empuxo
 * maximo, ligar/desligar motor, acelerar com validacao). Tambem e um
 * ComponenteEspacial, integrando-se ao restante do sistema. As subclasses
 * sobrescrevem acelerar() e calculam o empuxo de formas diferentes.
 * ============================================================================
 */

/** Classe abstrata base para todos os sistemas de propulsao. */
public abstract class SistemaPropulsao extends ComponenteEspacial {

    protected double potenciaAtual;   // potencia atual em % (0 a 100)
    protected double empuxoMaximo;    // empuxo maximo do motor em kN

    public SistemaPropulsao(int id, String nome, double empuxoMaximo) {
        super(id, nome);             // super(): chama o construtor de ComponenteEspacial
        this.empuxoMaximo = empuxoMaximo;
        this.potenciaAtual = 0.0;
    }

    /**
     * Acelera o motor para a porcentagem informada, COM VALIDACAO.
     * As subclasses sobrescrevem este metodo para adicionar comportamento
     * proprio e usam super.acelerar() para reaproveitar esta validacao.
     *
     * @return true se a aceleracao foi aplicada
     */
    public boolean acelerar(double porcentagem) {
        if (!ligado) {
            System.out.println("!! O motor [" + nome + "] esta DESLIGADO. Ligue-o antes de acelerar.");
            return false;
        }
        if (porcentagem < 0 || porcentagem > 100) {
            System.out.println("!! Potencia invalida (" + porcentagem + "). Use um valor entre 0 e 100.");
            return false;
        }
        this.potenciaAtual = porcentagem;
        return true;
    }

    /** Cada tipo de propulsao calcula o empuxo de forma diferente (metodo abstrato). */
    public abstract double calcularEmpuxo();

    /** Descricao do tipo concreto de propulsao. */
    public abstract String getTipoPropulsao();

    public double getPotenciaAtual() {
        return potenciaAtual;
    }

    public double getEmpuxoMaximo() {
        return empuxoMaximo;
    }

    @Override
    public String gerarRelatorio() {
        return String.format(
                "[%s] %s | Status: %s | Potencia: %.0f%% | Empuxo: %.1f kN",
                getTipoPropulsao(), nome, (ligado ? "LIGADO" : "DESLIGADO"),
                potenciaAtual, calcularEmpuxo());
    }
}
