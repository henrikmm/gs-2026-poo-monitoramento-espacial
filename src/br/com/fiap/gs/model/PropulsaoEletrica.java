/*
 * Conceito de POO demonstrado: HERANCA + uso de super()
 *
 * Propulsao eletrica (ionica): pouco empuxo, mas altissima eficiencia e baixo
 * aquecimento. Sobrescreve acelerar() e calcularEmpuxo() de forma diferente da
 * propulsao quimica -> mesmo metodo, comportamentos distintos (polimorfismo).
 * ============================================================================
 */

package br.com.fiap.gs.model;

/** Motor de propulsao eletrica/ionica. */
public class PropulsaoEletrica extends SistemaPropulsao {

    // Atributos ESPECIFICOS deste tipo de propulsao.
    private double eficiencia;       // 0.0 a 1.0 (rendimento)
    private double consumoEnergia;   // kW na potencia maxima

    public PropulsaoEletrica(int id, String nome, double empuxoMaximo, double eficiencia) {
        super(id, nome, empuxoMaximo); // super(): construtor da classe mae
        this.eficiencia = eficiencia;
        this.consumoEnergia = 5.0;
    }

    @Override
    public boolean acelerar(double porcentagem) {
        boolean aplicado = super.acelerar(porcentagem); // validacao herdada
        if (aplicado) {
            // Comportamento ESPECIFICO da propulsao eletrica: esquenta pouco.
            this.temperatura = 40.0 + (porcentagem * 0.6);
            System.out.printf(
                    ">> [ELETRICA] %s acelerando a %.0f%% | energia: %.1f kW | eficiencia: %.0f%% | %.0f C%n",
                    nome, porcentagem, consumoEnergia * (porcentagem / 100.0),
                    eficiencia * 100.0, temperatura);
        }
        return aplicado;
    }

    @Override
    public double calcularEmpuxo() {
        // Empuxo baixo, ponderado pela eficiencia do motor ionico.
        return empuxoMaximo * (potenciaAtual / 100.0) * eficiencia;
    }

    @Override
    public String getTipoPropulsao() {
        return "Propulsao Eletrica";
    }
}
