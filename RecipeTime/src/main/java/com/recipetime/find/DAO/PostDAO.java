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

public interface PostDAO {
	void insertPost(Post post);
    void insertTags(List<Tag> tags);
    void insertIngredients(List<Ingredients> ingredients);
    void insertSequences(List<PostSequence> sequences);
    void insertAttachments(List<Attachment> attachments);

    List<Post> getAllPosts();
    Post getPostById(Map<String, Object> params);
    void deactivatePost(int recipeid);

    // category 관련 (post 내부에 포함)
    List<CategoryItem> listCategoryItems();
    List<CategoryOption> listCategoryOptionsByItemId(int itemid);
}
