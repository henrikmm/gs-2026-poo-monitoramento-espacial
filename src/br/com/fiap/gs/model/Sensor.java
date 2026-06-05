/*
 * Conceito de POO demonstrado: INTERFACE
 *
 * Sensor e um CONTRATO: define quais metodos todo sensor DEVE possuir, sem
 * dizer COMO eles funcionam. Cada sensor (Temperatura, Pressao, Radiacao)
 * implementa esse contrato a sua maneira. Inclui ainda um metodo DEFAULT
 * (acimaDoLimite) que reaproveita os metodos do contrato.
 * ============================================================================
 */

package br.com.fiap.gs.model;

/**
 * Contrato comum a todos os sensores da estacao.
 */
public interface Sensor {

    /** Realiza uma nova leitura (pode ser simulada) e devolve o valor lido. */
    double lerValor();

    /** Verifica se o sensor esta funcionando corretamente. */
    boolean verificarFuncionamento();

    /** Retorna o tipo/descricao do sensor (ex.: "Sensor de Temperatura"). */
    String retornarTipo();

    /** Limite de alerta configurado para o sensor. */
    double getLimiteAlerta();

    /** Define um novo limite de alerta. */
    void setLimiteAlerta(double limite);

    /** Ultima leitura registrada (sem gerar uma nova). */
    double getUltimaLeitura();

    /** Unidade de medida do sensor (ex.: "C", "kPa", "mSv"). */
    String getUnidade();

    /**
     * Metodo DEFAULT: detecta se a ultima leitura atingiu ou ultrapassou o
     * limite de alerta. Como usa apenas outros metodos do contrato, todas as
     * classes ganham essa logica de graca, sem precisar reescrever.
     */
    default boolean acimaDoLimite() {
        return getUltimaLeitura() >= getLimiteAlerta();
    }
}
