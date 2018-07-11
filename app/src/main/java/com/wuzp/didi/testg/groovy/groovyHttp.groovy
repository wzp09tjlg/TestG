//这个文件就是访问网络的实现
//http://berdy.iteye.com/blog/1183062
//可参考上边的实现
//@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7' )
//
//
//@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.6')
//@GrabExclude('asm:*')
//
//import groovyx.net.http.*
//def http = new HTTPBuilder('http://www.baidu.com') //why there can not import httpbuilder
//http.request(GET,TEXT) {
//    //设置url相关信息
//    uri.path='/'
//    uri.query=[a:'1',b:2]
//    //设置请求头信息
//    headers.'User-Agent' = 'Mozill/5.0'
//    //设置成功响应的处理闭包
//    response.success= {resp,reader->
//        println resp.status
//        println resp.statusLine.statusCode
//        println resp.headers.'content-length'
//        System.out << reader
//    }
//    //根据响应状态码分别指定处理闭包
//    response.'404' = { println 'not found' }
//    //未根据响应码指定的失败处理闭包
//    response.failure = { println "Unexpected failure: ${resp.statusLine}" }
//}

//这是访问网络的一种形式，也应该是最基本的形式吧。在groovy中页应该存在其他的modual 来封装对网络的访问吧，只不过现在我没有找到解决方案
def connection = new URL("http://www.baidu.com").openConnection()
connection.setRequestMethod('GET')
connection.doOutput = true


def writer = new OutputStreamWriter(connection.outputStream)
writer.flush()
writer.close()
connection.connect()


def respText = connection.content.text

println respText //是可以得到服务端返回的数据，只不过是需要对得到的数据进行解析。查看xml是什么规则