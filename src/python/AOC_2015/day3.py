from src.python import parser

# too high 6112
# too low 1587

# too high 4161
def move(pos, curr_pos):
    if pos == '<': 
        curr_pos[0] -= 1
    if pos == '>': 
        curr_pos[0] += 1
    if pos == '^': 
        curr_pos[1] -= 1
    if pos == 'v': 
        curr_pos[1] += 1

    if tuple(curr_pos) in position_map:
        position_map[tuple(curr_pos)] += 1
    else:
        position_map[tuple(curr_pos)] = 1


content = parser.get_content('3', '2015')
position_map = dict()

current_pos = [0, 0]
robo_pos = [0, 0]

position_map[tuple(current_pos)] = 1

i = 0
for pos in content:
    if i % 2 == 0: move(pos, current_pos)
    else : move(pos, robo_pos)
    i += 1

result = 0
for key in position_map.keys():
    if position_map[key] > 0:
        result += 1

print(result)