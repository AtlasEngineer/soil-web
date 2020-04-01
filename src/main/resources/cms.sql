#namespace("cms")

###根据类目获取文章列表
#sql("selectCmsArticlesByCategoryId")
select ca.* from cms_article_category cac left join cms_article ca on cac.article_id=ca.article_id join (
			select article_id from cms_article order by article_id desc
		) ca_order on ca_order.article_id=ca.article_id 
		where ca.status=1 and cac.category_id=#para(0)
#end

#sql("findCmsArticlesByCategoryId")
select ca.* from cms_article_category cac left join cms_article ca on cac.article_id=ca.article_id join (
			select article_id from cms_article order by #(orderBy??'article_id') desc
		) ca_order on ca_order.article_id=ca.article_id 
		where ca.status=#para(status) and cac.category_id=#para(categoryId)
#end


###根据标签获取文章列表
#sql("selectCmsArticlesByTagId")
select ca.* from cms_article_tag cat left join cms_article ca on cat.article_id=ca.article_id join (
			select article_id from cms_article order by article_id desc
		) ca_order on ca_order.article_id=ca.article_id 
		where ca.status=1 and cat.tag_id=#para(0)
#end

#sql("findListByTagId")
select ca.* from cms_article_tag cat left join cms_article ca on cat.article_id=ca.article_id join (
			select article_id from cms_article order by #(orderBy??'article_id') desc
		) ca_order on ca_order.article_id=ca.article_id 
		where ca.status=#para(status) and cat.tag_id=#para(tagId)
#end

###相关文章，根据tag关联
#sql("findRelevantListByArticleId")
select * from cms_article a left join cms_article_tag m on a.article_id = m.article_id 
	where m.tag_id in (
                #for(x:tagIds)
                    #(for.index == 0 ? "" : ",")  #para(x)
                #end
        ) and a.article_id=#para(articleId) and a.status=#para(status) 
#end

###根据文章获取分类列表
#sql("findCategoryListByArticleId")
select ca.* from cms_article_category cat left join cms_category ca on cat.category_id=ca.category_id 
		where cat.article_id=#para(0)
#end

#end