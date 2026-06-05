/*
 * Conceitos demonstrados: HERANCA (extends ComponenteEspacial)
 *                       + INTERFACE (implements Sensor)
 *
 * Um sensor E um componente espacial (tem id, nome, status, temperatura e pode
 * ser ligado/desligado) E TAMBEM cumpre o contrato Sensor. Essa combinacao e
 * o que torna o sistema coeso: o monitoramento guarda todos como
 * ComponenteEspacial e, quando precisa, usa o contrato Sensor.
 * ============================================================================
 */

package br.com.fiap.gs.model;

import java.util.Random;

/** Sensor de temperatura. Leitura simulada na faixa de -20 C a 120 C. */
public class SensorTemperatura extends ComponenteEspacial implements Sensor {

    private double limiteAlerta;
    private double ultimaLeitura;
    private final Random random = new Random();
    private static final String UNIDADE = "C";

    public SensorTemperatura(int id, String nome) {
        super(id, nome);            // chama o construtor de ComponenteEspacial
        this.limiteAlerta = 80.0;   // limite padrao de temperatura
    }

    @Override
    public double lerValor() {
        // Simula uma leitura entre -20 e 120 graus.
        ultimaLeitura = -20.0 + random.nextDouble() * 140.0;
        ultimaLeitura = Math.round(ultimaLeitura * 10.0) / 10.0;
        this.temperatura = ultimaLeitura; // mantem o atributo herdado coerente
        return ultimaLeitura;
    }

    @Override
    public boolean verificarFuncionamento() {
        // Um sensor so funciona se estiver ligado.
        return ligado;
    }

    @Override
    public String retornarTipo() {
        return "Sensor de Temperatura";
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
            System.out.println("!! Limite invalido para temperatura (deve ser > 0).");
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
