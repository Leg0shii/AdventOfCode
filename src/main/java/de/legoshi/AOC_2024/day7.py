import math

from src.main.java.de.legoshi.AOC_2024 import parser

def get_mask(unmasked, length):
    local_mask = []

    while unmasked > 0:
        if unmasked % 3 == 0: local_mask.append(0)
        if unmasked % 3 == 1: local_mask.append(1)
        if unmasked % 3 == 2: local_mask.append(2)

        unmasked = math.floor(unmasked / 3)

    while len(local_mask) < length:
        local_mask.append(0)

    local_mask.reverse()
    return local_mask

# 76
# too low: 930825174
# too low: 930825601
content = parser.get_content("7")
rows = content.splitlines()

result_1 = 0
for row in rows:
    result, temp = row.split(": ")
    numbers = [int(x) for x in temp.split(" ")]

    for i in range(int(math.pow(3, len(numbers) - 1))):
        mask = get_mask(i, len(numbers) - 1)
        possible_result = numbers[0]
        index_counter = 0

        for m in mask:
            if int(m) == 0: possible_result += numbers[index_counter + 1]
            if int(m) == 1: possible_result *= numbers[index_counter + 1]
            if int(m) == 2: possible_result = int(str(possible_result) + str(numbers[index_counter + 1]))
            index_counter += 1

        if possible_result == int(result):
            result_1 += possible_result
            escape = True
            break

print(result_1)