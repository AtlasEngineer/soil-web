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
package com.soli.server.model.sql;

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
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
public class OperationRecordCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static OperationRecordCriteria create() {
		return new OperationRecordCriteria();
	}
	
	public static OperationRecordCriteria create(Column column) {
		OperationRecordCriteria that = new OperationRecordCriteria();
		that.add(column);
        return that;
    }

    public static OperationRecordCriteria create(String name, Object value) {
        return (OperationRecordCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("tr_operation_record", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public OperationRecordCriteria eq(String name, Object value) {
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
    public OperationRecordCriteria ne(String name, Object value) {
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

    public OperationRecordCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public OperationRecordCriteria notLike(String name, Object value) {
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
    public OperationRecordCriteria gt(String name, Object value) {
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
    public OperationRecordCriteria ge(String name, Object value) {
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
    public OperationRecordCriteria lt(String name, Object value) {
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
    public OperationRecordCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public OperationRecordCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public OperationRecordCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public OperationRecordCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public OperationRecordCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public OperationRecordCriteria add(Column column) {
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
		 
	public OperationRecordCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public OperationRecordCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public OperationRecordCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public OperationRecordCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public OperationRecordCriteria andIdEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordCriteria andIdNotEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordCriteria andIdGreaterThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordCriteria andIdGreaterThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordCriteria andIdLessThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordCriteria andIdLessThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordCriteria andIdBetween(java.lang.Long value1, java.lang.Long value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Long", "Float");
    	  return this;
      }

      public OperationRecordCriteria andIdNotBetween(java.lang.Long value1, java.lang.Long value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Long", "Float");
          return this;
      }
        
      public OperationRecordCriteria andIdIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordCriteria andIdNotIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Long", "Float");
          return this;
      }
	public OperationRecordCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public OperationRecordCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public OperationRecordCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public OperationRecordCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public OperationRecordCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public OperationRecordCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public OperationRecordCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public OperationRecordCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public OperationRecordCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public OperationRecordCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public OperationRecordCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public OperationRecordCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public OperationRecordCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public OperationRecordCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public OperationRecordCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public OperationRecordCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public OperationRecordCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public OperationRecordCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public OperationRecordCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "String");
    	   return this;
      }

      public OperationRecordCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "String");
          return this;
      }
      public OperationRecordCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public OperationRecordCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public OperationRecordCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public OperationRecordCriteria andUsernameIsNull() {
		isnull("username");
		return this;
	}
	
	public OperationRecordCriteria andUsernameIsNotNull() {
		notNull("username");
		return this;
	}
	
	public OperationRecordCriteria andUsernameIsEmpty() {
		empty("username");
		return this;
	}

	public OperationRecordCriteria andUsernameIsNotEmpty() {
		notEmpty("username");
		return this;
	}
        public OperationRecordCriteria andUsernameLike(java.lang.String value) {
    	   addCriterion("username", value, ConditionMode.FUZZY, "username", "java.lang.String", "String");
    	   return this;
      }

      public OperationRecordCriteria andUsernameNotLike(java.lang.String value) {
          addCriterion("username", value, ConditionMode.NOT_FUZZY, "username", "java.lang.String", "String");
          return this;
      }
      public OperationRecordCriteria andUsernameEqualTo(java.lang.String value) {
          addCriterion("username", value, ConditionMode.EQUAL, "username", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andUsernameNotEqualTo(java.lang.String value) {
          addCriterion("username", value, ConditionMode.NOT_EQUAL, "username", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andUsernameGreaterThan(java.lang.String value) {
          addCriterion("username", value, ConditionMode.GREATER_THEN, "username", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andUsernameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("username", value, ConditionMode.GREATER_EQUAL, "username", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andUsernameLessThan(java.lang.String value) {
          addCriterion("username", value, ConditionMode.LESS_THEN, "username", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andUsernameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("username", value, ConditionMode.LESS_EQUAL, "username", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andUsernameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("username", value1, value2, ConditionMode.BETWEEN, "username", "java.lang.String", "String");
    	  return this;
      }

      public OperationRecordCriteria andUsernameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("username", value1, value2, ConditionMode.NOT_BETWEEN, "username", "java.lang.String", "String");
          return this;
      }
        
      public OperationRecordCriteria andUsernameIn(List<java.lang.String> values) {
          addCriterion("username", values, ConditionMode.IN, "username", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andUsernameNotIn(List<java.lang.String> values) {
          addCriterion("username", values, ConditionMode.NOT_IN, "username", "java.lang.String", "String");
          return this;
      }
	public OperationRecordCriteria andUserIdIsNull() {
		isnull("user_id");
		return this;
	}
	
	public OperationRecordCriteria andUserIdIsNotNull() {
		notNull("user_id");
		return this;
	}
	
	public OperationRecordCriteria andUserIdIsEmpty() {
		empty("user_id");
		return this;
	}

	public OperationRecordCriteria andUserIdIsNotEmpty() {
		notEmpty("user_id");
		return this;
	}
       public OperationRecordCriteria andUserIdEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andUserIdNotEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.NOT_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andUserIdGreaterThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andUserIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andUserIdLessThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andUserIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andUserIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("user_id", value1, value2, ConditionMode.BETWEEN, "userId", "java.lang.Integer", "Float");
    	  return this;
      }

      public OperationRecordCriteria andUserIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("user_id", value1, value2, ConditionMode.NOT_BETWEEN, "userId", "java.lang.Integer", "Float");
          return this;
      }
        
      public OperationRecordCriteria andUserIdIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.IN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andUserIdNotIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.NOT_IN, "userId", "java.lang.Integer", "Float");
          return this;
      }
	public OperationRecordCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public OperationRecordCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public OperationRecordCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public OperationRecordCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
        public OperationRecordCriteria andTypeLike(java.lang.String value) {
    	   addCriterion("type", value, ConditionMode.FUZZY, "type", "java.lang.String", "Float");
    	   return this;
      }

      public OperationRecordCriteria andTypeNotLike(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_FUZZY, "type", "java.lang.String", "Float");
          return this;
      }
      public OperationRecordCriteria andTypeEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andTypeNotEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andTypeGreaterThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andTypeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andTypeLessThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andTypeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andTypeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.String", "String");
    	  return this;
      }

      public OperationRecordCriteria andTypeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.String", "String");
          return this;
      }
        
      public OperationRecordCriteria andTypeIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andTypeNotIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.String", "String");
          return this;
      }
	public OperationRecordCriteria andContentIsNull() {
		isnull("content");
		return this;
	}
	
	public OperationRecordCriteria andContentIsNotNull() {
		notNull("content");
		return this;
	}
	
	public OperationRecordCriteria andContentIsEmpty() {
		empty("content");
		return this;
	}

	public OperationRecordCriteria andContentIsNotEmpty() {
		notEmpty("content");
		return this;
	}
        public OperationRecordCriteria andContentLike(java.lang.String value) {
    	   addCriterion("content", value, ConditionMode.FUZZY, "content", "java.lang.String", "String");
    	   return this;
      }

      public OperationRecordCriteria andContentNotLike(java.lang.String value) {
          addCriterion("content", value, ConditionMode.NOT_FUZZY, "content", "java.lang.String", "String");
          return this;
      }
      public OperationRecordCriteria andContentEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andContentNotEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.NOT_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andContentGreaterThan(java.lang.String value) {
          addCriterion("content", value, ConditionMode.GREATER_THEN, "content", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andContentGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.GREATER_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andContentLessThan(java.lang.String value) {
          addCriterion("content", value, ConditionMode.LESS_THEN, "content", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andContentLessThanOrEqualTo(java.lang.String value) {
          addCriterion("content", value, ConditionMode.LESS_EQUAL, "content", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andContentBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("content", value1, value2, ConditionMode.BETWEEN, "content", "java.lang.String", "String");
    	  return this;
      }

      public OperationRecordCriteria andContentNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("content", value1, value2, ConditionMode.NOT_BETWEEN, "content", "java.lang.String", "String");
          return this;
      }
        
      public OperationRecordCriteria andContentIn(List<java.lang.String> values) {
          addCriterion("content", values, ConditionMode.IN, "content", "java.lang.String", "String");
          return this;
      }

      public OperationRecordCriteria andContentNotIn(List<java.lang.String> values) {
          addCriterion("content", values, ConditionMode.NOT_IN, "content", "java.lang.String", "String");
          return this;
      }
	public OperationRecordCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public OperationRecordCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public OperationRecordCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public OperationRecordCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public OperationRecordCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public OperationRecordCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public OperationRecordCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
}