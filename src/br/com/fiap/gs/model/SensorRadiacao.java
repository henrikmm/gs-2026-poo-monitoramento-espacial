/*
 * Conceitos demonstrados: HERANCA (extends ComponenteEspacial)
 *                       + INTERFACE (implements Sensor)
 * ============================================================================
 */

package br.com.fiap.gs.model;

import java.util.Random;

/** Sensor de radiacao. Leitura simulada na faixa de 0 a 10 mSv. */
public class SensorRadiacao extends ComponenteEspacial implements Sensor {

    private double limiteAlerta;
    private double ultimaLeitura;
    private final Random random = new Random();
    private static final String UNIDADE = "mSv";

    public SensorRadiacao(int id, String nome) {
        super(id, nome);
        this.limiteAlerta = 5.0; // limite padrao de radiacao
    }

    @Override
    public double lerValor() {
        // Simula uma leitura entre 0 e 10 mSv.
        ultimaLeitura = random.nextDouble() * 10.0;
        ultimaLeitura = Math.round(ultimaLeitura * 100.0) / 100.0;
        return ultimaLeitura;
    }

    @Override
    public boolean verificarFuncionamento() {
        return ligado;
    }

    @Override
    public String retornarTipo() {
        return "Sensor de Radiacao";
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
            System.out.println("!! Limite invalido para radiacao (deve ser > 0).");
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
                "[%s] %s | Leitura: %.2f %s | Limite: %.2f %s | Status: %s",
                retornarTipo(), nome, ultimaLeitura, UNIDADE, limiteAlerta, UNIDADE,
                (ligado ? "LIGADO" : "DESLIGADO"));
    }
}
