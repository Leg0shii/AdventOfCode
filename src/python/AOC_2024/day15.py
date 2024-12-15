from src.python import parser

def get_robot_pos(rows) -> tuple:
    for y in range(len(rows)):
        x = "".join(rows[y]).find('@')
        if x != -1:
            return (x, y)


def get_robot_move(symbol) -> tuple:
    match symbol:
        case '>': 
            return (1, 0)
        case '<': 
            return (-1, 0)
        case 'v': 
            return (0, 1)
        case '^': 
            return (0, -1)
        case _: 
            return (0, 0)
        

def get_next_free_space(rows, r_pos, move) -> int:
    x, y = r_pos
    while rows[y][x] != '#':
        if rows[y][x] == '.':
            return (x, y)
        
        x += move[0]
        y += move[1]
    
    return (-1, -1)


def get_next_area_space(rows, r_pos, y_move):
    field = rows[r_pos[1] + y_move][r_pos[0]]
    if field == '.':
        single_set = set()
        single_set.add(r_pos)
        return single_set
    
    if field == '#':
        return None
    
    # get set of box positions
    boxes_to_check = set()
    boxes_checked = set()
    check_pos = r_pos

    while True:
        if field == '[':
            boxes_to_check.add((check_pos[0], check_pos[1] + y_move))
            boxes_to_check.add((check_pos[0] + 1, check_pos[1] + y_move))
        if field == ']':
            boxes_to_check.add((check_pos[0], check_pos[1] + y_move))
            boxes_to_check.add((check_pos[0] - 1, check_pos[1] + y_move))
        if field == '#':
            return None

        if len(boxes_to_check) == 0:
            break
        
        box = boxes_to_check.pop()
        field = rows[box[1] + y_move][box[0]]
        check_pos = (box[0], box[1])
        boxes_checked.add(box)
        
    boxes_checked.add(r_pos)
    return boxes_checked

def double_width(rows) -> None:
    for y in range(len(rows)):
        end = len(rows[y]) - 1
        while end >= 0:
            elem = rows[y][end]

            if elem == 'O':
                rows[y][end] = ']'
                elem = '['
            if elem == '@':
                rows[y][end] = '.'
                elem = '@'

            rows[y].insert(end, elem)
            end -= 1


def print_grid(rows) -> None:
    s = ''
    for y in range(len(rows)):
        for x in range(len(rows[0])):
            s += rows[y][x]
        s += '\n'
    print(s)


content = parser.get_content('15')
row_string, inputs = content.split('\n\n')
rows = row_string.splitlines()

for y in range(len(rows)):
    rows[y] = list(rows[y])

double_width(rows)

for input in inputs.replace('\n', ''):
    r_x, r_y = get_robot_pos(rows)
    move_offset = get_robot_move(input)

    if move_offset in [(-1, 0), (1, 0)]:
        nfs_x, nfs_y = get_next_free_space(rows, (r_x, r_y), move_offset)
        if nfs_x == -1:
            continue

        while nfs_x != r_x or nfs_y != r_y:
            # flip nfs with move_offset * -1
            temp = rows[nfs_y][nfs_x]
            rows[nfs_y][nfs_x] = rows[nfs_y + (-1 * move_offset[1])][nfs_x + (-1 * move_offset[0])]
            rows[nfs_y + (-1 * move_offset[1])][nfs_x + (-1 * move_offset[0])] = temp

            nfs_x += (-1 * move_offset[0])
            nfs_y += (-1 * move_offset[1])
    else:
        move_set = get_next_area_space(rows, (r_x, r_y), move_offset[1])
        if move_set is None:
            continue

        move_list = list(move_set)
        move_list.sort(key=lambda x: x[1])
        if move_offset[1] == 1:
            move_list.reverse()
        else:
            move_list.sort(key=lambda x: x[1])

        for move_object in move_list:
            x, y = move_object
            temp = rows[y][x]
            rows[y][x] = rows[y + move_offset[1]][x]
            rows[y + move_offset[1]][x] = temp

result = 0
for y in range(len(rows)):
    for x in range(len(rows[0])):
        if rows[y][x] == '[':
            result += 100 * y + x

print(result)
print_grid(rows)










