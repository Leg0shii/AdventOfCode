from python import parser

# 1
# too low: 1658

# 2
# too high: 2751
def check(grid, x_val, y_val, x_range, y_range, value):
    if (0 <= y_val + y_range[2] < len(grid) and 0 <= x_val + x_range[2] < len(grid[0]) and
            0 <= y_val < len(grid) and 0 <= x_val < len(grid[0])):

        if (grid[y_val][x_val] == value[0] and
                grid[y_val + y_range[0]][x_val + x_range[0]] == value[1] and
                grid[y_val + y_range[1]][x_val + x_range[1]] == value[2] and
                grid[y_val + y_range[2]][x_val + x_range[2]] == value[3]):

            return 1
    return 0


def check_mas(grid, x_val, y_val):
    if 0 < y_val < len(grid) - 1 and 0 < x_val < len(grid[0]) - 1:
        if not grid[y_val][x_val] == 'A':
            return 0

        if ((grid[y_val + 1][x_val + 1] == 'M' and grid[y_val - 1][x_val - 1] == 'S' or
                grid[y_val + 1][x_val + 1] == 'S' and grid[y_val - 1][x_val - 1] == 'M') and
                (grid[y_val + 1][x_val - 1] == 'M' and grid[y_val - 1][x_val + 1] == 'S' or
                grid[y_val + 1][x_val - 1] == 'S' and grid[y_val - 1][x_val + 1] == 'M')):
            return 1

    return 0




content = parser.get_content("4")
rows = content.split("\n")[:-1]

result_1 = 0
result_2 = 0
for y in range(len(rows)):
    for x in range(len(rows[0])):
        result_1 = (result_1 +
                    check(rows, x, y, [1,2,3], [0,0,0], "XMAS") +
                    check(rows, x, y, [1,2,3], [0,0,0], "SAMX") +

                    check(rows, x, y, [0,0,0], [1,2,3], "XMAS") +
                    check(rows, x, y, [0,0,0], [1,2,3], "SAMX") +

                    check(rows, x, y, [1,2,3], [1,2,3], "XMAS") +
                    check(rows, x, y, [1,2,3], [1,2,3], "SAMX") +

                    check(rows, x, y, [-1,-2,-3], [1,2,3], "XMAS") +
                    check(rows, x, y, [-1,-2,-3], [1,2,3], "SAMX"))

        result_2 = result_2 + check_mas(rows, x, y)

print(result_1)
print(result_2)