# CentOS 7

## 概述

```
CentOS 是免费开源的linux系统，7.0版本是企业级的发行版本也是目前使用最多的一个版本
其底层采用RedHat免费公开的源代码进行再发行
常用的linux操作系统有 RedHat  Ubuntu	CentOS
```

## 下载地址

```
https://wiki.centos.org/Download
```

![image-20230517190502595](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/image-20230517190502595.png)

## 安装

虚拟机安装



## 配置IP

```
刚刚安装完成的操作系统网卡是没有打开的 需要打开网卡 否则无法进行链接
修改 /etc/sysconfig/network-scripts/ifcfg-ens33 文件
vim /etc/sysconfig/network-scripts/ifcfg-ens33
将 ONBOOT改为yes
```

![image-20230517191102543](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/image-20230517191102543.png)

## 配置静态IP地址

```
静态IP指的是无论什么时候IP地址都不会发送改变
配置静态IP同样需要修改ifcfg-ens33文件
vim /etc/sysconfig/network-scripts/ifcfg-ens33
修改内容如下
BOOTPROTO=static
IPADDR=192.168.199.106
GATEWAY=192.168.199.2
DNS1=192.168.199.2
ZONE=public

解释
	BOOTPROTO 改为stataic静态
	IPADDR 根据网段自定义IP地址
	GATEWAY 配置网关
	DNS1 配置DNS 通常DNS与网关相同
	ZONE 配置信任级别
	
```

![image-20230517191818376](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/image-20230517191818376.png)

## 查看网关

虚拟机 -> 虚拟网络编辑器 -> NAT设置中查看

![image-20230517192806218](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/image-20230517192806218.png)

## 重启网络

```
service network restart
```

重启后可以通过ip addr指令查看网络是否配置成功

![image-20230517193037659](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230517193037659.png)

## 目录介绍

```
目录大致分为4类
	管理类
		/boot 	linux的内核及引导系统程序所需要的文件目录
		/bin	存放标准 linux 的工具，在终端里输入ls，系统就系统将会到该目录查看是否存在该命令程序
		/sbin	大多是涉及系统管理的命令的存放，是超级权限用户root的可执行命令存放地，
				普通用户无权限执行这个目录下的命令
		/var	这个目录的内容是经常变动的，用来存储经常被修改的文件，如日志、数据文件、邮箱等
		/etc	主要存放系统配置方面的文件
		/dev	主要存放与设备有关的文件
		/mnt	这个目录一般是用于存放挂载储存设备的挂载目录的，比如有cdrom 等目录
	用户类
		/root	系统管理员目录
		/home	主要存放个人数据		
	应用程序类
		/lib	该目录用来存放系统动态链接共享库，几乎所有的应用程序都会用到该目录下的共享库
		/tmp	临时目录，有些linux会定期清理
		/usr	存放一些不适合放在/bin 或 /etc 目录下的额外工具，如个人安装的程序或工具
		/usr/local	主要存放那些手动安装的软件
		/usr/bin	用于存放程序
		/usr/share	用于存放一些共享数据
		/usr/lib	存放一些不能不能直接运行，但却是许多程序运行所必需的一些函数库文件
		/opt		主要存放可选程序，直接删除程序不影响系统其设置
					安装到/opt目录下的程序，它所有的数据、库文件等等都是放在同个目录下面

	信息类
		/lost+found 在ext2或ext3文件系统中，当系统意外崩溃或机器意外关机，
				而产生一些文件碎片放在这里。但当突然停电、或者非正常关机后，有些文件就临时存放在这里
		/proc	操作系统运行时，进程信息及内核信息（比如cpu、硬盘分区、内存信息等）存放在这里
	其他重要目录：
        /etc/rc.d 放置开机和关机的脚本。
        /etc/rc.d/init.d 放置启动脚本
        /etc/xinetd.d 配置xinetd.conf可以配置启动其他额外服务。
        /usr/include  一些distribution套件的头文件放置目录，安装程序时可能会用到。
        /usr/lib*  套件的程序库
        /usr/local  默认的软件安装目录。
        /usr/share/doc 系统说明文件的放置目录
        /usr/share/man  程序说明文件放置目录
        /usr/src 内核源代码目录
        /usr/X11R6 X的存放目录
```

## 目录结构

```
linux 目录结构
	/ 根目录，一般根目录下只存放目录，
		不要存放文件，/etc、/bin、/dev、/lib、/sbin应该和根目录放置在一个分区中
	/bin:/usr/bin: 可执行二进制文件的目录，如常用的命令ls、tar、mv、cat等。
	/boot： 放置linux系统启动时用到的一些文件。
		/boot/vmlinuz为linux的内核文件，以及/boot/gurb。建议单独分区，分区大小100M即可
	/dev：存放linux系统下的设备文件，访问该目录下某个文件，
			相当于访问某个设备，常用的是挂载光驱mount /dev/cdrom /mnt。
	/etc： 系统配置文件存放的目录，不建议在此目录下存放可执行文件，重要的配置文件	
			有/etc/inittab、/etc/fstab、/etc/init.d、/etc/X11、/etc/sysconfig、
			/etc/xinetd.d修改配置文件之前记得备份。注：/etc/X11存放与x windows有关的设置。
	/home： 系统默认的用户家目录，新增用户账号时，用户的家目录都存放在此目录下，
			~表示当前用户的家目录，~test表示用户test的家目录。
			建议单独分区，并设置较大的磁盘空间，方便用户存放数据
    /lib:/usr/lib:/usr/local/lib：
    	系统使用的函数库的目录，程序在执行过程中，需要调用一些额外的参数时需要函数库的协助，
    	比较重要的目录为/lib/modules。
    /lost+fount：
   	 	系统异常产生错误时，会将一些遗失的片段放置于此目录下，通常这个目录会自动出现在装置目录下。
    	如加载硬盘于/disk 中，此目录下就会自动产生目录/disk/lost+found

    /mnt:/media：
    	光盘默认挂载点，通常光盘挂载于/mnt/cdrom下，也不一定，可以选择任意位置进行挂载。

    /opt：
    		给主机额外安装软件所摆放的目录。如：FC4使用的Fedora 社群开发软件，
    		如果想要自行安装新的KDE 桌面软件，可以将该软件安装在该目录下。
    		以前的 Linux 系统中，习惯放置在 /usr/local 目录下

    /proc：
   		 此目录的数据都在内存中，如系统核心，外部设备，网络状态，由于数据都存放于内存中，
   		 所以不占用磁盘空间，比较重要的目录
   		 有/proc/cpuinfo、/proc/interrupts、/proc/dma、/proc/ioports、/proc/net/*等

    /root：
   		 系统管理员root的家目录，系统第一个启动的分区为/，所以最好将/root和/放置在一个分区下。

    /sbin:/usr/sbin:/usr/local/sbin：
   		 放置系统管理员使用的可执行命令，如fdisk、shutdown、mount等。
   		 与/bin不同的是，这几个目录是给系统管理员root使用的命令，一般用户只能”查看”而不能设置和使用。

    /tmp：
    一般用户或正在执行的程序临时存放文件的目录,任何人都可以访问,重要数据不可放置在此目录下
    /srv：
    	服务启动之后需要访问的数据目录，如www服务需要访问的网页数据存放在/srv/www内

    /usr：
    	应用程序存放目录， /usr/bin 存放应用程序， /usr/share 存放共享数据， 
    	/usr/lib 存放不能直接运行的，却是许多程序运行所必需的一些函数库文件。 
    	/usr/local:存放软件升级包。 /usr/share/doc: 系统说明文件存放目录。 
    	/usr/share/man: 程序说明文件存放目录，
    	使用 man ls时会查询/usr/share/man/man1/ls.1.gz的内容建议单独分区，设置较大的磁盘空间
    /var：
    	放置系统执行过程中经常变化的文件，如随时更改的日志文件 /var/log，
    	/var/log/message： 所有的登录文件存放目录， 
    	/var/spool/mail： 邮件存放的目录， 
    	/var/run: 程序或服务启动后，其PID存放在该目录下。
    	建议单独分区，设置较大的磁盘空间

    /dev： 目录
    	dev是设备(device)的英文缩写。/dev这个目录对所有的用户都十分重要。
    	因为在这个目录中包含了所有Linux系统中使用的外部设备。但是这里并不是放的外部设备的驱动程序，
    	这一点和windows,dos操作系统不一样。它实际上是一个访问这些外部设备的端口。
    	我们可以非常方便地去访问这些外部设备，和访问一个文件，一个目录没有任何区别。
```

## 目录/文件操作

### 1、查询目录中的内容

```
首先解释下这块内容的含义  [root@localhost ~]#
 	root代表当前登录用户，localhost代表主机名， ~代表当前主机目录
 	#代表用户权限 #表示超级用户，$表示普通用户

查询目录中内容命令 ls  (list缩写)
格式 ls [选项]  [文件或目录]
选项：
  -a 显示所有文件，包括隐藏文件
  -l  显示详细信息
  -d 查看目录属性
  -h 人性化显示文件大小
  -i  显示inode

查看当前所在目录pwd命令(Print Working Directory 打印当前工作目录)
```

![image-20230517195716272](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/image-20230517195716272.png)

![image-20230517195907963](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/image-20230517195907963.png)

```
这里第一列 比如 dr-xr-xr-x.  代表文件类型以及所有者，所属组以及其他者权限
	第一位d代表文件类型 常见的有   - 文件 d目录 | 软件链接文件
	后面9位 每3位一个组 分别是 所有者u 所属组g 以及 其他者o的权限
	权限分三种 r读  w写   x执行
	比如  dr-xr-xr-x  这个目录 所有者 所属组 以及其他者 都有 读和执行权限；
	比如  -rw------- 这个文件  所有者有读写权限 所属组以及其他者没有权限；
第二列 那个数字  是 硬链接次数 后面再说；
第三列 root  是 所有者；
第四列 root 是 所属组；
第五列 是文件大小；假如看不惯  可用 ls -lh 
[root@localhost ~]# ls -lh
总用量 4.0K
-rw-------. 1 root root 1.3K 6月  10 05:24 anaconda-ks.cfg
第六列的日期是该文件最后一次修改时间；
最后一列 是文件名称；
查看目录属性 要加 -d
[root@localhost ~]# ls -ld /etc/
	drwxr-xr-x. 78 root root 8192 6月  13 15:50 /etc/
[root@localhost ~]# 
	查看文件的inode属性 类似 主键 唯一识别文件的Id 用 -i
[root@localhost ~]# ls -li
总用量 4
33574979 -rw-------. 1 root root 1237 6月  10 05:24 anaconda-ks.cfg
	33574979 就是inode属性
```

### 2、目录处理命令

创建目录

```
linux中 关于目录 有几个重要概念
一个是 / 根目录  还有一个当前用户的家目录 比如 root用户的家目录是 /root  普通用户的家目录是/home/xxx 下
root登录 默认家目录 是root 我们这里先用 cd .. 进入 / 根目录
	[root@localhost ~]# cd ..
	[root@localhost /]# 
然后我们看下 根目录  / 下有哪些目录
	[root@localhost /]# ls 
	bin  boot  dev  etc  home  lib  lib64  media  mnt  opt  proc  root  run  sbin  srv  	sys  tmp  usr  var
cd 回到家目录
	[root@localhost /]# cd
	[root@localhost ~]# 
创建目录命令  mkdir (make directories)
如在/root 下建一个 书籍目录
    [root@localhost ~]# mkdir 书籍
    [root@localhost ~]# ls
    anaconda-ks.cfg  书籍
    [root@localhost ~]# 
假如我们要递归创建目录和文件
	[root@localhost ~]# mkdir 书籍2/java编程思想
	mkdir: 无法创建目录"书籍2/java编程思想": 没有那个文件或目录
	发现失败了 默认不准递归创建 要递归创建的话 加-p
    [root@localhost ~]# mkdir -p 书籍2/java编程思想
    [root@localhost ~]# ls
    anaconda-ks.cfg  书籍  书籍2
    [root@localhost ~]# ls /root/书籍2/
    java编程思想
    
```

切换目录

```
目录补全以及命名补全  tag键

切换所在目录，cd (change directory)
简化操作
cd ~     进行当前用户的家目录
cd 
cd - 进入上次目录
cd .. 进入上一级目录
cd . 进入当前目录

例如
我们先进入 /root/书籍2/ 目录
然后cd ~ 回到家目录 /root/
然后 cd - 进入上次目录 也就是 /root/书籍2/ 目录
再cd.. 进入 /root/目录
cd .没啥意义（注意cd后面要加个空格）；
[root@localhost ~]# cd /root/书籍2/
[root@localhost 书籍2]# cd ~
[root@localhost ~]# cd -
/root/书籍2
```

### 3、删除目录

```
删除空目录： rmdir （remove empty directories）
[root@localhost ~]# ls
anaconda-ks.cfg  书籍  书籍2
[root@localhost ~]# rmdir 书籍/
[root@localhost ~]# ls
anaconda-ks.cfg  书籍2
[root@localhost ~]# 
发现没问题
我们删除 书籍2目录时，
[root@localhost ~]# rmdir 书籍2/
rmdir: 删除 "书籍2/" 失败: 目录非空
[root@localhost ~]# 
报错了，因为 书籍2 目录 非空；
这里的话 假如要删除 书籍2 目录 我们需要先把书籍2里面的文件或者目录先删除，然后才能删除书籍2目录，
这个是非常不现实的，所以 rmdir命令了解即可，以后基本不用的。
删除文件或目录：rm  （remove）
rm -rf [文件或目录]
选项： 
-r 删除目录
-f 强制
我们用 touch命令新建一个空文件
然后用rm命令删除
[root@localhost ~]# touch java牛
[root@localhost ~]# ls
anaconda-ks.cfg  java牛  书籍2
[root@localhost ~]# rm java牛
rm：是否删除普通空文件 "java牛"？n
[root@localhost ~]# ls
anaconda-ks.cfg  java牛  书籍2
[root@localhost ~]# rm java牛
rm：是否删除普通空文件 "java牛"？y
[root@localhost ~]# ls
anaconda-ks.cfg  书籍2
[root@localhost ~]# 
删除文件会提醒是否删除，输入n 不删除 输入y 删除；
假如我们删除 书籍2 目录
[root@localhost ~]# rm 书籍2/
rm: 无法删除"书籍2/": 是一个目录
报错，假如要用rm删除目录，必须 加 -r
[root@localhost ~]# rm -r 书籍2/
rm：是否进入目录"书籍2/"? y
rm：是否删除目录 "书籍2/java编程思想"？y
rm：是否删除目录 "书籍2/"？y
[root@localhost ~]# ls
anaconda-ks.cfg
依然提示我们是否要删除，假如有一万个文件 那得输入y到手麻；
我们用 mk -rf 来强制删除 无需提醒
[root@localhost ~]# mkdir -p 书籍2/java编程思想
[root@localhost ~]# ls
anaconda-ks.cfg  书籍2
[root@localhost ~]# rm -rf 书籍2/
[root@localhost ~]# ls
anaconda-ks.cfg
[root@localhost ~]# 
这样方便很多  直截了当；以后用删除  rm -rf 命令即可
```

### 4、复制命令

```
cp (copy)
cp [选项] [源文件或目录] [目标目录]
选项：
-r 复制目录
-p 连带文件属性复制
-d 若源文件是链接文件，则复制链接属性
-a 相当于 -pdr

案例 - 复制目录
[root@localhost ~]# ls
anaconda-ks.cfg
[root@localhost ~]# cp anaconda-ks.cfg /tmp/abc
[root@localhost ~]# ls
anaconda-ks.cfg
[root@localhost ~]# ls /tmp/
abc
ks-script-p0Ci4J
systemd-private-12ece3aaa1eb44678dbf684dbccbc32f-vmtoolsd.service-xjTV7r
systemd-private-3588b18d27f94b80849d22621ef2defb-vmtoolsd.service-wmk7Jt
systemd-private-90f54554da784e52aeb890d207e41094-vmtoolsd.service-I8F1Yy
systemd-private-d5f08625d54e4ed3a0faa9b9fb944db5-vmtoolsd.service-4rHPpX
systemd-private-f68c251a6a6c46ac94b4c1e73090ef59-vmtoolsd.service-S6J6U0
systemd-private-f7cb736915b24ef2b72faee1ec860c6d-vmtoolsd.service-ldpfcl
yum.log
把 root下的anaconda-ks.cfg复制到了 tmp下 并且重名为 abc
假如 cp anaconda-ks.cfg /tmp/abc 不加文件名 则新文件名称不变
[root@localhost ~]# cp anaconda-ks.cfg /tmp/
[root@localhost ~]# ls /tmp/
abc
anaconda-ks.cfg
ks-script-p0Ci4J
systemd-private-12ece3aaa1eb44678dbf684dbccbc32f-vmtoolsd.service-xjTV7r
systemd-private-3588b18d27f94b80849d22621ef2defb-vmtoolsd.service-wmk7Jt
systemd-private-90f54554da784e52aeb890d207e41094-vmtoolsd.service-I8F1Yy
systemd-private-d5f08625d54e4ed3a0faa9b9fb944db5-vmtoolsd.service-4rHPpX
systemd-private-f68c251a6a6c46ac94b4c1e73090ef59-vmtoolsd.service-S6J6U0
systemd-private-f7cb736915b24ef2b72faee1ec860c6d-vmtoolsd.service-ldpfcl
yum.log
假如复制目录：
[root@localhost ~]# ls
anaconda-ks.cfg
[root@localhost ~]# mkdir -p 书籍2/java编程思想
[root@localhost ~]# ls
anaconda-ks.cfg  书籍2
[root@localhost ~]# cp 书籍2/ /tmp/
cp: 略过目录"书籍2/"
我们需要加 -r
[root@localhost ~]# cp -r 书籍2/ /tmp/
[root@localhost ~]# ls /tmp/
abc
anaconda-ks.cfg
ks-script-p0Ci4J
systemd-private-12ece3aaa1eb44678dbf684dbccbc32f-vmtoolsd.service-xjTV7r
systemd-private-3588b18d27f94b80849d22621ef2defb-vmtoolsd.service-wmk7Jt
systemd-private-90f54554da784e52aeb890d207e41094-vmtoolsd.service-I8F1Yy
systemd-private-d5f08625d54e4ed3a0faa9b9fb944db5-vmtoolsd.service-4rHPpX
systemd-private-f68c251a6a6c46ac94b4c1e73090ef59-vmtoolsd.service-S6J6U0
systemd-private-f7cb736915b24ef2b72faee1ec860c6d-vmtoolsd.service-ldpfcl
yum.log
书籍2

这里我们用 ls -l  或者缩写命令 ll 来看下 文件的详细信息

```

### 5、剪切目录

```
剪切或改名命令：mv  (move)
mv [原文件或目录] [目标目录]
我们把 root下的 书籍2 剪切到 tmp 下 并且重命名
[root@localhost ~]# ls
anaconda-ks.cfg  书籍2
[root@localhost ~]# mv 书籍2 /tmp/书籍2哈哈
[root@localhost ~]# ls
anaconda-ks.cfg
[root@localhost ~]# ls /tmp/
abc
anaconda-ks.cfg
ks-script-p0Ci4J
systemd-private-12ece3aaa1eb44678dbf684dbccbc32f-vmtoolsd.service-xjTV7r
systemd-private-3588b18d27f94b80849d22621ef2defb-vmtoolsd.service-wmk7Jt
systemd-private-90f54554da784e52aeb890d207e41094-vmtoolsd.service-I8F1Yy
systemd-private-d5f08625d54e4ed3a0faa9b9fb944db5-vmtoolsd.service-4rHPpX
systemd-private-f68c251a6a6c46ac94b4c1e73090ef59-vmtoolsd.service-S6J6U0
systemd-private-f7cb736915b24ef2b72faee1ec860c6d-vmtoolsd.service-ldpfcl
yum.log
书籍2
书籍2哈哈
假如在同一个目录 那就是重新命令操作了
[root@localhost ~]# ls
anaconda-ks.cfg
[root@localhost ~]# mkdir -p 书籍2/java.pdf
[root@localhost ~]# ls
anaconda-ks.cfg  书籍2
[root@localhost ~]# mv 书籍2/ 牛逼/
[root@localhost ~]# ls
anaconda-ks.cfg  牛逼
[root@localhost ~]# ls 牛逼/
java.pdf
[root@localhost ~]# 
```

## 常见目录作用介绍

```
先切换到系统根目录 / 看看根目录下有哪些目录

[root@localhost ~]# cd /
[root@localhost /]# ls
bin   dev  home  lib64  mnt  proc  run   srv  tmp  var
boot  etc  lib   media  opt  root  sbin  sys  usr
[root@localhost /]# 

这里首先看下 根目录/ 下的 bin 和 sbin；
在user下也有bin和sbin
[root@localhost /]# ls usr/
bin  games    lib    libexec  sbin   src
etc  include  lib64  local    share  tmp
[root@localhost /]# 
根目录下的bin和sbin，usr目录下的bin和sbin，
这四个目录都是用来保存系统命令的。

bin: 
bin为binary的简写主要放置一些系统的必备执行档例如:cat、cp、chmod df、dmesg、gzip、kill、ls、mkdir、more、mount、rm、su、tar等。

/usr/bin:
主 要放置一些应用软体工具的必备执行档例如c++、g++、gcc、chdrv、diff、dig、du、eject、elm、free、gnome*、 gzip、htpasswd、kfm、ktop、last、less、locale、m4、make、man、mcopy、ncftp、 newaliases、nslookup passwd、quota、smb*、wget等。 

/sbin: 
主 要放置一些系统管理的必备程式例如:cfdisk、dhcpcd、dump、e2fsck、fdisk、halt、ifconfig、ifup、 ifdown、init、insmod、lilo、lsmod、mke2fs、modprobe、quotacheck、reboot、rmmod、 runlevel、shutdown等。

/usr/sbin:
放置一些网路管理的必备程式例如:dhcpd、httpd、imap、in.*d、inetd、lpd、named、netconfig、nmbd、samba、sendmail、squid、swap、tcpd、tcpdump等

bin目录下的命令普通用户和root用户都可以执行，
但是sbin下的命令只有root用户可以执行；

/boot目录，是启动目录，存的是启动相关的文件
该目录下不要乱存东西；

/dev设备文件保存目录
/etc配置文件保存目录
/home普通用户的家目录
/lib系统库保存目录
/mnt系统挂载目录
/media挂载目录
/root超级用户的家目录
/tmp临时目录
/proc直接写入内存的，虚拟文件系统
/sys直接写入内存的，虚拟文件系统
/var系统相关文档内容
```

## 链接命令

### 1、硬链接

==硬链接的一些特性，比如不能跨分区，不能针对目录使用，以及容易误操作文件。所以我们一般不推荐使用，我们推荐使用软链接，类似windows里的快捷方式；==

```
链接命令：ln  （link）
ln -s [源文件] [目标文件]
功能描述：生成链接文件
选项： -s 创建软链接

硬链接特征：
    1、拥有相同的i节点和存储block块，可以看作是同一个文件
    2、可通过i节点识别
    3、不能跨分区
    4、不能针对目录使用
    
例如 把/root下的anaconda-ks.cfg创建一个硬链接到/tmp下
[root@localhost ~]# ls
anaconda-ks.cfg  牛
[root@localhost ~]# ln /root/anaconda-ks.cfg /tmp/ana.hard
[root@localhost ~]# ll
总用量 4
-rw-------. 2 root root 1237 6月  10 05:24 anaconda-ks.cfg
drwxr-xr-x. 3 root root   22 6月  18 11:03 牛
[root@localhost ~]# ll /tmp/
总用量 16
-rw-------. 1 root root 1237 6月  18 10:12 abc
-rw-------. 1 root root 1237 6月  18 10:16 anaconda-ks.cfg
-rw-------. 2 root root 1237 6月  10 05:24 ana.hard
-rwx------. 1 root root  836 6月  10 05:24 ks-script-p0Ci4J
drwx------. 3 root root   17 6月  12 23:50 systemd-private-12ece3aaa1eb44678dbf684dbccbc32f-vmtoolsd.service-xjTV7r
drwx------. 3 root root   17 6月  17 17:42 systemd-private-3588b18d27f94b80849d22621ef2defb-vmtoolsd.service-wmk7Jt
drwx------. 3 root root   17 6月  16 04:51 systemd-private-90f54554da784e52aeb890d207e41094-vmtoolsd.service-I8F1Yy
drwx------. 3 root root   17 6月  10 05:26 systemd-private-d5f08625d54e4ed3a0faa9b9fb944db5-vmtoolsd.service-4rHPpX
drwx------. 3 root root   17 6月  14 06:38 systemd-private-f68c251a6a6c46ac94b4c1e73090ef59-vmtoolsd.service-S6J6U0
drwx------. 3 root root   17 6月  11 04:08 systemd-private-f7cb736915b24ef2b72faee1ec860c6d-vmtoolsd.service-ldpfcl
-rw-------. 1 root root    0 6月  10 05:19 yum.log
drwxr-xr-x. 3 root root   30 6月  18 10:24 书籍2
drwxr-xr-x. 3 root root   30 6月  18 10:24 书籍2哈哈
[root@localhost ~]# 

看着基本一样 我们用vim 打开文件 vim /root/anaconda-ks.cfg

我们随便修改下这个文件 （不要担心，这个文件是安装时候产生的，就算删除了也没事）
然后 esc  :wq 保存并且退出

我们打开/tmp下的ana.hard文件我们发现 硬链接内容也变了

假如我们修改 硬链接里的内容，anaconda-ks.cfg里的同样修改；
这里说明同一个文件相当于有两个入口，我们可以查看下两个入口的inode；
[root@localhost ~]# ls -i /root/anaconda-ks.cfg /tmp/ana.hard 
33574979 /root/anaconda-ks.cfg  33574979 /tmp/ana.hard
[root@localhost ~]# 
我们发现 inode节点号一样；
就算我们把其中一个删除，也不会影响文件另外一个的访问；
```

### 2、软连接

```
软链接特征：
1、类似Windows快捷方式；
2、软链接拥有自己的I节点和Block块，但是数据块中只保存原有文件的文件名和I节点号，并没有实际的文件数据；
3、lrwxrwxrwx l 软链接 软链接文件权限都为 rwxrwxrwx
4、修改任意文件，另一个都改变；
5、删除原文件，软链接不能使用；
```

==**软链接和硬链接的重要区别是 假如把原文件删除，软链接则不能用，但是假如是硬链接，删除原文件的话，不影响硬链的使用**==

```
我们在root下 用touch命令新建一个a文件
然后用echo命令追加点内容进入 ，再用cat命令打印下内容
[root@localhost ~]# touch a
[root@localhost ~]# 
[root@localhost ~]# cat /root/a
1111
[root@localhost ~]# 
然后在/tmp/下创建一个软链接
 ln -s /root/a /tmp/a.soft
我们来看下这个两个文件的inode节点
[root@localhost ~]# ls -li /root/a /tmp/a.soft
33575024 -rw-r--r--. 1 root root 5 6月  19 16:01 /root/a
17066258 lrwxrwxrwx. 1 root root 7 6月  19 16:41 /tmp/a.soft -> /root/a
我们发现是不一样的，我们可以把软链接理解成是指向原文件的引用 存的仅仅是地址，这样，假如原文件删除了，那软链接就没啥用了。但是这里 我们无论是修改两个文件的任意一个，另外一个始终能看到最终结果；
[root@localhost ~]# echo 2222 >> /root/a
[root@localhost ~]# cat /root/a
1111
2222
[root@localhost ~]# cat /tmp/a.soft
1111
2222
[root@localhost ~]# 
我们修改了原文件a 然后我们打开软链接 能看到结果
[root@localhost ~]# echo 3333 >> /tmp/a.soft
[root@localhost ~]# cat /tmp/a.soft
1111
2222
3333
[root@localhost ~]# cat /root/a
1111
2222
3333
[root@localhost ~]# 
我们修改软链接，用a.soft 和a打开 都能看到最终结果；
假如我们删除原文件
[root@localhost ~]# rm -rf /root/a
[root@localhost ~]# cat /tmp/a.soft
cat: /tmp/a.soft: 没有那个文件或目录
[root@localhost ~]# 
我们打开软链接 报错。
```



### 软连接使用注意事项

如果链接无效 则如下图所示

![image-20230519171116721](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230519171116721.png)





## 文件检索命令

### 1、locate

如果出现换屏的情况 可以使用 |less 翻页显示

例如 locate java | less

locate搜索速度比较快

使用之前需要先安装 yum -y install mlocate

==**locate优点是 搜索速度快 ，缺点是只能按文件名搜索**==

```
locate 文件名
在后台数据库中按文件名搜索，搜索速度更快
/var/lib/mlocate
#locate命令所搜索的后台数据库
在搜索之前需要先更新下后台的数据库 否则不会被来记录 就无法搜索到
updatedb
更新数据库
如果不想每次手动执行updatedb 可以通过修改配置文件方式 字段刷新数据库
更新数据库配置文件/etc/updatedb.conf配置文件
vim /etc/updatedb.conf 配置文件

    这里PRUNE_BIND_MOUNTS="yes"开启搜索限制
    PRUNEFS = 搜索时，不搜索的文件系统
    PRUNENAMES = 搜索时，不搜索的文件类型
    PRUNEPATHS = 搜索时，不搜索的路径
    比如这里的tmp路径的文件 默认配置是搜索不到的；
```

![image-20230517205311514](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/image-20230517205311514.png)

### 2、whereis 与 which

```
whereis 命令名
#搜索命令所在路径及帮助文档所在位置
选项：
 -b :只查找可执行文件位置
 -m:只查找帮助文件

[root@localhost ~]# whereis ls
ls: /usr/bin/ls /usr/share/man/man1/ls.1.gz
[root@localhost ~]# whereis -b ls
ls: /usr/bin/ls
[root@localhost ~]# whereis -m ls
ls: /usr/share/man/man1/ls.1.gz
我们可以查到ls命令的位置以及帮助文档的位置


which 文件名
搜索命令所在路径及别名
[root@localhost ~]# which ls
alias ls='ls --color=auto'
	/usr/bin/ls
相比 ，多了个别名；
```

### 3、find

```
find [搜索范围] [搜索条件]
#搜索文件
find / -name install.log
#避免大范围搜索，会非常耗费系统资源
#find是在系统当中搜索符合条件的文件名。如果需要匹配，
使用通配符匹配，通配符是完全匹配。


[root@localhost ~]# find / -name 牛
[root@localhost ~]# 
我们发现 搜索牛牛 能搜索到结果，但是搜索牛，么有结果，
所以说 find搜索 是完全匹配搜索；

如果我们需要进行模糊查询，我们要使用通配符；
* 匹配任意内容  
?匹配任意一个字符 
[]匹配任意一个中括号的字符

[root@localhost ~]# find / -name "牛*"
/root/牛牛
/root/牛牛2
/root/牛牛3
/root/牛牛5
[root@localhost ~]# 


查找10天前修改的文件
find /var/log/ -mtime +10
-10 10天内修改的文件
10 10天当前修改的文件
+10 10天前修改的文件
atime 文件访问时间
ctime 改变文件属性
mtime 修改文件内容
[root@localhost ~]# find /var/log -mtime +10
/var/log/ppp
查找10天前的日志
find /root  -size 2k
查找文件大小是1到2KB的文件（进一法）
-2k 小于2KB的文件
2k 等于2KB的文件
+2k 大于2KB的文件
find /root -inum 262422
查找i节点是262422的文件
```

### 4、grep

```
grep [选项] 字符串 文件名
在文件当中匹配符合条件的字符串
选项：
-i 忽略大小写
-v 排除指定字符串
[root@localhost ~]# grep "work" anaconda-ks.cfg 
# Network information
network  --bootproto=dhcp --device=ens33 --onboot=off --ipv6=auto --no-activate
network  --hostname=localhost.localdomain
[root@localhost ~]# 
我们可以找到anaconda-ks.cfg 文件中含有"work"字符串的行

[root@localhost ~]# grep -v  "work" anaconda-ks.cfg 
#version=DEVEL
#sdfsddsd
 System authorization information
auth --enableshadow --passalgo=sha512
# Use CDROM installation media
cdrom
# Use graphical install
graphical
# Run the Setup Agent on first boot
firstboot --enable
# Keyboard layouts
keyboard --vckeymap=cn --xlayouts='cn'
# System language
lang zh_CN.UTF-8

# Root password
rootpw --iscrypted $6$G7eVijyXAp8DMSXi$bKh/vjEbEdH.4WmgXjhpw08/jYzjGDgaTnc8ZNFfUREgFX0Kepz39OwQsjhlFBaYUPwUUuI.RcsCAgkqrWeSJ1

# System services
services --disabled="chronyd"
# System timezone
timezone Asia/Shanghai --isUtc --nontp
# System bootloader configuration
bootloader --append=" crashkernel=auto" --location=mbr --boot-drive=sda
autopart --type=lvm
# Partition clearing information
clearpart --none --initlabel
%packages
@^minimal
@core
kexec-tools
%end
%addon com_redhat_kdump --enable --reserve-mb='auto'
%end
%anaconda
pwpolicy root --minlen=6 --minquality=50 --notstrict --nochanges --notempty
pwpolicy user --minlen=6 --minquality=50 --notstrict --nochanges --notempty
pwpolicy luks --minlen=6 --minquality=50 --notstrict --nochanges --notempty
%end
[root@localhost ~]# 
加了 -v 就查找不包含"work"的行；
```

![image-20230517212418119](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/image-20230517212418119.png)

## 关机和重启命令

```
shutdown命令
shutdown [选项] 时间
-c：取消前一个关机命令
-h：关机
-r：重启
[root@localhost ~]# date
2023年 05月 17日 星期三 21:48:33 CST
[root@localhost ~]# shutdown -r 21:48
Shutdown scheduled for 三 2023-05-17 21:48:00 CST, use 'shutdown -c' to cancel.
[root@localhost ~]# 
可以指定重启时间
[root@localhost ~]# shutdown -c
Broadcast message from root@localhost.localdomain (Wed 2023-05-17  CST):
[root@localhost ~]# 
用-c终止
shutdown -r now 是立即重启
shutdown -h now 是立即关机
其他关机命令 (不太安全 不建议使用  )
halt  
poweroff
init 0
其他重启命名
reboot  (安全)
init 6
系统运行级别
0 关机
1 单用户
2 不完全多用户，不含NFS服务
3 完全多用户
4 未分配
5 图形界面
6 重启
 cat /ect/inittab
修改系统默认运行级别
id:3:initdefalult
runlevel
查询系统运行级别
退出登录命令
logout
```

## 压缩和解压缩命令

### 1、zip

```
常用压缩格式：.zip .gz .bz2
常用压缩格式：.tar.gz  .tar.bz2
zip格式压缩
使用前需要先安装
yum -y install zip

zip压缩文件名 源文件	压缩文件
zip -r 压缩文件名 源目录	压缩目录

unzip解压缩
安装解压缩命令
yum install -y unzip

[root@localhost ~]# unzip 牛牛.zip
```

### 2、gz

```
.gz格式压缩
gzip 源文件
压缩为.gz格式的压缩文件，源文件会消失
gzip -c 源文件 > 压缩文件
压缩为.gz格式，源文件保留
例如：gzip -c 书籍 > 书籍.gz
gzip -r 目录
压缩目录下所有的子文件，但是不能压缩目录

[root@localhost ~]# gzip -r 书籍

gz格式解压缩
gzip -d 压缩文件

解压缩文件
gunzip 压缩文件
解压缩文件
[root@localhost ~]# gzip -d 牛牛.gz
```

### 3、bz2

yum -y install bzip2

```
.bz2格式压缩
默认会删除源文件 如果需要保留 加-k
bzip2 源文件
压缩为.bz2格式，不保留源文件
bzip2 -k 源文件
压缩之后保留源文件
注意：bzip2命令不能压缩目录
源文件没了，假如要保留源文件 bzip2 -k 牛牛

bunzip2 解压缩文件
-k 保留压缩文件
```

### 4、tar

```
打包命令tar
tar -cvf 打包文件名 源文件
选项：
-c ：打包
-v ：显示过程
-f ：指定打包后的文件名
例如
tar -cvf 牛牛.tar 牛牛


把书籍.tar压缩gz
[root@localhost ~]# gzip 书籍.tar 
[root@localhost ~]# ls
anaconda-ks.cfg  牛牛  书籍  书籍.tar.gz
[root@localhost ~]# 


解打包命令
tar -xvf 打包文件名
选项：
-x : 解打包
例如：
tar -xvf 书籍.tar
[root@localhost ~]# bzip2 -d 书籍.tar.bz2
[root@localhost ~]# ls
anaconda-ks.cfg  牛牛  书籍  书籍.tar
[root@localhost ~]# tar -xvf 书籍.tar
书籍/
书籍/java.pdf
书籍/php.pdf
书籍/asp.pdf
[root@localhost ~]# ls
anaconda-ks.cfg  牛牛  书籍  书籍.tar
[root@localhost ~]# ls 书籍/
asp.pdf  java.pdf  php.pdf
[root@localhost ~]# 
```

### 5、.tar.gz压缩格式

```
其实.tar.gz格式是先打包为.tar格式，再压缩为.gz格式
tar -zcvf 压缩名.tar.gz 源文件
选项：
-z ：压缩为.tar.gz格式
tar -zxvf 压缩包名.tar.gz
选项：
-x：解压缩.tar.gz格式

.tar.bz2压缩格式
其实.tar.bz2格式是先打包为.tar格式，再压缩为.bz2格式
tar -jcvf 压缩名.tar.bz2 源文件
选项：
-j ：压缩为.tar.bz2格式
tar -zxvf 压缩包名.tar.bz2
选项：
-x：解压缩.tar.bz2格式
[root@localhost ~]# rm -rf 书籍.tar
[root@localhost ~]# ls
anaconda-ks.cfg  牛牛  书籍
[root@localhost ~]# tar -zcvf 书籍.tar.gz 书籍
书籍/
书籍/java.pdf
书籍/php.pdf
书籍/asp.pdf
[root@localhost ~]# ls
anaconda-ks.cfg  牛牛  书籍  书籍.tar.gz
[root@localhost ~]# 
压缩tar.gz
[root@localhost ~]# rm -rf 书籍
[root@localhost ~]# tar -zxvf 书籍.tar.gz
书籍/
书籍/java.pdf
书籍/php.pdf
书籍/asp.pdf
[root@localhost ~]# ls
anaconda-ks.cfg  牛牛  书籍  书籍.tar.gz
[root@localhost ~]# 
解压缩tar.gz
[root@localhost ~]# ls
anaconda-ks.cfg  牛牛  书籍  书籍.tar.gz
[root@localhost ~]# tar -jcvf 书籍.tar.bz2 书籍
书籍/
书籍/java.pdf
书籍/php.pdf
书籍/asp.pdf
[root@localhost ~]# ls
anaconda-ks.cfg  牛牛  书籍  书籍.tar.bz2  书籍.tar.gz
[root@localhost ~]# 
压缩tar.bz2
[root@localhost ~]# tar -jxvf 书籍.tar.bz2 -C /tmp/
书籍/
书籍/java.pdf
书籍/php.pdf
书籍/asp.pdf
```

## 文件内容操作

```
光标移动到行首  
按 Esc 键进入正常模式。
按0键将光标移动到行首（第 0 列）
按$键将光标移动到行尾（最后一列）

首行和末尾
定位到第一行：   	1 + shift + G
定位到最后一行：	shift + G
定位到第x行：      x + shift + G

显示行号
:set nu

快速删除一行
dd

快速复制一行
首先光标所在的一行 按两次yy
然后按$ 到行尾 在按p进行黏贴

查找文件中的内容
/要查找的内容

保存并退出
:wq
不保存退出
:q!

```





## 防火墙添加添加端口

```
 firewall-cmd --zone=public --add-port=21/tcp --permanent		永久添加tcp 21端口
 firewall-cmd --permanent --zone=public --add-service=ftp		添加ftp服务
 firewall-cmd --reload	重启防火墙
```

