package src

func HeapSort(values []int) (ret []int, err error) {
	heapSize := len(values)
	for heapSize > 0 {
		sortHeap(values, heapSize)
		temp := values[0]
		values[0] = values[heapSize-1]
		values[heapSize-1] = temp
		heapSize -= 1
	}
	return values, nil
}

func sortHeap(values []int, heapSize int) {
	root := heapSize / 2
	for root >= 1 {
		maxHeap(values, root, heapSize)
		root -= 1
	}
}

func maxHeap(values []int, root int, heapSize int) {
	left := root * 2
	right := root*2 + 1
	maxIndex := root
	if (left <= heapSize) && (values[left-1] > values[maxIndex-1]) {
		maxIndex = left
	}
	if (right <= heapSize) && (values[right-1] > values[maxIndex-1]) {
		maxIndex = right
	}
	if maxIndex != root {
		temp := values[maxIndex-1]
		values[maxIndex-1] = values[root-1]
		values[root-1] = temp
	}
}
