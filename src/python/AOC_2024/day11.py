import python.parser as parser

def add_to_dict(new_num, prev_num, remove=True):
    if new_num in temp_dict:
        temp_dict[new_num] += number_dict[prev_num]
    else:  
        temp_dict[new_num] = number_dict[prev_num]
    

content = parser.get_content('11')
number_list = [int(x) for x in content.replace('\n', '').split(' ')]
number_dict = {num: 1 for num in number_list}
temp_dict = dict(number_dict)

index = 0
while index < 75:
    index += 1
    i = -1

    temp_dict = dict()
    to_do_list = list(number_dict.keys())
    for num in to_do_list:
        if num == 0:
            add_to_dict(1, 0)
            continue

        str_number = str(num)
        if len(str_number) % 2 == 0:
            cut_off = int(len(str_number)/2)
            first_num = int(str_number[:cut_off])
            second_num = int(str_number[cut_off:])

            add_to_dict(first_num, num, False)
            add_to_dict(second_num, num, False)

            continue

        add_to_dict(num * 2024, num)
    
    number_dict = dict(temp_dict)


result = 0
for val in number_dict.values():
    result += val
    
print(result)
