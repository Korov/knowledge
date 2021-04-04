package src_test

import (
	. "algorithm/src"
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
)

var _ = Describe("Merge Sort Test", func() {
	Describe("ReturnResult", func() {
		Context("default", func() {
			var (
				input  []int
				result []int
			)
			BeforeEach(func() {
				println("BeforeEach in Context")
				input = []int{1, 3, 2, 6, 5, 4}
				result = []int{1, 2, 3, 4, 5, 6}
			})
			It("merge_sort", func() {
				v, _ := MergeSort(input)
				var logger = GetLogger()
				logger.Info("result:")
				Expect(v).To(Equal(result))
			})
		})
	})
})
