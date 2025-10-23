package com.recipetime.find.DAO;

import com.recipetime.find.Model.PostSequence;

public interface SequenceDAO {
	void insertSequence(PostSequence sequences);
	
	void updateSequence(PostSequence sequences);
	
	void deleteSequence(PostSequence sequences);
}
