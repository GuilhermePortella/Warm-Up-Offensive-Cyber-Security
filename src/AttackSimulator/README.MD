# AttackSimulator - Documentação

## 🔧 Visão Geral
O AttackSimulator é uma aplicação Java modular e reutilizável projetada para simular diferentes padrões de tráfego HTTP em servidores web. Foca em testes de carga, resiliência e comportamento sob conexões lentas. Ideal para estudo de redes, devops e segurança ofensiva em ambientes controlados.

## 📊 Funcionalidades
- Suporte a múltiplos hosts/IPs simultaneamente
- Execução multi-threaded com ExecutorService
- Dois modos de operação: normal e slowloris
- Geração de logs por host em arquivos separados
- Medição de banda consumida por IP (bytes enviados e recebidos)
- Reutilizável como template para outros testes de rede

## 🗂️ Estrutura do Projeto
```
AttackSimulator/
├── AttackSimulator.java        <- Classe principal CLI
├── AttackTask.java             <- Thread de ataque por host
├── LoggerUtil.java            <- Utilitário de log para arquivos
└── BandwidthTracker.java      <- Medição de tráfego por IP
```

## 🚀 Requisitos
- Java 8 ou superior
- Sistema Operacional com suporte a sockets TCP (Windows/Linux/Mac)
- Permissões adequadas para conexões de saída TCP

## ⚙️ Modo de Uso - CLI

### Comando Geral
```bash
java AttackSimulator <hosts> <porta> <threads> <modo>
```

### Parâmetros
| Parâmetro | Tipo    | Descrição                                    |
| --------- | ------- | -------------------------------------------- |
| hosts     | String  | Lista de IPs/domínios, separados por vírgula |
| porta     | Inteiro | Porta do destino TCP                         |
| threads   | Inteiro | Quantidade de threads por host               |
| modo      | String  | `normal` ou `slowloris`                      |

### 🔹 Exemplos

1. Ataque normal a localhost:8080 com 10 threads:
```bash
java AttackSimulator localhost 8080 10 normal
```

2. Ataque normal a dois IPs:
```bash
java AttackSimulator localhost,192.168.0.50 8080 10 normal
```

3. Simulação slowloris em dois IPs:
```bash
java AttackSimulator 127.0.0.1,192.168.0.105 80 10 slowloris
```

## 📁 Logs
Logs são gravados em arquivos como:
```
log_127_0_0_1.txt
log_192_168_0_105.txt
```

Exemplo de log:
```
[2025-06-06 17:45:10] [NORMAL][127.0.0.1] Tempo: 34ms, Bytes recebidos: 245
[2025-06-06 17:45:11] [SLOWLORIS] Fragmento enviado.
...
TOTAL Enviado: 3287 bytes, Recebido: 5892 bytes
```

## 🔄 Modularidade e Reuso
- **AttackTask**: pode ser adaptada para simular outros tipos de ataque (ex: HTTP Flood, Smuggling)
- **LoggerUtil**: útil em qualquer aplicação Java com necessidade de logging simples
- **BandwidthTracker**: pode ser reutilizado para monitoramento em aplicações web/desktop

## ⚖️ Aviso Legal
> ⚠️ Este projeto é apenas para uso educacional e testes em ambientes controlados.
> Nunca execute ataques em sistemas que você não tem autorização explícita para interagir.

## 💡 Ideias Futuras
- [ ] Suporte a ataques por proxy
- [ ] Simulação de headers maliciosos
- [ ] Dashboard de visualização de tráfego

## 🤝 Contribuição
Pull requests e sugestões são bem-vindas!

## 📄 Licença
MIT




Para teste de desempenho de verdade, o ideal é usar ferramenta dedicada (k6, wrk, Gatling, JMeter) em vez de implementar tudo na mão.
