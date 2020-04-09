/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.atlas.server.model.sql;

import com.lambkit.db.sql.ConditionMode;
import com.lambkit.db.sql.column.Column;
import com.lambkit.db.sql.column.Columns;
import com.lambkit.db.sql.column.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-09
 * @version 1.0
 * @since 1.0
 */
public class ReplyCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static ReplyCriteria create() {
		return new ReplyCriteria();
	}
	
	public static ReplyCriteria create(Column column) {
		ReplyCriteria that = new ReplyCriteria();
		that.add(column);
        return that;
    }

    public static ReplyCriteria create(String name, Object value) {
        return (ReplyCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_reply", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public ReplyCriteria eq(String name, Object value) {
    	super.eq(name, value);
        return this;
    }

    /**
     * not equals !=
     *
     * @param name
     * @param value
     * @return
     */
    public ReplyCriteria ne(String name, Object value) {
    	super.ne(name, value);
        return this;
    }


    /**
     * like
     *
     * @param name
     * @param value
     * @return
     */

    public ReplyCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public ReplyCriteria notLike(String name, Object value) {
    	super.notLike(name, value);
        return this;
    }

    /**
     * 大于 great than
     *
     * @param name
     * @param value
     * @return
     */
    public ReplyCriteria gt(String name, Object value) {
    	super.gt(name, value);
        return this;
    }

    /**
     * 大于等于 great or equal
     *
     * @param name
     * @param value
     * @return
     */
    public ReplyCriteria ge(String name, Object value) {
    	super.ge(name, value);
        return this;
    }

    /**
     * 小于 less than
     *
     * @param name
     * @param value
     * @return
     */
    public ReplyCriteria lt(String name, Object value) {
    	super.lt(name, value);
        return this;
    }

    /**
     * 小于等于 less or equal
     *
     * @param name
     * @param value
     * @return
     */
    public ReplyCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public ReplyCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public ReplyCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public ReplyCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public ReplyCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public ReplyCriteria add(Column column) {
    	super.add(column);
    	return this;
    }
    
    /**************************/
	
	public void addCriterion(String name, Object value, ConditionMode logic, String property, String typeHandler, String valueType) {
		 if (value == null) {
			 throw new RuntimeException("Value for " + property + " cannot be null");
		 }
		 add(Column.create(name, value, logic, typeHandler, valueType));
	}
   
	public void addCriterion(String name, Object value1, Object value2, ConditionMode logic, String property, String typeHandler, String valueType) {
		 if (value1 == null || value2 == null) {
			 throw new RuntimeException("Between values for " + property + " cannot be null");
		 }
		 add(Column.create(name, value1, value2, logic, typeHandler, valueType));
	}
		 
	public ReplyCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public ReplyCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public ReplyCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public ReplyCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public ReplyCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andCommentIdIsNull() {
		isnull("comment_id");
		return this;
	}
	
	public ReplyCriteria andCommentIdIsNotNull() {
		notNull("comment_id");
		return this;
	}
	
	public ReplyCriteria andCommentIdIsEmpty() {
		empty("comment_id");
		return this;
	}

	public ReplyCriteria andCommentIdIsNotEmpty() {
		notEmpty("comment_id");
		return this;
	}
       public ReplyCriteria andCommentIdEqualTo(java.lang.Integer value) {
          addCriterion("comment_id", value, ConditionMode.EQUAL, "commentId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andCommentIdNotEqualTo(java.lang.Integer value) {
          addCriterion("comment_id", value, ConditionMode.NOT_EQUAL, "commentId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andCommentIdGreaterThan(java.lang.Integer value) {
          addCriterion("comment_id", value, ConditionMode.GREATER_THEN, "commentId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andCommentIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("comment_id", value, ConditionMode.GREATER_EQUAL, "commentId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andCommentIdLessThan(java.lang.Integer value) {
          addCriterion("comment_id", value, ConditionMode.LESS_THEN, "commentId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andCommentIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("comment_id", value, ConditionMode.LESS_EQUAL, "commentId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andCommentIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("comment_id", value1, value2, ConditionMode.BETWEEN, "commentId", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andCommentIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("comment_id", value1, value2, ConditionMode.NOT_BETWEEN, "commentId", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andCommentIdIn(List<java.lang.Integer> values) {
          addCriterion("comment_id", values, ConditionMode.IN, "commentId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andCommentIdNotIn(List<java.lang.Integer> values) {
          addCriterion("comment_id", values, ConditionMode.NOT_IN, "commentId", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andReplyIdIsNull() {
		isnull("reply_id");
		return this;
	}
	
	public ReplyCriteria andReplyIdIsNotNull() {
		notNull("reply_id");
		return this;
	}
	
	public ReplyCriteria andReplyIdIsEmpty() {
		empty("reply_id");
		return this;
	}

	public ReplyCriteria andReplyIdIsNotEmpty() {
		notEmpty("reply_id");
		return this;
	}
       public ReplyCriteria andReplyIdEqualTo(java.lang.Integer value) {
          addCriterion("reply_id", value, ConditionMode.EQUAL, "replyId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andReplyIdNotEqualTo(java.lang.Integer value) {
          addCriterion("reply_id", value, ConditionMode.NOT_EQUAL, "replyId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andReplyIdGreaterThan(java.lang.Integer value) {
          addCriterion("reply_id", value, ConditionMode.GREATER_THEN, "replyId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andReplyIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("reply_id", value, ConditionMode.GREATER_EQUAL, "replyId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andReplyIdLessThan(java.lang.Integer value) {
          addCriterion("reply_id", value, ConditionMode.LESS_THEN, "replyId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andReplyIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("reply_id", value, ConditionMode.LESS_EQUAL, "replyId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andReplyIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("reply_id", value1, value2, ConditionMode.BETWEEN, "replyId", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andReplyIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("reply_id", value1, value2, ConditionMode.NOT_BETWEEN, "replyId", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andReplyIdIn(List<java.lang.Integer> values) {
          addCriterion("reply_id", values, ConditionMode.IN, "replyId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andReplyIdNotIn(List<java.lang.Integer> values) {
          addCriterion("reply_id", values, ConditionMode.NOT_IN, "replyId", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andContentIsNull() {
		isnull("content");
		return this;
	}
	
	public ReplyCriteria andContentIsNotNull() {
		notNull("content");
		return this;
	}
	
	public ReplyCriteria andContentIsEmpty() {
		empty("content");
		return this;
	}

	public ReplyCriteria andContentIsNotEmpty() {
		notEmpty("content");
		return this;
	}
       public ReplyCriteria andContentEqualTo(java.lang.Integer value) {
          addCriterion("content", value, ConditionMode.EQUAL, "content", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andContentNotEqualTo(java.lang.Integer value) {
          addCriterion("content", value, ConditionMode.NOT_EQUAL, "content", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andContentGreaterThan(java.lang.Integer value) {
          addCriterion("content", value, ConditionMode.GREATER_THEN, "content", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andContentGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("content", value, ConditionMode.GREATER_EQUAL, "content", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andContentLessThan(java.lang.Integer value) {
          addCriterion("content", value, ConditionMode.LESS_THEN, "content", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andContentLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("content", value, ConditionMode.LESS_EQUAL, "content", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andContentBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("content", value1, value2, ConditionMode.BETWEEN, "content", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andContentNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("content", value1, value2, ConditionMode.NOT_BETWEEN, "content", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andContentIn(List<java.lang.Integer> values) {
          addCriterion("content", values, ConditionMode.IN, "content", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andContentNotIn(List<java.lang.Integer> values) {
          addCriterion("content", values, ConditionMode.NOT_IN, "content", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public ReplyCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public ReplyCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public ReplyCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public ReplyCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andReplyTypeIsNull() {
		isnull("reply_type");
		return this;
	}
	
	public ReplyCriteria andReplyTypeIsNotNull() {
		notNull("reply_type");
		return this;
	}
	
	public ReplyCriteria andReplyTypeIsEmpty() {
		empty("reply_type");
		return this;
	}

	public ReplyCriteria andReplyTypeIsNotEmpty() {
		notEmpty("reply_type");
		return this;
	}
        public ReplyCriteria andReplyTypeLike(java.lang.String value) {
    	   addCriterion("reply_type", value, ConditionMode.FUZZY, "replyType", "java.lang.String", "Float");
    	   return this;
      }

      public ReplyCriteria andReplyTypeNotLike(java.lang.String value) {
          addCriterion("reply_type", value, ConditionMode.NOT_FUZZY, "replyType", "java.lang.String", "Float");
          return this;
      }
      public ReplyCriteria andReplyTypeEqualTo(java.lang.String value) {
          addCriterion("reply_type", value, ConditionMode.EQUAL, "replyType", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andReplyTypeNotEqualTo(java.lang.String value) {
          addCriterion("reply_type", value, ConditionMode.NOT_EQUAL, "replyType", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andReplyTypeGreaterThan(java.lang.String value) {
          addCriterion("reply_type", value, ConditionMode.GREATER_THEN, "replyType", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andReplyTypeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("reply_type", value, ConditionMode.GREATER_EQUAL, "replyType", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andReplyTypeLessThan(java.lang.String value) {
          addCriterion("reply_type", value, ConditionMode.LESS_THEN, "replyType", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andReplyTypeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("reply_type", value, ConditionMode.LESS_EQUAL, "replyType", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andReplyTypeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("reply_type", value1, value2, ConditionMode.BETWEEN, "replyType", "java.lang.String", "String");
    	  return this;
      }

      public ReplyCriteria andReplyTypeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("reply_type", value1, value2, ConditionMode.NOT_BETWEEN, "replyType", "java.lang.String", "String");
          return this;
      }
        
      public ReplyCriteria andReplyTypeIn(List<java.lang.String> values) {
          addCriterion("reply_type", values, ConditionMode.IN, "replyType", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andReplyTypeNotIn(List<java.lang.String> values) {
          addCriterion("reply_type", values, ConditionMode.NOT_IN, "replyType", "java.lang.String", "String");
          return this;
      }
	public ReplyCriteria andFromUidIsNull() {
		isnull("from_uid");
		return this;
	}
	
	public ReplyCriteria andFromUidIsNotNull() {
		notNull("from_uid");
		return this;
	}
	
	public ReplyCriteria andFromUidIsEmpty() {
		empty("from_uid");
		return this;
	}

	public ReplyCriteria andFromUidIsNotEmpty() {
		notEmpty("from_uid");
		return this;
	}
       public ReplyCriteria andFromUidEqualTo(java.lang.Integer value) {
          addCriterion("from_uid", value, ConditionMode.EQUAL, "fromUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andFromUidNotEqualTo(java.lang.Integer value) {
          addCriterion("from_uid", value, ConditionMode.NOT_EQUAL, "fromUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andFromUidGreaterThan(java.lang.Integer value) {
          addCriterion("from_uid", value, ConditionMode.GREATER_THEN, "fromUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andFromUidGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("from_uid", value, ConditionMode.GREATER_EQUAL, "fromUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andFromUidLessThan(java.lang.Integer value) {
          addCriterion("from_uid", value, ConditionMode.LESS_THEN, "fromUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andFromUidLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("from_uid", value, ConditionMode.LESS_EQUAL, "fromUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andFromUidBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("from_uid", value1, value2, ConditionMode.BETWEEN, "fromUid", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andFromUidNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("from_uid", value1, value2, ConditionMode.NOT_BETWEEN, "fromUid", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andFromUidIn(List<java.lang.Integer> values) {
          addCriterion("from_uid", values, ConditionMode.IN, "fromUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andFromUidNotIn(List<java.lang.Integer> values) {
          addCriterion("from_uid", values, ConditionMode.NOT_IN, "fromUid", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andToUidIsNull() {
		isnull("to_uid");
		return this;
	}
	
	public ReplyCriteria andToUidIsNotNull() {
		notNull("to_uid");
		return this;
	}
	
	public ReplyCriteria andToUidIsEmpty() {
		empty("to_uid");
		return this;
	}

	public ReplyCriteria andToUidIsNotEmpty() {
		notEmpty("to_uid");
		return this;
	}
       public ReplyCriteria andToUidEqualTo(java.lang.Integer value) {
          addCriterion("to_uid", value, ConditionMode.EQUAL, "toUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andToUidNotEqualTo(java.lang.Integer value) {
          addCriterion("to_uid", value, ConditionMode.NOT_EQUAL, "toUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andToUidGreaterThan(java.lang.Integer value) {
          addCriterion("to_uid", value, ConditionMode.GREATER_THEN, "toUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andToUidGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("to_uid", value, ConditionMode.GREATER_EQUAL, "toUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andToUidLessThan(java.lang.Integer value) {
          addCriterion("to_uid", value, ConditionMode.LESS_THEN, "toUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andToUidLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("to_uid", value, ConditionMode.LESS_EQUAL, "toUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andToUidBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("to_uid", value1, value2, ConditionMode.BETWEEN, "toUid", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andToUidNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("to_uid", value1, value2, ConditionMode.NOT_BETWEEN, "toUid", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andToUidIn(List<java.lang.Integer> values) {
          addCriterion("to_uid", values, ConditionMode.IN, "toUid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andToUidNotIn(List<java.lang.Integer> values) {
          addCriterion("to_uid", values, ConditionMode.NOT_IN, "toUid", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public ReplyCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public ReplyCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public ReplyCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public ReplyCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public ReplyCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public ReplyCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
}