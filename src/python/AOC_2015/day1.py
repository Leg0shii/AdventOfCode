from src.python import parser

content = parser.get_content('p')

down = content.count(')')
up = content.count('(')

floor = 0
count = 0
for walk in content:
    if walk == ')': floor -= 1
    else: floor += 1

    if floor == -1: break
    count += 1
    print(count, floor)

print(up - down)
print(count + 1)
