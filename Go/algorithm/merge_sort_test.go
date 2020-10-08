package algorithm

import (
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
			It("return value", func() {
				println("Exec Test Case")
				v, _ := MergeSort(input)
				Logger.Info("result:", v)
				Logger.Flush()
				Expect(v).To(Equal(result))
			})
		})
	})
})
