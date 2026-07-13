const texto = document.body.innerText;
const linhas = texto.split('\n');
const ips = new Set();

linhas.forEach(linha => {
    const ip = linha.split(' ')[0];
    if (ip && ip.match(/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/)) {
        ips.add(ip);
    }
});

console.log(`Total de IPs envolvidos: ${ips.size}`);