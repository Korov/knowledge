from src.base.list import get_movies
from src.log import log


def test_print_value():
    movies = get_movies()
    log.info('movies size %s, all movies %s' % (len(movies), movies))
    assert True
