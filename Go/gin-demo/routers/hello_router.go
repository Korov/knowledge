package routers

import (
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
)

func HelloDefault(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"message": "Hello Gin",
	})
}

func HelloWithName(c *gin.Context) {
	name := c.Param("name")
	c.JSON(http.StatusOK, gin.H{
		"message": fmt.Sprintf("Hello %s", name),
	})
}
