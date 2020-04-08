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
	public ReplyCriteria andAIdIsNull() {
		isnull("a_id");
		return this;
	}
	
	public ReplyCriteria andAIdIsNotNull() {
		notNull("a_id");
		return this;
	}
	
	public ReplyCriteria andAIdIsEmpty() {
		empty("a_id");
		return this;
	}

	public ReplyCriteria andAIdIsNotEmpty() {
		notEmpty("a_id");
		return this;
	}
       public ReplyCriteria andAIdEqualTo(java.lang.Integer value) {
          addCriterion("a_id", value, ConditionMode.EQUAL, "aId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andAIdNotEqualTo(java.lang.Integer value) {
          addCriterion("a_id", value, ConditionMode.NOT_EQUAL, "aId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andAIdGreaterThan(java.lang.Integer value) {
          addCriterion("a_id", value, ConditionMode.GREATER_THEN, "aId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andAIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("a_id", value, ConditionMode.GREATER_EQUAL, "aId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andAIdLessThan(java.lang.Integer value) {
          addCriterion("a_id", value, ConditionMode.LESS_THEN, "aId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andAIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("a_id", value, ConditionMode.LESS_EQUAL, "aId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andAIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("a_id", value1, value2, ConditionMode.BETWEEN, "aId", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andAIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("a_id", value1, value2, ConditionMode.NOT_BETWEEN, "aId", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andAIdIn(List<java.lang.Integer> values) {
          addCriterion("a_id", values, ConditionMode.IN, "aId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andAIdNotIn(List<java.lang.Integer> values) {
          addCriterion("a_id", values, ConditionMode.NOT_IN, "aId", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andUserIdIsNull() {
		isnull("user_id");
		return this;
	}
	
	public ReplyCriteria andUserIdIsNotNull() {
		notNull("user_id");
		return this;
	}
	
	public ReplyCriteria andUserIdIsEmpty() {
		empty("user_id");
		return this;
	}

	public ReplyCriteria andUserIdIsNotEmpty() {
		notEmpty("user_id");
		return this;
	}
       public ReplyCriteria andUserIdEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUserIdNotEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.NOT_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUserIdGreaterThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUserIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUserIdLessThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUserIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUserIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("user_id", value1, value2, ConditionMode.BETWEEN, "userId", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andUserIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("user_id", value1, value2, ConditionMode.NOT_BETWEEN, "userId", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andUserIdIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.IN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUserIdNotIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.NOT_IN, "userId", "java.lang.Integer", "Float");
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
}