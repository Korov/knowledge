package src_test

import (
	. "algorithm/src"
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
)

var _ = Describe("Array", func() {
	BeforeEach(func() {
		println("BeforeEach-2")
	})
	BeforeEach(func() {
		println("BeforeEach-1")
	})
	JustBeforeEach(func() {
		println("JustBeforeEach-1")
	})
	JustBeforeEach(func() {
		println("JustBeforeEach-2")
	})
	JustAfterEach(func() {
		println("JustAfterEach-1")
	})
	JustAfterEach(func() {
		println("JustAfterEach-2")
	})
	AfterEach(func() {
		println("AfterEach-1")
	})
	AfterEach(func() {
		println("AfterEach-2")
	})
	Describe("ReturnInt", func() {
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
			AfterEach(func() {
				println("AfterEach in Context")
			})
			It("insertion_sort", func() {
				v, _ := InsertionSort(input)
				Logger.Info("result:", v)
				Logger.Flush()
				Expect(v).To(Equal(result))
			})
		})
	})
})
