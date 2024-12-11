import hashlib
from src.python import parser

# too low: 343598
# too low: 343597
# too high: 444964
content = parser.get_content('4', '2015').replace('\n', '')
result = str(hashlib.md5('iwrupvqb343597'.encode()).hexdigest()[:6])
i = 0

while result != '000000':
    i += 1
    str_num = str(i)
    result = str(hashlib.md5((content + str_num).encode()).hexdigest()[:6])
    if i % 100000 == 0:
        print(content + str_num)

print(i)