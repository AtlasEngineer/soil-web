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
 * @date 2020-04-27
 * @version 1.0
 * @since 1.0
 */
public class OpinionCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static OpinionCriteria create() {
		return new OpinionCriteria();
	}
	
	public static OpinionCriteria create(Column column) {
		OpinionCriteria that = new OpinionCriteria();
		that.add(column);
        return that;
    }

    public static OpinionCriteria create(String name, Object value) {
        return (OpinionCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_opinion", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public OpinionCriteria eq(String name, Object value) {
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
    public OpinionCriteria ne(String name, Object value) {
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

    public OpinionCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public OpinionCriteria notLike(String name, Object value) {
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
    public OpinionCriteria gt(String name, Object value) {
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
    public OpinionCriteria ge(String name, Object value) {
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
    public OpinionCriteria lt(String name, Object value) {
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
    public OpinionCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public OpinionCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public OpinionCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public OpinionCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public OpinionCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public OpinionCriteria add(Column column) {
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
		 
	public OpinionCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public OpinionCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public OpinionCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public OpinionCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public OpinionCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public OpinionCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public OpinionCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public OpinionCriteria andContentIsNull() {
		isnull("content");
		return this;
	}
	
	public OpinionCriteria andContentIsNotNull() {
		notNull("content");
		return this;
	}
	
	public OpinionCriteria andContentIsEmpty() {
		empty("content");
		return this;
	}

	public OpinionCriteria andContentIsNotEmpty() {
		notEmpty("content");
		return this;
	}
        public OpinionCriteria andContentLike(java.lang.String value) {
    	   addCriterion("content", value, ConditionMode.FUZZY, "content", "java.lang.String", "Float");
    	   return this;
      }

      public OpinionCriteria andContentNotLike(java.lang.String value) {
          addCriterion("content", value, ConditionMode.NOT_FUZZY, "content", "java.lang.String", "Float");
          return this;
      }
      public OpinionCriteria andContentEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andContentNotEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.NOT_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andContentGreaterThan(java.lang.String value) {
          addCriterion("content", value, ConditionMode.GREATER_THEN, "content", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andContentGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.GREATER_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andContentLessThan(java.lang.String value) {
          addCriterion("content", value, ConditionMode.LESS_THEN, "content", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andContentLessThanOrEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.LESS_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andContentBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("content", value1, value2, ConditionMode.BETWEEN, "content", "java.lang.String", "String");
    	  return this;
      }

      public OpinionCriteria andContentNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("content", value1, value2, ConditionMode.NOT_BETWEEN, "content", "java.lang.String", "String");
          return this;
      }
        
      public OpinionCriteria andContentIn(List<java.lang.String> values) {
          addCriterion("content", values, ConditionMode.IN, "content", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andContentNotIn(List<java.lang.String> values) {
          addCriterion("content", values, ConditionMode.NOT_IN, "content", "java.lang.String", "String");
          return this;
      }
	public OpinionCriteria andPhoneIsNull() {
		isnull("phone");
		return this;
	}
	
	public OpinionCriteria andPhoneIsNotNull() {
		notNull("phone");
		return this;
	}
	
	public OpinionCriteria andPhoneIsEmpty() {
		empty("phone");
		return this;
	}

	public OpinionCriteria andPhoneIsNotEmpty() {
		notEmpty("phone");
		return this;
	}
        public OpinionCriteria andPhoneLike(java.lang.String value) {
    	   addCriterion("phone", value, ConditionMode.FUZZY, "phone", "java.lang.String", "String");
    	   return this;
      }

      public OpinionCriteria andPhoneNotLike(java.lang.String value) {
          addCriterion("phone", value, ConditionMode.NOT_FUZZY, "phone", "java.lang.String", "String");
          return this;
      }
      public OpinionCriteria andPhoneEqualTo(java.lang.String value) {
          addCriterion("phone", value, ConditionMode.EQUAL, "phone", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andPhoneNotEqualTo(java.lang.String value) {
          addCriterion("phone", value, ConditionMode.NOT_EQUAL, "phone", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andPhoneGreaterThan(java.lang.String value) {
          addCriterion("phone", value, ConditionMode.GREATER_THEN, "phone", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andPhoneGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("phone", value, ConditionMode.GREATER_EQUAL, "phone", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andPhoneLessThan(java.lang.String value) {
          addCriterion("phone", value, ConditionMode.LESS_THEN, "phone", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andPhoneLessThanOrEqualTo(java.lang.String value) {
          addCriterion("phone", value, ConditionMode.LESS_EQUAL, "phone", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andPhoneBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("phone", value1, value2, ConditionMode.BETWEEN, "phone", "java.lang.String", "String");
    	  return this;
      }

      public OpinionCriteria andPhoneNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("phone", value1, value2, ConditionMode.NOT_BETWEEN, "phone", "java.lang.String", "String");
          return this;
      }
        
      public OpinionCriteria andPhoneIn(List<java.lang.String> values) {
          addCriterion("phone", values, ConditionMode.IN, "phone", "java.lang.String", "String");
          return this;
      }

      public OpinionCriteria andPhoneNotIn(List<java.lang.String> values) {
          addCriterion("phone", values, ConditionMode.NOT_IN, "phone", "java.lang.String", "String");
          return this;
      }
	public OpinionCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public OpinionCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public OpinionCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public OpinionCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public OpinionCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public OpinionCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public OpinionCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public OpinionCriteria andUserIdIsNull() {
		isnull("user_id");
		return this;
	}
	
	public OpinionCriteria andUserIdIsNotNull() {
		notNull("user_id");
		return this;
	}
	
	public OpinionCriteria andUserIdIsEmpty() {
		empty("user_id");
		return this;
	}

	public OpinionCriteria andUserIdIsNotEmpty() {
		notEmpty("user_id");
		return this;
	}
       public OpinionCriteria andUserIdEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andUserIdNotEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.NOT_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andUserIdGreaterThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andUserIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andUserIdLessThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andUserIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andUserIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("user_id", value1, value2, ConditionMode.BETWEEN, "userId", "java.lang.Integer", "Float");
    	  return this;
      }

      public OpinionCriteria andUserIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("user_id", value1, value2, ConditionMode.NOT_BETWEEN, "userId", "java.lang.Integer", "Float");
          return this;
      }
        
      public OpinionCriteria andUserIdIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.IN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OpinionCriteria andUserIdNotIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.NOT_IN, "userId", "java.lang.Integer", "Float");
          return this;
      }
}