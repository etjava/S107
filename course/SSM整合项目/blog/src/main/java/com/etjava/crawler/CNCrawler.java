package com.etjava.crawler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etjava.bean.CrawlerBlog;
import com.etjava.service.CrawlerBlogService;
import com.etjava.util.DateUtil;
import com.etjava.util.PropertiesUtil;
import com.vdurmont.emoji.EmojiParser;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Status;

/**
 * CNBLOG文章
 * @author etjav
 *
 */
public class CNCrawler {
	
	private static CrawlerBlogService crawlerBlogService;
	
	private static String realPath;

	public CNCrawler(CrawlerBlogService crawlerBlogService,String realPath){
		this.crawlerBlogService = crawlerBlogService;
		this.realPath=realPath;
		parseHome();
	}
	
	public static void main(String[] args) {
		parseHome();
	}
	
	private static Logger logger = LoggerFactory.getLogger(CNCrawler.class);
	
	private static final String URL = "https://www.cnblogs.com/";
	
	private static Integer total = 0;
	
	// 缓存管理器
	private static CacheManager manager;
	// 缓存对象
	private static Cache cache;
	
	/**
	 * 解析首页内容
	 */
	private static void parseHome() {
		logger.info("第["+(total++)+"]次爬取开始 "+URL);
		manager = CacheManager.create(PropertiesUtil.getValue("cacheFilePath"));
		// 获取cache对象
		cache = manager.getCache("cnblog");
		
		
		
		
		// 获取HttpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建get方式请求
		HttpGet httpGet = new HttpGet(URL);
		// 配置请求消息
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(1*1000*10) // 配置读取超时时间 毫秒单位
				.setConnectTimeout(5000) // 配置链接时间 毫秒单位
				.build();
		httpGet.setConfig(config);

		CloseableHttpResponse response = null;
		
		try {
			// 执行get请求
			response = httpClient.execute(httpGet);
			
		} catch (IOException e) {
			logger.error("请求 ["+URL+"] 时发生了异常 ",e);
			e.printStackTrace();
		}
		
		if(response!=null) {
			// 获取返回实体
			HttpEntity entity = response.getEntity();
			// 判断返回状态是否是200
			if(response.getStatusLine().getStatusCode()==200) {
				String webContent = "";
				try {
					// 获取网页中的内容
					webContent = EntityUtils.toString(entity, "utf-8");
					//System.out.println(webContent);
					parseHomeLink(webContent);
				} catch (ParseException | IOException e) {
					logger.error("解析链接["+URL+"]返回的实体异常 ",e);
					e.printStackTrace();
				}
			}else {
				logger.info("获取链接["+URL+"]返回的状态是  "+response.getStatusLine().getStatusCode());
			}
		}else {
			logger.error("请求 ["+URL+"] 时 链接超时");
		}
		
		// 关闭资源
		try {
			if(response!=null)
				response.close();
			if(httpClient!=null)
				httpClient.close();
		} catch (IOException e) {
			logger.error("释放资源发生错误 ");
			e.printStackTrace();
		}
		
		if(cache.getStatus()==Status.STATUS_ALIVE) {
			// 刷新缓存 否则无法写入到文件中
			cache.flush();
		}
		manager.shutdown();
		
		logger.info("第["+(total++)+"]次爬取结束 "+URL);
	}
	
	/**
	 * 解析首页内容 提取博客链接地址
	 * @param webContent - 首页内容
	 */
	private static void parseHomeLink(String webContent) {
		if("".equals(webContent)) {
			return ;
		}
		logger.info("解析首页内容 提取文章链接  开始");
		// 解析首页内容获取document对象
		Document document = Jsoup.parse(webContent);
		// 获取指定的链接地址  因为存在子的a标签 因此这里直接使用clss定位到需要的链接标签中
		Elements links = document.select("#post_list .post-item .post-item-body .post-item-text .post-item-title");
		
		for(int i=0;i<links.size();i++) {
			Element link = links.get(i);
			String url = link.attr("href");
			//System.out.println(url);
			
			
			// 判断是否抓取过当前链接 如果抓取过了 则本次舍弃执行
			if(cache.get(url)!=null) {
				logger.info("已抓取当前链接博客信息 " + URL);
				continue;
			}else {
				// 根据链接地址解析博客内容
				parseLink(url);
			}
		}
		logger.info("解析首页内容 提取文章链接  结束");
	}
	
	
	/**
	 * 解析博客链接地址获取博客内容(根据链接地址获取对应的博客文章)
	 * @param link
	 */
	private static void parseLink(String link) {
		logger.info("解析["+URL+"] 的博客内容 开始 ");
		// 获取HttpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建get方式请求
		HttpGet httpGet = new HttpGet(link);
		// 配置请求消息
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(1*1000*10) // 配置读取超时时间 毫秒单位
				.setConnectTimeout(5000) // 配置链接时间 毫秒单位
				.build();
		httpGet.setConfig(config);

		CloseableHttpResponse response = null;
		
		try {
			// 执行get请求
			response = httpClient.execute(httpGet);
			
		} catch (IOException e) {
			logger.error("请求 ["+URL+"] 时发生了异常 ",e);
			e.printStackTrace();
		}
		
		if(response!=null) {
			// 获取返回实体
			HttpEntity entity = response.getEntity();
			// 判断返回状态是否是200
			if(response.getStatusLine().getStatusCode()==200) {
				String blogContent = "";
				try {
					// 获取博客中的内容
					blogContent = EntityUtils.toString(entity, "utf-8");
					
					// 解析获取到的内容 提取有效信息
					parseBlogPage(blogContent,link);
				} catch (ParseException | IOException e) {
					logger.error("解析链接["+URL+"]返回的实体异常 ",e);
					e.printStackTrace();
				}
			}else {
				logger.info("获取链接["+URL+"]返回的状态是  "+response.getStatusLine().getStatusCode());
			}
		}else {
			logger.error("请求 ["+URL+"] 时 链接超时");
		}
		
		// 关闭资源
		try {
			if(response!=null)
				response.close();
			if(httpClient!=null)
				httpClient.close();
		} catch (IOException e) {
			logger.error("释放资源发生错误 ");
			e.printStackTrace();
		}
		
		logger.info("解析["+URL+"] 的博客内容结束 ");
	}
	
	/**
	 * 解析博客内容 提取有效信息  
	 * @param blogContent
	 * @param link
	 */
	private static void parseBlogPage(String blogContent,String link) {
		if("".equals(blogContent)) {
			return ;
		}
		Document document = Jsoup.parse(blogContent);
		Elements titles = document.select("#cb_post_title_url span");
		if(titles.size()==0) {
			logger.error(link + " - 未获取到标题内容");
			return;
		}
		// 获取博客标题
		String title = titles.get(0).text();
		//System.out.println("title - "+title);
		// 获取博客内容  - 包含标签的文章内容
		Elements contents = document.select("#cnblogs_post_body");
		if(contents.size()==0) {
			logger.error(link + " - 未获取到博客内容");
			return;
		}
		// 获取带有html标签的文章内容
		String content = contents.get(0).html();
		String content2 = EmojiParser.removeAllEmojis(content);
		//System.out.println("content - "+content2);
		
		// 查找所有img标签 将图片保存到本地指定目录
		Elements images = contents.select("img");
		List<String> imgList = new ArrayList<>();
		for(int i=0;i<images.size();i++) {
			String imgUrl = images.get(i).attr("src");
			imgList.add(imgUrl);
		}
		
		if(imgList.size()>0) {
			// 下载图片到本地 返回 原始地址-新地址 map集合   后边需要替换到文章中的img src属性中
			Map<String, String> imageMap = downloadImages(imgList);
			// 替换文章中图片地址 返被修改好之后的文章内容
			content2 = replaceContentImages(imageMap,content2);
		}
		
		CrawlerBlog blog = new CrawlerBlog();
		blog.setTitle(title);
		blog.setContent(content2);
		blog.setOriginalUrl(link);
		
		// 保存到数据库中
		Integer total = 0;
		try {
			total = crawlerBlogService.add(blog );
		} catch (Exception e) {
			logger.error(link+"  数据保存失败",e);
			e.printStackTrace();
		}
		
		
		if(total>0) {
			logger.info(link+"  数据保存成功");
			// 将链接加入到缓存中
            cache.put(new net.sf.ehcache.Element(link, link));
		}else {
			logger.error(link+"  数据保存失败");
		}
	}
	
	/**
	 * 替换文章中的img标签值 然后返回替换好之后的文章内容
	 * @param imageMap key=原始地址  value=本地地址
	 * @param content  文章原始内容
	 * @return
	 */
	private static String replaceContentImages(Map<String, String> imageMap,String content) {
		for(String url:imageMap.keySet()) {
			String newPath = imageMap.get(url);// 根据原始地址获取新的地址
			content = content.replace(url, newPath);
		}
		
		return content;
	}

	/**
	 * 下载图片
	 * @param imgList 图片地址集合
	 */
	private static Map<String,String> downloadImages(List<String> imgList) {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 配置请求消息
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(1*1000*10) // 配置读取超时时间 毫秒单位
				.setConnectTimeout(5000) // 配置链接时间 毫秒单位
				.build();
		
		
		Map<String,String> map = new HashMap<>();
		for(String url:imgList) {
			
			// 剔除小图标的抓取
			String s = url.substring(url.length()-4, url.length());
			if(s.contains("gif")) {
				continue;
			}
			
			// 创建get方式请求
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(config);
			try {
				Thread.sleep(1000);
				
				logger.info("开始爬取"+url+"图片");
				CloseableHttpResponse response=httpClient.execute(httpGet);
				if(response!=null) {
					// 获取返回实体
					HttpEntity entity = response.getEntity();
					// 判断返回状态是否是200
					if(response.getStatusLine().getStatusCode()==200) {
						try {
							// 将图片转为InputStream
							InputStream is = entity.getContent();
							// 文件类型 例如 image/jpg   image/png
							String imageType = entity.getContentType().getValue();
							String b = imageType.split("/")[1];
							
							// common-io.FileUtils .copyToFile 直接将文件下载到本地指定目录
							String uuid = UUID.randomUUID().toString();
							// 图片在项目中的位置
							String fileURI = DateUtil.currentDatePath()+"/"+uuid+"."+b;
							// 图片保存的地址
							//String newPath = PropertiesUtil.getValue("imageFilePath")+fileURI;
							
							FileUtils.copyToFile(is,new File(realPath+"static/blogImages/"+fileURI));
							
							// 封装图片信息返回map
							map.put(url, PropertiesUtil.getValue("imageUrl")+fileURI);
						} catch (UnsupportedOperationException | IOException e) {
							e.printStackTrace();
						}
					}else {
						logger.error("获取链接["+URL+"]返回的状态是  "+response.getStatusLine().getStatusCode());
					}
				}else {
					logger.error("请求 ["+URL+"] 时 链接超时");
				}
				
				logger.info("结束爬取"+url+"图片");
				
				// 关闭资源
				try {
					if(response!=null)
						response.close();
				} catch (IOException e) {
					logger.error("释放资源发生错误 ");
					e.printStackTrace();
				}
			} catch (Exception e) {
				logger.error("抓取图片异常 "+url,e);
				e.printStackTrace();
			}
		}
		
		return map;
	}
	
}
