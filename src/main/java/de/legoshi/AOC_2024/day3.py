from src.main.java.de.legoshi.AOC_2024 import parser

content = str(parser.get_content("3"))
result_1 = 0
state = True

for i in range(len(content)):
    if content[i] == 'd' and content[i+1] == 'o' and content[i+2] == '(' and content[i+3] == ')':
        state = True

    if (content[i] == 'd' and content[i+1] == 'o' and content[i+2] == 'n' and content[i+3] == '\'' and content[i+4] == 't'
            and content[i+5] == '(' and content[i+6] == ')'):
        state = False

    if content[i] == 'm' and content[i+1] == 'u' and content[i+2] == 'l' and content[i+3] == '(' and state:
        extra = i
        while not content[extra] == ')':
            extra = extra + 1

        sub_content = content[i+4:extra].split(",")
        try:
            result_1 = result_1 + int(sub_content[0]) * int(sub_content[1])
        except:
            print()

print(result_1)