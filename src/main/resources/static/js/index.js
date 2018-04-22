$(function () {

    //计算控制台的高度和宽度
    $width = $('.layui-body').width();
    $height = $('.layui-body').height();
    console.log($width + "---" + $height);


    //创建websocket连接
    createWebSocket();
});
//--------------------------------------------------------------
//创建Websocket连接
//--------------------------------------------------------------
function createWebSocket() {

    //获取服务器socket连接地址
    var wsHost = "localhost:8080/demo"
    //连接服务器
    var client = new WebSocket("ws://" + wsHost);
    var term = null;

    //刚刚打开连接
    client.onopen = function (evt) {
        console.log("Connection open ...");

        //创建Term的控制台]
        term = new Terminal({
            cols: Math.floor($width / 7.25),
            rows: Math.floor($height / 17.42),
            screenKeys: false,
            useStyle: true,
            cursorBlink: true,
            convertEol: true
        });
        //打开指定的term
        term.open($("#content_term").empty()[0]);

        term.write('\x1b[31mWelcome to term.js!\x1b[m\r\n');
        term.write('Welcome to term.js!');

        //回车
        term.write('\r\n');
        //term.on方法就是实时监控输入的字段，
        term.on('data', function (data) {
            client.send(data);
        });
    };

    //接收消息的情况
    client.onmessage = function (evt) {
        console.log("Received Message: " + evt.data);
        //写数据到term 控制台
        term.write(evt.data);

        //移动光标
        //term.write("\b");
        //term.write("\b [P");

        //删除
    };

    //关闭连接的情况
    client.onclose = function (evt) {
        console.log("连接关闭");
    };

    //连接失败的情况
    client.onerror = function (event) {
        console.log("连接失败");
    };


    //计算控制台的高度和宽度
    $width = $('.layui-body').width();
    $height = $('.layui-body').height();
}

//将字符串转化为二进制的数据
function strToBinary(str) {
    var result = [];
    var list = str.split("");
    for (var i = 0; i < list.length; i++) {
        if (i != 0) {
            result.push(" ");
        }
        var item = list[i];
        //将字符串转化为二进制数据
        var binaryStr = item.charCodeAt().toString(2);
        result.push(binaryStr);
    }
    return result.join("");
}