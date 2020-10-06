package algorithm

import (
	"reflect"
	"testing"
)

func TestInsertionSort(t *testing.T) {
	type args struct {
		values []int
	}
	var tests = []struct {
		name    string
		args    args
		wantRet []int
		wantErr bool
	}{
		{name: "test1", args: args{values: []int{1, 2, 3, 6, 5, 4}}, wantRet: []int{1, 2, 3, 4, 5, 6}, wantErr: false},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			gotRet, err := InsertionSort(tt.args.values)
			t.Logf("input:%v, result:%v", tt.args.values, gotRet)
			if (err != nil) != tt.wantErr {
				t.Errorf("InsertionSort() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if !reflect.DeepEqual(tt.args.values, tt.wantRet) {
				t.Errorf("InsertionSort() gotRet = %v, want %v", gotRet, tt.wantRet)
			}
		})
	}
}
