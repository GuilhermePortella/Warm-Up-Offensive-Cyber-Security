import requests
from bs4 import BeautifulSoup

def extract_urls(url):
    response = requests.get(url)
    soup = BeautifulSoup(response.content, 'html.parser')
    urls = []

    for link in soup.find_all('a', href=True):
        urls.append(link['href'])

    return urls

# Exemplo de uso
url_to_test = 'http://'
extracted_urls = extract_urls(url_to_test)

for url in extracted_urls:
    print(url)