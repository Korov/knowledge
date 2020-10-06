package algorithm

func InsertionSort(values []int) (ret []int, err error) {
	for i := 1; i < len(values); i++ {
		for j := i - 1; j >= 0; j-- {
			if values[j] > values[j+1] {
				key := values[j]
				values[j] = values[j+1]
				values[j+1] = key
			} else {
				break
			}
		}
	}
	return values, nil
}
