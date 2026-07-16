# Foundation

## O que é o CVSS

- A importância do CVSS na gestão de vulnerabilidades
- Como a pontuação CVSS é interpretada
- Relação entre CVE, CWE e CVSS
- Impacto na confidencialidade, integridade e disponibilidade
- Exploitabilidade da vulnerabilidade
- [CVSS Scoring Calculator 4.0](https://www.first.org/cvss/calculator/4.0)
- [CWE - Common Weakness Enumeration](https://cwe.mitre.org/)
- [CVE - Common Vulnerabilities and Exposures](https://www.cve.org/)

## Shift Left

## Desenvolvimento Seguro e Shift Left: Segurança desde o Início

## A Modelagem de Ameaças como Primeira Prática de Segurança

## Frameworks de Modelagem de Ameaças

Uso de frameworks ajuda na estruturação da modelagem:

- STRIDE (Spoofing, Tampering, Repudiation, Information Disclosure, Denial of Service, Elevation of Privilege).
- PASTA, CVSS (embora CVSS seja mais para scoring).

## Conceitos-Chave

| Conceito | Definição/Importância |
| --- | --- |
| Shift Left | Antecipação das práticas de segurança para o início do ciclo de desenvolvimento. |
| Pentest Tradicional | Teste feito no final do ciclo; pode deixar a aplicação exposta e gerar retrabalho. |
| Requisitos Funcionais | Matéria-prima do software; devem conter os aspectos de segurança desde o início. |
| Modelagem de Ameaças | Identificação proativa de riscos com base em requisitos e funcionalidades esperadas. |
| STRIDE/PASTA | Frameworks que orientam a análise de ameaças estruturadamente. |
| Consentimento | Requisito legal e ético no tratamento de dados pessoais. |
| Criptografia e Logs | Controles técnicos essenciais para proteção de dados e auditoria de acessos. |

## Desenvolvimento Seguro: Arquitetura, Requisitos e Modelagem de Ameaças

## Modelagem de Ameaças (Threat Modeling)

- DFD (Data Flow Diagram)

## Ferramentas e Frameworks

- Ferramentas automatizadas (ex.: Microsoft Threat Modeling Tool).
- Frameworks manuais (ex.: STRIDE - Spoofing, Tampering, Repudiation, Information Disclosure, Denial of Service, Elevation of Privilege).

Esses instrumentos ajudam a identificar onde há risco e sugerem ameaças específicas, como:

- Spoofing: falsificação de identidade.
- Tampering: alteração maliciosa de dados.
- DoS: sobrecarga e indisponibilidade.

## Desenvolvimento Seguro: Experiência do Desenvolvedor e Práticas Preventivas

### Práticas de Segurança em Tempo de Desenvolvimento

#### Pre-Commit Hooks

- São ferramentas que realizam verificações automáticas antes que um commit seja enviado ao repositório.
- Elas têm como objetivo impedir que falhas comuns de segurança sejam propagadas.
- Exemplo: uma regra de pre-commit pode identificar a presença de uma variável com nome "password" contendo uma string literal, o que caracteriza o uso de credenciais fixas (hard-coded credentials).
- Essas validações ajudam a prevenir vazamentos de dados sensíveis e podem ser configuradas localmente ou diretamente no repositório, por meio de funcionalidades como o "Push Protection" do GitHub.
- Para que funcionem bem, as regras precisam ser definidas em conjunto com o time de desenvolvimento. Assim, evitam-se interrupções indevidas no fluxo de trabalho.
- Analogia destacada pelo professor: é melhor bloquear o envio de uma planilha de salários antes que ela seja enviada, do que ser avisado depois que ela já foi vazada.

#### Linters e Plugins na IDE

- Linters são ferramentas que realizam análises estáticas do código enquanto o desenvolvedor ainda está escrevendo.
- Elas detectam más práticas e padrões de código inseguros em tempo real, sugerindo correções.
- Exemplo: identificar leitura de arquivos sem validação prévia ou uso de comandos com dados não verificados.
- O SonarLint é uma das ferramentas mencionadas.
- Embora alguns desenvolvedores vejam os linters como incômodos, eles fornecem alertas valiosos e ajudam a prevenir problemas desde o início.
- O professor faz uma analogia interessante com corretores automáticos de texto, como os que usamos em editores de texto e aplicativos de mensagens. Se aceitamos sugestões ortográficas automaticamente, por que não fazer o mesmo no código?
- Frase de destaque: "A vulnerabilidade nasce quando você a cria."

#### Code Review com Foco em Segurança

- Validação de parâmetros de entrada.
- Validação de arquivos lidos.
- Comunicação segura (HTTPS).

## Secret Management e o uso de Secret Managers (Vaults)

## SAST - Static Application Security Testing

### Ferramentas SAST

- Checkmarx
- Sneak
- Veracode
- Fortify
- HCL AppScan
- SameGrep (versão gratuita disponível)

## Integração no Ciclo de Desenvolvimento

- Pre-Build: Análise realizada antes da compilação do código.
- IDE Plugins: Ferramentas integradas diretamente à IDE, permitindo que os desenvolvedores verifiquem o código localmente, sem necessidade de um pull request.
- CI Pipelines: Ferramentas como GitHub Actions, Jenkins, Azure DevOps, etc., podem ser configuradas para executar SAST de forma automatizada, bloqueando pull requests se vulnerabilidades críticas forem encontradas.

## Exemplos de Vulnerabilidades Detectáveis

- SQL Injection: Detecção de entrada de dados manipulada que poderia ser utilizada para injetar comandos SQL maliciosos.
- Cross-Site Scripting (XSS): Identificação de scripts inseridos de forma insegura.
- Injeções de Comandos: Identificação de código que pode ser executado remotamente.

## Software Composition Analysis (SCA)

### Como Funciona o SCA

- Análise de Metadados: O SCA não escaneia o código da biblioteca propriamente dita, mas verifica as versões e nomes dos componentes presentes no projeto.
- Banco de Dados de Vulnerabilidades: O scanner consulta bases públicas e privadas para identificar vulnerabilidades conhecidas associadas aos componentes utilizados.
- Relatório de Vulnerabilidades: A ferramenta retorna um relatório indicando quais componentes possuem vulnerabilidades e suas respectivas versões mais seguras disponíveis.
- Árvore de Dependências: É gerado um catálogo completo das dependências do projeto, incluindo dependências diretas e transitivas (dependências das dependências).

## Ferramentas Comuns para SCA

- Comerciais: Veracode, Checkmarx, Snyk, Whitesource (atualmente Mandy).
- Open Source: Dependency Track (projeto da OWASP).
- Ferramentas do Package Manager: NPM Audit, entre outras.

## Integração do SCA

- Implementado na IDE, no CI/CD (Continuous Integration/Continuous Deployment) ou na fase de pré-build.

## Relatórios e Mitigações

## Infrastructure as Code (IaC) e Segurança

## Post-Build no CI/CD

### Container Scan (Scan de Imagens Containerizadas)

- Ferramentas como Snyk e Check realizam análise das imagens.

### Supply Chain Security

- Técnicas como SCA (Software Composition Analysis) e Container Scan.

### Assinatura de Código (Code Signing)

- Code Signing: assinatura de um código compilado para garantir sua integridade e autenticidade.
- Image Signing: assinatura de imagens de containers para assegurar que não foram adulteradas.
- Commit Signing: assinatura de commits no repositório para garantir que apenas desenvolvedores autorizados realizem modificações.

## DAST e IAST - Testes de Segurança em Aplicações

### DAST - Dynamic Application Security Testing

#### Características

- Caixa preta.
- Dependente de protocolo HTTP.
- Executa requisições HTTP.
- Normalmente configurado para rodar de forma assíncrona, sem impedir o progresso do pipeline de CI/CD.

#### Ferramentas Comuns

- ZAP Proxy (OWASP ZAP)
- Acunetix / Invicti
- Checkmarx e Snyk

### IAST - Interactive Application Security Testing

### Comparação entre DAST e IAST

| Aspecto | DAST (Dynamic Application Security Testing) | IAST (Interactive Application Security Testing) |
| --- | --- | --- |
| Acesso ao Código | Não possui acesso (caixa preta). | Acesso parcial ao servidor (caixa cinza). |
| Método de Teste | Requisições HTTP dinâmicas. | Interceptação de requisições via agente. |
| Aplicabilidade | Mais abrangente, pode testar APIs e aplicações web. | Requer interação com a aplicação. |
| Desempenho | Lento (demora horas ou até um dia). | Mais rápido, mas depende de testes automatizados. |
| Ferramentas | OWASP ZAP, Acunetix, Snyk, etc. | Contrast Security e outras ferramentas pagas. |

## Segurança em Produção, Monitoramento e Alertas

### RASP (Runtime Application Security Protection)

- Funcionamento: atua embarcado na aplicação (mobile, web, etc.), funcionando como um agente que bloqueia requisições potencialmente maliciosas.
- Diferença para IAST: o IAST testa a aplicação, enquanto o RASP é focado em proteção e bloqueio dinâmico de requisições suspeitas.
- Exemplo: bloqueio de requisições de SQL Injection identificadas durante a execução da aplicação.

### WAF (Web Application Firewall)

- Funcionamento: atua na frente da aplicação, inspecionando requisições por padrões maliciosos, como IP de origem e assinaturas conhecidas de ataques.
- Exemplo: bloqueio de requisições provenientes de IPs de listas negras (blacklists) ou requisições que contenham comandos maliciosos identificados.
- Uso principal: produção. Não é usual utilizar WAF em ambientes de desenvolvimento ou teste.

### Diferença entre RASP e WAF

- RASP: atua dentro da aplicação após a requisição passar pelo WAF.
- WAF: atua antes da requisição alcançar o servidor da aplicação, servindo como a primeira linha de defesa.

## Monitoramento e Alertas

### Monitoramento

- Importância: monitorar o comportamento da aplicação permite detectar atividades incomuns e identificar possíveis ataques.
- Ferramentas: logs gerados pela aplicação e integrados com ferramentas de monitoramento como Elastic Stack, Datadog, Splunk, etc.
- Comparação com um painel de carro: da mesma forma que um motorista monitora os sinais do carro durante uma viagem, o desenvolvedor deve monitorar o software durante sua execução.

#### Aspectos Monitorados

- Requests por minuto, hora, dia e mês.
- Quantidade de logins e requisições feitas por usuários.
- Análise de endpoints para identificar padrões suspeitos.
- Log de transações críticas e erros.

### Alertas

- Importância: permitem uma reação proativa em caso de anomalias.
- Definição de alertas: baseada no comportamento considerado normal da aplicação.
- Exemplo: se o sistema normalmente recebe 1.000 requisições por minuto, um alerta deve ser configurado para quando esse número ultrapassar um determinado limite, como 10.000 requisições por minuto.
- Ações: receber alertas via e-mail, SMS, Telegram, etc. para permitir uma resposta rápida.
- Automatização: possibilidade de integrar alertas com sistemas que respondem automaticamente a ameaças.

### Ferramentas Recomendadas

- RASP: Contrast Security, Sqreen.
- WAF: Azure Application Firewall, AWS Application Firewall, F5, Imperva, Cloudflare.
- Monitoramento e Logs: Elastic Stack, Datadog, Splunk, Logstash.
