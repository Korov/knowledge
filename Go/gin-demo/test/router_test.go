package test

import (
	"gin-demo/routers"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/go-playground/assert/v2"
)

func TestHelloDefault(t *testing.T) {
	router := routers.GetRouter()

	w := httptest.NewRecorder()
	req, _ := http.NewRequest("GET", "/v1", nil)
	router.ServeHTTP(w, req)

	assert.Equal(t, 200, w.Code)
}
