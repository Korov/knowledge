import time

from src.log import log
from src.merge_sort import merge_sort


def test_merge():
    values = [1, 2, 3, 9, 5, 6, 7, 8, 4]
    start = time.time()
    result = merge_sort(values)
    log.info('merge sort cost: %s second' % (time.time() - start))
    assert result == [1, 2, 3, 4, 5, 6, 7, 8, 9]
