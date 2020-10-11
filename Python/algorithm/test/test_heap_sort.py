import time

from src.heap_sort import heap_sort
from src.log import log


def test_heap_sort():
    values = [1, 2, 3, 9, 5, 6, 7, 8, 4]
    start = time.time()
    heap_sort(values)
    log.info('heap sort cost: %s second' % (time.time() - start))
    assert values == [1, 2, 3, 4, 5, 6, 7, 8, 9]
