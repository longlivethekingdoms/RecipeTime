package com.recipetime.find.DAO;

import java.util.List;
import java.util.Map;

import com.recipetime.find.Model.Attachment;
import com.recipetime.find.Model.CategoryItem;
import com.recipetime.find.Model.CategoryOption;
import com.recipetime.find.Model.Ingredients;
import com.recipetime.find.Model.Post;
import com.recipetime.find.Model.PostSequence;
import com.recipetime.find.Model.Tag;
import com.recipetime.find.pager.Pager;

public interface PostDAO {
	void insertPost(Post post);
    void insertTags(List<Tag> tags);
    void insertIngredients(List<Ingredients> ingredients);
    void insertSequence(PostSequence seq);
    void insertSequences(List<PostSequence> sequences);
    void insertAttachments(List<Attachment> attachments);
    void insertseqAttachments(List<Attachment> seqattachments);
    
    List<Post> getAllPosts();
    Post getPostById(Map<String, Object> params);
    void updatePost(Post post);
    void deactivatePost(int recipeid);
	void activatePost(int recipeid);

    // category 관련 (post 내부에 포함)
    List<CategoryItem> listCategoryItems();
    List<CategoryOption> listCategoryOptionsByItemId(int itemid);
    
	int posttotal(Pager pager);
	List<Post> postlist(Pager pager);
	
	Post getPostDetail(Map<String, Object> paramMap);

	
}
