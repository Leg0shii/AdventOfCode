from python import parser

# too high: 7457
# too high: 7277
# too low: 5871

# wrong: 6011
# wrong: 6280
# wrong: 6515
# wrong: 6749
# wrong: 6823
# wrong: 6847
# wrong: 6973
content: list[str] = parser.get_content(day="1").split("\n")[:-1]

current: int = 50
prev: int = 50

zero_counter = 0
zero_counter_all = 0

for c in content:
    num = int(c[1:])

    for n in [1] * num:
        if "L" in c:
            current = (current - n) % 100

        else:
            current = (current + n) % 100

        if current == 0:
            zero_counter_all += 1

    if current == 0:
        zero_counter += 1

print(zero_counter)     # 1150
print(zero_counter_all) # 6738