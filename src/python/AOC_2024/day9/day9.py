from python import parser

# 2333133121414131402
def find_next(elements):
    for index, elem in enumerate(elements):
        if elem == '.':
            return index


content = parser.get_content("p")
number_list = []
for digit in range(len(content)):
    amount = int(content[digit])
    number_id = str(int(digit / 2))

    for _ in range(amount):
        if digit % 2 == 0:
            number_list.append(number_id)
        else:
            number_list.append('.')

while '.' in number_list:
    if number_list[len(number_list) - 1] == '.':
        number_list.pop()
        continue

    index = find_next(number_list)
    last_element = number_list.pop()
    number_list[index] = last_element

result_1 = 0
for i in range(len(number_list)):
    result_1 += i * int(number_list[i])

print(result_1)
