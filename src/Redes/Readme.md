# Análise Avançada de Redes e Protocolos

## 1. Conceitos Fundamentais de Redes

- **Modelo OSI e TCP/IP:**  
  - OSI: 7 camadas (Física, Enlace, Rede, Transporte, Sessão, Apresentação, Aplicação).
  - TCP/IP: 4 camadas (Acesso à Rede, Internet, Transporte, Aplicação).
- **Topologias:**  
  - Estrela, barramento, anel, malha, híbrida.
- **Dispositivos de Rede:**  
  - Switches, roteadores, hubs, firewalls, access points.

## 2. Protocolos de Rede Essenciais

- **Ethernet (IEEE 802.3):**  
  - Protocolo de camada 2 (Enlace), base das LANs.
- **IP (Internet Protocol):**  
  - IPv4 e IPv6, roteamento, sub-redes, CIDR.
- **TCP (Transmission Control Protocol):**  
  - Confiável, orientado à conexão, controle de fluxo, handshake 3-way.
- **UDP (User Datagram Protocol):**  
  - Não confiável, sem conexão, usado para streaming e DNS.
- **ICMP (Internet Control Message Protocol):**  
  - Diagnóstico de rede (ping, traceroute).
- **ARP (Address Resolution Protocol):**  
  - Resolução de endereços IP para MAC.
- **DHCP (Dynamic Host Configuration Protocol):**  
  - Atribuição dinâmica de IPs.
- **DNS (Domain Name System):**  
  - Resolução de nomes para IPs.
- **HTTP/HTTPS:**  
  - Comunicação web, HTTP (porta 80), HTTPS (porta 443, criptografado via TLS).
- **FTP, SSH, SMTP, POP3, IMAP:**  
  - Protocolos de transferência de arquivos e e-mails.

## 3. Análise de Tráfego de Rede

- **Captura de Pacotes:**  
  - Ferramentas: Wireshark, tcpdump, tshark.
  - Permite inspecionar cabeçalhos, payloads e identificar anomalias.
- **Filtros e Expressões:**  
  - Filtragem por IP, porta, protocolo, conteúdo.
- **Reconhecimento de Protocolos:**  
  - Identificação de protocolos em diferentes camadas.
- **Detecção de Ataques:**  
  - Scans (Nmap), DoS/DDoS, spoofing, ARP poisoning, MITM.
- **Análise de Fluxos (NetFlow, sFlow):**  
  - Monitoramento de tráfego agregado, identificação de padrões suspeitos.

## 4. Segurança em Redes

- **Firewalls:**  
  - Filtragem de pacotes, regras de acesso, firewalls de próxima geração (NGFW).
- **IDS/IPS:**  
  - Sistemas de detecção e prevenção de intrusos (Snort, Suricata).
- **VPN (Virtual Private Network):**  
  - Tunelamento seguro, protocolos (IPSec, OpenVPN, WireGuard).
- **Segmentação de Rede:**  
  - VLANs, sub-redes, DMZ.
- **Criptografia:**  
  - TLS/SSL, IPsec, WPA2/WPA3 para Wi-Fi.
- **Autenticação e Controle de Acesso:**  
  - 802.1X, RADIUS, TACACS+.

## 5. Protocolos e Vulnerabilidades Comuns

- **DNS Spoofing/Poisoning:**  
  - Manipulação de respostas DNS.
- **ARP Spoofing:**  
  - Redirecionamento de tráfego local.
- **Port Scanning:**  
  - Identificação de serviços abertos.
- **SMB/NetBIOS:**  
  - Vulnerabilidades em compartilhamento de arquivos.
- **SSL/TLS:**  
  - Ataques como downgrade, Heartbleed, certificados inválidos.

## 6. Ferramentas de Análise e Pentest

- **Wireshark:**  
  - Análise detalhada de pacotes.
- **Nmap:**  
  - Varredura de portas, detecção de serviços e sistemas operacionais.
- **Netcat:**  
  - Ferramenta para testes de conexão e transferência de dados.
- **Ettercap:**  
  - Ataques MITM, sniffing em redes locais.
- **Scapy:**  
  - Manipulação e criação de pacotes personalizados em Python.

## 7. Boas Práticas e Monitoramento

- **Monitoramento Contínuo:**  
  - Uso de SIEM, logs centralizados, alertas em tempo real.
- **Atualização de Dispositivos:**  
  - Patches de firmware e software.
- **Políticas de Senha e Autenticação Forte.**
- **Educação e Treinamento de Usuários.**

## 8. Exemplos de Análise Prática

- **Identificação de um ataque de SYN Flood:**  
  - Muitos pacotes SYN sem finalização do handshake.
- **Detecção de tráfego suspeito:**  
  - Comunicação com IPs externos desconhecidos, portas incomuns.
- **Análise de vazamento de dados:**  
  - Pacotes contendo informações sensíveis em texto claro.

---

> **Referências:**  
> - RFCs dos protocolos (IETF)  
> - [Wireshark Documentation](https://www.wireshark.org/docs/)  
> - [Nmap Reference Guide](https://nmap.org/book/man.html)  
> - [OWASP Network Security](https://owasp.org/www-project-network-security/)
