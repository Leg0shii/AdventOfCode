from src.python import parser


def get_sum(robot_positions, from_x, to_x, from_y, to_y) -> int:
    robot_sum = 0
    for x in range(from_x, to_x + 1):
        for y in range(from_y, to_y + 1):
            if (x, y) in robot_positions:
                robot_sum += robot_positions.count((x, y))
    return robot_sum
            

def create_string_robots(robot_positions) -> str:
    p_string = ''
    for y in range(height):
        for x in range(width):
            if (x, y) in robot_positions:
                p_string += 'x'
            else:
                p_string += '.'
        p_string += '\n'
    
    return p_string


content = parser.get_content('p')
rows = content.splitlines()
height = 103
width = 101

f = open('day14_file.txt', 'a')
offset = 40

robots = []
for row in rows:
    pos_x, pos_y = row.split(' ')[0][2:].split(',')
    vel_x, vel_y = row.split(' ')[1][2:].split(',')
    robots.append(((int(pos_x), int(pos_y)),(int(vel_x), int(vel_y))))

for i in range(101):
    seconds = offset + (i * 103)
    print(i) 

    rp = []
    for robot in robots:
        curr_pos = robot[0]
        velocity = robot[1]
        pos = ((curr_pos[0] + seconds * velocity[0]) % width, 
            (curr_pos[1] + seconds * velocity[1]) % height)
        rp.append(pos)

    # result = get_sum(rp, 0, int((width/2)) - 1, 0, int((height/2)) - 1)
    # result *= get_sum(rp, 0, int((width/2)) - 1, int((height/2)) + 1, height)
    # result *= get_sum(rp, int((width/2)) + 1, width, 0, int((height/2)) - 1)
    # result *= get_sum(rp, int((width/2)) + 1, width, int((height/2)) + 1, height)

    f.write(str(i) + '\n' + create_string_robots(rp))

f.close()
# print(result)
