# Plano de Estudos para Cibersegurança - Foco em Segurança Ofensiva/Red Team

## Introdução

Este plano de estudos foi desenvolvido para guiar sua transição de carreira de engenheiro de software backend Java para engenheiro de cibersegurança, com foco específico em segurança ofensiva e operações de Red Team. O plano está estruturado para um período de 12 meses, considerando sua disponibilidade de 2-3 horas nos dias de semana e 4-6 horas nos fins de semana.

## Fundamentos de Cibersegurança (Meses 1-2)

### Mês 1: Conceitos Básicos e Fundamentos de Redes

**Semana 1-2: Fundamentos de Segurança da Informação**
- Princípios básicos: Confidencialidade, Integridade e Disponibilidade (CIA)
- Modelos de segurança e controles
- Tipos de ameaças e vetores de ataque
- Gestão de riscos em segurança da informação

**Recursos recomendados:**
- Curso: CompTIA Security+ (material preparatório)
- Livro: "Fundamentos de Segurança da Informação" de Michael E. Whitman e Herbert J. Mattord
- Plataforma: TryHackMe - Módulos introdutórios

**Semana 3-4: Fundamentos de Redes**
- Modelo OSI e TCP/IP
- Protocolos de rede (HTTP, HTTPS, FTP, SSH, etc.)
- Configuração de redes e sub-redes
- Ferramentas de análise de rede (Wireshark, tcpdump)

**Recursos recomendados:**
- Curso: Networking Fundamentals (Pluralsight ou Coursera)
- Laboratório prático: Configuração de redes virtuais com VirtualBox/VMware
- Plataforma: TryHackMe - Network Fundamentals

**Projeto prático:**
- Configurar um ambiente de laboratório virtual com diferentes sistemas operacionais
- Realizar captura e análise de tráfego de rede usando Wireshark

### Mês 2: Sistemas Operacionais e Introdução ao Linux

**Semana 1-2: Fundamentos de Sistemas Operacionais**
- Arquitetura de sistemas operacionais
- Gerenciamento de processos e memória
- Sistemas de arquivos e permissões
- Vulnerabilidades comuns em sistemas operacionais

**Recursos recomendados:**
- Curso: Introduction to Operating Systems (edX ou Coursera)
- Livro: "Modern Operating Systems" de Andrew S. Tanenbaum

**Semana 3-4: Linux para Segurança**
- Comandos básicos e avançados do Linux
- Administração de sistemas Linux
- Hardening de sistemas Linux
- Bash scripting para automação

**Recursos recomendados:**
- Curso: Linux Fundamentals (Linux Academy ou Pluralsight)
- Plataforma: OverTheWire - Bandit (desafios de linha de comando)
- Livro: "The Linux Command Line" de William Shotts

**Projeto prático:**
- Criar scripts em Bash para automatizar tarefas de segurança básicas
- Configurar um servidor Linux com hardening de segurança

## Segurança Ofensiva Básica (Meses 3-5)

### Mês 3: Introdução ao Ethical Hacking e Ferramentas Básicas

**Semana 1-2: Metodologias de Ethical Hacking**
- Ciclo de vida de um pentest
- Reconhecimento e coleta de informações
- Scanning e enumeração
- Exploração básica

**Recursos recomendados:**
- Curso: Ethical Hacking Fundamentals (Udemy, Pluralsight)
- Livro: "The Hacker Playbook 3" de Peter Kim
- Plataforma: HackTheBox - Starting Point

**Semana 3-4: Ferramentas Essenciais**
- Kali Linux e distribuições de segurança
- Nmap e técnicas de scanning
- Metasploit Framework básico
- Burp Suite Community Edition

**Recursos recomendados:**
- Curso: Kali Linux Revealed (kali.org)
- Laboratório: Configuração e uso do Kali Linux
- Plataforma: TryHackMe - Rooms sobre ferramentas específicas

**Projeto prático:**
- Realizar um pentest básico em ambiente controlado (máquinas vulneráveis como DVWA)
- Documentar o processo e resultados em formato de relatório profissional

### Mês 4: Vulnerabilidades Web e Exploração Básica

**Semana 1-2: Segurança de Aplicações Web**
- OWASP Top 10
- Injeção SQL e XSS
- CSRF e quebra de autenticação
- Configurações incorretas de segurança

**Recursos recomendados:**
- Curso: Web Security Academy (PortSwigger)
- Plataforma: OWASP Juice Shop
- Livro: "The Web Application Hacker's Handbook" de Dafydd Stuttard

**Semana 3-4: Exploração de Vulnerabilidades Web**
- Técnicas avançadas de SQL Injection
- Bypass de autenticação
- Ataques de upload de arquivos
- Ferramentas automatizadas (SQLmap, OWASP ZAP)

**Recursos recomendados:**
- Curso: Advanced Web Attacks and Exploitation (SANS ou equivalente)
- Plataforma: PentesterLab - Web para CTF
- Laboratório: Configuração de ambientes vulneráveis (DVWA, bWAPP)

**Projeto prático:**
- Identificar e explorar vulnerabilidades em aplicações web vulneráveis
- Desenvolver um script personalizado para automatizar a detecção de vulnerabilidades específicas

### Mês 5: Exploração de Sistemas e Escalação de Privilégios

**Semana 1-2: Exploração de Sistemas**
- Identificação de vulnerabilidades em sistemas
- Exploração de serviços vulneráveis
- Buffer overflows básicos
- Uso avançado do Metasploit Framework

**Recursos recomendados:**
- Curso: Penetration Testing with Kali Linux (Offensive Security)
- Plataforma: VulnHub - Máquinas vulneráveis
- Livro: "Penetration Testing: A Hands-On Introduction" de Georgia Weidman

**Semana 3-4: Escalação de Privilégios**
- Técnicas de escalação de privilégios em Windows
- Técnicas de escalação de privilégios em Linux
- Bypass de controles de segurança
- Persistência em sistemas comprometidos

**Recursos recomendados:**
- Curso: Windows/Linux Privilege Escalation (TCM Security ou equivalente)
- GitHub: PayloadsAllTheThings (repositório de técnicas)
- Plataforma: HackTheBox - Máquinas intermediárias

**Projeto prático:**
- Comprometer um sistema e realizar escalação de privilégios completa
- Documentar vetores de ataque e técnicas utilizadas

## Segurança Ofensiva Intermediária (Meses 6-8)

### Mês 6: Engenharia Social e Phishing

**Semana 1-2: Fundamentos de Engenharia Social**
- Princípios psicológicos da engenharia social
- Técnicas de manipulação e persuasão
- Reconhecimento e OSINT para engenharia social
- Defesas contra engenharia social

**Recursos recomendados:**
- Livro: "Social Engineering: The Science of Human Hacking" de Christopher Hadnagy
- Curso: Social Engineering for Penetration Testers

**Semana 3-4: Ataques de Phishing**
- Criação de campanhas de phishing
- Infraestrutura para phishing
- Evasão de filtros de segurança
- Análise de resultados

**Recursos recomendados:**
- Ferramentas: Gophish, SET (Social Engineering Toolkit)
- Curso: Advanced Phishing Techniques
- Laboratório: Configuração de servidor de phishing em ambiente controlado

**Projeto prático:**
- Desenvolver uma campanha de phishing completa (em ambiente controlado)
- Criar templates personalizados e analisar taxas de sucesso

### Mês 7: Testes de Penetração em Redes Internas

**Semana 1-2: Movimentação Lateral**
- Técnicas de movimentação em redes
- Pass-the-hash e outros ataques de credenciais
- Pivoting e tunelamento
- Bypass de segmentação de rede

**Recursos recomendados:**
- Curso: Network Penetration Testing (eLearnSecurity ou equivalente)
- Livro: "Red Team Field Manual" (RTFM)
- Plataforma: HackTheBox Pro Labs

**Semana 3-4: Ataques a Infraestrutura de Domínio**
- Ataques a Active Directory
- Kerberoasting e AS-REP Roasting
- Abuso de relações de confiança
- Persistência em ambientes de domínio

**Recursos recomendados:**
- Curso: Active Directory Security (Pentester Academy)
- GitHub: BloodHound (ferramenta e documentação)
- Laboratório: Configuração de ambiente AD vulnerável

**Projeto prático:**
- Simular um ataque completo a uma infraestrutura de domínio
- Documentar a cadeia de ataque e técnicas utilizadas

### Mês 8: Evasão de Defesas e Antivírus

**Semana 1-2: Técnicas de Evasão**
- Evasão de firewalls e IDS/IPS
- Técnicas de ofuscação
- Comunicações encriptadas
- Bypass de WAF

**Recursos recomendados:**
- Curso: Advanced Evasion Techniques
- GitHub: Repositórios de técnicas de evasão
- Plataforma: TryHackMe - Rooms sobre evasão

**Semana 3-4: Bypass de Antivírus e EDR**
- Técnicas de ofuscação de malware
- Shellcoding básico
- Injeção de processos
- Técnicas de Living-off-the-Land

**Recursos recomendados:**
- Curso: Malware Development Essentials
- GitHub: LOLBAS Project
- Livro: "Antivirus Bypass Techniques" (recursos online)

**Projeto prático:**
- Desenvolver um payload personalizado capaz de evadir soluções de antivírus
- Testar diferentes técnicas de evasão e documentar resultados

## Segurança Ofensiva Avançada (Meses 9-12)

### Mês 9: Red Team Operations

**Semana 1-2: Metodologias de Red Team**
- Diferenças entre pentest e red team
- Planejamento de operações
- Threat emulation
- Command and Control (C2)

**Recursos recomendados:**
- Curso: Red Team Operations (Zero-Point Security ou equivalente)
- Livro: "Red Team Development and Operations" de Joe Vest e James Tubberville
- Plataforma: SANS CTFs ou equivalentes

**Semana 3-4: Infraestrutura de Red Team**
- Configuração de infraestrutura resiliente
- Redirectors e proxies
- Domains e certificados
- OPSEC (Operational Security)

**Recursos recomendados:**
- Curso: Red Team Infrastructure
- GitHub: Red Team Infrastructure Wiki
- Laboratório: Configuração de infraestrutura C2

**Projeto prático:**
- Planejar e executar uma operação de red team completa
- Configurar infraestrutura de comando e controle

### Mês 10: Exploração Avançada e Desenvolvimento de Exploits

**Semana 1-2: Análise de Vulnerabilidades Avançada**
- Fuzzing básico
- Análise de código para vulnerabilidades
- Engenharia reversa básica
- Exploração de vulnerabilidades zero-day

**Recursos recomendados:**
- Curso: Exploit Development (Offensive Security ou equivalente)
- Livro: "The Shellcoder's Handbook" de Chris Anley
- Plataforma: CTFs focados em exploração

**Semana 3-4: Desenvolvimento de Exploits**
- Buffer overflows avançados
- Exploração de heap
- Bypass de proteções (ASLR, DEP, etc.)
- Desenvolvimento de exploits personalizados

**Recursos recomendados:**
- Curso: Advanced Exploit Development
- GitHub: Repositórios de exploits e técnicas
- Laboratório: Ambiente para desenvolvimento de exploits

**Projeto prático:**
- Desenvolver um exploit para uma vulnerabilidade conhecida
- Modificar exploits existentes para contornar proteções específicas

### Mês 11: Segurança em Nuvem e Containers

**Semana 1-2: Segurança em Ambientes Cloud**
- Modelos de segurança em nuvem
- Vulnerabilidades específicas de AWS, Azure, GCP
- Configurações incorretas comuns
- Técnicas de ataque em ambientes cloud

**Recursos recomendados:**
- Curso: Cloud Security (A Cloud Guru, Pluralsight)
- GitHub: CloudGoat, Pacu (ferramentas de pentest AWS)
- Laboratório: Ambientes vulneráveis em nuvem

**Semana 3-4: Segurança de Containers e Kubernetes**
- Vulnerabilidades em containers
- Escape de containers
- Ataques a Kubernetes
- Técnicas de exploração em ambientes containerizados

**Recursos recomendados:**
- Curso: Container Security
- GitHub: Kubernetes Goat
- Laboratório: Ambiente Kubernetes vulnerável

**Projeto prático:**
- Realizar um pentest completo em ambiente cloud
- Explorar vulnerabilidades em clusters Kubernetes

### Mês 12: Automação e Preparação para Certificações

**Semana 1-2: Automação em Red Team**
- Desenvolvimento de ferramentas personalizadas
- Automação com Python para pentest
- Integração de ferramentas
- CI/CD para segurança ofensiva

**Recursos recomendados:**
- Curso: Python for Hackers
- GitHub: Repositórios de ferramentas de automação
- Livro: "Black Hat Python" de Justin Seitz

**Semana 3-4: Preparação para Certificações**
- Revisão de conceitos-chave
- Exercícios práticos
- Simulados de exames
- Planejamento para certificações específicas

**Recursos recomendados:**
- Materiais preparatórios para OSCP, eCPPT, CRTP
- Plataformas: HackTheBox, TryHackMe (desafios avançados)
- Comunidade: Participação em fóruns e grupos de estudo

**Projeto prático:**
- Desenvolver uma ferramenta de automação para algum aspecto de pentest
- Realizar um pentest completo utilizando as habilidades adquiridas ao longo do ano

## Certificações Recomendadas (em ordem sugerida)

1. **CompTIA Security+** - Certificação de fundamentos de segurança, boa para iniciantes
2. **eJPT (eLearnSecurity Junior Penetration Tester)** - Certificação entry-level para pentest
3. **eCPPT (eLearnSecurity Certified Professional Penetration Tester)** - Certificação intermediária de pentest
4. **OSCP (Offensive Security Certified Professional)** - Certificação altamente reconhecida na indústria
5. **CRTP (Certified Red Team Professional)** - Especialização em ataques a Active Directory
6. **OSEP (Offensive Security Experienced Penetration Tester)** - Certificação avançada de evasão e red team

## Recursos Contínuos

- **Plataformas de CTF**: HackTheBox, TryHackMe, VulnHub
- **Comunidades**: Reddit (r/netsec, r/AskNetSec), Discord (NetSec Focus, Hack The Box)
- **Blogs**: PortSwigger Research, Project Zero, HackerOne Hacktivity
- **Conferências**: DEF CON, Black Hat, BSides (muitas têm gravações disponíveis online)
- **GitHub**: Acompanhar repositórios de ferramentas e técnicas de segurança
- **Podcasts**: Darknet Diaries, Security Now, Risky Business

## Dicas para o Sucesso

1. **Pratique regularmente**: Dedique tempo para laboratórios práticos e desafios
2. **Documente seu aprendizado**: Mantenha um blog ou notas detalhadas
3. **Construa uma rede de contatos**: Participe de comunidades e eventos
4. **Contribua para projetos open source**: Melhore ferramentas existentes ou crie novas
5. **Mantenha-se atualizado**: A segurança evolui rapidamente, acompanhe as novidades
6. **Ética profissional**: Sempre pratique dentro de ambientes controlados e com autorização
7. **Equilíbrio**: Alterne entre teoria e prática para maximizar o aprendizado
