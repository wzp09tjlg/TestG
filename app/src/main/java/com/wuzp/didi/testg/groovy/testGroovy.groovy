println 'hello groovy'

int num = 1
def name = 100
name = 'wuzp'

println num.class
println name.class

 def clouser = {String name1 -> println "my name is $name1"}
clouser.call("abcd")

//groovy的语言的可以不用规范一个文件些一个类 可以支持一个文件中有多个类 ，语言区分大小写
char c = '1'
byte b = 1
short s = 1
int i = 1
long  l = 1l
float f = 1.0f
double  d = 2.0
boolean bl = true

println c.class
println b.class
println s.class
println i.class
println l.class
println f.class
println d.class
println bl.class

def nums = [1,2,3,4,5] as int[] //没有强制的转换 就是array  进行了强转 就是 数组
println nums.size()
println nums.class

def mapM = ["name":"this is name","age":100,"clazz":"3-1"] //map的形式也跟数组一样，
println mapM.getClass().class


def aRange = 1..<10  //range的 一种数据结构 是结合 数组和list的一种新的数据结构
println aRange.from
println aRange.to

/****/
//groovy的基本数据类型与java的数据类型一致
/****/

//groovy的简单表达是 (groovy的语法是保证每一行是一个语句，如果使用;的法  那么就不限制一行为一个语句)
//for 的使用和java一直
for(i=0;i<10;i++){
 println i
}

def numS = 2
switch (numS){
 case 1:
  println "switch 1";break
 case 2:
  println "switch 2"
  break
 case 3:
  println "switch 3"
  break
 default:
  println "switch default"
  break
}

def ifN = 10
if(ifN > 9){
  println "if a result"
}else{
 println "else b result"
}

def hello={ //一个task 就是一个闭包 就是具有可执行的一个代码块
  println "task simple demo"
}

hello.call()


//这种语言真好，不愧是动态语言，态灵活了。简直是太棒了
//学习语言的基本流程
//1.基本数据类型
//2.简单的表达方式
//3.方法及函数的书写
//4.复杂的数据结构和算法
//5.工程方向的使用
