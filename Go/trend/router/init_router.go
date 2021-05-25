package router
import (
	"github.com/gin-gonic/gin"
)

func GetRouter() *gin.Engine {
	// 获取一个默认的gin框架实例
	r := gin.Default()

	groupV1 := r.Group("/v1")
	{
		// 注册GET请求路由，使用postman，请求/ping路径会返回对应的数据
		groupV1.GET("", HelloDefault)
		groupV1.GET("/:name", HelloWithName)
	}

	return r
}