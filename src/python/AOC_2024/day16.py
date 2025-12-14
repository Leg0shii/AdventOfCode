import math

from src.python import parser

# too high: 143572
# too low: 142572
# too low: 523
# too low: 591
class PathNode:

    def __init__(self, pos, g_cost, h_cost, parent):
        self.pos = pos
        self.g_cost = g_cost
        self.h_cost = h_cost
        self.f_cost = g_cost + h_cost
        self.parent = parent

        pass

    def __eq__(self, other): 
        if not isinstance(other, PathNode):
            return False
        
        return self.pos == other.pos
    

    def __hash__(self):
        return hash(self.pos)
    

    def __str__(self):
        return f"Position: {self.pos}, G cost: {self.g_cost}, H cost: {self.h_cost}, F cost: {self.f_cost}"
    

def get_letter_pos(rows, letter):
    for y in range(len(rows)):
        pos = rows[y].find(letter)
        if pos != -1:
            return (pos, y)
        

def get_neighbor(rows, node):
    node_list = [
        (node.pos[0] + 1, node.pos[1]),
        (node.pos[0] - 1, node.pos[1]),
        (node.pos[0], node.pos[1] + 1),
        (node.pos[0], node.pos[1] - 1)
    ]

    new_list = []
    for x in node_list:
        if rows[x[1]][x[0]] != '#':
            new_list.append(x)

    return new_list 


def get_turn(new, old):
    if abs(new[0] - old[0]) == 2 or abs(new[1] - old[1]) == 2:
        return 0
    return 1000


def find_path_cost(start_path_node, end):
    node_map = {}
    open = set()
    closed = set()

    open.add(start_path_node)
    node_map[start_path_node.pos] = start_path_node
    shortest = 10000000000

    current_node = None
    while len(open) > 0:
        l = list(open)
        l.sort(key=lambda node: node.f_cost)
        current_node = l[0]
        open.remove(current_node)
        closed.add(current_node)

        if current_node.pos == end:
            return closed, current_node

        for n in get_neighbor(rows_string, current_node):
            if n not in node_map:
                g_cost = current_node.g_cost + 1 + get_turn(n, current_node.parent.pos)
                h_cost = math.dist([n[0], n[1]], [end[0], end[1]])
                neighbor_node = PathNode(n, g_cost, h_cost, current_node)
                node_map[n] = neighbor_node
                open.add(neighbor_node)
            else:
                nn = node_map[n]
                g_cost = current_node.g_cost + 1 + get_turn(n, current_node.parent.pos)
                other_get_turn = get_turn(current_node.pos, nn.parent.pos)
                if g_cost <= nn.g_cost + other_get_turn:
                    nn.g_cost = g_cost
                    nn.f_cost = g_cost + nn.h_cost
                    nn.parent = current_node

                    open.add(nn)
    
    return closed, shortest


content = parser.get_content('16')
rows_string = content.splitlines()
start, end = get_letter_pos(rows_string, 'S'), get_letter_pos(rows_string, 'E')

start_path_node = PathNode(start, 0, 0, PathNode((start[0] - 1, start[1]), 0, 0, None))
closed, node = find_path_cost(start_path_node, end)
best_solution = node.g_cost

print(best_solution)

nl = set()
check = True
if check:
    result = 0
    i = 1
    for c in closed:
        if c.g_cost <= best_solution:
            _, part2 = find_path_cost(c, end)
            if part2.g_cost == best_solution:
                nl.add(c.pos)
                result += 1
            
            print(result, i, len(closed))
            i += 1

    print(result)

s = ''
for y in range(len(rows_string)):
    for x in range(len(rows_string[0])):
        if (x, y) in nl:
            s += 'O'
        else:
            s += rows_string[y][x]
    s += '\n'

f = open('day16_file.txt', 'a')
f.write(s)
f.close()

print(len(nl))

