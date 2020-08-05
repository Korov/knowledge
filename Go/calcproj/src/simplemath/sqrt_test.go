package simplemath

import "testing"

func TestSqrt1(t *testing.T) {
	v := Sqrt(16)
	if v != 4 {
		t.Errorf("Add(1, 2) failed")
	}
}
