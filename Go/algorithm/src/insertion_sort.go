package src

func InsertionSort(values []int) (ret []int, err error) {
	for i := 1; i < len(values); i++ {
		print(values[i])
	}
	return values, nil
}