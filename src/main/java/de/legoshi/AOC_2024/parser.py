import json

import requests

def get_content(day):
    if day == "p":
        f = open("test_data", "r")
        return f.read()

    link = "https://adventofcode.com/2024/day/" + day + "/input"

    with open('session_file.json', 'r') as file:
        token = json.load(file)

    f = requests.get(link, cookies=token)
    return f.text
