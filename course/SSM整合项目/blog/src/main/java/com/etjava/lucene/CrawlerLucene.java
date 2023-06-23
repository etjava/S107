package com.etjava.lucene;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.etjava.bean.CrawlerBlog;
import com.etjava.util.DateUtil;
import com.etjava.util.PropertiesUtil;
import com.etjava.util.StringUtil;
/**
 * 	 全文检索
 * @author etjava
 *
 */

public class CrawlerLucene {

	private Directory dir;
	
	
	
	/**
	 * 获取IndexWriter实例
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter()throws Exception{
		String path = PropertiesUtil.getValue("lucenePath1");
		dir=FSDirectory.open(Paths.get(path));
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}
	
	/**
	 * 	更新词片索引
	 * @param blog
	 * @throws Exception
	 */
	public void updateIndex(CrawlerBlog blog) throws Exception{
		IndexWriter writer=getWriter();
		Document doc=new Document();
		doc.add(new StringField("id",String.valueOf(blog.getId()),Field.Store.YES));
		doc.add(new TextField("title",blog.getTitle(),Field.Store.YES));
		doc.add(new StringField("releaseDate",DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
		doc.add(new TextField("content",blog.getContentNoTag(),Field.Store.YES));
		writer.updateDocument(new Term("id",String.valueOf(blog.getId())), doc);
		writer.close();
		
	}
	
	/**
	 * 添加博客索引
	 * @param blog
	 * @throws Exception
	 */
	public void addIndex(CrawlerBlog blog)throws Exception{
		IndexWriter writer=getWriter();
		Document doc=new Document();
		// StringField 方法不需要作为分词保存  TextField 是需要作为分词保存
		doc.add(new StringField("id",String.valueOf(blog.getId()),Field.Store.YES));
		doc.add(new TextField("title",blog.getTitle(),Field.Store.YES));
		doc.add(new StringField("releaseDate",DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
		doc.add(new TextField("content",blog.getContentNoTag(),Field.Store.YES));
		writer.addDocument(doc);
		writer.close();
	}
	
	/**
	 * 	全文检索 - 匹配标题内容为主
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<CrawlerBlog> search(String keyword) throws Exception{
		List<CrawlerBlog> list = new LinkedList<>();
		// 读取词片内容
		dir = FSDirectory.open(Paths.get("c://lucene"));
		IndexReader reader = DirectoryReader.open(dir);
		// 
		IndexSearcher is = new IndexSearcher(reader);
		// 多条件查询 title,content
		BooleanQuery.Builder query = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		// title内容查询
		QueryParser parser = new QueryParser("title",analyzer);
		Query q = parser.parse(keyword);// 解析title中词片
		// content内容查询
		QueryParser parser2 = new QueryParser("content",analyzer);
		Query q2 = parser2.parse(keyword);// 解析content中词片
		
		// BooleanClause.Occur.SHOULD 查询条件 SHOULD | 的关系
		query.add(q,BooleanClause.Occur.SHOULD);
		query.add(q2,BooleanClause.Occur.SHOULD);
		// 开始检索 query.build() 多条件时使用booleanQuery.build 进行构造   1000 表示返回的记录数
		TopDocs hits = is.search(query.build(), 1000);
// 计算得分 - 根据title内容计算  得分高的会放在最前边
		QueryScorer score = new QueryScorer(q);
		// 代码高亮显示
		Fragmenter fragmenter = new SimpleSpanFragmenter(score);
		// 替换html标签
		SimpleHTMLFormatter formater = new SimpleHTMLFormatter("<b><font color=red>","</font></b>");
		// 高亮显示
		Highlighter highlighter = new Highlighter(formater,score);
		highlighter.setTextFragmenter(fragmenter);
		
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			CrawlerBlog blog=new CrawlerBlog();
			blog.setId(Integer.parseInt(doc.get("id")));
			String title=doc.get("title");
			// apache公共包提供的 转换html标签
			String content=StringEscapeUtils.escapeHtml(doc.get("content")) ;
			blog.setReleaseDateStr(doc.get("releaseDate"));
			if(title!=null){
				TokenStream tokenStream=analyzer.tokenStream("title", new StringReader(title));
				// 设置title高亮
				String hTitle=highlighter.getBestFragment(tokenStream, title);
				if(StringUtil.isEmpty(hTitle)){
					blog.setTitle(title);
				}else{
					blog.setTitle(hTitle);
				}
			}
			if(content!=null){
				TokenStream tokenStream=analyzer.tokenStream("content", new StringReader(content));
				// 设置content高亮显示
				String hContent=highlighter.getBestFragment(tokenStream, content);
				if(StringUtil.isEmpty(hContent)){
					if(content.length()<=200){
						blog.setContent(content);						
					}else{
						blog.setContent(content.substring(0, 200));	
					}
				}else{
					blog.setContent(hContent);
				}
			}
			list.add(blog);
		}
		return list;
	}
	
	/**
	 * 	删除指定博客的lucene索引
	 */
	public void deleteIndex(String blogId) throws Exception{
		IndexWriter writer = getWriter();
		writer.deleteDocuments(new Term("id",blogId));
		// 强制删除 - 不强制删除 不会立即删除
		writer.forceMergeDeletes();
		
		writer.commit();
		writer.close();
	}
}
