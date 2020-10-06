from src.insertion_sort import insertion_sort


def test_insertion_sort():
    values = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    result = insertion_sort(values)
    assert result == 45

def test_insertion_sort1():
    values = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    result = insertion_sort(values)
    assert result == 50
