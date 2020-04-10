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
 * @date 2020-04-10
 * @version 1.0
 * @since 1.0
 */
public class AnswerCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static AnswerCriteria create() {
		return new AnswerCriteria();
	}
	
	public static AnswerCriteria create(Column column) {
		AnswerCriteria that = new AnswerCriteria();
		that.add(column);
        return that;
    }

    public static AnswerCriteria create(String name, Object value) {
        return (AnswerCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_answer", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public AnswerCriteria eq(String name, Object value) {
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
    public AnswerCriteria ne(String name, Object value) {
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

    public AnswerCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public AnswerCriteria notLike(String name, Object value) {
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
    public AnswerCriteria gt(String name, Object value) {
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
    public AnswerCriteria ge(String name, Object value) {
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
    public AnswerCriteria lt(String name, Object value) {
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
    public AnswerCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public AnswerCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public AnswerCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public AnswerCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public AnswerCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public AnswerCriteria add(Column column) {
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
		 
	public AnswerCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public AnswerCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public AnswerCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public AnswerCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public AnswerCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public AnswerCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public AnswerCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public AnswerCriteria andQIdIsNull() {
		isnull("q_id");
		return this;
	}
	
	public AnswerCriteria andQIdIsNotNull() {
		notNull("q_id");
		return this;
	}
	
	public AnswerCriteria andQIdIsEmpty() {
		empty("q_id");
		return this;
	}

	public AnswerCriteria andQIdIsNotEmpty() {
		notEmpty("q_id");
		return this;
	}
       public AnswerCriteria andQIdEqualTo(java.lang.Integer value) {
          addCriterion("q_id", value, ConditionMode.EQUAL, "qId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andQIdNotEqualTo(java.lang.Integer value) {
          addCriterion("q_id", value, ConditionMode.NOT_EQUAL, "qId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andQIdGreaterThan(java.lang.Integer value) {
          addCriterion("q_id", value, ConditionMode.GREATER_THEN, "qId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andQIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("q_id", value, ConditionMode.GREATER_EQUAL, "qId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andQIdLessThan(java.lang.Integer value) {
          addCriterion("q_id", value, ConditionMode.LESS_THEN, "qId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andQIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("q_id", value, ConditionMode.LESS_EQUAL, "qId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andQIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("q_id", value1, value2, ConditionMode.BETWEEN, "qId", "java.lang.Integer", "Float");
    	  return this;
      }

      public AnswerCriteria andQIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("q_id", value1, value2, ConditionMode.NOT_BETWEEN, "qId", "java.lang.Integer", "Float");
          return this;
      }
        
      public AnswerCriteria andQIdIn(List<java.lang.Integer> values) {
          addCriterion("q_id", values, ConditionMode.IN, "qId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andQIdNotIn(List<java.lang.Integer> values) {
          addCriterion("q_id", values, ConditionMode.NOT_IN, "qId", "java.lang.Integer", "Float");
          return this;
      }
	public AnswerCriteria andUserIdIsNull() {
		isnull("user_id");
		return this;
	}
	
	public AnswerCriteria andUserIdIsNotNull() {
		notNull("user_id");
		return this;
	}
	
	public AnswerCriteria andUserIdIsEmpty() {
		empty("user_id");
		return this;
	}

	public AnswerCriteria andUserIdIsNotEmpty() {
		notEmpty("user_id");
		return this;
	}
       public AnswerCriteria andUserIdEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andUserIdNotEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.NOT_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andUserIdGreaterThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andUserIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andUserIdLessThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andUserIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andUserIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("user_id", value1, value2, ConditionMode.BETWEEN, "userId", "java.lang.Integer", "Float");
    	  return this;
      }

      public AnswerCriteria andUserIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("user_id", value1, value2, ConditionMode.NOT_BETWEEN, "userId", "java.lang.Integer", "Float");
          return this;
      }
        
      public AnswerCriteria andUserIdIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.IN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andUserIdNotIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.NOT_IN, "userId", "java.lang.Integer", "Float");
          return this;
      }
	public AnswerCriteria andContentIsNull() {
		isnull("content");
		return this;
	}
	
	public AnswerCriteria andContentIsNotNull() {
		notNull("content");
		return this;
	}
	
	public AnswerCriteria andContentIsEmpty() {
		empty("content");
		return this;
	}

	public AnswerCriteria andContentIsNotEmpty() {
		notEmpty("content");
		return this;
	}
        public AnswerCriteria andContentLike(java.lang.String value) {
    	   addCriterion("content", value, ConditionMode.FUZZY, "content", "java.lang.String", "Float");
    	   return this;
      }

      public AnswerCriteria andContentNotLike(java.lang.String value) {
          addCriterion("content", value, ConditionMode.NOT_FUZZY, "content", "java.lang.String", "Float");
          return this;
      }
      public AnswerCriteria andContentEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andContentNotEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.NOT_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andContentGreaterThan(java.lang.String value) {
          addCriterion("content", value, ConditionMode.GREATER_THEN, "content", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andContentGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.GREATER_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andContentLessThan(java.lang.String value) {
          addCriterion("content", value, ConditionMode.LESS_THEN, "content", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andContentLessThanOrEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.LESS_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andContentBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("content", value1, value2, ConditionMode.BETWEEN, "content", "java.lang.String", "String");
    	  return this;
      }

      public AnswerCriteria andContentNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("content", value1, value2, ConditionMode.NOT_BETWEEN, "content", "java.lang.String", "String");
          return this;
      }
        
      public AnswerCriteria andContentIn(List<java.lang.String> values) {
          addCriterion("content", values, ConditionMode.IN, "content", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andContentNotIn(List<java.lang.String> values) {
          addCriterion("content", values, ConditionMode.NOT_IN, "content", "java.lang.String", "String");
          return this;
      }
	public AnswerCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public AnswerCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public AnswerCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public AnswerCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public AnswerCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public AnswerCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public AnswerCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AnswerCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public AnswerCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public AnswerCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public AnswerCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public AnswerCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public AnswerCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public AnswerCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public AnswerCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public AnswerCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public AnswerCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public AnswerCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public AnswerCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public AnswerCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public AnswerCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public AnswerCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public AnswerCriteria andUnameIsNull() {
		isnull("uname");
		return this;
	}
	
	public AnswerCriteria andUnameIsNotNull() {
		notNull("uname");
		return this;
	}
	
	public AnswerCriteria andUnameIsEmpty() {
		empty("uname");
		return this;
	}

	public AnswerCriteria andUnameIsNotEmpty() {
		notEmpty("uname");
		return this;
	}
        public AnswerCriteria andUnameLike(java.lang.String value) {
    	   addCriterion("uname", value, ConditionMode.FUZZY, "uname", "java.lang.String", "String");
    	   return this;
      }

      public AnswerCriteria andUnameNotLike(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.NOT_FUZZY, "uname", "java.lang.String", "String");
          return this;
      }
      public AnswerCriteria andUnameEqualTo(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.EQUAL, "uname", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUnameNotEqualTo(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.NOT_EQUAL, "uname", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUnameGreaterThan(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.GREATER_THEN, "uname", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUnameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.GREATER_EQUAL, "uname", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUnameLessThan(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.LESS_THEN, "uname", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUnameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.LESS_EQUAL, "uname", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUnameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("uname", value1, value2, ConditionMode.BETWEEN, "uname", "java.lang.String", "String");
    	  return this;
      }

      public AnswerCriteria andUnameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("uname", value1, value2, ConditionMode.NOT_BETWEEN, "uname", "java.lang.String", "String");
          return this;
      }
        
      public AnswerCriteria andUnameIn(List<java.lang.String> values) {
          addCriterion("uname", values, ConditionMode.IN, "uname", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUnameNotIn(List<java.lang.String> values) {
          addCriterion("uname", values, ConditionMode.NOT_IN, "uname", "java.lang.String", "String");
          return this;
      }
	public AnswerCriteria andUrlIsNull() {
		isnull("url");
		return this;
	}
	
	public AnswerCriteria andUrlIsNotNull() {
		notNull("url");
		return this;
	}
	
	public AnswerCriteria andUrlIsEmpty() {
		empty("url");
		return this;
	}

	public AnswerCriteria andUrlIsNotEmpty() {
		notEmpty("url");
		return this;
	}
        public AnswerCriteria andUrlLike(java.lang.String value) {
    	   addCriterion("url", value, ConditionMode.FUZZY, "url", "java.lang.String", "String");
    	   return this;
      }

      public AnswerCriteria andUrlNotLike(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_FUZZY, "url", "java.lang.String", "String");
          return this;
      }
      public AnswerCriteria andUrlEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUrlNotEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUrlGreaterThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUrlLessThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("url", value1, value2, ConditionMode.BETWEEN, "url", "java.lang.String", "String");
    	  return this;
      }

      public AnswerCriteria andUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("url", value1, value2, ConditionMode.NOT_BETWEEN, "url", "java.lang.String", "String");
          return this;
      }
        
      public AnswerCriteria andUrlIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.IN, "url", "java.lang.String", "String");
          return this;
      }

      public AnswerCriteria andUrlNotIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.NOT_IN, "url", "java.lang.String", "String");
          return this;
      }
}