from loguru import logger

def insertion_sort(values):
    result = 0
    for value in values:
        result += value

    return result


if __name__ == "__main__":
    values = [1,2,3,45,6,75,6,4,7,9]
    insertion_sort(values)