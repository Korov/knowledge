package src_test

import (
	"testing"

	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
)

func TestAlgorithm(t *testing.T) {
	RegisterFailHandler(Fail)
	RunSpecs(t, "Algorithm Suite")
}

var _ = BeforeSuite(func() {
	println("BeforeSuite")
})
var _ = AfterSuite(func() {
	println("AfterSuite")
})
