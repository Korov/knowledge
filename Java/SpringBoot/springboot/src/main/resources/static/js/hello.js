/**
 *
 */

function testFunction() {
    alert("test alert!");

}

function registerFunction() {
    $.ajax({
        url: "http://localhost:8888/test/123?desc=hhh",
        type: 'POST',
        data: {},
        contentType: false,
        processData: false,
        cache: false,
        success: function (resultVo) {
            if (resultVo.code == 123) {
                alert("提交成功");
            } else {
                alert("提交失败");
            }
        }
    });
}


function registerFunction1() {
    $.ajax({
        url: "http://localhost:8888/test/all/13?desc=描述",
        type: 'POST',
        data: JSON.stringify({"id": 123, "userName": "娃哈哈"}),
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        processData: false,
        cache: false,
        success: function (resultVo) {
            if (resultVo.code == 123) {
                alert("提交成功");
            } else {
                alert("提交失败");
            }
        }
    });
}
