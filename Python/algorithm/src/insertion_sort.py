from loguru import logger


def insertion_sort(values):
    logger.info(values)
    i = 1
    for i in range(len(values)):
        j = i - 1
        while j >= 0:
            if values[j] > values[j + 1]:
                key = values[j + 1]
                values[j + 1] = values[j]
                values[j] = key
                j -= 1
            else:
                break
        i += 1
    logger.info(values)
