/*
 * Conceito de POO demonstrado: CLASSE ABSTRATA
 *
 * ComponenteEspacial e a base de toda a plataforma. Nao pode ser instanciada
 * diretamente (e abstrata) e serve de molde para TODOS os componentes da
 * estacao: sensores e sistemas de propulsao. Por isso o sistema consegue
 * tratar tudo de forma uniforme (polimorfismo) em uma unica lista.
 * ============================================================================
 */

/**
 * Classe abstrata que representa qualquer componente da estacao espacial.
 * Define atributos e comportamentos comuns (ligar/desligar) e obriga as
 * subclasses a implementarem o metodo abstrato {@link #gerarRelatorio()}.
 */
public abstract class ComponenteEspacial {

    // Atributos comuns a todos os componentes (protected: visiveis as subclasses).
    protected int id;
    protected String nome;
    protected boolean ligado;        // status: ligado (true) ou desligado (false)
    protected double temperatura;    // temperatura interna do componente em graus Celsius

    /**
     * Construtor base. Todo componente nasce DESLIGADO e em temperatura ambiente.
     */
    public ComponenteEspacial(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.ligado = false;
        this.temperatura = 20.0; // temperatura ambiente padrao
    }

    // ----------------------- Metodos CONCRETOS (herdados por todos) -----------

    /** Liga o componente. */
    public void ligar() {
        this.ligado = true;
        System.out.println(">> [" + nome + "] foi LIGADO.");
    }

    /** Desliga o componente. */
    public void desligar() {
        this.ligado = false;
        System.out.println(">> [" + nome + "] foi DESLIGADO.");
    }

    // ----------------------- Metodo ABSTRATO ----------------------------------

    /**
     * Cada tipo de componente sabe gerar seu proprio relatorio de status.
     * Metodo abstrato: as subclasses SAO OBRIGADAS a implementar.
     *
     * @return texto formatado com o estado atual do componente
     */
    public abstract String gerarRelatorio();

    // ----------------------- Getters ------------------------------------------

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isLigado() {
        return ligado;
    }

    public double getTemperatura() {
        return temperatura;
    }
}
