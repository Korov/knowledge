package src

import (
	"reflect"
	"testing"
)

func TestInsertionSort(t *testing.T) {
	type args struct {
		values []int
	}
	tests := []struct {
		name    string
		args    args
		wantRet []int
		wantErr bool
	}{
		// TODO: Add test cases.
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			gotRet, err := InsertionSort(tt.args.values)
			if (err != nil) != tt.wantErr {
				t.Errorf("InsertionSort() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if !reflect.DeepEqual(gotRet, tt.wantRet) {
				t.Errorf("InsertionSort() gotRet = %v, want %v", gotRet, tt.wantRet)
			}
		})
	}
}
