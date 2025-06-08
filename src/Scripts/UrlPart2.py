import os
import javalang
import requests

def extract_endpoints_from_file(file_path):
    """
    Extrai endpoints de um arquivo Java.

    Args:
        file_path (str): O caminho do arquivo Java.

    Returns:
        list: Uma lista de endpoints extraídos.
    """
    with open(file_path, 'r', encoding='utf-8') as file:
        java_code = file.read()

    tree = javalang.parse.parse(java_code)
    endpoints = []

    for path, node in tree:
        if isinstance(node, javalang.tree.MethodDeclaration):
            annotations = [annotation.name for annotation in node.annotations]
            if any(annotation in annotations for annotation in ['RequestMapping', 'GetMapping', 'PostMapping']):
                endpoint = {
                    'name': node.name,
                    'parameters': [param.name for param in node.parameters]
                }
                endpoints.append(endpoint)

    return endpoints

def extract_endpoints_from_directory(directory_path):
    """
    Extrai endpoints de todos os arquivos Java em um diretório.

    Args:
        directory_path (str): O caminho do diretório.

    Returns:
        dict: Um dicionário com os caminhos dos arquivos como chaves e listas de endpoints como valores.
    """
    endpoints_by_file = {}

    for root, _, files in os.walk(directory_path):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                endpoints = extract_endpoints_from_file(file_path)
                if endpoints:
                    endpoints_by_file[file_path] = endpoints

    return endpoints_by_file

def get_cve_details(cve_id_list, api_key=None):
    """
    Consulta detalhes de CVEs usando a API sec1.io.
    """
    url = "https://api.sec1.io/rest/foss/cve/v1/cve-details"
    headers = {
        "Content-Type": "application/json"
    }
    if api_key:
        headers["sec1-api-key"] = api_key

    payload = {
        "cveIdList": cve_id_list
    }

    response = requests.post(url, json=payload, headers=headers)
    if response.status_code == 200:
        print("CVE Details Retrieved Successfully")
        return response.json()
    else:
        print(f"Failed to retrieve CVE details. Status code: {response.status_code}")
        print(response.text)
        return None

if __name__ == "__main__":
    project_directory = '/path/to/your/java/project'
    
    endpoints = extract_endpoints_from_directory(project_directory)
    for file_path, endpoints_list in endpoints.items():
        print(f"File: {file_path}")
        for endpoint in endpoints_list:
            print(f"  Endpoint: {endpoint['name']}, Parameters: {endpoint['parameters']}")

    # Exemplo de uso da função de consulta de CVEs
    cve_ids = ["CVE-2021-44228", "CVE-2023-38408"]
    api_key = "[your-generated-api-key]"  # Substitua pela sua chave, se necessário
    cve_details = get_cve_details(cve_ids, api_key)
    if cve_details:
        print(cve_details)