package src

import (
	"time"
)

func InsertionSort(values []int) (ret []int, err error) {
	start := time.Now()
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
	Logger.Info("insertion sort cost:", time.Since(start))
	Logger.Flush()
	return values, nil
}