package router
import (
	"github.com/gin-gonic/gin"
	"trend/config"
)

func GetRouter() *gin.Engine {
	// 获取一个默认的gin框架实例
	router := gin.Default()
	router.Use(gin.Logger())
	router.Use(gin.Recovery())
	router.Use(config.Cors())

	groupV1 := router.Group("/v1")
	{
		// 注册GET请求路由，使用postman，请求/ping路径会返回对应的数据
		groupV1.GET("", HelloDefault)
		// groupV1.GET("/:name", HelloWithName)
		groupV1.GET("/get/series", GetSeries)
		groupV1.GET("/get/name/time", GetNameByTime)
	}

	return router
}