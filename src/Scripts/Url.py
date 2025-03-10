import requests
from bs4 import BeautifulSoup
from urllib.parse import urlparse

def extract_urls(url):
    """
    Extrai todas as URLs de uma página web.

    Args:
        url (str): A URL da página web.

    Returns:
        list: Uma lista de URLs extraídas.
    """
    try:
        response = requests.get(url)
        response.raise_for_status()  # Verifica se a requisição foi bem-sucedida
    except requests.RequestException as e:
        print(f"Erro ao acessar a URL: {e}")
        return []

    soup = BeautifulSoup(response.content, 'html.parser')
    urls = []

    for link in soup.find_all('a', href=True):
        urls.append(link['href'])

    return urls

def is_valid_url(url):
    """
    Verifica se uma URL é válida.

    Args:
        url (str): A URL a ser verificada.

    Returns:
        bool: True se a URL for válida, False caso contrário.
    """
    parsed = urlparse(url)
    return bool(parsed.netloc) and bool(parsed.scheme)

if __name__ == "__main__":
    url_to_test = 'http://example.com'
    
    if is_valid_url(url_to_test):
        extracted_urls = extract_urls(url_to_test)
        for url in extracted_urls:
            print(url)
    else:
        print("URL inválida")