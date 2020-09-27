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
public class OperationRecordImgCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static OperationRecordImgCriteria create() {
		return new OperationRecordImgCriteria();
	}
	
	public static OperationRecordImgCriteria create(Column column) {
		OperationRecordImgCriteria that = new OperationRecordImgCriteria();
		that.add(column);
        return that;
    }

    public static OperationRecordImgCriteria create(String name, Object value) {
        return (OperationRecordImgCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("tr_operation_record_img", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public OperationRecordImgCriteria eq(String name, Object value) {
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
    public OperationRecordImgCriteria ne(String name, Object value) {
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

    public OperationRecordImgCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public OperationRecordImgCriteria notLike(String name, Object value) {
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
    public OperationRecordImgCriteria gt(String name, Object value) {
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
    public OperationRecordImgCriteria ge(String name, Object value) {
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
    public OperationRecordImgCriteria lt(String name, Object value) {
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
    public OperationRecordImgCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public OperationRecordImgCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public OperationRecordImgCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public OperationRecordImgCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public OperationRecordImgCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public OperationRecordImgCriteria add(Column column) {
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
		 
	public OperationRecordImgCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public OperationRecordImgCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public OperationRecordImgCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public OperationRecordImgCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public OperationRecordImgCriteria andIdEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordImgCriteria andIdNotEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordImgCriteria andIdGreaterThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordImgCriteria andIdGreaterThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordImgCriteria andIdLessThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordImgCriteria andIdLessThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordImgCriteria andIdBetween(java.lang.Long value1, java.lang.Long value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Long", "Float");
    	  return this;
      }

      public OperationRecordImgCriteria andIdNotBetween(java.lang.Long value1, java.lang.Long value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Long", "Float");
          return this;
      }
        
      public OperationRecordImgCriteria andIdIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Long", "Float");
          return this;
      }

      public OperationRecordImgCriteria andIdNotIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Long", "Float");
          return this;
      }
	public OperationRecordImgCriteria andUrlIsNull() {
		isnull("url");
		return this;
	}
	
	public OperationRecordImgCriteria andUrlIsNotNull() {
		notNull("url");
		return this;
	}
	
	public OperationRecordImgCriteria andUrlIsEmpty() {
		empty("url");
		return this;
	}

	public OperationRecordImgCriteria andUrlIsNotEmpty() {
		notEmpty("url");
		return this;
	}
        public OperationRecordImgCriteria andUrlLike(java.lang.String value) {
    	   addCriterion("url", value, ConditionMode.FUZZY, "url", "java.lang.String", "Float");
    	   return this;
      }

      public OperationRecordImgCriteria andUrlNotLike(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_FUZZY, "url", "java.lang.String", "Float");
          return this;
      }
      public OperationRecordImgCriteria andUrlEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public OperationRecordImgCriteria andUrlNotEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public OperationRecordImgCriteria andUrlGreaterThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public OperationRecordImgCriteria andUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public OperationRecordImgCriteria andUrlLessThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public OperationRecordImgCriteria andUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public OperationRecordImgCriteria andUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("url", value1, value2, ConditionMode.BETWEEN, "url", "java.lang.String", "String");
    	  return this;
      }

      public OperationRecordImgCriteria andUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("url", value1, value2, ConditionMode.NOT_BETWEEN, "url", "java.lang.String", "String");
          return this;
      }
        
      public OperationRecordImgCriteria andUrlIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.IN, "url", "java.lang.String", "String");
          return this;
      }

      public OperationRecordImgCriteria andUrlNotIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.NOT_IN, "url", "java.lang.String", "String");
          return this;
      }
	public OperationRecordImgCriteria andOperationIdIsNull() {
		isnull("operation_id");
		return this;
	}
	
	public OperationRecordImgCriteria andOperationIdIsNotNull() {
		notNull("operation_id");
		return this;
	}
	
	public OperationRecordImgCriteria andOperationIdIsEmpty() {
		empty("operation_id");
		return this;
	}

	public OperationRecordImgCriteria andOperationIdIsNotEmpty() {
		notEmpty("operation_id");
		return this;
	}
       public OperationRecordImgCriteria andOperationIdEqualTo(java.lang.Integer value) {
          addCriterion("operation_id", value, ConditionMode.EQUAL, "operationId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordImgCriteria andOperationIdNotEqualTo(java.lang.Integer value) {
          addCriterion("operation_id", value, ConditionMode.NOT_EQUAL, "operationId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordImgCriteria andOperationIdGreaterThan(java.lang.Integer value) {
          addCriterion("operation_id", value, ConditionMode.GREATER_THEN, "operationId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordImgCriteria andOperationIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("operation_id", value, ConditionMode.GREATER_EQUAL, "operationId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordImgCriteria andOperationIdLessThan(java.lang.Integer value) {
          addCriterion("operation_id", value, ConditionMode.LESS_THEN, "operationId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordImgCriteria andOperationIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("operation_id", value, ConditionMode.LESS_EQUAL, "operationId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordImgCriteria andOperationIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("operation_id", value1, value2, ConditionMode.BETWEEN, "operationId", "java.lang.Integer", "Float");
    	  return this;
      }

      public OperationRecordImgCriteria andOperationIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("operation_id", value1, value2, ConditionMode.NOT_BETWEEN, "operationId", "java.lang.Integer", "Float");
          return this;
      }
        
      public OperationRecordImgCriteria andOperationIdIn(List<java.lang.Integer> values) {
          addCriterion("operation_id", values, ConditionMode.IN, "operationId", "java.lang.Integer", "Float");
          return this;
      }

      public OperationRecordImgCriteria andOperationIdNotIn(List<java.lang.Integer> values) {
          addCriterion("operation_id", values, ConditionMode.NOT_IN, "operationId", "java.lang.Integer", "Float");
          return this;
      }
}