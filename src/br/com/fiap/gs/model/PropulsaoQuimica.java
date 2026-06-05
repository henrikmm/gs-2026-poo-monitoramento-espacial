/*
 * Conceito de POO demonstrado: HERANCA + uso de super()
 *
 * Propulsao quimica: muito empuxo, mas alto consumo de combustivel.
 * Sobrescreve acelerar() (comportamento proprio) e reutiliza a validacao da
 * classe mae via super.acelerar(). Possui atributos especificos.
 * ============================================================================
 */

package br.com.fiap.gs.model;

/** Motor de propulsao quimica (queima de combustivel). */
public class PropulsaoQuimica extends SistemaPropulsao {

    // Atributos ESPECIFICOS deste tipo de propulsao.
    private final String tipoCombustivel;
    private double consumoPorSegundo; // litros/s na potencia maxima

    public PropulsaoQuimica(int id, String nome, double empuxoMaximo, String tipoCombustivel) {
        super(id, nome, empuxoMaximo); // super(): reaproveita o construtor da classe mae
        this.tipoCombustivel = tipoCombustivel;
        this.consumoPorSegundo = 15.0;
    }

    @Override
    public boolean acelerar(double porcentagem) {
        // super.acelerar() faz toda a validacao e ajusta a potencia.
        boolean aplicado = super.acelerar(porcentagem);
        if (aplicado) {
            // Comportamento ESPECIFICO da propulsao quimica:
            this.temperatura = 200.0 + (porcentagem * 8.0); // queima esquenta muito
            System.out.printf(
                    ">> [QUIMICA] %s acelerando a %.0f%% | combustivel (%s) consumido: %.1f L/s | camara a %.0f C%n",
                    nome, porcentagem, tipoCombustivel,
                    consumoPorSegundo * (porcentagem / 100.0), temperatura);
        }
        return aplicado;
    }

    @Override
    public double calcularEmpuxo() {
        // Propulsao quimica entrega o empuxo de forma quase linear e potente.
        return empuxoMaximo * (potenciaAtual / 100.0);
    }

    @Override
    public String getTipoPropulsao() {
        return "Propulsao Quimica";
    }
}
