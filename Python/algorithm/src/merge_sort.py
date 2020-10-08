from src.log import log


def merge(left_values, right_values):
    left_index = 0
    right_index = 0
    result = []
    while left_index < len(left_values) and right_index < len(right_values):
        if left_values[left_index] < right_values[right_index]:
            result.append(left_values[left_index])
            left_index += 1
        else:
            result.append(right_values[right_index])
            right_index += 1
    result += left_values[left_index:]
    result += right_values[right_index:]
    return result


def merge_sort(values):
    if len(values) <= 1:
        return values

    middle = len(values) // 2
    left_values = merge_sort(values[:middle])
    right_values = merge_sort(values[middle:])
    log.info("middle:%s, left:%s, right:%s" % (middle, left_values, right_values))
    return merge(left_values, right_values)
