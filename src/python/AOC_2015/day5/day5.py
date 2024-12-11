from src.python import parser

content = parser.get_content('5', '2015')
lines = content.splitlines()

nice_lines = 0
for line in lines:
    if any(substring in line for substring in ['ab', 'cd', 'pq', 'xy']):
        continue

    s = len([x for x in line if x in ['a', 'e', 'i', 'o', 'u']])
    if s < 3:
        continue

    check = 0
    for i in range(len(line) - 1):
        if line[i] == line[i+1]:
            check = 1
            break
    
    if check == 0:
        continue

    nice_lines += 1

print(nice_lines)


