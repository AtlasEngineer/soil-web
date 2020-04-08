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
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
public class QuestionCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static QuestionCriteria create() {
		return new QuestionCriteria();
	}
	
	public static QuestionCriteria create(Column column) {
		QuestionCriteria that = new QuestionCriteria();
		that.add(column);
        return that;
    }

    public static QuestionCriteria create(String name, Object value) {
        return (QuestionCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_question", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public QuestionCriteria eq(String name, Object value) {
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
    public QuestionCriteria ne(String name, Object value) {
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

    public QuestionCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public QuestionCriteria notLike(String name, Object value) {
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
    public QuestionCriteria gt(String name, Object value) {
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
    public QuestionCriteria ge(String name, Object value) {
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
    public QuestionCriteria lt(String name, Object value) {
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
    public QuestionCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public QuestionCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public QuestionCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public QuestionCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public QuestionCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public QuestionCriteria add(Column column) {
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
		 
	public QuestionCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public QuestionCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public QuestionCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public QuestionCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public QuestionCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public QuestionCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public QuestionCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public QuestionCriteria andUserIdIsNull() {
		isnull("user_id");
		return this;
	}
	
	public QuestionCriteria andUserIdIsNotNull() {
		notNull("user_id");
		return this;
	}
	
	public QuestionCriteria andUserIdIsEmpty() {
		empty("user_id");
		return this;
	}

	public QuestionCriteria andUserIdIsNotEmpty() {
		notEmpty("user_id");
		return this;
	}
       public QuestionCriteria andUserIdEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andUserIdNotEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.NOT_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andUserIdGreaterThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andUserIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andUserIdLessThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andUserIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andUserIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("user_id", value1, value2, ConditionMode.BETWEEN, "userId", "java.lang.Integer", "Float");
    	  return this;
      }

      public QuestionCriteria andUserIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("user_id", value1, value2, ConditionMode.NOT_BETWEEN, "userId", "java.lang.Integer", "Float");
          return this;
      }
        
      public QuestionCriteria andUserIdIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.IN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andUserIdNotIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.NOT_IN, "userId", "java.lang.Integer", "Float");
          return this;
      }
	public QuestionCriteria andContentIsNull() {
		isnull("content");
		return this;
	}
	
	public QuestionCriteria andContentIsNotNull() {
		notNull("content");
		return this;
	}
	
	public QuestionCriteria andContentIsEmpty() {
		empty("content");
		return this;
	}

	public QuestionCriteria andContentIsNotEmpty() {
		notEmpty("content");
		return this;
	}
        public QuestionCriteria andContentLike(java.lang.String value) {
    	   addCriterion("content", value, ConditionMode.FUZZY, "content", "java.lang.String", "Float");
    	   return this;
      }

      public QuestionCriteria andContentNotLike(java.lang.String value) {
          addCriterion("content", value, ConditionMode.NOT_FUZZY, "content", "java.lang.String", "Float");
          return this;
      }
      public QuestionCriteria andContentEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andContentNotEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.NOT_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andContentGreaterThan(java.lang.String value) {
          addCriterion("content", value, ConditionMode.GREATER_THEN, "content", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andContentGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.GREATER_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andContentLessThan(java.lang.String value) {
          addCriterion("content", value, ConditionMode.LESS_THEN, "content", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andContentLessThanOrEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.LESS_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andContentBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("content", value1, value2, ConditionMode.BETWEEN, "content", "java.lang.String", "String");
    	  return this;
      }

      public QuestionCriteria andContentNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("content", value1, value2, ConditionMode.NOT_BETWEEN, "content", "java.lang.String", "String");
          return this;
      }
        
      public QuestionCriteria andContentIn(List<java.lang.String> values) {
          addCriterion("content", values, ConditionMode.IN, "content", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andContentNotIn(List<java.lang.String> values) {
          addCriterion("content", values, ConditionMode.NOT_IN, "content", "java.lang.String", "String");
          return this;
      }
	public QuestionCriteria andImageIsNull() {
		isnull("image");
		return this;
	}
	
	public QuestionCriteria andImageIsNotNull() {
		notNull("image");
		return this;
	}
	
	public QuestionCriteria andImageIsEmpty() {
		empty("image");
		return this;
	}

	public QuestionCriteria andImageIsNotEmpty() {
		notEmpty("image");
		return this;
	}
        public QuestionCriteria andImageLike(java.lang.String value) {
    	   addCriterion("image", value, ConditionMode.FUZZY, "image", "java.lang.String", "String");
    	   return this;
      }

      public QuestionCriteria andImageNotLike(java.lang.String value) {
          addCriterion("image", value, ConditionMode.NOT_FUZZY, "image", "java.lang.String", "String");
          return this;
      }
      public QuestionCriteria andImageEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andImageNotEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.NOT_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andImageGreaterThan(java.lang.String value) {
          addCriterion("image", value, ConditionMode.GREATER_THEN, "image", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andImageGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.GREATER_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andImageLessThan(java.lang.String value) {
          addCriterion("image", value, ConditionMode.LESS_THEN, "image", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andImageLessThanOrEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.LESS_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andImageBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("image", value1, value2, ConditionMode.BETWEEN, "image", "java.lang.String", "String");
    	  return this;
      }

      public QuestionCriteria andImageNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("image", value1, value2, ConditionMode.NOT_BETWEEN, "image", "java.lang.String", "String");
          return this;
      }
        
      public QuestionCriteria andImageIn(List<java.lang.String> values) {
          addCriterion("image", values, ConditionMode.IN, "image", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andImageNotIn(List<java.lang.String> values) {
          addCriterion("image", values, ConditionMode.NOT_IN, "image", "java.lang.String", "String");
          return this;
      }
	public QuestionCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public QuestionCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public QuestionCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public QuestionCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public QuestionCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public QuestionCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public QuestionCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public QuestionCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public QuestionCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public QuestionCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public QuestionCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public QuestionCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public QuestionCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public QuestionCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public QuestionCriteria andTitleIsNull() {
		isnull("title");
		return this;
	}
	
	public QuestionCriteria andTitleIsNotNull() {
		notNull("title");
		return this;
	}
	
	public QuestionCriteria andTitleIsEmpty() {
		empty("title");
		return this;
	}

	public QuestionCriteria andTitleIsNotEmpty() {
		notEmpty("title");
		return this;
	}
        public QuestionCriteria andTitleLike(java.lang.String value) {
    	   addCriterion("title", value, ConditionMode.FUZZY, "title", "java.lang.String", "String");
    	   return this;
      }

      public QuestionCriteria andTitleNotLike(java.lang.String value) {
          addCriterion("title", value, ConditionMode.NOT_FUZZY, "title", "java.lang.String", "String");
          return this;
      }
      public QuestionCriteria andTitleEqualTo(java.lang.String value) {
          addCriterion("title", value, ConditionMode.EQUAL, "title", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andTitleNotEqualTo(java.lang.String value) {
          addCriterion("title", value, ConditionMode.NOT_EQUAL, "title", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andTitleGreaterThan(java.lang.String value) {
          addCriterion("title", value, ConditionMode.GREATER_THEN, "title", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andTitleGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("title", value, ConditionMode.GREATER_EQUAL, "title", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andTitleLessThan(java.lang.String value) {
          addCriterion("title", value, ConditionMode.LESS_THEN, "title", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andTitleLessThanOrEqualTo(java.lang.String value) {
          addCriterion("title", value, ConditionMode.LESS_EQUAL, "title", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andTitleBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("title", value1, value2, ConditionMode.BETWEEN, "title", "java.lang.String", "String");
    	  return this;
      }

      public QuestionCriteria andTitleNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("title", value1, value2, ConditionMode.NOT_BETWEEN, "title", "java.lang.String", "String");
          return this;
      }
        
      public QuestionCriteria andTitleIn(List<java.lang.String> values) {
          addCriterion("title", values, ConditionMode.IN, "title", "java.lang.String", "String");
          return this;
      }

      public QuestionCriteria andTitleNotIn(List<java.lang.String> values) {
          addCriterion("title", values, ConditionMode.NOT_IN, "title", "java.lang.String", "String");
          return this;
      }
	public QuestionCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public QuestionCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public QuestionCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public QuestionCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
       public QuestionCriteria andTypeEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andTypeNotEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andTypeGreaterThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andTypeGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andTypeLessThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andTypeLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andTypeBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.Integer", "Float");
    	  return this;
      }

      public QuestionCriteria andTypeNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.Integer", "Float");
          return this;
      }
        
      public QuestionCriteria andTypeIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andTypeNotIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.Integer", "Float");
          return this;
      }
	public QuestionCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public QuestionCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public QuestionCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public QuestionCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public QuestionCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public QuestionCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public QuestionCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public QuestionCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
}