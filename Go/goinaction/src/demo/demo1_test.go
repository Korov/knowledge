package demo

import "testing"

func Test_demo1(t *testing.T) {
	type args struct {
		value   string
		another string
	}
	tests := []struct {
		name string
		args args
	}{
		{name: "args1", args: args{value: "demo1", another: "de"}},
		{name: "args2", args: args{value: "demo2", another: "de"}},
		{name: "args3", args: args{value: "demo3", another: "de"}},
		{name: "args4", args: args{value: "demo4", another: "de"}},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
		})
	}
}