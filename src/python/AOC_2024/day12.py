from src.python import parser


def get_node_in_list(pos, region_list):
    all_regions = []
    for region in region_list:
        if pos in region:
            all_regions.append(region)
        
    if len(all_regions) == 0: return None
    if len(all_regions) == 1: return all_regions[0]

    merged_region = set()
    for region in all_regions:
        for pos in region:
            merged_region.add(pos)
        region_list.remove(region)

    region_list.append(merged_region)
    return merged_region


def get_neighbors(pos):
    n_list = [(pos[0] + 1, pos[1] + 0), 
              (pos[0] - 1, pos[1] + 0), 
              (pos[0] + 0, pos[1] + 1), 
              (pos[0] + 0, pos[1] - 1)]
    
    n_list = [x for x in n_list if 0 <= x[0] < len(rows[0]) and 0 <= x[1] < len(rows)]
    return n_list


def get_actual_neighbors(neighbors, current_pos):
    actual_neighbors = set()
    for neighbor in neighbors:
            if rows[current_pos[1]][current_pos[0]] == rows[neighbor[1]][neighbor[0]]:
                actual_neighbors.add(neighbor)
    return actual_neighbors


def get_individual_sides(region_set):
    s_number = 0
    for current_pos in region_set:
        neighbors = get_neighbors(current_pos)
        actual_neighbors = get_actual_neighbors(neighbors, current_pos)
        s_number += (4 - len(actual_neighbors))

    return s_number


def get_pos_sides(current_pos):
    s_list = []
    
    up = (current_pos[0], current_pos[1] - 1)
    down = (current_pos[0], current_pos[1] + 1)
    left = (current_pos[0] - 1, current_pos[1])
    right = (current_pos[0] + 1, current_pos[1])

    if not (0 <= up[0] < len(rows[0]) and 0 <= up[1] < len(rows)) or rows[up[1]][up[0]] != rows[current_pos[1]][current_pos[0]]:
        s_list.append((current_pos, 'up'))

    if not (0 <= down[0] < len(rows[0]) and 0 <= down[1] < len(rows)) or rows[down[1]][down[0]] != rows[current_pos[1]][current_pos[0]]:
        s_list.append((current_pos, 'down'))

    if not (0 <= left[0] < len(rows[0]) and 0 <= left[1] < len(rows)) or rows[left[1]][left[0]] != rows[current_pos[1]][current_pos[0]]:
        s_list.append((current_pos, 'left'))

    if not (0 <= right[0] < len(rows[0]) and 0 <= right[1] < len(rows)) or rows[right[1]][right[0]] != rows[current_pos[1]][current_pos[0]]:
        s_list.append((current_pos, 'right'))

    return s_list


def get_combined_sides(s):
    side_list = []
    for current_pos in s:
        current_pos_side_list = get_pos_sides(current_pos)

        for current_pos_side in current_pos_side_list:
            side_set = get_node_in_list(current_pos_side, side_list) 

            if side_set == None:
                side_set = set()
                side_set.add(current_pos_side)
                side_list.append(side_set)

            neighbors = get_neighbors(current_pos)
            for neighbor in neighbors:
                neighbor_side_list = get_pos_sides(neighbor)

                for neighbor_side in neighbor_side_list:
                    if neighbor_side[1] == current_pos_side[1] and rows[current_pos_side[0][1]][current_pos_side[0][0]] == rows[neighbor_side[0][1]][neighbor_side[0][0]]:
                        side_set.add(neighbor_side)

    return len(side_list)


content = parser.get_content('12')
rows = content.splitlines()

region_list = []

# for each letter, create curr_pos (x, y): get all neighbors
for y in range(len(rows)):
    for x in range(len(rows[0])):
        current_pos = (x, y)
        region_set = get_node_in_list(current_pos, region_list) 
        if region_set == None:
            region_set = set()
            region_set.add(current_pos)
            region_list.append(region_set)

        # for each neighbor, check in what set it belongs (where curr_pos is in) -> if none: create a new set
        neighbors = get_neighbors(current_pos)
        for neighbor in neighbors:
            if rows[y][x] == rows[neighbor[1]][neighbor[0]]:
                region_set.add(neighbor)

price = 0
discount = 0
for region_set in region_list:
    s_number = get_individual_sides(region_set)
    d_number = get_combined_sides(region_set)
    
    price += (s_number * len(region_set))
    discount += (d_number * len(region_set))

print(price)
print(discount)
