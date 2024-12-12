from src.python import parser


content = parser.get_content('6', '2015')
grid = dict()

instructions = content.splitlines()
count = 0
for instruction in instructions:
    count += 1
    print(count)
    key = ''
    if 'on' in instruction: 
        instruction = instruction.replace('turn on ', '').replace('through ', '')
        key = 'on'
    elif 'off' in instruction:
        instruction = instruction.replace('turn off ', '').replace('through ', '')
        key = 'off'
    else:
        instruction = instruction.replace('toggle ', '').replace('through ', '')
        key = 'toggle'

    start_pos, end_pos = instruction.split(' ')
    start_x, start_y = [int(x) for x in start_pos.split(',')]
    end_x, end_y = [int(x) for x in end_pos.split(',')]

    temp_x = start_x
    if key == 'on':
        while start_y <= end_y:
            while temp_x <= end_x:
                if (temp_x, start_y) in grid:
                    grid[(temp_x, start_y)] += 1
                else: grid[(temp_x, start_y)] = 1
                temp_x += 1
            start_y += 1
            temp_x = start_x
    elif key == 'off':
        while start_y <= end_y:
            while temp_x <= end_x:
                if (temp_x, start_y) in grid and grid[(temp_x, start_y)] >= 1:
                    grid[(temp_x, start_y)] -= 1
                temp_x += 1
            start_y += 1
            temp_x = start_x
    else:
        while start_y <= end_y:
            while temp_x <= end_x:
                if (temp_x, start_y) in grid:
                    grid[(temp_x, start_y)] += 2
                else: grid[(temp_x, start_y)] = 2
                temp_x += 1
            start_y += 1
            temp_x = start_x

result = 0
for pos in grid.keys():
    result += grid[pos]

print(result)
