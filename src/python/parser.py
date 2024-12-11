import os
import json

import requests

def get_content(day, year='2024') -> str:
    cript_dir = os.path.dirname(__file__)

    if day == 'p':
        practice_file_path = os.path.join(cript_dir, 'test_data')
        f = open(practice_file_path, 'r')
        return f.read()

    link = 'https://adventofcode.com/' + year + '/day/' + day + '/input'

    session_file_path = os.path.join(cript_dir, 'session_file.json')

    with open(session_file_path, 'r') as file:
        token = json.load(file)

    f = requests.get(link, cookies=token)
    return f.text
