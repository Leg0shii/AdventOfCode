import json

import requests

def get_content(day):
    link = "https://adventofcode.com/2024/day/" + day + "/input"

    with open('../session_file.json', 'r') as file:
        token = json.load(file)

    f = requests.get(link, cookies=token)
    return f.text