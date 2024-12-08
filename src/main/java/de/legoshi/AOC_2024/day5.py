from collections import defaultdict

from src.main.java.de.legoshi.AOC_2024 import parser

# too high: 39783
# too low: 5692
content = parser.get_content("5")
lines = content.split("\n")[:-1]

rules_section, updates_section = content.split("\n\n")
equal_line = rules_section.splitlines()
normal_line = updates_section.splitlines()

compare_map = defaultdict(list)
for equal in equal_line:
    first, second = map(int, equal.split("|"))
    compare_map[first].append(second)


def is_valid_order(sequence, graph):
    position = {page: idx for idx, page in enumerate(sequence)}
    for start, ends in graph.items():
        if start in position:
            for end in ends:
                if end in position and position[start] >= position[end]:
                    return False
    return True


result_1 = 0
wrongly_ordered = []
for line in normal_line:
    number_list = list(map(int, line.split(",")))
    middle_number = number_list[len(number_list) // 2]

    if is_valid_order(number_list, compare_map):
        result_1 += middle_number
    else:
        wrongly_ordered.append(number_list)


result_2 = 0
for line in wrongly_ordered:
    while not is_valid_order(line, compare_map):
        for i in range(len(line)-1):
            to_check, next_num = line[i], line[i + 1]

            if next_num in compare_map and to_check in compare_map[next_num]:
                line[i + 1], line[i] = to_check, next_num
                break

    middle_number = line[len(line) // 2]
    result_2 += middle_number

print(result_1)
print(result_2)