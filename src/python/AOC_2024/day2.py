import operator

from python import parser

# too high
# too high 323
# too low 246
# wrong 303
def check(numbers, compare) -> bool:
    for i in range(len(numbers) - 1):
        a, b = numbers[i], numbers[i + 1]

        if (abs(a - b) < 1 or abs(a - b) > 3) or a == b or compare(a , b):
            return False

    return True

def deep_check(numbers) -> bool:
    for i in range(len(numbers)):
        number_copy = list(numbers)
        number_copy.pop(i)
        if check(number_copy, operator.lt) or check(number_copy, operator.gt):
            return True

    if check(numbers, operator.lt) or check(numbers, operator.gt):
        return True

    return False


content = parser.get_content("2")
level_list = content.split("\n")[:-1]

result_1 = 0
result_2 = 0

for level in level_list:
    number_list = [int(x) for x in level.split(" ")]
    if check(number_list, operator.lt) or check(number_list, operator.gt):
        result_1 += 1

    if deep_check(number_list):
        result_2 += 1

print(result_1)
print(result_2)
