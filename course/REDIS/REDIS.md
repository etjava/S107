# REDIS

## 概述

```
Redis（Remote Dictionary Server )，即远程字典服务，是一个开源的使用C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API
Redis是NOSQL的一种实现，是基于内存的非关系型数据库
Redis作者：Antirez
github:https://github.com/Antirez
个人博客：http://antirez.com/latest/0

redis是什么？
	redis是基于内存的KV键值对内存数据库
为什么要使用redis?
	1. redis是分布式缓存 可以分摊mysql等关系型数据库的压力
	2. redis是基于内存存储 同时支持异步的持久化内存中的数据到磁盘上 同时不影响继续服务
	3、redis的高可用架构搭配
		单机、主从、哨兵、集群
	4、支持分布式锁
	5、支持消息队列
	6、缓存穿透，击穿，雪崩
	
redis与RDBMS(以mysql为例)对比
	1、redis是KV数据库（NOSQL）的一种 mysql是关系型数据库
	2、redis的操作主要在内存，mysql主要在磁盘
	3、redis在某些操作方面明显优于mysql 例如 计数器，秒杀，点赞等
	4、redis通常用于一些特定场景，而有些场景需要与mysql配合使用
	注意：两者并不是相互替代的关系 而是共同协作关系

redis优势
	redis读的速度每秒可达到十万以上 写的速度每秒可达八万余次
	redis数据类型丰富 不仅仅支持简单的KV类型数据 同时还提供了list,set,zset,hash等数据结构的存储
	redis支持数据的持久化 可以将内存中的数据保存到磁盘中 重启的时候可以再次加载进行使用
	redis支持数据备份 即master-slave模式的数据备份

redis相关资料
官方地址
redis.io
redis.cn
官方文档
https://redis.io/docs/
命令参考手册
http://doc.redisfans.com/
redis历史版本
https://download.redis.io/release

redis版本的命名规则
版本号第二位如果是偶数则为稳定版，基数为开发版
例如 3.6，3.8， 7.0

redis7新增特性有哪些？
	redis7.0版本与之前的各个版本基本保持一致 主要是自身底层在性能和资源利用率方面进行了优化和提高
	1、多AOF文件的支持
		7.0版本一个比较大的改变就是AOF文件由一个变成了多个，主要分为两种类型 基本文件，增量文件
	2、config命令增强
		对于config set和get命令 支持在一次调用过程中传递多个配置参数 
			例如config set maxmemory 10000001 maxmemory-clients 50% port 6379
	3、限制客户端内存使用
		一旦redis连接较多 在加上每个连接内存占用都比较大的时候 redis总链接内存占用可能会达到最大内存
		的上限 此时可以通过增加匀速限制所有客户端的总内存使用量配置项来控制客户端的内存使用
	4、listpack紧凑列表调整
		listpack是用来替代ziplist的新的数据结构，在7.0版本中已经没有ziplist的配置了
	5、安全访问增强
		在redis配置文件中 protected-mode 默认为yes 只有当你希望你的客户端在没有授权的情况下
		可以连接redis server的时候 可以将其改为no
	6、RDB保存时间调整
		持久化文件RDB的保存时间做了调整
	7、redis functions 函数
		redis functions是一种新的通过服务端脚本扩展redis的方式，函数与数据本身一起存储
	8、命令的新增和改变
		zset增加zmpop,bzmpop,zintercard等命令
		set 增加sintercard等命令
		list增加lmpop,blmpop等命令 从提供的键名列表中的第一个非空列表键中弹出一个或多个元素
	9、性能资源利用率，安全等改进
		自身底层部分优化改动 redis在许多方面进行了重构和改造 
		例如 碎片的主动整理等
		
```



## 安装

### redis官网下载安装包

![image-20230509211435605](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230509211435605.png)

```
下载redis安装包
wget https://github.com/redis/redis/archive/7.0.11.tar.gz

注意：需要使用64位版本的操作系统
查看linux版本
getconf LONG_BIT
```

![image-20230508181154643](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508181154643.png)

### 解压缩redis

```
tar -zxvf 7.0.11.tar.gz
补充 tar -zxvf为解压缩，tar -zcvf为压缩指令
tar -zcvf my.tar dir1 dir2 ...
```

![image-20230508181254434](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508181254434.png)

### 安装GCC

GCC是C语言的编译工具 我们需要用来编译redis

```
yum -y install gcc
```

![image-20230508181915265](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508181915265.png)

### 进入到解压缩目录

```
cd redis-7.0.11/
```

### 编译redis

```
make install
如果第一次执行失败了 需要先执行make distclean 清楚之前的编译 然后在重新执行make指令
```

![image-20230508182350352](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508182350352.png)

### redis的默认目录

```
/usr/local/bin
```

![image-20230508182434255](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508182434255.png)

### 启动redis

#### 直接启动[不推荐]

```
进入到安装目录 /usr/local/bin下输入 ./redis-server
退出时需要使用CTRL+C 直接结束当前进程即可
```

![image-20230508182603672](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508182603672.png)

#### 通过指定配置文件进行启动[推荐]

```
在/usr/local/bin/目录下选择redis-server 后面跟要指定的配置文件即可
redis-server /home/myredis/redis.conf

首先需要先复制一份redis的配置文件到/home/myredis目录下
redis.conf在redis解压缩目录下
如果myredis目录不存在 需要先创建
mkdir /home/myredis
执行
cp redis.conf /home/myredis/redis.conf
启动redis
cd /usr/local/bin/
redis-server /home/myredis/redis.conf
```

![image-20230508183009090](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508183009090.png)

![image-20230508183632860](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508183632860.png)

#### 修改redis配置文件

```
redis.conf配置文件 修改完成后必须要重启 否则不生效
需要修改如下内容
1、daemonize no 改为yes 后台方式启动
	默认redis不会以后台方式启动 需要修改redis.conf中的daemonize no改为yes
2、protected-mode yes 改为no  不使用守护进程
3、注释掉bind 127.0.0.1 或改为实际的IP地址 否则会影响远程IP连接
4、添加redis密码	设置requirepass+密码
```

![image-20230508184003705](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508184003705.png)

再次启动时不会显示redis图标了 可以通过查看进程的方式查看redis是否启动成功

redis-server /home/myredis/redis.conf

![image-20230508184134086](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508184134086.png)

### 查看redis是否启动成功

```
通过端口号查看
netstat -lntup | grep 6379
通过进程查看
ps -ef|grep redis
或
ps -ef|grep redis|grep -v grep
```



### 启动redis客户端

```
在/usr/local/bin中执行
redis-cli -p 6379
注意：
	如果redis设置了密码在连接redis时需要添加密码
	redis-cli -a 123456 -p 6379
	或是连接到服务端后再次执行访问密码
	auth 123456
```

![image-20230508185858935](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508185858935.png)

### 退出和关闭redis

```
退出redis客户端连接(只是退出客户端的连接 不会关闭服务端)
quit
```

![image-20230509222058893](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230509222058893.png)

```
关闭redis服务端
单节点关闭 直接执行shutdown
多节点远程关闭 需要指定关闭的节点端口
	redis-cli [-a redis密码] -p 6379 shutdown
```

![image-20230509222542168](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230509222542168.png)

### 设置远程连接redis

需要在redis.conf中修改

```
注释掉bind 127.0.0.1
关闭保护模式
protected-mode yes 改为no
关闭防火墙
	查看防火墙状态 firewall-cmd --state
	关闭防火墙 systemctl stop firewalld.service
	禁止开机启动 systemctl disable firewalld.service
最后重启redis
	重启需要先结束 然后在启动
```

![image-20230508190511460](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508190511460.png)

![image-20230508190753434](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508190753434.png)

![image-20230508195600424](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230508195600424.png)

### 卸载redis

```
1、停止redis服务
	可以通过普通关闭方式进行停止
	redis-cli [-a redis访问密码] -p redis的端口
	也可以直接结束进程方式停止
	查看redis的进程
	ps -ef|grep redis
	然后执行kill -9 进程ID
2、删除/ust/local/bin目录下的所有redis相关服务
	rm -rf /usr/local/bin/redis-*
```



## redis开机自启动

```
redis开机自启动需要通过shell脚本实现
在/etc/init.d目录下编写redis的启动shell脚本
vim /etc/init.d/redis
添加如下代码
#!/bin/sh
#Configurations injected by install_server below....

EXEC=/usr/local/redis/bin/redis-server
CLIEXEC=/usr/local/redis/bin/redis-cli
PIDFILE=/var/run/redis_6379.pid 
CONF="/etc/redis/6379.conf"
REDISPORT="6379"
###############
# SysV Init Information
# chkconfig: - 58 74
# description: redis_6379 is the redis daemon.
### BEGIN INIT INFO
# Provides: redis_6379
# Required-Start: $network $local_fs $remote_fs
# Required-Stop: $network $local_fs $remote_fs
# Default-Start: 2 3 4 5
# Default-Stop: 0 1 6
# Should-Start: $syslog $named
# Should-Stop: $syslog $named
# Short-Description: start and stop redis_6379
# Description: Redis daemon
### END INIT INFO


case "$1" in
    start)
        if [ -f $PIDFILE ]
        then
            echo "$PIDFILE exists, process is already running or crashed"
        else
            echo "Starting Redis server..."
            $EXEC $CONF
        fi
        ;;
    stop)
        if [ ! -f $PIDFILE ]
        then
            echo "$PIDFILE does not exist, process is not running"
        else
            PID=$(cat $PIDFILE)
            echo "Stopping ..."
            $CLIEXEC -p $REDISPORT shutdown
            while [ -x /proc/${PID} ]
            do
                echo "Waiting for Redis to shutdown ..."
                sleep 1
            done
            echo "Redis stopped"
        fi
        ;;
    status)
        PID=$(cat $PIDFILE)
        if [ ! -x /proc/${PID} ]
        then
            echo 'Redis is not running'
        else
            echo "Redis is running ($PID)"
        fi
        ;;
    restart)
        $0 stop
        $0 start
        ;;
    *)
        echo "Please use start, stop, restart or status as first argument"
        ;;
esac
```

```
chmod 775 /etc/init.d/redis #授权
systemctl daemon-reload     #刷新配置
systemctl enable redis      #设置开机自启动
systemctl disable redis     #关闭开机自启动
systemctl start redis       #启动
```

## Redis数据类型

```
redis7.0数据类型有10种
	数据类型通常指的是value的类型 key的类型都是字符串
	1、String	字符串
	2、List		列表
	3、Hash		散列表
	4、Set		无序集合 唯一
	5、ZSet		有序集合 唯一
	6、GEO		地理空间（经纬度）
	7、HyperLogLog 基数统计
	8、bitmap	位图
	9、bitfield	位域
	10、Stream 	流(MQ)
```

### String（字符串）

```
String是redis最基本也是最常用的类型之一，一个key对应一个value
String类型是二进制安全的 也就是说redis的String可以包含任何数据 例如 jpg图片或是序列化对象
一个String类型的value最多可以存储512M的数据
```

### List（列表）

```
List是简单的字符串列表 按照插入的顺序排序 可以添加一个元素到列表的头部(左边)或尾部(右边)
List类型底层实际是双向链表 最多可以包含2的32次方-1 个元素(每个list可以存放超过40亿个元素)
```

### Hash（散列表）

```
hash是一个String类型的field和value的映射表
hash适用于存储对象
每个hash类型可以存放超过40亿个元素
```

### Set（集合）

```
Set是String类型的无序集合，集合中的元素是唯一的 
集合对象的编码可以是intset或hashtable
```

### ZSet（集合）

```
zset（sorted set） 有序集合
zset和set集合一样也是string类型元素的集合，且不允许出现重复元素
不同的是每个元素都会关联一个double类型的分数 该分数是用来对元素进行排序的
另外 zset中的元素是唯一的 但用于排序的分数是可以重复的
```

### GEO（地理空间）

```
GEO 主要用于存储地理位置信息，并对存储的信息进行操作
可以存放地理位置的坐标，计算两个位置之间的距离，根据用户的经纬度坐标来获取指定范围内的地理位置集合
```

### HyperLogLog（基数统计）

```
HyperLogLog 用来做基数统计的算法，其优点是在输入元素的数量或体积非常庞大时 计算基数所需要的空间总是固定且很小的
在redis中每个hyperloglog键只需要12kb内存就可以计算2的64次方元素个数
```

### Bitmap（位图）

```
bitmap用来统计或记录点赞，打开等信息 底层是由bit数组实现 数组中每个存储单元只能是1或0 
也就是说 bitmap是由0或1表现的二进制数组
```

### Bitfield（位域）

```
bitfield可以一次性操作多个比特位域(连续的多个bit位) 他会执行一系列操作并返回一个响应数组，这个数组中的元素对应参数列表中的响应操作的执行结果
通过bitfield我们可以一次性对多个bit位域进行操作
```

### Stream（流）

```
在5.0版本中新增的数据结构
stream主要用于消息队列，5.0之前redis中的消息队列有一个最大的缺陷就是无法持久化，一旦服务器宕机就会丢失数据，stream的出现就是解决这个问题的，stream提供了主备复制和持久化功能
```

## Redis常用命令

```
redis常用命令参考手册
https://redis.io/commands/
或
http://www.redis.cn/commands.html
```

![image-20230509230918395](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305241631178.png)



### Key的常用操作

注意：命令不区分大小写 但key严格区分大小写

| 命令                     | 说明                                                   |
| ------------------------ | ------------------------------------------------------ |
| DUMP key                 | 序列化指定的key 并返回序列化的值                       |
| DEL key                  | 删除存在的key                                          |
| EXISTS key               | 检查是否存在key                                        |
| EXPIRE key seconds       | 为指定的key设置过期时间                                |
| EXPIREAT key timestamp   | 为指定的key设置过期时间 传入的unix时间戳               |
| PEXPIRE key milliseconds | 为指定的key设置国企时间 传入毫秒数                     |
| KEYS pattern             | 查询所有符合给定模式(pattern)的key                     |
| MOVE key db              | 移动当前的key到指定的库中                              |
| PERSIST key              | 移除key的过期时间                                      |
| PTTL key                 | 返回key的剩余时间 以毫秒计算                           |
| TTL key                  | 返回key的剩余时间 以秒计算 -1表示永不过期 -2表示已过期 |
| RANDOMKEY                | 随机返回一个key                                        |
| RENAME key newKey        | 重命名key                                              |
| RENAMENX key newKey      | 只有newkey不存在时将key改为newkey                      |
| TYPE key                 | 返回key的类型                                          |
| select dbindex           | 选择不同的库 根据库的下标选择                          |
| dbsize                   | 查看当前库中的所有key                                  |
| flushdb                  | 清空当前库中所有元素                                   |
| flushall                 | 清空所有库的中所有元素                                 |

### Value的操作

#### String

```
https://redis.io/docs/data-types/strings/
String类型是最常用的数据类型，是单值单value

set key value 相关参数解释
set key value [NX|XX] [GET] [EX seconds|PX milliseconds|EXAT unix-time-seconds|PXAT unix-time-milliseconds|KEEPTTL]
	NX 键不存在的时候设置键值
	XX 键存在的时候设置键值
	GET 返回指定键原本的值 如果键不存在返回nil
	EX seconds 以秒为单位设置过期时间
	PX milliseconds 以毫秒位单位设置过期时间
	EXAT unix-time-seconds 设置以秒为单位的时间戳过期时间
	PXAT unix-time-milliseconds 设置以毫秒为单位的unix时间戳过期时间
	KEEPTTL 保留设置前key的过期时间
```

##### 单值单value

```
设置值
set k1 hello
获取值
get k1
```

![image-20230509235505602](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305241631698.png)

![image-20230509235700143](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305241631024.png)

![image-20230510000243216](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202305241631658.png)

##### 同时设置/获取多个键值

```
同时设置多个键值
	mset key1 value1 key2 value2 ...
同时获取多个键对应的值
	mget key1 key2
同时设置多个键值 遵循事务的完整性 - 如果有存在的key则全都不会新增成功
	msetnx key1 value1 key2 value2 ...

```

![image-20230510000838940](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510000838940.png)

##### 获取/设置指定区间范围内的值

```
相当于subString 获取指定的子串
getrange/setrange
```

![image-20230510001227397](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510001227397.png)



##### 数值递增/递减

```
注意 value必须是数字才能进行递增
incr key 每次递增1
incyby key 3 每次递增3
decr key 每次递减1
decrby 3 每次递减3
```

![image-20230510001943589](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510001943589.png)



##### 获取字符串和追加内容

```
strlen key 获取key对应的value长度
append key value 给key对应的value追加内容 只能在末尾追加
```

![image-20230510002240985](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510002240985.png)

##### getset

```
getset  先get获取key之前的值在set新的值
```

![image-20230510070442167](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510070442167.png)

#### List

```
List是列表数据结构
底层采用双向链表存储元素，主要功能有push和pop等，一般用在栈，队列等场景
left,right都可以插入数据，如果键不存在则创建新的链表，如果键存在则新增内容
list对两端的操作性能很高，通过索引操作中间节点的元素性能较差
```

##### 常用操作命令

| 命令                                 | 说明                                                         |
| ------------------------------------ | ------------------------------------------------------------ |
| BLPOP key timeout                    | 移除获取列表中左侧第一个元素 如果列表中没有元素则会进入阻塞或等到超时 |
| BRPOP key timeout                    | 移除获取列表中右侧第一个元素 如果列表中没有元素则会进入阻塞或等到超时 |
| BRPOPLPUSH source disination timeout | 从列表中弹出一个值放入到新的列表中并返回该值                 |
| LINDEX key index                     | 通过索引获取key                                              |
| LINSERT key BEFORE\|AFTER value      | 在列表的前边或后边插入元素                                   |
| LLEN key                             | 获取key的长度                                                |
| LPOP key                             | 移除并返回列表中第一个元素                                   |
| LPUSH key1 [key2]                    | 向列表头部插入一个或多个元素                                 |
| LPUSHX key1 [key2]                   | 向列表头部插入一个或多个元素                                 |
| LRANGE key start stop                | 获取指定范围内的key                                          |
| LREM key count value                 | 移除列表中的元素                                             |
| LSET key index value                 | 通过索引设置列表中元素的值                                   |
| LTRIM key start stop                 | 对一个列表进行修剪(trim) 让列表只保留指定区间内的元素 不在指定区间内的元素全部移除 |
| RPOP key                             | 移除并获取最后一个元素                                       |
| RPOPLPUSH source destination         | 移除列表中最后一个元素 将元素添加到另一个列表并返回          |
| RPUSH key value1 [value2]            | 在列表末尾添加一个或多个元素                                 |
| RPUSH key value                      | 为已存在的key添加值                                          |

##### 案例

```
lpush 在左侧添加值
rpush 在右侧添加值
lrange 获取指定范围的值
```

![image-20230510074255804](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510074255804.png)

![image-20230510074330210](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510074330210.png)

```
lpop 获取左侧的值
rpop 获取右侧的值
```

![image-20230510074514982](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510074514982.png)

```
lindex 按照索引下标获取元素
llen 获取列表中的元素个数
```

![image-20230510074712666](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510074712666.png)

```
lrem key N v1 删除N个值等于v1的元素
ltrim key 开始index 结束index 截取指定范围内的值后在重新赋值给key
```

![image-20230510074934381](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510074934381.png)

![image-20230510075236775](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510075236775.png)

```
rpoplpush 源列表 目标列表
```

![image-20230510075512962](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510075512962.png)

```
lset key indx value  设置给定下标的值
```

![image-20230510075636928](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510075636928.png)

```
linsert key before|after 给已有的值插入新的值
```

![image-20230510075827685](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510075827685.png)

#### Hash

```
hash类型 底层采用散列表
redis中 KV模式不变，但Value是一个键值对
	Map<String,Map<Object,Object>>
```

##### 常用操作指令

| 命令                                         | 说明                                               |
| -------------------------------------------- | -------------------------------------------------- |
| HDEL key field2 [field3]                     | 删除一个或多个hash字段                             |
| HEXISTS field                                | 查看指定的字段是否存在                             |
| HGET key field                               | 获取存储在hash表中指定字段的值                     |
| HINCRBY key field inccrement                 | 为hash表key中的指定字段的浮点数值加上增量increment |
| HKEYS key                                    | 获取所有hash表中的字段                             |
| HLEN key                                     | 获取hash表中所字段的数量                           |
| HMGET key field1 [field2]                    | 获取所有指定字段的值                               |
| HMSET key field1 value1 [filed2 value2]      | 同时设置多个field-value到hash表中                  |
| HSET key field value                         | 将hash表中key中的字段field的值设置为value          |
| HSETNX key field value                       | 只有在字段field不存在时 设置hash表字段的值         |
| HVALS key                                    | 获取hash表中所有的值                               |
| HSCAN key cursor[MATCH pattern][COUNT count] | 遍历hash表中的键值对                               |

##### 案例

###### hset/hget

```
hset user:001 id 1 name tom age 12  
	解释：添加user:001 他的value也是键值对 id name age 都是key  后边跟着的是value
hget user:001 id
	解释 获取user:001这个key中的value中的key是id的值
```

![image-20230510183607005](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510183607005.png)

###### hmset/hmget

```
hmset和hmget都是批处理用的 即一次可以设置或获取多个元素
HMSET user:002 id 2 name jerry age 13
	解释：添加user:002信息他的value也是键值对 id name age 都是key  后边跟着的是value
HMGET user:002 id name age
	解释：一次性获取多个field对应的值
```

![image-20230510183806402](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510183806402.png)

###### hgetall/hdel

```
hgetall 遍历hash表
hdel 删除key的value中指定的field(key)
HGETALL user:001   解释  遍历key是user:001的所有value
HDEL user:001 id	解释 删除key的value中的key为id的值
```

![image-20230510184121918](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510184121918.png)

###### hlen

```
hlen用来获取某个key中的所有元素个数
hlen user:001
```

![image-20230510184318960](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510184318960.png)

###### hexists

```
判断在key的value中是否包含某个字段(key) 存在返回1 不存在返回0
HEXISTS user:001 name
```

![image-20230510184503419](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510184503419.png)

###### hkeys/hvals

```
单独获取所有的key和所有的value
HKEYS user:002
HVALS user:002
```

![image-20230510184639093](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510184639093.png)

###### hincrby/hincrbyfloat

```
给整数和小数增量
HINCRBY user:001 age 1   给整数增量1
HINCRBYFLOAT user:001 score 0.5  给小数增量0.5
```

![image-20230510185004883](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510185004883.png)



###### hsetnx

```
只有key的value中字段（key）不存在时才会新建
新建成功返回1 否则0
```

![image-20230510185310915](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510185310915.png)

#### Set

```
Set是单值多value 与list不同的是set中的value是不允许重复的
```

##### 常用命令

| 命令                                   | 说明                                              |
| -------------------------------------- | ------------------------------------------------- |
| SADD key member1 ...                   | 向set集合中添加元素 一个或多个                    |
| SCARD key                              | 获取集合中所有元素                                |
| SDIFF key1[ key2]                      | 集合运算 取差集                                   |
| SDIFFSTORE destination key1 [key2...]  | 将集合中的差集保存到destination中                 |
| SINTER key1[key2]                      | 取两个或多个集合的交集                            |
| SINTERSTORE destination key1[key2]     | 将集合中的交集保存到destination中                 |
| SISMEMBER key member                   | 判断集合中是否包含某个value(member)               |
| SMEMBERS key                           | 获取集合中所有元素                                |
| SMOVE source destination member        | 将member元素从source集合移动到destination集合     |
| SPOP key                               | 移除并返回被移除的集合中随机的元素                |
| SRANDMEMBER key [num]                  | 随机获取集合中一个或多个元素                      |
| SREM key member1 [member2]             | 移除集合中一个或多个成员                          |
| SUNION key1 [key2]                     | 合并集合并返回合并后的结果集                      |
| SUNIONSTORE destination key1[key2]     | 取两个或多个集合的并集然后保存到distination集合中 |
| SSCAN key cursor[MATCH pattern][count] | 遍历集合中的元素                                  |

##### 案例

###### sadd

```
添加元素
sadd key value1 value2 value3 ...
```

![image-20230510190251018](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510190251018.png)

###### smembers

```
遍历集合中的所有元素
smsmbers key
```

###### sismember

```
判断集合中是否存在该元素 存在返回1 
SISMEMBER key 需要判断的值
```

![image-20230510190422242](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510190422242.png)

###### srem

```
删除元素
srem key 需要删除的元素
```

![image-20230510190534468](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510190534468.png)

###### scard

```
获取集合中的元素个数
scard key
```

![image-20230510190616362](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510190616362.png)

###### srandmember

```
在集合中随机抽取指定个数的元素 不会删除元素
srandmember key 指定随机抽取展示的数字
```

![image-20230510190855323](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510190855323.png)

###### spop

```
在集合中随机抽取指定个数的元素 同时会删除抽取的元素
spop key 需要抽取的元素个数
```

![image-20230510191005934](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510191005934.png)

###### smove 

```
将某个值赋值给另一个集合
smove key1 key2 key1中需要移动的某个值
```

![image-20230510191220580](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510191220580.png)

###### del

```
删除集合
del key
```

![image-20230510191422636](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510191422636.png)

###### 集合运算

```
A B 两个集合
	A   abc12
	B   123ax
```

![image-20230510191815520](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510191815520.png)

交集

```
AB两个集合共有部分
sinter a b
```

![image-20230510192431022](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510192431022.png)



```
sintercard 是7.0提供的新命令 取交集的 
	他不返回结果集 而只返回结果集的基数 这个基数是由所有给定集合的交集产生的集合的基数
	基数： 指的是多个key取交集后的元素个数(不重复)
语法 
	sintercard key的个数 key的名称
例如
	SINTERCARD 2 s1 s2   取s1 和 s2两个key的交集的元素个数 其中的2表示两个key即s1 s2
```

![image-20230510192919147](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510192919147.png)

并集

```
合并A和B两个集合 元素不重复
sunion a b
```

![image-20230510192120851](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510192120851.png)

差集

```
AB两个集合 A独有的部分 以第一个集合为基准
SDIFF s1 s2 取s1和s2两个集合的差集  在s1中但不在s2中的元素
```

![image-20230510191858623](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510191858623.png)



#### ZSet

```
ZSet是在Set基础上给每个value前增加一个score分数值 该值用来排序的
set添加元素 sadd k1 v1 v2 v3 
zset添加元素 zadd k1 score1 v1 score2 v2 ...
```

##### 常用命令

| 命令                                         | 说明                                             |
| -------------------------------------------- | ------------------------------------------------ |
| ZADD key score1 member1 [score2 member2]     | 添加元素到集合中如果集合中已存在则更新           |
| ZCARD key                                    | 获取所有结合中的元素                             |
| ZCOUNT key min max                           | 统计分数范围内的集合中的元素个数                 |
| ZINCRBY key increment member                 | 对score进行增量                                  |
| ZINTERSTORE destination numkeys key[key2...] | 统计一个或多个结果集并将结果保存到distincation中 |
| ZRANGE key start stop[WITHSCORES]            | 获取指定分数范围内的元素                         |
| ZRANGEBYLEX key min max [LIMIT offset count] | 获取指定分数范围内的元素 带分页                  |
| ZRANK key member                             | 获取集合中元素的索引                             |
| ZREVRANK                                     | 逆序获取集合中元素的索引                         |
| ZREM key member[member...]                   | 移除集合中一个或多个value 根据value移除          |
| ZREMRANGEBYRANK key start stop               | 移除分数范围内的所有元素 根据下标索引            |
| ZREMRANGEBYLEX key min max                   | 移除分数范围内的所有元素 根据分数                |
|                                              |                                                  |

###### zadd/zrange

```
向有序集合中假如一个元素和该元素对应的分数
zrange 遍历zset集合  0到-1表示遍历全部  withscores 带分数值的遍历
语法
	zrange key 0 -1 遍历所有元素
	zrange key 0 2 根据下标取值
	zrange key 0 -1 withscores 带有分数的遍历
```

![image-20230510194615895](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510194615895.png)

![image-20230510194959149](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510194959149.png)

![image-20230510194733236](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510194733236.png)

###### zrevrange

```
zrange 是升序  zrevrange就是降序 都是根据score进行排序的

```

![image-20230510195147078](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510195147078.png)

###### zrangebyscore

```
根据分数获取value 可以获取指定的分数范围内的所有value
ZRANGEBYSCORE z1 0 30 这里的0 30 表示score为0到score为30
ZRANGEBYSCORE z1 (10 (30 withscores 带有( 表示不包含
ZRANGEBYSCORE z1 (10 (30 withscores limit 0 2  limit 与分页类似 从哪开始取多少个元素
```

![image-20230510195429969](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510195429969.png)

![image-20230510195643433](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510195643433.png)

![image-20230510195742407](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510195742407.png)

###### zcard

```
获取集合中元素个数
zcard key
```

![image-20230510195944197](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510195944197.png)



###### zrem

```
删除元素 根据socre对应的value值进行删除
zrem z1 v4  删除value是v4的值 score也会跟着删除
```

![image-20230510200148699](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510200148699.png)

###### zincrby

```
增加某个元素的分数
ZINCRBY z1 10 v1   给v1的分数增加10
```

![image-20230510200308883](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510200308883.png)

###### zcount

```
获取指定分数范围内的元素个数
ZCOUNT z1 10 20 获取score为10到20的所有元素个数
```

![image-20230510200435456](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510200435456.png)



###### zmpop

```
弹出一个或多个元素 他们的元素的是分数对（分数+value）
ZMPOP 1 z1 min count 1  
	解释
		ZMPOP 1(需要弹出的元素个数) z1(集合key) min（弹出最小的） count 1(总的需要弹出个数)
```

![image-20230510200815919](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510200815919.png)

![image-20230510201023657](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510201023657.png)

###### zrank/zrevrank

```
zrank 获取下标
zrevrank 逆序获取下标
```

![image-20230510201412601](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510201412601.png)

#### Bitmap

```
由0和1组成的bit数组 该数组中只有0和1 可以极大的节约存储空间
用于状态记录或统计
例如 校验用户是否登录，签到情况，点击量，各种打开点赞等 点击则为1 否则为0
```

##### 基本命令

| 命令                   | 说明                           |
| ---------------------- | ------------------------------ |
| setbit key offset val  | 给指定key的值的第offset赋值val |
| geibit key offset      | 根据offset获取key的值          |
| bitcount key start end | 返回指定key中为1的值的数量     |

##### 案例

###### setbit

```
setbit 底层string
语法
	setbit key offset value
	解释
	setbit 键 偏移量 值(只能是0或1)
	bitmap的偏移量是从零开始
	
例如记录每个月的出勤
```

![image-20230510203331584](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510203331584.png)

###### getbit

```
根据偏移量获取值
```

![image-20230510203440652](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510203440652.png)

###### strlen

```
统计占用字节个数
不是字符串长度 而是占用了几个字节 超过8位后按照8位一组进行扩容
```

![image-20230510203821575](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510203821575.png)

###### bitcount

```
统计全部键中含有1的有多少个
例如统计出勤
```

![image-20230510204200431](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230510204200431.png)

#### HyperLogLog

```
HyperLogLog是去重复统计功能的基数估算算法 
用于统计一个集合中不重复的元素个数 就是对集合去重后剩余元素的个数
优点：在输入元素的数量非常庞大时 计算基数所需要的空间总是固定的 并且是很小的 因为hyperloglog本身不会存储元素，只是用于基数统计
缺点：会存在0.8%左右的误差，另外由于hyperloglog不会存储元素 因此也就不会像其他集合那样可以返回每个元素

需求
统计某个网站每天的访问量 需要去重操作(同一个IP同一天只记录一次)
统计用户每天搜索不同词条个数

什么是基数？
	是一种数据集，去重后的真实个数
	例如
		{1，2，33，55，77，2，33}(全集)
		去重后
		{1，2，33，55，77} = 5（基数）
```

##### 基本命令

| 命令                          | 说明                             |
| ----------------------------- | -------------------------------- |
| PFADD key element ...         | 添加元素到hyperloglog            |
| PFCOUNT key                   | 返回估算值 根据给定的hyperloglog |
| PFMERGE destkey sourcekey ... | 将多个hyperloglog合并为一个      |

##### 案例

###### pfadd/pfcount

```
pfadd 添加元素
pfcount 统计元素个数
```

![image-20230511002944242](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511002944242.png)



###### pfmerge

```
整合两个集合中的元素到一个新的集合中
PFMERGE h3 h1 h2  把h1和h2整合到h3中 去重后的数据
```

![image-20230511003229916](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511003229916.png)



#### GEO

```
GEO类型底层采用ZSet实现 只是将score改为了经纬度 值是对应的位置名称
GEO是地理空间 可以用存储地球上的任意坐标，还可以通过坐标搜索附近的小姐姐，美食店铺，学校，银行等
我们的地球上的地理位置是使用二维的经纬度来表示的，精度范围（-180，180）维度范围（-90，90）只要我们确定一个点的经纬度就可以获取到地球上的任意位置
以滴滴打车为例，最直观的操作就是实时记录各个车辆的位置，当我们需要找车时 会在数据库中查找距离我们最近的车辆
如果使用SQL实现会存在如下问题
1、查询性能问题，当遇到打车高峰时 大数据量的查询对于普通关系型数据库是很难承受的
2、精确度的问题，我们的地球不是平面的而是一个球体，这种矩阵计算在长距离计算时会有很大误差

如何获取经纬度？
https://api.map.baidu.com/lbsapi/getpoint/index.html

```

![image-20230511071954127](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511071954127.png)



##### 基本命令

| 命令                           | 说明                                                   |
| ------------------------------ | ------------------------------------------------------ |
| GEOADD 多个经度和纬度 位置名称 | 添加经纬度                                             |
| GROPOS                         | 从键里面返回指定位置的元素                             |
| GEODIST                        | 返回两个指定位置之间的距离                             |
| GEORADIUS                      | 获取以经纬度为中心距离不超过给定最大距离的所有位置元素 |
| GEORANIUSBYMEMBER              | 与GEORADIUS类似                                        |
| GEOHASH                        | 获取一个或多个位置元素 以hash表示                      |

##### 案例

###### GEOADD

```
添加经纬度
GEOADD city 113.331084 23.112223 "广州塔" 113.382269 23.105305 "琶洲塔" 112.711516 22.859528 "文昌塔"
```

![image-20230511073711187](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511073711187.png)

###### zrange

```
遍历集合
如果中文乱码 重新登陆客户端时添加 --raw即可
```

![image-20230511073938647](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511073938647.png)

###### geopos

```
返回指定位置名称的经纬度坐标
GEOPOS city 广州塔 琶洲塔
```

![image-20230511074146331](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511074146331.png)

###### geohash

```
以hash形式返回经纬度坐标
	geohash算法生成bease32编码值（三维变二维变一维）
		
使用
GEOHASH city 广州塔 琶洲塔
```

![image-20230511074430500](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511074430500.png)

###### geodist

```
获取两个坐标之间的距离
geodist key 位置名称1 位置名称2 ... km
最后的是距离单位
mi 英里
ft 英尺
km 千米
m	米
GEOdist city 广州塔 琶洲塔 km
```

![image-20230511074750569](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511074750569.png)



###### georadius

```
以指定位置为半径 查找附近的所有位置元素

GEORADIUS city 113.331084 23.112223 20 km withdist withcoord withhash count 10 desc
	说明 查找以113.331084 23.112223为半径 20公里以内的前10个元素倒序排列 并以hash码形式返回 
	同时带有距离
	
```

![image-20230511075517601](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511075517601.png)

###### georadiusbymember

```
根据value(位置名称)查询指定半径内的位置元素
GEORADIUSBYMEMBER city 广州塔 20 km withdist withcoord withhash count 10 desc
```

![image-20230511075839334](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511075839334.png)



计算距离

```
GEOADD c2 113.616838 23.530535 "城建职业学院" 117.029089 36.631289 "山东财经大学舜耕校区"

GEODIST c2 城建职业学院 山东财经大学舜耕校区
```

![image-20230511080539021](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511080539021.png)



#### Stream

```
Stream是MQ 与Java中的Stream完全不同
为什么要使用Stream?
	在5.0之前redis的消息队列有两种实现方式
		1、List数据类型 可以实现点对点的消息队列
			lpush 1 2 3  取值  rpop list
		2、发布订阅
			list是点对点的 pub/sub是发布订阅模式 一对多的
			redis的发布订阅有个缺点 就是当网络断开或redis宕机 消息就会丢失，
			同时也没有ACK签收机制保证
			数据的可靠性 如果一个消息消费者都没有 那么消息就直接被丢弃了
	在redis5.0中引入了一个更加强大的数据结构 Stream
	
Stream能做什么？
	stream可以实现消息队列，它支持消息的持久化 支持自动生成全局唯一的ID
	支持ACK确认消息模式，支持消息消费组模式等，让消息队列更加的稳定和可靠
	
Stream结构如下图
	Message Content	消息内容
	Consumer Group 消费组，同一个消费组可以有多个消费者
	Last_delivered_id	游标 每个消费组都会有一个游标用来记录当前读取的位置 
								读完一个消息游标就会向后移动一次
	Consumer	消费组，消费组中的消费者
	Pending_ids	消息消费状态
					消费者会有一个状态变量，用于记录当前已消费但未进行签收的消息ID
					如果客户端没有ack 那么这个变量里面的消息ID会越来越多，一旦某个消息被签收
					它就开始减少 redis官方称为PEL(Pending Enters List)
	
```

![image-20230511195114565](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511195114565.png)

##### 队列相关指令

| 名称      | 作用                                        |
| --------- | ------------------------------------------- |
| XADD      | 添加消息到队列末尾                          |
| XTRIM     | 限制Stream的长度 如果已经超过长度则进行截取 |
| XDEL      | 删除消息                                    |
| XLEN      | 获取Stream中的消息长度                      |
| XRANGE    | 获取消息列表（可以指定范围） 忽略删除的消息 |
| XREVRANGE | 反向获取消息列表 ID从大到小                 |
| XREAD     | 获取消息 返回大于指定ID的消息               |
|           |                                             |

##### 消费组相关命令

| 命令               | 作用                                                         |
| ------------------ | ------------------------------------------------------------ |
| XREADGROUP GROUP   | 读取消费组中的消息                                           |
| XGROUP SETID       | 设置消费组最后读取的消息ID                                   |
| XGROUP DELCONSUMER | 删除消费组                                                   |
| XPENDING           | 打印待处理消息的相信信息                                     |
| XCLAIM             | 转义消息的归属权（长期未被处理或无法处理的消息 转移给其他消费组进行处理） |
| XINFO              | 打印Stream、Consumer、Group详细信息                          |
| XINFO GROUPS       | 打印消费者组的相信信息                                       |
| XINFO STREAM       | 打印Stream详细信息                                           |
| XACK               | 标记消息已处理，签收消息                                     |

##### 四个特殊符号

| 符号 | 作用                                                         |
| ---- | ------------------------------------------------------------ |
| + -  | 最大和最小可能出现的ID                                       |
| $    | 表示只消费新的消息 当前Stream中最大的ID                      |
| >    | 用于SREADGROUP命令 表示还没有到消费组中的消息 会更新消费组的最后ID |
| *    | 用户XADD命令 系统自动生成消息ID  类似mysql中的主键自增       |

##### 案例

###### XADD

```
向消息队列中添加消息 - 会添加到队列的末尾
注意：消息的ID必须要大于上一个消息的ID  可以使用*默认生成消息ID
XADD mq1 * name tom
```

![image-20230511234206236](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511234206236.png)

![image-20230511234745823](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511234745823.png)

###### xrange

```
获取消息 
+ - 表示最大和最小可能出现的ID 就是说查询全部的消息
XRANGE mq1 - + 
```

![image-20230511234727508](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511234727508.png)

###### xrevrange

```
与xrange类似 只是ID从大到小
XREVRANGE mq1 + -
```

![image-20230511234904616](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511234904616.png)

###### xdel

```
删除消息 - 需要根据ID删除
XDEL mq1 1683820005824-0
```

![image-20230511235215081](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511235215081.png)

###### xlen

```
获取Stream中消息个数
XLEN mq1
```

![image-20230511235320474](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511235320474.png)

###### xtrim

```
截取消息（对stream的长度进行限制） 有两个参数 maxlen表示允许的最大长度 minid 表示允许的最小ID
可以根据最大长度或最小ID进行截取
XTRIM mq3 maxlen 2  最2表示截取两个 会将时间戳ID最小的截取掉
XTRIM mq3 minid 1683820650334-0 截取比id=1683820650334-0小的消息
```

![image-20230511235859157](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230511235859157.png)



![image-20230512000150174](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512000150174.png)

###### xread

```
用于获取消息（阻塞/非阻塞） 只会返回大于指定ID的消息
XREAD [COUNT count] [BLOCK milliseconds] STREAMS key [key ...] id [id ...]
	非阻塞
		末尾使用$表示特殊ID 获取比当前ID大的ID对应的消息 
		COUNT 当不指定时返回Stream中所有的消息
	
		案例
			XREAD count 2 streams mq3 $ 
                count2表示读取两条消息 末尾的$表示比当前ID大的下一个ID

            XREAD count 2 streams mq3 0-0  
                count2表示读取两条消息  最后的0-0表示全部读取
		
	阻塞
		会等待有消息时才会进行读取 没有消息则一直处于阻塞状态
		此时在开一个窗口 新增一条消息后就会立即被读取
		案例
			XREAD count 1 block 0 streams mq3 $
			读取1条消息 使用阻塞方式
			block 0 表示一直阻塞 0为毫秒数
			

```

![image-20230512001359843](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512001359843.png)

##### 消费组相关指令

###### xgroup create

```
创建消费组组
$表示从stream末尾开始消费消息
0表示从stream头部开始消费消息
注意
	创建消费者组的时候必须指定ID，ID如果为0则表示从头部开始消费消息，为$表示从尾部开始消费消息
案例
	XGROUP CREATE mq3 gmq3 $  给mq3创建消费组gmq3 从末尾开始消费消息
	XGROUP CREATE mq3 gmq2 0  给mq3创建消费组gmq3 从头部开始消费消息
```

![image-20230512072018684](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512072018684.png)

###### xreadgroup group

```
读取消费组中的消息
> 表示从第一条未被消费的消息开始读取

XREADGROUP group groupB cousumer streams mq3 >
	在groupB消费组中读取streams类型的消息 > 从第一条开始读取
注意：
	当一个消费者组中的任意一个消费者读取全部消息后 那么该消费组内的其他消费者则不能再次读取消息
	但是 其他消费组中的消费者可以读取同一条消息
	
```

![image-20230512072956071](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512072956071.png)

![image-20230512073207521](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512073207521.png)

![image-20230512073332245](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512073332245.png)

###### 消费者组的负载均衡

```
让每一个消费者共同分担读取消息 所以 通常会让每个消费者读取部分消息，从而实现一个消费组内多个消费者间的的负载均衡分布


```

![image-20230512074406802](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512074406802.png)

###### 问题

```
基于stream实现的消息队列 如何保证消费者在发送故障或宕机 再次重启后 仍然可以读取未处理完的消息？
streams会自动使用内部队列(pending list)留存消费者组里的每个消费者读取的消息保底措施，直到消费者使用xack命令通知streams消息已经处理完成
消息确认增加了消息的可靠性 一般业务在处理完成后 需要执行xack命令来确认消息已经被消费完成

```

![image-20230512075050669](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512075050669.png)

###### xpending

```
查看每个消费组内所有消费者 已读取 但尚未确认的消息
XPENDING mq3 groupC   查看 groupC消费组内的消费者读取消息的信息

查看消费组内的某个消费者读取了哪些消息
XPENDING mq3 groupA - + 10 cousumer2
```

![image-20230512075357588](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512075357588.png)

![image-20230512075932346](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512075932346.png)

###### xack

```
向消息队列发送已读取消息确认信息
XACK mq3 groupA 1683820650334-0  告诉mq3这个消息队列 groupA消费组内的1683820650334-0消息ID已经被消费处理完成 可以将其删除了
```

![image-20230512080111962](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230512080111962.png)







#### bitfield

```
```





























































































































# Jedis使用

## 依赖

```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>2.9.0</version>
</dependency>
```

## 测试

```java
  @Test
    public void testJedis(){
        //1.生成一个Jedis对象，这个对象负责和指定Redis实例进行通信
        Jedis jedis = new Jedis("192.168.43.237", 6379);
        // 2.jedis执行set操作
        jedis.set("hello", "world");
        //3.jedis执行get操作,value="world"
        String value = jedis.get("k1");
        System.out.println(value);
    }
```

