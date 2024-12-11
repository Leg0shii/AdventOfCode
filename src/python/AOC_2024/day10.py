import python.parser as parser

def get_neighbors(pos, value):
    check_list = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    neighbors = []

    for check in check_list:
        shift_x = pos[0] + check[0]
        shift_y = pos[1] + check[1]
        if 0 <= shift_x < len(rows[0]) and 0 <= shift_y < len(rows):
            to_check = int(rows[shift_y][shift_x])
            if to_check == (value + 1):
                neighbors.append((shift_x, shift_y))

    return neighbors

content = parser.get_content('p')
rows = content.splitlines()

# find all 0 start positions
zero_map = {}
path_map = {}
for y in range(len(rows)):
    for x in range(len(rows[0])):
        if int(rows[y][x]) == 0:
            zero_map[(x, y)] = set()
            path_map[(x, y)] = []

for zero_start in zero_map.keys():
    positions = list([(zero_start[0], zero_start[1])])

    while len(positions) > 0:
        pos = positions.pop()
        value = int(rows[pos[1]][pos[0]])

        if value == 9:
            zero_map[zero_start].add(pos)
            path_map[zero_start].append(pos)
            continue

        neighbors = get_neighbors(pos, value)
        for n in neighbors:
            positions.append(n)

result_1 = 0
result_2 = 0
for key in zero_map.keys():
    result_1 += len(zero_map[key])
    result_2 += len(path_map[key])

print(result_1)
print(result_2)
