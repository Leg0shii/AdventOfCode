from src.python import parser

content = parser.get_content('p')
rows = content.splitlines()

result_1 = 0
result_2 = 0
for row in rows:
    row_list = [int(x) for x in row.split('x')]
    row_list.sort()
    
    result_1 += row_list[0] * row_list[1] + 2 * (row_list[0] * row_list[1] + row_list[0] * row_list[2] + row_list[1] * row_list[2])
    result_2 += row_list[0] * row_list[1] * row_list[2] + 2 * row_list[0] + 2 * row_list[1]

print(result_1)
print(result_2)