/*
 * Classe PRINCIPAL: contem o metodo main, o menu interativo, o loop principal
 * e o motor de alertas. Amarra todos os conceitos:
 *   - CLASSE ABSTRATA: trata sensores e propulsores como ComponenteEspacial
 *   - INTERFACE: usa o contrato Sensor para ler valores e detectar limites
 *   - ENCAPSULAMENTO: acessa DadosMissao apenas pelos metodos validados
 *   - HERANCA + POLIMORFISMO: acelerar()/gerarRelatorio() variam por tipo
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaMonitoramento {

    // Polimorfismo: uma unica lista guarda TODOS os componentes da estacao.
    private final List<ComponenteEspacial> componentes = new ArrayList<>();
    private final List<Sensor> sensores = new ArrayList<>();
    private final List<SistemaPropulsao> propulsores = new ArrayList<>();
    private DadosMissao dadosMissao;

    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new SistemaMonitoramento().iniciar();
    }

    /** Configura os componentes e roda o loop principal do programa. */
    public void iniciar() {
        configurarEstacao();
        System.out.println("============================================================");
        System.out.println("   PLATAFORMA DE MONITORAMENTO DE SISTEMAS ESPACIAIS");
        System.out.println("   Global Solution 2026 - POO | Henrique Mandrick - RM 562715");
        System.out.println("============================================================");

        boolean executando = true;
        while (executando) {           // LOOP PRINCIPAL
            exibirMenu();
            int opcao = lerInteiro("Escolha uma opcao: ");
            switch (opcao) {
                case 1: verificarSensores();      break;
                case 2: controlarPropulsao();     break;
                case 3: gerenciarDadosMissao();   break;
                case 4: simularAlertas();         break;
                case 5: exibirStatusCompleto();   break;
                case 6: configurarLimites();      break;
                case 0:
                    executando = false;
                    System.out.println("\nEncerrando o sistema. Boa viagem, tripulacao!");
                    break;
                default:
                    System.out.println("!! Opcao invalida. Tente novamente.");
            }
        }
        sc.close();
    }

    // ====================== Configuracao inicial ==============================

    private void configurarEstacao() {
        // Sensores (sao tambem ComponenteEspacial -> entram nas duas listas).
        SensorTemperatura st = new SensorTemperatura(1, "Sensor Temp. Reator");
        SensorPressao sp = new SensorPressao(2, "Sensor Pressao Cabine");
        SensorRadiacao sr = new SensorRadiacao(3, "Sensor Radiacao Externa");
        registrarSensor(st);
        registrarSensor(sp);
        registrarSensor(sr);

        // Propulsores (tambem ComponenteEspacial).
        PropulsaoQuimica pq = new PropulsaoQuimica(4, "Motor Principal", 9000.0, "Hidrogenio liquido");
        PropulsaoEletrica pe = new PropulsaoEletrica(5, "Motor Ionico", 1.5, 0.9);
        registrarPropulsor(pq);
        registrarPropulsor(pe);

        // Dados da missao (encapsulados, codigo de acesso = "1234").
        dadosMissao = new DadosMissao("1234", "LAT -23.5 / LON -46.6 / ALT 408km",
                "Terra -> Estacao Orbital -> Lua", 75.0, 4);

        // Liga todos os sensores por padrao para ja produzirem leituras.
        for (ComponenteEspacial c : componentes) {
            c.ligar();
        }
    }

    private void registrarSensor(Sensor s) {
        sensores.add(s);
        componentes.add((ComponenteEspacial) s); // todo sensor tambem e componente
    }

    private void registrarPropulsor(SistemaPropulsao p) {
        propulsores.add(p);
        componentes.add(p);
    }

    // ====================== Menu ==============================================

    private void exibirMenu() {
        System.out.println("\n--------------------- MENU PRINCIPAL ---------------------");
        System.out.println(" 1 - Verificar sensores");
        System.out.println(" 2 - Controlar propulsao");
        System.out.println(" 3 - Gerenciar dados da missao");
        System.out.println(" 4 - Simular alertas (varredura automatica)");
        System.out.println(" 5 - Exibir status completo");
        System.out.println(" 6 - Configurar limites de alerta dos sensores");
        System.out.println(" 0 - Sair");
        System.out.println("----------------------------------------------------------");
    }

    // ====================== 1. Sensores =======================================

    private void verificarSensores() {
        System.out.println("\n===== LEITURA DOS SENSORES =====");
        for (Sensor s : sensores) {
            if (!s.verificarFuncionamento()) {
                System.out.println("[" + s.retornarTipo() + "] DESLIGADO - sem leitura.");
                continue;
            }
            double valor = s.lerValor(); // nova leitura simulada
            System.out.printf("[%s] Leitura: %.2f %s (limite: %.2f %s) -> %s%n",
                    s.retornarTipo(), valor, s.getUnidade(),
                    s.getLimiteAlerta(), s.getUnidade(),
                    classificarAlerta(s));
        }
    }

    // ====================== 2. Propulsao ======================================

    private void controlarPropulsao() {
        System.out.println("\n===== CONTROLE DE PROPULSAO =====");
        for (int i = 0; i < propulsores.size(); i++) {
            System.out.println(" " + (i + 1) + " - " + propulsores.get(i).getNome()
                    + " (" + propulsores.get(i).getTipoPropulsao() + ")");
        }
        int escolha = lerInteiro("Escolha o motor: ");
        if (escolha < 1 || escolha > propulsores.size()) {
            System.out.println("!! Motor invalido.");
            return;
        }
        SistemaPropulsao motor = propulsores.get(escolha - 1);

        System.out.println("\n 1 - Ligar motor");
        System.out.println(" 2 - Desligar motor");
        System.out.println(" 3 - Acelerar (0-100%)");
        int acao = lerInteiro("Acao: ");
        switch (acao) {
            case 1:
                motor.ligar();
                break;
            case 2:
                motor.desligar();
                break;
            case 3:
                double potencia = lerDouble("Potencia desejada (0-100): ");
                // Polimorfismo: cada motor reage de forma diferente ao mesmo metodo.
                if (motor.acelerar(potencia)) {
                    System.out.printf(">> Empuxo gerado: %.2f kN%n", motor.calcularEmpuxo());
                }
                break;
            default:
                System.out.println("!! Acao invalida.");
        }
    }

    // ====================== 3. Dados da missao ================================

    private void gerenciarDadosMissao() {
        System.out.println("\n===== DADOS DA MISSAO =====");
        System.out.println(" 1 - Ver coordenadas (requer codigo)");
        System.out.println(" 2 - Alterar coordenadas (requer codigo)");
        System.out.println(" 3 - Ver/alterar combustivel");
        System.out.println(" 4 - Alterar numero de tripulantes");
        System.out.println(" 5 - Alterar trajetoria");
        int acao = lerInteiro("Acao: ");
        switch (acao) {
            case 1: {
                String codigo = lerTexto("Digite o codigo de acesso: ");
                System.out.println("Coordenadas: " + dadosMissao.getCoordenadas(codigo));
                break;
            }
            case 2: {
                String codigo = lerTexto("Digite o codigo de acesso: ");
                String novas = lerTexto("Novas coordenadas: ");
                dadosMissao.setCoordenadas(codigo, novas);
                break;
            }
            case 3: {
                System.out.printf("Combustivel atual: %.1f%%%n", dadosMissao.getNivelCombustivel());
                double novo = lerDouble("Novo nivel (0-100): ");
                dadosMissao.setNivelCombustivel(novo);
                if (dadosMissao.combustivelBaixo()) {
                    emitirAlerta("CRITICO", "Combustivel abaixo de 20%! Reabastecer com urgencia.");
                }
                break;
            }
            case 4: {
                int trip = lerInteiro("Numero de tripulantes: ");
                dadosMissao.setNumeroTripulantes(trip);
                System.out.println("Tripulantes: " + dadosMissao.getNumeroTripulantes());
                break;
            }
            case 5: {
                String traj = lerTexto("Nova trajetoria: ");
                dadosMissao.setTrajetoria(traj);
                System.out.println("Trajetoria: " + dadosMissao.getTrajetoria());
                break;
            }
            default:
                System.out.println("!! Acao invalida.");
        }
    }

    // ====================== 4. Motor de alertas ===============================

    /** Faz uma varredura automatica: le todos os sensores e emite alertas. */
    private void simularAlertas() {
        System.out.println("\n===== VARREDURA AUTOMATICA DE ALERTAS =====");
        boolean houveAlerta = false;

        for (Sensor s : sensores) {
            if (!s.verificarFuncionamento()) {
                System.out.println("[" + s.retornarTipo() + "] DESLIGADO - sem leitura.");
                continue;
            }
            s.lerValor(); // gera leitura nova
            String nivel = nivelDeAlerta(s);
            // Mostra SEMPRE o valor lido, para o usuario entender a decisao.
            System.out.printf("  %s leu %.2f %s (limite %.2f %s) -> %s%n",
                    s.retornarTipo(), s.getUltimaLeitura(), s.getUnidade(),
                    s.getLimiteAlerta(), s.getUnidade(),
                    nivel.equals("OK") ? "OK" : nivel);
            if (!nivel.equals("OK")) {
                emitirAlerta(nivel, String.format("%s leu %.2f %s (limite %.2f %s).",
                        s.retornarTipo(), s.getUltimaLeitura(), s.getUnidade(),
                        s.getLimiteAlerta(), s.getUnidade()));
                houveAlerta = true;
            }
        }

        // Alerta de combustivel (vem dos dados encapsulados da missao).
        if (dadosMissao.combustivelBaixo()) {
            emitirAlerta("CRITICO", String.format("Combustivel em %.1f%% (abaixo de 20%%).",
                    dadosMissao.getNivelCombustivel()));
            houveAlerta = true;
        }

        if (!houveAlerta) {
            System.out.println(">> Tudo sob controle. Nenhum alerta detectado.");
        }
    }

    // ====================== 6. Configurar limites =============================

    /** Permite ao usuario definir o limite de alerta de cada sensor. */
    private void configurarLimites() {
        System.out.println("\n===== CONFIGURAR LIMITES DE ALERTA =====");
        for (int i = 0; i < sensores.size(); i++) {
            Sensor s = sensores.get(i);
            System.out.printf(" %d - %s (limite atual: %.2f %s)%n",
                    i + 1, s.retornarTipo(), s.getLimiteAlerta(), s.getUnidade());
        }
        int escolha = lerInteiro("Escolha o sensor: ");
        if (escolha < 1 || escolha > sensores.size()) {
            System.out.println("!! Sensor invalido.");
            return;
        }
        Sensor s = sensores.get(escolha - 1);
        double novo = lerDouble("Novo limite (" + s.getUnidade() + "): ");
        s.setLimiteAlerta(novo); // a propria classe valida (so aceita > 0)
        System.out.printf(">> Limite de %s agora e %.2f %s%n",
                s.retornarTipo(), s.getLimiteAlerta(), s.getUnidade());
    }

    /**
     * Classifica o nivel de alerta de um sensor comparando leitura x limite:
     *   < 90% do limite ............ OK
     *   90% a 100% do limite ....... ATENCAO
     *   100% a 130% do limite ...... ALERTA
     *   > 130% do limite ........... CRITICO
     */
    private String nivelDeAlerta(Sensor s) {
        double leitura = s.getUltimaLeitura();
        double limite = s.getLimiteAlerta();
        if (leitura >= limite * 1.3) {
            return "CRITICO";
        } else if (leitura >= limite) {
            return "ALERTA";
        } else if (leitura >= limite * 0.9) {
            return "ATENCAO";
        }
        return "OK";
    }

    /** Versao textual usada na listagem simples de sensores. */
    private String classificarAlerta(Sensor s) {
        String nivel = nivelDeAlerta(s);
        return nivel.equals("OK") ? "OK" : ">>> " + nivel + " <<<";
    }

    /** Emite um alerta visual no console, diferenciando o nivel. */
    private void emitirAlerta(String nivel, String mensagem) {
        String prefixo;
        switch (nivel) {
            case "CRITICO": prefixo = "[!!! CRITICO !!!]"; break;
            case "ALERTA":  prefixo = "[ !! ALERTA !! ]";  break;
            default:         prefixo = "[ . ATENCAO . ]";   break;
        }
        System.out.println(prefixo + " " + mensagem);
    }

    // ====================== 5. Status completo ================================

    private void exibirStatusCompleto() {
        System.out.println("\n===== STATUS COMPLETO DA ESTACAO =====");
        // Polimorfismo puro: cada componente gera seu proprio relatorio.
        for (ComponenteEspacial c : componentes) {
            System.out.println(" - " + c.gerarRelatorio());
        }
        System.out.println("\n--- Dados da Missao ---");
        System.out.printf(" Combustivel : %.1f%%%s%n", dadosMissao.getNivelCombustivel(),
                dadosMissao.combustivelBaixo() ? "  <-- BAIXO!" : "");
        System.out.println(" Trajetoria  : " + dadosMissao.getTrajetoria());
        System.out.println(" Tripulantes : " + dadosMissao.getNumeroTripulantes());
        System.out.println(" Coordenadas : [PROTEGIDAS - requer codigo de acesso]");
    }

    // ====================== Utilitarios de entrada ============================

    private int lerInteiro(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Digite um numero inteiro valido: ");
        }
        int v = sc.nextInt();
        sc.nextLine(); // limpa o restante da linha
        return v;
    }

    private double lerDouble(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            sc.next();
            System.out.print("Digite um numero valido: ");
        }
        double v = sc.nextDouble();
        sc.nextLine();
        return v;
    }

    private String lerTexto(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
