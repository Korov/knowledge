from src.insertion_sort import insertion_sort
from loguru import logger
import time


def test_insertion_sort():
    values = [1, 2, 3, 9, 5, 6, 7, 8, 4]
    start = time.time()
    insertion_sort(values)
    logger.info('insertion sort cost: %s second' % (time.time() - start))
    assert values == [1, 2, 3, 4, 5, 6, 7, 8, 9]
