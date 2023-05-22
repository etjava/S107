# 网络编程

## 网络通讯概述

```
什么是网络？
	网络就是计算机之间进行数据通讯和交流的一种工具 
		这种工具是由大量的网络设备(交换机，路由器，电缆，双绞线等)+计算机组成
	如下图所示 我们可以把每个点看作是一台计算机 多台计算机之间进行数据通讯和交流时就形成网状结构
		网状结构：保证传输的稳定性
```



![image-20230407072219298](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230407072219298.png)

![image-20230407072247017](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230407072247017.png)

### 为什么要使用网络

```
网络的主要目的是用来联通多方然后进行数据通讯的，即 把数据从一方传递给另一方
	我们前面所学的内容都是单机的，不能和其它电脑上的程序进行通讯，为了让我们写的程序在多台电脑之间都能够相互传递数据 就需要借助网络的功能
	所谓的网络编程就是让在不同电脑上的软件能够进行数据传递，即 网络进程之间的通讯
	
目的：
	借助网络实现数据相互传递和通讯
	网络间的数据传输也被称为远程进程间的传输
	本地进程
		指的是同一台电脑中不同进程间的数据传输
	远程进程
		指的是多台电脑之间的数据传输
				
```

### 网络的基本概念

```
协议(protocol)
	广义：
		多方指定的必须要遵循的规则
	网络协议：
		例如 不同国家的人 有的说英语，有的说中文，还有的说法语 那么说同一种语言的人可以交流，不同语言		之间就无法进行交流 为了解决不同种族人之间的语言沟通问题 因此国际联盟在1920年做出规定 英文作为		  世界通用的官方语言
		
		早期的计算机网络 都是由各个厂商自己规定的一套协议，例如IBM，Apple,HP 都有各自的网络协议
		互不兼容，为了使不同计算机厂商生产的计算机能够相互通讯，一边在更大范围内建立计算机网络
		ISO(国际标准化组织)在1978年提出了 开放系统互联网参考模型 
		即著名的OSI/RM 模型(OSI七层网络模型)(面试重点)
		Open System Interconnection/Reference Model 将计算机网络体系结构的通讯协议划分为7层
        自下而上 依次是
        	物理层(Physics Layer)
        		建立、维护、断开物理连接
        	数据链路层(Data Link Layer)
        		建立逻辑连接、进行硬件地址寻址、差错校验等功能（由底层网络定义协议）
        		将比特组合成字节进而组合成帧，用MAC地址访问介质，错误发现但不能纠正
        	网络层(Network Layer)
        		进行逻辑地址寻址，实现不同网络之间的路径选择
        		协议有：ICMP IGMP IP（IPV4 IPV6）
        	传输层(Transport Layer)
        		定义传输数据的协议端口号，以及流控和差错校验
        		协议有：TCP UDP，数据包一旦离开网卡即进入网络传输层
        	会话层(Session Layer)
        		建立、管理、终止会话
        		对应主机进程，指本地主机与远程主机正在进行的会话
        	表示层(Presentation Layer)
        		数据的表示、安全、压缩
        		格式有: JPEG、ASCll、EBCDIC、加密格式等
        	应用层(Application Layer)
        		网络服务与最终用户的一个接口
        		协议有: HTTP FTP TFTP SMTP SNMP DNS TELNET HTTPS POP3 DHCP
        		
	在各计算机厂商实现过程种 将应用层，表示层，会话层整合到了一起 统称为应用层
		
```

![img](https://bkimg.cdn.bcebos.com/pic/b21bb051f8198618b8f0ae2b40ed2e738ad4e6ee?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U5Mg==,g_7,xp_5,yp_5)

### TCP/IP协议

TCP/IP协议指的是一簇协议的共同特点

```
网络协议有TCP/IP、NetBEUI、IPX/SPX

NetBEUI 即 NetBios Enhanced User Interface 是NetBois增强用户接口 特点是短小精悍 通信效率高的广播型协议，曾被需要操作系统采用 也是windows98之前的操作系统的缺省协议

IPX/SPX协议是专用于NerWare网络种的协议，大部分联机游戏都支持IPX/SPX协议

TCP/IP 协议簇毫无疑问是这三大协议种最重要的一个 作为互联网的基础协议 没有它就根本不能上网，任何互联网有关的操作都离不开TCP/IP协议 例如 UDP、HTTP、HTTPS、ARP等 所有与网络相关的都是基于TCP/IP协议这四层网络协议模型
```

网络协议在各计算机厂商实现过程种 将应用层，表示层，会话层整合到了一起 统称为应用层

TCP/IP 四(五)层模型：

![image-20230405225251882](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230405225251882.png)

### 端口 （port）

```
端口可以理解为设备与外界通讯交流的入口，端口可分为虚拟端口和物理端口，其中虚拟端口指的是计算机内部或交换机路由器的内置端口，是不可见的 例如80、443、21、22等端口 物理端口又称为接口，是可见的端口，计算机背板的RJ45网口，交换机路由集线器等RJ45端口 电话使用的RJ11插口都属于物理端口

端口是进程间在进行数据传递时用来唯一确定一个进程的 ，每一个进程都有一个独立的端口 不允许重复 

我们说的网络编程协议种的端口是虚拟端口 是一个不可见的逻辑概念 如同一个房间的门 是出入这个房子的必经之路 
不可见但真实存在
如果一个进程需要收发网络数据，那么就需要端口 在linux系统中 端口可以有65535个（2的16次方-1）操作系统为了统一管理这些不可见的虚拟端口 所以对他们进行了编号 这就是端口的由来

动态端口
	动态端口的范围是从1024到65535 之所以称为动态端口是因为一般不固定分配某种服务，而是动态分配
	动态分配指的是当一个进程或应用程序进行需要网络通讯时  他会向主机申请一个端口，主机在可用的端口中
	随机分配一个供他使用，当这个进程关闭时 同时也释放了所占用的端口号
	
注意：前1024个端口号在编程过程中不建议使用的 因为操作系统底层有固定的进程在使用 另外常用的软件端口也不建议占用 例如1521是oracle数据库 8080是tomcat 等
 我们可以使用netstat -ano 查看系统有进程使用的端口
```

### IP地址

```
IP地址指的是电脑中的唯一编号 通常情况下指的是点对点的通讯
	每一个网络的接入终端在接入互联网的时候 会分配一个IP地址 用来确定唯一身份
	早期IPv4 随着互联网的不断扩大衍生出Ipv6	
```

#### IPV4

```
IPv4
	每一个IP地址是由网络地址和主机地址两大部分组成
	ip地址总位数32个位 占4个字节
	A类 由1个字节的网络地址和3个字节的主机地址组成，网络地址的最高位必须是0
		地址范围 1.0.0.1到126.255.255.254 可用的A类网络有126个 每个网络可容纳1677214个主机
		
	B类 由2个字节的网络地址和2个字节的主机地址组成，网络地址最高位必须是10
		地址范围128.1.0.1到191.255.255.254 可用的B类网络有16384个 每个网络可容纳65524个主机
		
	C类 由3个字节的网络地址和1个字节的主机地址组成 网络地址的最高位必须是110
		地址范围 192.0.1.1到233.255.255.254 C类网络可达2097152个 每个网络可容纳254个主机
		
	D类地址用于多点广播 IP地址第一个字节以1110开始 它是一个专门保留的地址，它并不指向特定的网络 
		目前这一类地址被用在多点广播中多点广播地址用来一次寻址一组计算机
		范围224.0.0.1到239.255.255.254
	E类 以1111开始 为将来使用保留，E类地址保留仅作实验和开发使用
	
	不完全统计 全球服务器多大十几亿台，如果在加上个人电脑以及手机端 数字是非常庞大的 因此IPV4提供的是远	 远不够用的

私有IP地址
	在这么多的网络IP中 国际规定有一部分IP地址是用于我们的局域网络使用，也就是属于私网IP 不能在公网中使	 用
	范围 
	10.0.0.0到10.255.255.255
	172.16.0.0到172.32.255.255.255
	192.168.0.0到192.168.255.255
特殊IP地址
	127.0.0.1到127.255.255.255属于回路测试 例如127.0.0.1可以代表本机IP地址 就可以测试本机中配置	的web服务器
```

![image-20230405235042074](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230405235042074.png)

#### IPV6

![image-20230406001324924](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230406001324924.png)

```
IPv6 是Internet Protocol Version 6的缩写 其中Internet Protocol译为互联网协议
由于IPv4最大的问题在于网络地址资源优先，严重制约了互联网的应用和发展，IPv6的使用不进能解决网络地址资源数量的问题 而且也解决了多种接入设备炼乳互联网的障碍
IPv6是IETF(Internet Engineering Task Force 互联网工程任务组)设计的 用来替代现行版本IP协议(IPv4)的下一代IP协议，号称可以为全世界每一粒沙子编上一个网址
```



### 子网掩码

```
子网掩码是用来指定IP地址中哪些位是网络号 哪些位是主机号
子网掩码只有一个作用 就是将某个IP地址划分成网络地址和主机地址两部分
子网掩码的设定必须遵循一定的规则
	与IP地址相同 子网掩码的长度也是32个位
	左边是网络位 用二进制1表示
	右边是主机位 用二进制0表示
	假设IP地址位192.168.1.1 其子网掩码为255.255.255.0
	其中1有24个 代表与此相应的IP地址左边24位是网络号
	0有8个 代表与此对应的IP地址右边8位是主机号
	这样子网掩码就确定了一个IP地址的32个位二进制数字中哪些是网络号 哪些是主机号
	这对于TCP/IP协议来说是非常重要的 只有通过子网掩码才能明确一台计算机在网络中的位置
	
```

### Socket对象

```
Socket是网络中进程间通讯的一种方式，它封装了我们网络传输中的细节(例如 数据转换，编码，加解密，IP地址，端口 等) 提供了大量好用的方法 可以让我们快速的进行网络间数据传输

它与其它进程间通讯的一个主要不同是 它能实现不同计算机间的进程通讯
我们网络上的大多数服务都是基于socket来完成通信的 例如 浏览网页，收发Email，各种聊天工具等

网络中进程通信首要解决的问题是如何唯一标识一个进程，否则通信将无从谈起，在本地可以通过进程PID来标注一个进程 但网络中这是行不通的，其实TCP/IP协议簇已经帮我们解决了这个问题，网络层的IP地址可以唯一标识网络中的主机，而传输层的协议+端口可以唯一标识进程(主机中的应用程序)
这样利用IP地址+协议+端口的方式就可以标识网络的进程了 这就是网络编程 又称为Socket编程
```

### TCP/IP协议簇（cu）

TCP/IP协议簇下有两大协议 UDP和TCP

UDP: 代表的是不可靠不安全的无连接的网络协议  速度快

​			UDP不考虑网络传输过程中的数据安全问题 因此速度会相对快一些

​				例如 大型网游通常采用UDP模式 即便有几台机器出现网络延迟 但不会影响其它玩家

TCP: 代表可靠安全的有连接的网络协议  相对比较慢

​			TCP需要保证网络传输过程中的数据安全 因此相对UDP会慢一些

​			通常用于商务办公或对数据要求严格的一些应用	例如 传输压缩文件等 如果出现丢包情况 则会导致整个文件失效 无法打开

## UDP网络编程

java 中所有跟网络相关的对象都是存放在java.net包下

UDP(User Datagram protocol 用户数据报文协议)

UDP网络协议图



![image-20230407074712400](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230407074712400.png)





所有的网络通信相关类 都在java.net包下

### UDP 发送数据

```
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

// 发送数据
public class TestUDP1 {

    /**
     * UDP 用户数据报文
     * 发送数据的流程
     * 1. 创建UDP对象 DatagramSocket 该对象是用来发送数据的
     *      可以在创建对象时直接指定端口 如果不指定默认会有一个随机的端口
     *
     *      也可以在创建后通过bind方法进行指定 
     		这种方式指定必须在创建DatagramSocket时传入null 否则会抛出端口占用异常
     *
     * 2. 创建需要发送的数据报文
     * 3. 创建报文对象 DatagramPacket 该对象是用来封装数据的
     *      DatagramPacket参数解释
     *      DatagramPacket(byte buf[], int offset, int length, SocketAddress address)
     *             参数1 字节数组 里面存放需要发送的报文
     *             参数2 开始读取的位置
     *             参数3 读取的长度
     *             参数4 发送给哪个进程 通过IP+端口形式指定一个进程
     * 4. 发送数据
     *
     */
    public static void main(String[] args) throws IOException {
        // 创建UDP对象 这里的端口表示监听的端口 可以用来接收其他进行的数据 如果不写 只能发送数据
        DatagramSocket ds = new DatagramSocket(8888);
        String msg = "测试1";
        byte[] bytes = msg.getBytes("UTF-8");
        DatagramPacket packet = new DatagramPacket(bytes,0,bytes.length,
                new InetSocketAddress("127.0.0.1",7777));// 指定接收方的IP和端口
        ds.send(packet);
        ds.close();// 使用完成一定要关闭资源
        System.out.println("发送完成");
    }
}

```

测试 

该工具临时充当服务端

![image-20230409111016257](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230409111016257.png)

### UDP 接收数据

```
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class TestUDP2 {

    /**
     * 接收数据 使用receive方法
     * 接收数据流程
     * 1 创建DatagramSocket 该对象是用来接收数据的
     * 2 定义缓冲区 将接收到的数据进行缓存的
     * 3 创建DatagramPacket对象 该对象是用来接收报文等信息的
     * 4 使用receive方法进行接收 需要传入一个报文对象
     * 5 输出接收到的结果即可
     */
    public static void main(String[] args) throws IOException {
        // 1. 创建UDP对象
        DatagramSocket ds = new DatagramSocket(7777);
        // 2 定义缓冲区 用来存放接收到的数据
        byte[] b = new byte[1024];
        // 3 创建报文对象 用来接收数据  不传IP+端口 就是接收数据 传了就是发送数据
        DatagramPacket p = new DatagramPacket(b,0,b.length);
        // 4. 接收数据
        ds.receive(p);
        byte[] data = p.getData();
        String s = new String(data,0,data.length);
        System.out.println("接收到的数据 "+s);
        ds.close();
    }
}
```

### 循环发送数据

```
package com.etjava.upd;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class TestUDP3 {
    /**
     * 控制台输入 循环发送数据
     * 流程
     * 1. 创建UDP对象 用来发送数
     * 2. 创建Scanner对象 用来接收控制台输入的数据
     * 3. 循环接收并发送 使用while
     * 4. 接收控制台的数据
     * 5. 将接收到的数据转成字节数组并设置编码集
     * 6. 定义报文对象 用来封装数据报文
     * 7. 发送数据
     */
    public static void main(String[] args) throws IOException {
        // 创建UDP对象 用来发送数据
        DatagramSocket ds = new DatagramSocket();
       // 控制台输入
        Scanner sc = new Scanner(System.in);
        while(true){
            String ms = sc.nextLine();
            // 转成字节数组 并指定编码集
            byte[] bytes = ms.getBytes("UTF-8");
            // 创建报文对象 用来封装数据报文
            DatagramPacket packet = new DatagramPacket(bytes,0,bytes.length,
                    new InetSocketAddress("127.0.0.1",7777));
            // 发送数据
            ds.send(packet);
        }
    }
}

```

测试

![image-20230409165733563](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230409165733563.png)

### 循环接收数据

```
package com.etjava.upd;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class TestUDP3 {
    /**
     * 控制台输入 循环发送数据
     * 流程
     * 1. 创建UDP对象 用来发送数
     * 2. 创建Scanner对象 用来接收控制台输入的数据
     * 3. 循环接收并发送 使用while
     * 4. 接收控制台的数据
     * 5. 将接收到的数据转成字节数组并设置编码集
     * 6. 定义报文对象 用来封装数据报文
     * 7. 发送数据
     */
    public static void main(String[] args) throws IOException {
        // 创建UDP对象 用来发送数据
        DatagramSocket ds = new DatagramSocket();
       // 控制台输入
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("输入需要发送的数据");
            String ms = sc.nextLine();
            // 转成字节数组 并指定编码集
            byte[] bytes = ms.getBytes("UTF-8");
            // 创建报文对象 用来封装数据报文
            DatagramPacket packet = new DatagramPacket(bytes,0,bytes.length,
                    new InetSocketAddress("127.0.0.1",7777));
            // 发送数据
            ds.send(packet);
        }
    }
}

```

测试

![image-20230409170832258](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230409170832258.png)

### 作业

实现一个简单的收据接收和发送

## TCP网络编程

```
TCP (Transmission Control Protpcol) 传输控制协议
	他是可靠的 安全的 有连接的网络协议(长连接)
三种网络连接模型
单工
	我们的数据只能从一端传到另外一端 例如发送邮件
半双工
	允许A传送到B 也允许B传送到A 但不允许AB同时发送数据
		例如 对讲机 在同一时刻只允许一个人讲话 
全双工
	双向通道 例如打电话 可以两端同时进行数据传输

TCP建立连接 需要经过三次握手机制
	
	两个进程在通信之前 会向被通讯的一方发送消息确定对方可以接收后才会建立连接开始数据传输
	例如 A B两个进程 A进程在向B进程传输数据之前 会先给B进程发送一个消息来确认B进程是否正常，当接收到B		进程回复的消息后会再次确认 然后才会建立连接 这种机制称为三次握手机制

TCP终止连接需要经过四次挥手机制
	
TCP是点对点的传输 在传输之前需要通过三次握手机制 完成两个端之间的连接 在进行数据传输
	点对点指的是 客户端对服务器端
	服务器端使用ServerSocket对象
	客户端使用Socket对象
	
```

UDP通信模型

![image-20230409213311382](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230409213311382.png)



![image-20230409185640109](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230409185640109.png)

### Server端 发送消息

由于TCP通信模式是点对点的 因此需要两个端进行通信

通常我们需要先定义server（服务）端

```
package com.qn.tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP Server端
 * @author Andy Lu
 *
 * 2023年4月10日下午9:32:16
 */
public class TestServer {

	public static void main(String[] args) throws IOException {
		// 创建Socket对象
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(8888);
		System.out.println("等待客户端连接");
		while(true) {
			// 等待客户端连接
			Socket socket = server.accept();
			byte[] address = socket.getInetAddress().getAddress();
			String addr = new String(address,0,address.length);
			 System.out.println("本地IP地址"+socket.getLocalAddress());
	         System.out.println("本地监听的端口 "+socket.getLocalPort());
	         System.out.println("客户端主机名称"+socket.getInetAddress().getHostAddress());
	         System.out.println("客户端的端口："+socket.getPort());
			int port = socket.getPort();
			System.out.println(addr+":"+port+" 连接上来了");
			// 发送消息给客户端
			String s = "hello "+socket.getInetAddress().getHostName();
			// 写数据到客户端
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			out.println(s);
		}
	}
}
```

测试

![image-20230410215821042](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230410215821042.png)

### Client端 连接服务端并接收消息

```
package com.qn.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 连接客户端
 * @author Andy Lu
 *
 * 2023年4月10日下午10:01:30
 */
public class TestClient {

	public static void main(String[] args) throws IOException {
		// 创建客户端Socket 如果不指定IP地址 则无法接收到服务端发送的消息
		@SuppressWarnings("resource")
		Socket socket = new Socket();
		// 连接客户端 必须指定服务端的IP和端口
		socket.connect(new InetSocketAddress("127.0.0.1",8888));
		System.out.println("连接到服务端");
		// 接收服务端数据
		InputStream is = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String s = null;
		while((s=br.readLine())!=null) {
			System.out.println(s);
		}
	}
}

```

### Server端 循环发送消息

```
package com.qn.tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Server端循环发送消息
 * @author Andy Lu
 *
 * 2023年4月10日下午10:10:56
 */
public class TestServer2 {

	public static void main(String[] args) {
		// 创建Server端Socket对象
		ServerSocket server = null;
		try {
			server = new ServerSocket(8888);
			// 等待客户端连接
			System.out.println("等待客户端连接");
			while(true) {
				// 等待连接 无连接会一直阻塞
				Socket socket = server.accept();
				byte[] address = socket.getInetAddress().getAddress();
				String addr = new String(address,0,address.length);
				int port = socket.getPort();
				System.out.println(addr+":"+port+" 连接上来了");
				while(true) {
					// 发送消息给客户端
					PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
					Scanner sc = new Scanner(System.in);
					String s = sc.nextLine();
					if(s.equals("exit"))
						break;// 这里退出后会回到等待连接处 不重新连接无法再次发送消息
					out.println(s);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(server!=null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

```

### Server端 循环接收消息

```
```

### Client端 循环发送消息

```

```

### Client端 循环接收消息

```

```

### Client端

```
package com.etjava.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestClientSocket {
    // TCP 客户端
    public static void main(String[] args) throws IOException {
        // 创建客户端 Socket 对象 可以指定IP地址 也可以不指定
        Socket socket = new Socket();
        // 可以绑定自己的端口 用来接收服务器发送过来的消息
        //socket.bind(new InetSocketAddress(2222));
        // 连接服务器
        socket.connect(new InetSocketAddress("127.0.0.1",8888));

        System.out.println("服务器连接成功");

        // 接收服务器发送过来的数据
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String s = null;
        while((s = br.readLine())!=null){
            System.out.println("服务器端传递过来的数据："+s);
        }
    }
}

```

![image-20230410000434420](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230410000434420.png)

![image-20230410000452536](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230410000452536.png)

### Server端收发消息

接收和发送消息使用socket对象

发送消息 使用OutputStream

接收消息使用InputStream

```
package com.etjava.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// 收发消息
public class Server {

    public static void main(String[] args) {
        try {
            // 创建socket服务端
            ServerSocket server = new ServerSocket(8888);
            System.out.println("等待客户端连接...");
            // 一直等待接收客户端连接
            while (true){
                // 等待客户端连接
                Socket socket = server.accept();
                System.out.println("客户端："+socket.getInetAddress().getHostAddress()+
                        ":"+socket.getPort()+" 连接上来了");
                // 发送消息给客户端
                String s = "hello ";
                // 写数据出去 IO流不能关闭 因此必须手动刷新 可以直接传递true表示手动刷新
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                out.println(s);// 直接写字符串出去

                // 接收客户端发送的消息
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("客户端："+socket.getInetAddress().getHostAddress()+
                        ":"+s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

```



### Client端收发消息 

接收和发送消息使用socket对象

发送消息 使用OutputStream

接收消息使用InputStream

```
package com.etjava.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

// 客户端收发消息
public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket();
            // 连接服务器端
            socket.connect(new InetSocketAddress("127.0.0.1",8888));
            System.out.println("连接到服务器");
            // 接收服务器发送的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 如果能确定服务器只发送一行数据 可以这样写 否则需要使用while循环接收
            //String s = br.readLine();
            //System.out.println("接收到服务器端发送的数据 "+s);
            // 如果不能确定服务端会发送多少数据 需要使用while循环处理
            String s = null;
            while ((s=br.readLine())!=null){
                System.out.println("接收到服务器端发送的数据 "+s);
                // 发送回执给服务端
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(s+" 1");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

```

### Client多线程接收消息



## 总结

```
网络编程
可以使用编程语言提供的Socket对象进行异地间进程间的数据通信
	首先需要指定数据的传输类型(传输层)
		UDP
		TCP
	UDP网络编程
		UDP是用户数据报文协议 简称数据报协议
			特点：不安全，不可靠，无连接(面对面)，速度快
			不安全	指的是UDP有一种广播的协议 我们接收到的数据无法确定来源
			不可靠 因为我们无法确定数据来源 另外在传输过程中如果出现丢包现象也不会有任何提示 
				因此是不可靠的
			无连接	指的是UDP的传输是面向面(广播) 而非点对点(电话)
            速度快 因为不考虑数据的安全性 也不考虑数据是否被接收 因此速度会相对快
		UDP协议有两个重要的对象
			DatagramSocket 创建UDP协议的Socket对象 并执行发送或接收数据
				发送数据 send方法
				接收数据 receive方法
			DatagramPacket	数据报对象 用来封装或接收报文

	TCP网络编程
		特点：安全，可靠，连接(点对点)
		
			
UDP和TCP的区别
	首先 UDP和TCP都是TCP/IP网络协议中的重要组件
	UDP采用的是面对面的模式进行数据传输 这样做的优点是速度快 但缺点也会比较明显 不安全，不可靠，无连接
	TCP采用的是点对点的模式进行数据传输 这样做的优点是能够确保数据的安全性 
		缺点 TCP是长连接 不使用时需要手动关闭
```



