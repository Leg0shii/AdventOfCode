from functools import reduce

from src.main.java.de.legoshi.AOC_2024 import parser

content = parser.get_content("1")

content = content.replace("\n", "   ").split("   ")
first_list, second_list = content[:-1:2], content[1::2]

first_list = [int(x) for x in first_list]
second_list = [int(x) for x in second_list]

first_list.sort()
second_list.sort()

result_list_1 = [abs(a - b) for (a, b) in zip(first_list, second_list)]
result_1 = reduce(lambda x, y: x + y, result_list_1)

# create a hashmap of the second list where key is the number and value is the amount the number is contained
number_map = {}
for num in second_list:
    if num in number_map:
        number_map[num] = number_map[num] + 1
    else:
        number_map[num] = 1

# iterate through first list and check if the current number is in the list and multiply by value
result_list_2 = [x * number_map[x] for x in first_list if x in number_map]
result_2 = reduce(lambda x, y: x + y, result_list_2)

print(result_1)
print(result_2)