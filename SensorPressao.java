/*
 * Conceitos demonstrados: HERANCA (extends ComponenteEspacial)
 *                       + INTERFACE (implements Sensor)
 * ============================================================================
 */

import java.util.Random;

/** Sensor de pressao. Leitura simulada na faixa de 0 a 300 kPa. */
public class SensorPressao extends ComponenteEspacial implements Sensor {

    private double limiteAlerta;
    private double ultimaLeitura;
    private final Random random = new Random();
    private static final String UNIDADE = "kPa";

    public SensorPressao(int id, String nome) {
        super(id, nome);
        this.limiteAlerta = 200.0; // limite padrao de pressao
    }

    @Override
    public double lerValor() {
        // Simula uma leitura entre 0 e 300 kPa.
        ultimaLeitura = random.nextDouble() * 300.0;
        ultimaLeitura = Math.round(ultimaLeitura * 10.0) / 10.0;
        return ultimaLeitura;
    }

    @Override
    public boolean verificarFuncionamento() {
        return ligado;
    }

    @Override
    public String retornarTipo() {
        return "Sensor de Pressao";
    }

    @Override
    public double getLimiteAlerta() {
        return limiteAlerta;
    }

    @Override
    public void setLimiteAlerta(double limite) {
        if (limite > 0) {
            this.limiteAlerta = limite;
        } else {
            System.out.println("!! Limite invalido para pressao (deve ser > 0).");
        }
    }

    @Override
    public double getUltimaLeitura() {
        return ultimaLeitura;
    }

    @Override
    public String getUnidade() {
        return UNIDADE;
    }

    @Override
    public String gerarRelatorio() {
        return String.format(
                "[%s] %s | Leitura: %.1f %s | Limite: %.1f %s | Status: %s",
                retornarTipo(), nome, ultimaLeitura, UNIDADE, limiteAlerta, UNIDADE,
                (ligado ? "LIGADO" : "DESLIGADO"));
    }
}
