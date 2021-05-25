package test

import (
	"net/http"
	"net/http/httptest"
	"testing"
	"trend/router"

	"github.com/go-playground/assert/v2"
)

func TestHelloDefault(t *testing.T) {
	router := router.GetRouter()

	w := httptest.NewRecorder()
	req, _ := http.NewRequest("GET", "/v1", nil)
	router.ServeHTTP(w, req)

	assert.Equal(t, 200, w.Code)
	assert.Equal(t, "{\"message\":\"Hello Gin\"}", w.Body.String())
}
