from src.main.java.de.legoshi.AOC_2024 import parser

content = parser.get_content("p")
number_list = []
for digit in range(len(content)):
    amount = int(content[digit])
    number_id = str(int(digit / 2))

    l = []
    for _ in range(amount):
        if digit % 2 == 0: l.append(number_id)
        else: l.append('.')

    if len(l) > 0:
        number_list.append(l)

index = len(number_list) - 1
while 0 < index:
    to_shift = number_list[index]
    if '.' in to_shift:
        index -= 1
        continue

    for i, num_l in enumerate(number_list):
        if i > index: break
        if '.' not in num_l: continue

        if len(to_shift) == len(num_l):
            number_list[i] = to_shift
            number_list[index] = num_l
            break

        if len(to_shift) <= len(num_l):
            new_list_front = ['.' for _ in range(len(num_l) - len(to_shift))]
            new_list_back = ['.' for _ in range(len(to_shift))]

            number_list[i] = to_shift
            number_list.insert(i + 1, new_list_front)

            index += 1
            number_list[index] = new_list_back
            break

    index -= 1

result_1 = 0
index = 0
for nums in number_list:
    for n in nums:
        if n != '.':
            result_1 += index * int(n)
        index += 1

print(result_1)