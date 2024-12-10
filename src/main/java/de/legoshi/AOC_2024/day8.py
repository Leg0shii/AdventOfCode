from collections import defaultdict

from src.main.java.de.legoshi.AOC_2024 import parser

# find all nodes
def find_nodes(rows):
    nodes = defaultdict(list)
    for y in range(len(rows)):
        for x in range(len(rows[0])):
            if rows[y][x] != ".":
                nodes[rows[y][x]].append((x, y))

    return nodes

# find all pairs as a list of lists
def find_pairs(rows):
    nodes = find_nodes(rows)
    pairs = []

    for key in nodes.keys():
        nodes_list = nodes[key]
        for n in range(len(nodes_list)):
            i = n + 1
            while i < len(nodes_list):
                pairs.append((nodes_list[n], nodes_list[i]))
                i += 1

    return pairs


content = parser.get_content("p")
rows = content.splitlines()

location_set = set()
for pair in find_pairs(rows):
    pos_1_x, pos_2_x, pos_1_y, pos_2_y = pair[0][0], pair[1][0], pair[0][1], pair[1][1]
    dx, dy = abs(pos_1_x - pos_2_x), abs(pos_1_y - pos_2_y)

    while (0 <= pos_1_x < len(rows[0]) and 0 <= pos_1_y < len(rows)) or (0 <= pos_2_x < len(rows[0]) and 0 <= pos_2_y < len(rows)):
        if pos_1_x < pos_2_x:
            pos_1_x -= dx
            pos_2_x += dx
        else:
            pos_1_x += dx
            pos_2_x -= dx

        if pos_1_y < pos_2_y:
            pos_1_y -= dy
            pos_2_y += dy
        else:
            pos_1_y += dy
            pos_2_y -= dy

        if 0 <= pos_1_x < len(rows[0]) and 0 <= pos_1_y < len(rows): location_set.add((pos_1_x, pos_1_y))
        if 0 <= pos_2_x < len(rows[0]) and 0 <= pos_2_y < len(rows): location_set.add((pos_2_x, pos_2_y))

    location_set.add((pair[0][0], pair[0][1]))
    location_set.add((pair[1][0], pair[1][1]))

print(len(location_set))
