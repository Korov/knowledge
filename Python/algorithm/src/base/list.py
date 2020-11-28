"""
关于Python中列表的使用介绍
"""

from src.log import log


def get_movies():
    movie = ["The Holy Grail", "The Life of Brian", "The Meaning of Life"]
    log.info('get index 1 %s' % (movie[1]))
    for value in movie:
        log.info('get value %s' % (value))

    # 扩展数据
    movie.extend(["value1", "value2"])
    log.info('all movie %s' % (movie))
    # pop从顶部弹出
    log.info('pop %s %s' % (movie.pop(), movie.pop()))

    movie.insert(0, "insert0")
    log.info('all movie %s' % (movie))
    movie.remove("insert0")
    return movie
