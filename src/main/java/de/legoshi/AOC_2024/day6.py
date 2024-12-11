from main.java.de.legoshi import parser

def next_step(x_, y_, status_switch_, direction_hit_map_):
    x_ += status_list[status_switch_ % 4][0]
    y_ += status_list[status_switch_ % 4][1]
    is_loop = False

    if rows[y_][x_] == "#":
        if (x_, y_) in direction_hit_map_ and direction_hit_map_[(x_, y_)][0] == status_list[status_switch_ % 4]:
            if direction_hit_map_[(x_, y_)][1] <= 25:
                direction_hit_map_[(x_, y_)][1] += 1
            else:
                is_loop = True
        else:
            direction_hit_map_[(x_, y_)] = [status_list[status_switch_ % 4], 0]
        x_ -= status_list[status_switch_ % 4][0]
        y_ -= status_list[status_switch_ % 4][1]
        status_switch_ += 1

    return x_, y_, status_switch_, direction_hit_map_, is_loop


content = parser.get_content("6")
rows = content.split("\n")[:-1]

# too high: 5161
start_pos = ()
for y in range(len(rows)):
    if "^" in rows[y]:
        start_pos = (rows[y].find('^'), y)


x, y = start_pos
status_list = [(0, -1), (1, 0), (0, 1), (-1, 0)]
status_switch = 0
position_set = {start_pos}
while 0 <= x <= (len(rows[0]) - 2) and 0 <= y <= (len(rows) - 2):
    position_status = next_step(x, y, status_switch, {})
    x, y = position_status[0], position_status[1]
    status_switch = position_status[2]
    position_set.add((x, y))

# check for loop: loop always if # is hit with the same direction -> safe direction hit for each #
# too high: 1574
# too high: 1573
prev_block = (0, 0)
result_2 = 0
prev_block_type = '.'
for y_block in range(len(rows)):
    print(y_block)
    for x_block in range(len(rows[0])):
        if x_block == start_pos[0] and y_block == start_pos[1]:
            continue

        if rows[y_block][x_block] == '#':
            continue

        rows[prev_block[1]] = rows[prev_block[1]][:prev_block[0]] + str(prev_block_type) + rows[prev_block[1]][prev_block[0] + 1:]
        prev_block_type = rows[y_block][x_block]
        rows[y_block] = rows[y_block][:x_block] + '#' + rows[y_block][x_block + 1:]
        x, y = start_pos
        prev_block = (x_block, y_block)
        direction_hit_map = {}
        status_switch = 0

        while 0 < x <= (len(rows[0]) - 2) and 0 < y <= (len(rows) - 2):
            position_status = next_step(x, y, status_switch, direction_hit_map)
            x, y = position_status[0], position_status[1]
            status_switch = position_status[2]
            direction_hit_map = position_status[3]
            if position_status[4]:
                result_2 += 1
                break


print("SOLUTION 1: ", len(position_set))
print("SOLUTION 2: ", result_2)