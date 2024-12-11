from src.python import parser

content = parser.get_content('5', '2015')
lines = content.splitlines()

nice_lines = 0
for line in lines:

    check = 0
    for i in range(len(line) - 2):
        if line[i] == line[i+2]:
            check = 1
            break
    
    if check == 0:
        continue

    check = 0
    for i in range(len(line) - 1):
        couple = line[i] + line[i + 1]
        temp = line.replace(couple, '')
        if len(temp) <= (len(line) - 4):
            check = 1
            break

    if check == 0:
        continue
    
    nice_lines += 1

print(nice_lines)