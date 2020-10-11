def max_heap(values, root, heap_size):
    left = root * 2
    right = root * 2 + 1
    max_index = root
    if (left <= heap_size) and (values[left - 1] > values[max_index - 1]):
        max_index = left
    if (right <= heap_size) and (values[right - 1] > values[max_index - 1]):
        max_index = right

    if max_index != root:
        temp = values[root - 1]
        values[root - 1] = values[max_index - 1]
        values[max_index - 1] = temp


def sort_heap(values, heap_size):
    root = heap_size // 2
    while root >= 1:
        max_heap(values, root, heap_size)
        root -= 1


def heap_sort(values):
    heap_size = len(values)
    while heap_size > 0:
        sort_heap(values, heap_size)
        temp = values[0]
        values[0] = values[heap_size - 1]
        values[heap_size - 1] = temp
        heap_size -= 1
