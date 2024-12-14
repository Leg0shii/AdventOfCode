from src.python import parser

# too high: 73800218823015
content = parser.get_content('13')
machine_string = content.split('\n\n')

machines = []
result = 0
offset = 10000000000000
for machine in machine_string:
    temp = machine.splitlines()
    button_a = [int(x) for x in temp[0].replace('Button A: X+', '').replace(' Y+', '').split(',')]
    button_b = [int(x) for x in temp[1].replace('Button B: X+', '').replace(' Y+', '').split(',')]
    prize = [int(x) + offset for x in temp[2].replace('Prize: X=', '').replace(' Y=', '').split(',')]

    x_result = (prize[0] * button_b[1] - prize[1] * button_b[0]) / (button_a[0] * button_b[1] - button_a[1] * button_b[0])
    y_result = (prize[1] * button_a[0] - prize[0] * button_a[1]) / (button_a[0] * button_b[1] - button_a[1] * button_b[0])

    if str(x_result)[-1:] == '0' and str(y_result)[-1:] == '0':
        result += 3 * x_result + y_result


print(int(result))
