package src

func MergeSort(values []int) (result []int, err error) {
	if len(values) <= 1 {
		return values, nil
	}
	middle := len(values) >> 1
	leftValues, _ := MergeSort(values[0:middle])
	rightValues, _ := MergeSort(values[middle:])
	return Merge(leftValues, rightValues)
}

func Merge(leftValues []int, rightValues []int) ([]int, error) {
	leftIndex := 0
	rightIndex := 0
	var result []int
	for leftIndex < len(leftValues) && rightIndex < len(rightValues) {
		if leftValues[leftIndex] < rightValues[rightIndex] {
			result = append(result, leftValues[leftIndex])
			leftIndex++
		} else {
			result = append(result, rightValues[rightIndex])
			rightIndex++
		}
	}
	result = append(result, leftValues[leftIndex:]...)
	result = append(result, rightValues[rightIndex:]...)
	return result, nil
}
